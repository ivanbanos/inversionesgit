/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade.impl;

import com.invbf.sistemagestionclientes.dao.AccionDao;
import com.invbf.sistemagestionclientes.dao.AtributoDao;
import com.invbf.sistemagestionclientes.dao.CasinoDao;
import com.invbf.sistemagestionclientes.dao.CategoriaDao;
import com.invbf.sistemagestionclientes.dao.ClienteDao;
import com.invbf.sistemagestionclientes.dao.ClienteatributoDao;
import com.invbf.sistemagestionclientes.dao.ConfiguracionDao;
import com.invbf.sistemagestionclientes.dao.EventoDao;
import com.invbf.sistemagestionclientes.dao.TareasDao;
import com.invbf.sistemagestionclientes.dao.TipoDocumentoDao;
import com.invbf.sistemagestionclientes.dao.TipoJuegoDao;
import com.invbf.sistemagestionclientes.dao.TipostareasDao;
import com.invbf.sistemagestionclientes.dao.UsuarioDao;
import com.invbf.sistemagestionclientes.entity.Accion;
import com.invbf.sistemagestionclientes.entity.Atributo;
import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Categoria;
import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Clienteatributo;
import com.invbf.sistemagestionclientes.entity.Configuracion;
import com.invbf.sistemagestionclientes.entity.Evento;
import com.invbf.sistemagestionclientes.entity.Listasclientestareas;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.TipoDocumento;
import com.invbf.sistemagestionclientes.entity.TipoJuego;
import com.invbf.sistemagestionclientes.entity.Tipotarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.facade.MarketingUserFacade;
import com.invbf.sistemagestionclientes.util.DBConnection;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ideacentre
 */
@Stateless
public class MarketingUserFacadeImpl implements MarketingUserFacade {

    @Override
    public List<Cliente> findAllClientes() {
        return ClienteDao.findAll();
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return CategoriaDao.findAll();
    }

    @Override
    public void deleteCategorias(Categoria elemento) {
        CategoriaDao.remove(elemento);
    }

    @Override
    public boolean guardarCategorias(Categoria elemento) {
        if (elemento.getIdCategorias() == null) {
            CategoriaDao.create(elemento);
            return false;
        } else {
            CategoriaDao.edit(elemento);
            return true;
        }

    }

    @Override
    public List<Atributo> findAllAtributos() {
        return AtributoDao.findAll();
    }

    @Override
    public void deleteAtributos(Atributo elemento) {
        List<Clienteatributo> listaca = ClienteatributoDao.findByAtributo(elemento);
        List<Cliente> clientes = ClienteDao.findAll();
        for (Cliente c : clientes) {
            c.getClientesatributosList().remove(new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo()));
        }

        if (listaca != null) {
            for (Clienteatributo ca : listaca) {

                ClienteatributoDao.remove(ca);
            }
        }
        AtributoDao.remove(elemento);
    }

    @Override
    public boolean guardarAtributos(Atributo elemento) {
        if (elemento.getIdAtributo() == null) {
            AtributoDao.create(elemento);
            List<Cliente> clientes = ClienteDao.findAll();
            List<Clienteatributo> clientesatributos = new ArrayList<Clienteatributo>();
            for (Cliente c : clientes) {
                Clienteatributo ca = new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo());
                ca.setAtributos(elemento);
                ca.setClientes(c);
                ClienteatributoDao.create(ca);
                clientesatributos.add(ca);
            }
            elemento.setClientesatributosList(clientesatributos);
            AtributoDao.edit(elemento);
            return false;
        } else {
            AtributoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTiposjuegos(TipoJuego elemento) {
        TipoJuegoDao.remove(elemento);
    }

    @Override
    public List<TipoJuego> findAllTiposjuegos() {
        return TipoJuegoDao.findAll();
    }

    @Override
    public boolean guardarTiposjuegos(TipoJuego elemento) {
        if (elemento.getIdTipoJuego() == null) {
            TipoJuegoDao.create(elemento);
            return true;
        } else {
            TipoJuegoDao.edit(elemento);
            return true;
        }

    }

    @Override
    public void deleteCasinos(Casino elemento) {
        CasinoDao.remove(elemento);
    }

    @Override
    public boolean guardarCasinos(Casino elemento) {
        if (elemento.getIdCasino() == null) {
            CasinoDao.create(elemento);
            return false;
        } else {
            CasinoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Casino> findAllCasinos() {
        return CasinoDao.findAll();
    }

    @Override
    public List<Evento> findAllEventos() {
        return EventoDao.findAll();
    }

    @Override
    public void deleteEventos(Evento elemento) {
        File imagen = null;
        StringBuilder sb = new StringBuilder();
        Configuracion urlImages = ConfiguracionDao.findByNombre("urlImages");
        sb.append(urlImages.getValor()).append(System.getProperty("file.separator"))
                .append("images").append(System.getProperty("file.separator"))
                .append("inversiones").append(System.getProperty("file.separator")).append(elemento.getImagen());
        imagen = new File(sb.toString());
        if (imagen.exists()) {
            imagen.delete();
        }
        EventoDao.remove(elemento);
    }

    @Override
    public void deleteClientes(Cliente elemento) {
        List<Clienteatributo> listaca = ClienteatributoDao.findByCliente(elemento);
        if (listaca != null) {
            for (Clienteatributo ca : listaca) {
                ClienteatributoDao.remove(ca);
            }
        }
        ClienteDao.remove(elemento);
    }

    @Override
    public Cliente guardarClientes(Cliente elemento) {
        if (elemento.getIdCliente() == null) {
            ClienteDao.create(elemento);
            return elemento;
        } else {
            ClienteDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public List<TipoJuego> getTiposJuegosNoClientes(Integer idCliente) {
        Cliente cliente = ClienteDao.find(idCliente);
        List<TipoJuego> tiposjuego = TipoJuegoDao.findAll();
        Iterator<TipoJuego> iter = tiposjuego.iterator();
        while (iter.hasNext()) {
            if (iter.next().getClientesList().contains(cliente)) {
                iter.remove();
            }
        }
        return tiposjuego;
    }

    @Override
    public Cliente findCliente(Integer integer) {
        return ClienteDao.find(integer);
    }

    @Override
    public Evento findEvento(Integer integer) {
        return EventoDao.find(integer);
    }

    @Override
    public Evento guardarEventos(Evento elemento) {
        if (elemento.getIdEvento() == null) {

            EventoDao.create(elemento);
            return elemento;
        } else {
            EventoDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public void guardarImagen(byte[] contents, String fileName) {
        FTPClient client = new FTPClient();
        try {
             System.out.println("entra");
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
            System.out.println(client.printWorkingDirectory());
            client.changeWorkingDirectory("/home/easl4284/public_html/imagenes");
            System.out.println(client.printWorkingDirectory());
            client.setFileType(FTP.BINARY_FILE_TYPE);
            
            client.deleteFile(fileName);
            
            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new ByteArrayInputStream(contents));//Ruta del archivo para enviar
            client.enterLocalPassiveMode();
            client.storeFile(fileName , buffIn);//Ruta completa de alojamiento en el FTP

            buffIn.close(); //Cerrar envio de arcivos al FTP

        } catch (FileNotFoundException ex) {
            System.out.println("filenotfound");
        } catch (IOException ex) {
            System.out.println("error");
        } finally {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public List<Accion> findAllAcciones() {
        return AccionDao.findAll();
    }

    @Override
    public void deleteAccion(Accion elemento) {
        AccionDao.remove(elemento);
    }

    @Override
    public boolean guardarAccion(Accion elemento) {
        if (elemento.getIdAccion() == null) {

            AccionDao.create(elemento);
            return false;
        } else {
            AccionDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Tipotarea> findAllTipotarea() {
        return TipostareasDao.findAll();
    }

    @Override
    public boolean guardarTipotarea(Tipotarea elemento) {
        if (elemento.getIdTipotarea() == null) {

            TipostareasDao.create(elemento);
            for (Accion a : elemento.getAccionList()) {
                a = AccionDao.find(a.getIdAccion());
                if (a.getTipostareasList() == null) {
                    a.setTipostareasList(new ArrayList<Tipotarea>());
                }
                a.getTipostareasList().add(elemento);
                AccionDao.edit(a);
            }
            return false;
        } else {
            TipostareasDao.edit(elemento);
            for (Accion a : elemento.getAccionList()) {
                a = AccionDao.find(a.getIdAccion());
                if (a.getTipostareasList() == null) {
                    a.setTipostareasList(new ArrayList<Tipotarea>());
                }
                a.getTipostareasList().add(elemento);
                AccionDao.edit(a);
            }
            return true;
        }
    }

    @Override
    public void deleteTipotarea(Tipotarea elemento) {
        TipostareasDao.remove(elemento);
    }

    @Override
    public void deleteTarea(Tarea tarea) {
        List<Usuario> usuarios = UsuarioDao.findAll();
        for (Usuario u : usuarios) {
            if (u.getTareasList().contains(tarea)) {
                u.getTareasList().remove(tarea);
                UsuarioDao.edit(u);
            }
        }
        TareasDao.remove(tarea);
    }

    @Override
    public Tarea guardarTarea(Tarea elemento) {
        if (elemento.getIdTarea() == null) {
            for (int i = 0; i < elemento.getUsuarioList().size(); i++) {
                Usuario u = UsuarioDao.find(elemento.getUsuarioList().get(i).getIdUsuario());
                elemento.getUsuarioList().remove(i);
                if (u.getTareasList() == null) {
                    u.setTareasList(new ArrayList<Tarea>());
                }
                u.getTareasList().add(elemento);
                elemento.getUsuarioList().add(i, u);
                UsuarioDao.edit(u);
            }
            TareasDao.create(elemento);
            return elemento;
        } else {
            for (int i = 0; i < elemento.getUsuarioList().size(); i++) {
                Usuario u = UsuarioDao.find(elemento.getUsuarioList().get(i).getIdUsuario());
                elemento.getUsuarioList().remove(i);
                if (u.getTareasList() == null) {
                    u.setTareasList(new ArrayList<Tarea>());
                }
                u.getTareasList().add(elemento);
                elemento.getUsuarioList().add(i, u);
                UsuarioDao.edit(u);
            }
            TareasDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public Accion findByNombreAccion(String nombre) {
        return AccionDao.findByNombreAccion(nombre);
    }

    @Override
    public List<Tarea> findAllTareas() {
        return TareasDao.findAll();
    }

    @Override
    public Tarea findTarea(Integer integer) {
        Tarea tarea = TareasDao.find(integer);
        return tarea;
    }

    @Override
    public List<TipoDocumento> findAllTipoDocumentos() {
        return TipoDocumentoDao.findAll();
    }

    @Override
    public boolean guardarTipoDocumentos(TipoDocumento elemento) {
        if (elemento.getIdTipoDocumento() == null) {

            TipoDocumentoDao.create(elemento);
            return false;
        } else {
            TipoDocumentoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTipoDocumentos(TipoDocumento elemento) {
        TipoDocumentoDao.remove(elemento);
    }

    @Override
    public String findNombreAccion(Integer idAccion) {
        Accion a = AccionDao.find(idAccion);
        if (a == null) {
            return "";
        } else {
            return a.getNombre();
        }
    }

    @Override
    public Accion findAccion(Integer accion) {
        return AccionDao.find(accion);
    }

    @Override
    public Date getLCTFecha(Listasclientestareas lct) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Date fechaAtencion = null;
        DBConnection dBConnection = new DBConnection();
        try {
            if (dBConnection.getConnection() != null) {
                String query = "SELECT fechaAtencion FROM listasclientestareas WHERE idCliente=? AND idTarea=?;";
                st = dBConnection.getConnection().prepareStatement(query);
                st.setInt(1, lct.getListasclientestareasPK().getIdCliente());
                st.setInt(2, lct.getListasclientestareasPK().getIdTarea());
                rs = st.executeQuery();
                if (rs.next()) {
                    Calendar cal1 = Calendar.getInstance();
                    if (rs.getTimestamp("fechaAtencion") != null) {
                        cal1.setTime(rs.getTimestamp("fechaAtencion"));
                        cal1.set(Calendar.HOUR, cal1.get(Calendar.HOUR) - 5);
                        fechaAtencion = cal1.getTime();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HostessFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dBConnection.shutdown();
                if (st != null) {
                    st.close();
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return fechaAtencion;
    }
}
