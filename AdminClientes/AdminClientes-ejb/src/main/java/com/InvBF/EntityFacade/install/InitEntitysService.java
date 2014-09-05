/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade.install;

import com.InvBF.EntityFacade.ConfiguracionesFacade;
import com.InvBF.EntityFacade.ConfiguracionesFacadeLocal;
import com.InvBF.EntityFacade.EstadosclienteFacadeLocal;
import com.InvBF.EntityFacade.FormulariosFacadeLocal;
import com.InvBF.EntityFacade.PerfilesFacadeLocal;
import com.InvBF.EntityFacade.UsuariosFacadeLocal;
import com.InvBF.EntityFacade.VistasFacadeLocal;
import com.InvBF.util.EncryptUtil;
import com.invbf.adminclientesapi.entity.Configuraciones;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.entity.Vistas;
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
    private UsuariosFacadeLocal usuariosFacadeLocal;
    @EJB
    private PerfilesFacadeLocal perfilesFacadeLocal;
    @EJB
    private FormulariosFacadeLocal formulariosFacadeLocal;
    @EJB
    private VistasFacadeLocal vistasFacadeLocal;
    @EJB
    private ConfiguracionesFacadeLocal configuracionesFacadeLocal;
    @EJB
    private EstadosclienteFacadeLocal estadosclienteFacadeLocal;

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
            Perfiles perfil = new Perfiles();
            perfil.setNombre("Administrador");
            perfil.setFormulariosList(new ArrayList<Formularios>());
            perfil.setVistasList(new ArrayList<Vistas>());
            perfilesFacadeLocal.create(perfil);

            Formularios formulario;
            formulario = new Formularios(null, "Usuarios", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Usuarios", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Usuarios", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Perfiles", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Perfiles", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Perfiles", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Formularios", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Formularios", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Formularios", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Vistas", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Vistas", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Vistas", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);

            Vistas vista;
            vista = new Vistas(null, "Usuarios");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "Perfiles");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "Vistas");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "Formularios");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "AtributosSistema");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "ConfiguracionesGenerales");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "Casinos");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "Categorias");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "EstadosClientes");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "TipoJuego");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "Atributos");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "AtributosMarketing");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "ManejadorEventosMarketing");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);

            perfilesFacadeLocal.edit(perfil);

            Usuarios usuario = new Usuarios();
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
            Perfiles perfil = new Perfiles();
            perfil.setNombre("Marketing");
            perfil.setFormulariosList(new ArrayList<Formularios>());
            perfil.setVistasList(new ArrayList<Vistas>());

            Formularios formulario;
            formulario = new Formularios(null, "Atributos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Atributos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Atributos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Casinos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Casinos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Casinos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Eventos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Eventos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Eventos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Categorias", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Categorias", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Categorias", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "TiposJuegos", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "TiposJuegos", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "TiposJuegos", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "EstadosCliente", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "EstadosCliente", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "EstadosCliente", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Clientes", "crear");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Clientes", "actualizar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Clientes", "eliminar");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);
            formulario = new Formularios(null, "Eventos", "listaclientes");
            formulariosFacadeLocal.create(formulario);
            perfil.getFormulariosList().add(formulario);

            Vistas vista;
            vista = new Vistas(null, "Clientes");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            vista = new Vistas(null, "Eventos");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);
            

            perfilesFacadeLocal.create(perfil);

            Usuarios usuario = new Usuarios();
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
            Perfiles perfil = new Perfiles();
            perfil.setNombre("Hostess");
            perfil.setFormulariosList(new ArrayList<Formularios>());
            perfil.setVistasList(new ArrayList<Vistas>());

            Vistas vista;
            vista = new Vistas(null, "ManejadorEventosHostess");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);

            perfilesFacadeLocal.create(perfil);

            Usuarios usuario = new Usuarios();
            usuario.setNombreUsuario("hostess");
            usuario.setContrasena(EncryptUtil.encryptPassword("123456"));
            usuario.setIdPerfil(perfil);
            usuariosFacadeLocal.create(usuario);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InitEntitysService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void congfiguracionesIniciales() {
        Configuraciones configuracion = new Configuraciones();
        configuracion.setNombre("CantidadClientes");
        configuracion.setValor("4");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuraciones();
        configuracion.setNombre("correo");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuraciones();
        configuracion.setNombre("contrasena");
        configuracionesFacadeLocal.create(configuracion);
        
        configuracion = new Configuraciones();
        configuracion.setNombre("host");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuraciones();
        configuracion.setNombre("username");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuraciones();
        configuracion.setNombre("protocol");
        configuracionesFacadeLocal.create(configuracion);
        configuracion = new Configuraciones();
        configuracion.setNombre("port");
        configuracionesFacadeLocal.create(configuracion);
        
        
        configuracion = new Configuraciones();
        configuracion.setNombre("paginacion");
        configuracion.setValor("10");


        configuracionesFacadeLocal.create(configuracion);
        Estadoscliente estadoscliente = new Estadoscliente();
        estadoscliente.setNombre("Inicial");
        estadosclienteFacadeLocal.create(estadoscliente);
        estadoscliente = new Estadoscliente();
        estadoscliente.setNombre("En revision");
        estadosclienteFacadeLocal.create(estadoscliente);
    }

    private void crearPerfilGerente() {
        try {
            Perfiles perfil = new Perfiles();
            perfil.setNombre("Gerente");
            perfil.setFormulariosList(new ArrayList<Formularios>());
            perfil.setVistasList(new ArrayList<Vistas>());

            Vistas vista;
            vista = new Vistas(null, "Reportes");
            vistasFacadeLocal.create(vista);
            perfil.getVistasList().add(vista);

            perfilesFacadeLocal.create(perfil);

            Usuarios usuario = new Usuarios();
            usuario.setNombreUsuario("gerente");
            usuario.setContrasena(EncryptUtil.encryptPassword("123456"));
            usuario.setIdPerfil(perfil);
            usuariosFacadeLocal.create(usuario);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InitEntitysService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
