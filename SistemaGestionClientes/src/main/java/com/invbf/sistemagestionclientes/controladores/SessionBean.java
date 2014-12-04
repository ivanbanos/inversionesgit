/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.controladores;

import com.invbf.sistemagestionclientes.dao.ConfiguracionDao;
import com.invbf.sistemagestionclientes.entity.Configuracion;
import com.invbf.sistemagestionclientes.entity.Evento;
import com.invbf.sistemagestionclientes.entity.Formulario;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.entity.Vista;
import com.invbf.sistemagestionclientes.exceptions.ClavesNoConcuerdanException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioInactivoException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioNoConectadoException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioNoExisteException;
import com.invbf.sistemagestionclientes.exceptions.UsuarioSinAccesoalSistemaException;
import com.invbf.sistemagestionclientes.facade.AdminFacade;
import com.invbf.sistemagestionclientes.facade.HostessFacade;
import com.invbf.sistemagestionclientes.facade.ManagerUserFacade;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.facade.SystemFacade;
import com.invbf.sistemagestionclientes.facade.impl.AdminFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.HostessFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.ManagerUserFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.MarketingUserFacadeImpl;
import com.invbf.sistemagestionclientes.facade.impl.SystemFacadeImpl;
import com.invbf.sistemagestionclientes.observer.Observer;
import com.invbf.sistemagestionclientes.observer.Subject;
import com.invbf.sistemagestionclientes.util.FacesUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ideacentre
 */
@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable, Subject {

    SystemFacade sessionFacade;
    AdminFacade adminFacade;
    MarketingUserFacade marketingUserFacade;
    HostessFacade hostessFacade;
    ManagerUserFacade managerUserFacade;
    private Usuario usuario;//Almacena el objeto usuario de la session
    private HashMap<String, Object> Attributes;
    private List<Observer> observers;
    private int paginacion;
    private String active;

    /**
     * Creates a new instance of SessionFlowumiUtil
     */
    public SessionBean() {
    }

    @PostConstruct
    public void init() {
        sessionFacade = new SystemFacadeImpl();
        adminFacade = new AdminFacadeImpl();
        marketingUserFacade = new MarketingUserFacadeImpl();
        hostessFacade = new HostessFacadeImpl();
        managerUserFacade = new ManagerUserFacadeImpl();
        usuario = new Usuario();
        Attributes = new HashMap<String, Object>();
        observers = new ArrayList<Observer>();
        Configuracion configuracion = sessionFacade.getConfiguracionByName("paginacion");
        if (configuracion == null) {
            paginacion = 10;
        } else {
            paginacion = Integer.parseInt(configuracion.getValor());
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String Conectar() {
        try {
            usuario = sessionFacade.iniciarSession(usuario);
            
            sessionFacade.registrarlog(null, null, "Inicio de sesion del usuario "+usuario.getNombreUsuario(), usuario);
            active = "inicio";
            return "/pages/index.xhtml";
        } catch (ClavesNoConcuerdanException ex) {
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioNoExisteException ex) {
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioNoConectadoException ex) {
            FacesUtil.addErrorMessage("Usuario no conectado", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioInactivoException ex) {
            FacesUtil.addErrorMessage("Usuario inactivo", ex.getMessage());
            usuario = new Usuario();
        } catch (UsuarioSinAccesoalSistemaException ex) {
            FacesUtil.addErrorMessage("Usuario sin acceso", ex.getMessage());
            usuario = new Usuario();
        }
        return "";
    }

    public String Desconectar() {
        usuario = new Usuario();
        return "/pages/InicioSession.xhtml";
    }

    public boolean perfilViewMatch(String vista) {
        if (usuario == null || usuario.getIdPerfil() == null || usuario.getIdPerfil().getVistasList() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        } else {
            List<Vista> vistasUsuario = usuario.getIdPerfil().getVistasList();
            for (Vista v : vistasUsuario) {
                if (v.getNombreVista().equals(vista)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean perfilFormMatch(String tabla, String accion) {
        if (usuario == null || usuario.getIdPerfil() == null || usuario.getIdPerfil().getFormulariosList() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        } else {
            for (Formulario f : usuario.getIdPerfil().getFormulariosList()) {
                if (f.es(tabla + accion)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void actualizarUsuario() {
        usuario = sessionFacade.actualizarUsuario(usuario);
    }

    public HashMap<String, Object> getAttributes() {
        return Attributes;
    }

    public void setAttributes(HashMap<String, Object> Attributes) {
        this.Attributes = Attributes;
    }

    public void goInicio() {
        try {
            if (usuario.getIdPerfil().getNombre().equals("Administrador")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioAdministrador.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Marketing")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioMarketing.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Hostess")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioHostess.xhtml");
            } else if (usuario.getIdPerfil().getNombre().equals("Gerente")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioGerente.xhtml");
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObserver(String tabla) {
        Iterator<Observer> i = observers.iterator();
        while (i.hasNext()) {
            Observer o = i.next();
            if (o == null) {
                i.remove();
            } else {
                if (tabla.equals("Perfiles") && o instanceof CrudUsuariosBean) {
                    o.update();
                }
            }

        }
    }

    public void registrarlog(String accion, String tabla, String mensaje) {
        sessionFacade.registrarlog(accion, tabla, mensaje, usuario);
    }

    public int getPaginacion() {
        return paginacion;
    }

    public void setPaginacion(int paginacion) {
        this.paginacion = paginacion;
    }

    public void checkUsuarioConectado() {
        if (usuario == null
                || usuario.getIdUsuario() == null
                || usuario.getIdUsuario() <= 0) {
            try {
                System.out.println("No lo coje");
                Desconectar();
                FacesUtil.addErrorMessage("Session finalizada", "No existe usuario conectado");
                FacesContext.getCurrentInstance().getExternalContext().redirect("InicioSession.xhtml");
            } catch (IOException ex) {
            }
        }
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public boolean isActive(String pestaña) {
        if (active == null) {
            return false;
        }
        return active.equals(pestaña);
    }

    public boolean isNotActive(String pestaña) {
        if (active == null) {
            return true;
        }
        return !active.equals(pestaña);
    }

    public String go(String page) {
        if (page.equals("inicio")) {
            active = "inicio";
            return "/pages/index.xhtml";
        } else if (page.equals("AtributosSistema")) {
            active = "configuracion";
            return "/pages/AdministradorAtributosSistema.xhtml";
        } else if (page.equals("AtributosMarketing")) {
            active = "configuracion";
            return "/pages/AdministradorAtributosMarketing.xhtml";
        } else if (page.equals("ConfiguracionesGenerales")) {
            active = "configuracion";
            return "/pages/ConfiguracionesGenerales.xhtml";
        } else if (page.equals("clientes")) {
            active = "clientes";
            return "/pages/clientes.xhtml";
        } else if (page.equals("eventos")) {
            active = "eventos";
            return "/pages/eventos.xhtml";
        } else if (page.equals("eventoshostess")) {
            active = "eventoshostess";
            return "/pages/tareasHostess.xhtml";
        } else if (page.equals("reportes")) {
            active = "reportes";
            return "/pages/Reportes.xhtml";
        } else if (page.equals("cuenta")) {
            active = "cuenta";
            return "/pages/CuentaUsuarios.xhtml";
        } else if (page.equals("tareas")) {
            active = "tareas";
            return "/pages/tareas.xhtml";
        } else if (page.equals("notificaciones")) {
            active = "notificaciones";
            return "/pages/notificaciones.xhtml";
        } else if (page.equals("logs")) {
            active = "logs";
            return "/pages/logs.xhtml";
        } 
        return "/pages/InicioSession.xhtml";
    }

    public void checkEstadoTarea(Tarea tarea) {
        try {
            Calendar fechainicio = Calendar.getInstance();
            Calendar fechafinal = Calendar.getInstance();
            
            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            fechainicio.setTime(tarea.getFechaInicio());
            fechafinal.setTime(tarea.getFechaFinalizacion());
            tarea.setEstado("POR INICIAR");
            if (fechainicio.before(nowDate)) {
                tarea.setEstado("ACTIVO");
            }
            if (fechafinal.before(nowDate)) {
                tarea.setEstado("VENCIDO");
            }
        } catch (ParseException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void obtenerUsuario(Integer idUsuario) {
        usuario = sessionFacade.getUsuario(idUsuario);
    }

    public SystemFacade getSessionFacade() {
        return sessionFacade;
    }

    public void setSessionFacade(SystemFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }

    public AdminFacade getAdminFacade() {
        return adminFacade;
    }

    public void setAdminFacade(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    public ManagerUserFacade getManagerUserFacade() {
        return managerUserFacade;
    }

    public void setManagerUserFacade(ManagerUserFacade managerUserFacade) {
        this.managerUserFacade = managerUserFacade;
    }

    public MarketingUserFacade getMarketingUserFacade() {
        return marketingUserFacade;
    }

    public void setMarketingUserFacade(MarketingUserFacade marketingUserFacade) {
        this.marketingUserFacade = marketingUserFacade;
    }

    public HostessFacade getHostessFacade() {
        return hostessFacade;
    }

    public void setHostessFacade(HostessFacade hostessFacade) {
        this.hostessFacade = hostessFacade;
    }

    byte[] getImage(Integer integer) {
        FTPClient client = new FTPClient();
        byte[] bytesArray = null;
        Evento ev = marketingUserFacade.findEvento(integer);
        if (ev != null && ev.getImagen() != null && !ev.getImagen().equals("")) {
            String remoteFile2 = ev.getImagen();
            try {
                String sFTP = ConfiguracionDao.findByNombre("FTP").getValor();
                String sUser = ConfiguracionDao.findByNombre("FTPuser").getValor();
                String sPassword = ConfiguracionDao.findByNombre("FTPpassword").getValor();

                client.connect(sFTP);
                boolean login = client.login(sUser, sPassword);

                int reply = client.getReplyCode();

                System.out.println("Respuesta recibida de conexión FTP:" + reply);

                if (FTPReply.isPositiveCompletion(reply)) {
                    System.out.println("Conectado Satisfactoriamente");
                } else {
                    System.out.println("Imposible conectarse al servidor");
                }
                client.changeWorkingDirectory("/home/easl4284/public_html/imagenes");
                client.setFileType(FTP.BINARY_FILE_TYPE);

                InputStream inputStream = client.retrieveFileStream(remoteFile2);
                bytesArray = IOUtils.toByteArray(inputStream);

                boolean success = client.completePendingCommand();
                if (success) {
                    System.out.println("File has been downloaded successfully.");
                }
                inputStream.close();

            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            } catch (IOException ex) {

                System.out.println(ex);
            } finally {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException ex) {

                    System.out.println(ex);
                }
            }

        }
        return bytesArray;
    }
}
