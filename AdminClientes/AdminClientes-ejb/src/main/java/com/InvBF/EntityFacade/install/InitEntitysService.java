/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade.install;


import com.InvBF.EntityFacade.AccionFacadeLocal;
import com.InvBF.EntityFacade.AtributoFacadeLocal;
import com.InvBF.EntityFacade.CasinoFacadeLocal;
import com.InvBF.EntityFacade.CategoriaFacadeLocal;
import com.InvBF.EntityFacade.ConfiguracionFacadeLocal;
import com.InvBF.EntityFacade.FormularioFacadeLocal;
import com.InvBF.EntityFacade.PerfilFacadeLocal;
import com.InvBF.EntityFacade.UsuarioFacadeLocal;
import com.InvBF.EntityFacade.VistaFacadeLocal;
import com.InvBF.util.EncryptUtil;
import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Configuracion;
import com.invbf.adminclientesapi.entity.Formulario;
import com.invbf.adminclientesapi.entity.Perfil;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.entity.Vista;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author jssepulveda
 */
@Singleton
@Startup
public class InitEntitysService {

    private static final org.apache.log4j.Logger LOGGER =
            org.apache.log4j.Logger.getLogger(InitEntitysService.class);
    // @Inject @Config("status.proyect")
    @EJB
    private UsuarioFacadeLocal usuariosFacadeLocal;
    @EJB
    private PerfilFacadeLocal perfilesFacadeLocal;
    @EJB
    private FormularioFacadeLocal formulariosFacadeLocal;
    @EJB
    private VistaFacadeLocal vistasFacadeLocal;
    @EJB
    private ConfiguracionFacadeLocal configuracionesFacadeLocal;
    @EJB
    private CasinoFacadeLocal casinoFacadeLocal;
    @EJB
    private CategoriaFacadeLocal categoriaFacadeLocal;
    @EJB
    private AtributoFacadeLocal atributoFacadeLocal;
    @EJB
    private AccionFacadeLocal accionFacadeLocal;
    

    /**
     * 1. dependiendo de un parametro de configuracion el sistema debe
     * determinar que entidades debe crear (produccion=solo lo basico,
     * desarrollo=todo lo que se especifique como escenarios de prueba)
     */
    @PostConstruct
    public void init() {
        initEntitysHelper();
    }

    /**
     * metodo encargado de la inicializacion de las entidades que van a estar
     * disponibles en el sistema, para ello depende del valor que indique el
     * estado del proyecto (desarrollo, produccion)
     */
    private void initEntitysHelper() {
        if (usuariosFacadeLocal.count() == 0) {
            crearPerfilAdmin();
            crearPerfilMarketing();
            crearPerfilHostess();
            crearPerfilGerente();
            congfiguracionesIniciales();
        }



    }

    private void crearPerfilAdmin() {
        try {
            Perfil perfil = new Perfil();
            perfil.setNombre("Administrador");
            perfil.setFormulariosList(new ArrayList<Formulario>());
            perfil.setVistasList(new ArrayList<Vista>());
            perfilesFacadeLocal.create(perfil);

            Formulario formulario;
            formulario = new Formulario(null, "Usuarios", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Usuarios", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Usuarios", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Perfiles", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Perfiles", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Perfiles", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Formularios", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Formularios", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Formularios", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Vistas", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Vistas", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Vistas", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Tipotareas", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Tipotareas", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Tipotareas", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);

            Vista vista;
            vista = new Vista(null, "Usuarios");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Perfiles");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Vistas");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Formularios");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "AtributosSistema");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "ConfiguracionesGenerales");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Casinos");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Categorias");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Acciones");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "TipoJuego");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Atributos");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Tipotareas");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "AtributosMarketing");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "ManejadorEventosMarketing");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);

            perfilesFacadeLocal.edit(perfil);

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("admin");
            usuario.setContrasena(EncryptUtil.encryptPassword("123456"));
            usuario.setIdPerfil(perfil);
            usuariosFacadeLocal.create(usuario);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InitEntitysService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearPerfilMarketing() {
        try {
            Perfil perfil = new Perfil();
            perfil.setNombre("Marketing");
            perfil.setFormulariosList(new ArrayList<Formulario>());
            perfil.setVistasList(new ArrayList<Vista>());

            Formulario formulario;
            formulario = new Formulario(null, "Atributos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Atributos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Atributos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Casinos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Casinos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Casinos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Eventos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Eventos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Eventos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Tareas", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Tareas", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Tareas", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Categorias", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Categorias", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Categorias", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "TiposJuegos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "TiposJuegos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "TiposJuegos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Acciones", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Acciones", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Acciones", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Clientes", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Clientes", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Clientes", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formulario(null, "Eventos", "listaclientes");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);

            Vista vista;
            vista = new Vista(null, "Clientes");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Eventos");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vista(null, "Tareas");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            

            perfilesFacadeLocal.create(perfil);

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("marketing");
            usuario.setContrasena(EncryptUtil.encryptPassword("123456"));
            usuario.setIdPerfil(perfil);
            usuariosFacadeLocal.create(usuario);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InitEntitysService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearPerfilHostess() {
        try {
            Perfil perfil = new Perfil();
            perfil.setNombre("Hostess");
            perfil.setFormulariosList(new ArrayList<Formulario>());
            perfil.setVistasList(new ArrayList<Vista>());

            Vista vista;
            vista = new Vista(null, "ManejadorEventosHostess");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);

            perfilesFacadeLocal.create(perfil);

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("hostess");
            usuario.setContrasena(EncryptUtil.encryptPassword("123456"));
            usuario.setIdPerfil(perfil);
            usuariosFacadeLocal.create(usuario);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InitEntitysService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void congfiguracionesIniciales() {
        Configuracion configuracion = new Configuracion();
        configuracion.setNombre("CantidadClientes");
        configuracion.setValor("4");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuracion();
        configuracion.setNombre("correo");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuracion();
        configuracion.setNombre("contrasena");
        configuracionesFacadeLocal.create(configuracion);
        
        configuracion = new Configuracion();
        configuracion.setNombre("host");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuracion();
        configuracion.setNombre("username");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuracion();
        configuracion.setNombre("protocol");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuracion();
        configuracion.setNombre("port");
        configuracionesFacadeLocal.create(configuracion);
        
        
        configuracion = new Configuracion();
        configuracion.setNombre("paginacion");
        configuracion.setValor("10");
        configuracionesFacadeLocal.create(configuracion);
        
        
        Casino c = new Casino();
        c.setNombre("Masters Royale Casino");
        c.setDireccion("Bocagrande");
        casinoFacadeLocal.create(c);
        c = new Casino();
        c.setNombre("Texas Luxury Casino - San Fernando");
        c.setDireccion("San Fernando");
        casinoFacadeLocal.create(c);
        c = new Casino();
        c.setNombre("Texas Luxury Casino - Centro");
        c.setDireccion("Centro");
        casinoFacadeLocal.create(c);
        c = new Casino();
        c.setNombre("Texas Luxury Casino - Barranquilla");
        c.setDireccion("Barranquilla");
        casinoFacadeLocal.create(c);
        
        Categoria cat = new Categoria();
        cat.setNombre("VIP");
        categoriaFacadeLocal.create(cat);
        cat = new Categoria();
        cat.setNombre("A");
        categoriaFacadeLocal.create(cat);
        cat = new Categoria();
        cat.setNombre("B");
        categoriaFacadeLocal.create(cat);
        cat = new Categoria();
        cat.setNombre("C");
        categoriaFacadeLocal.create(cat);
        cat = new Categoria();
        cat.setNombre("INTERNACIONAL");
        categoriaFacadeLocal.create(cat);
        
        Atributo a = new Atributo();
        a.setNombre("Dirección");
        a.setTipoDato("Text");
        atributoFacadeLocal.create(a);
        a = new Atributo();
        a.setNombre("Ciudad");
        a.setTipoDato("Text");
        atributoFacadeLocal.create(a);
        a = new Atributo();
        a.setNombre("Pais");
        a.setTipoDato("Text");
        atributoFacadeLocal.create(a);
        a = new Atributo();
        a.setNombre("Cupo de Fidelización");
        a.setTipoDato("Text");
        atributoFacadeLocal.create(a);
        
        Accion ac = new Accion();
        ac.setNombre("ASISTIRÁ");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("DATOS ACTUALIZADOS");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("EN REVISIÓN");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("ENVIADO");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("INFORMACIÓN ENTREGADA");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("INICIAL");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("TELEFONO ERRONEO");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("NO ASISTIRÁ");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("NO CONTESTO");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("NO ENVIADO");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("NO LE INTERESA");
        accionFacadeLocal.create(ac);
        ac = new Accion();
        ac.setNombre("TALVEZ");
        accionFacadeLocal.create(ac);
                
    }

    private void crearPerfilGerente() {
        try {
            Perfil perfil = new Perfil();
            perfil.setNombre("Gerente");
            perfil.setFormulariosList(new ArrayList<Formulario>());
            perfil.setVistasList(new ArrayList<Vista>());

            Vista vista;
            vista = new Vista(null, "Reportes");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);

            perfilesFacadeLocal.create(perfil);

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("gerente");
            usuario.setContrasena(EncryptUtil.encryptPassword("123456"));
            usuario.setIdPerfil(perfil);
            usuariosFacadeLocal.create(usuario);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InitEntitysService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
