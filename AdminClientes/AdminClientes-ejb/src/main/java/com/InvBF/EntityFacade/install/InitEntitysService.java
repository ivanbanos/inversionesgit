/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade.install;

import com.InvBF.EntityFacade.FormulariosFacadeLocal;
import com.InvBF.EntityFacade.PerfilesFacadeLocal;
import com.InvBF.EntityFacade.UsuariosFacadeLocal;
import com.InvBF.EntityFacade.VistasFacadeLocal;
import com.InvBF.util.EncryptUtil;
import com.invbf.adminclientesapi.Formularios;
import com.invbf.adminclientesapi.Perfiles;
import com.invbf.adminclientesapi.Usuarios;
import com.invbf.adminclientesapi.Vistas;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    
   

    /**
     * 1. dependiendo de un parametro de configuracion el sistema debe
     * determinar que entidades debe crear (produccion=solo lo basico,
     * desarrollo=todo lo que se especifique como escenarios de prueba)
     */
    @PostConstruct
    private void init() {
        initEntitysHelper(); 
    }

    /**
     * metodo encargado de la inicializacion de las entidades que van a estar
     * disponibles en el sistema, para ello depende del valor que indique el
     * estado del proyecto (desarrollo, produccion)
     */
    private void initEntitysHelper() {
        if(usuariosFacadeLocal.count()==0){
            try {
                LOGGER.info("Iniciando variables");
                Formularios formulario = new Formularios();
                formulario.setTabla("Usuarios");
                formulario.setAccion("Create");
                formulariosFacadeLocal.create(formulario);
                Vistas vista = new Vistas();
                vista.setNombreVista("AtributosSistemasView");
                vistasFacadeLocal.create(vista);
                Perfiles perfil = new Perfiles();
                perfil.setNombre("Administrador");
                perfil.setFormulariosList(new ArrayList<Formularios>());
                perfil.getFormulariosList().add(formulario);
                perfil.setVistasList(new ArrayList<Vistas>());
                perfil.getVistasList().add(vista);
                perfilesFacadeLocal.create(perfil);
                Usuarios usuario = new Usuarios();
                usuario.setNombreUsuario("Administrador");
                usuario.setContrasena(EncryptUtil.encryptPassword("123456"));
                usuario.setIdPerfil(perfil);
                usuariosFacadeLocal.create(usuario);
                LOGGER.info("Usuario admin inicial creado");
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error(ex);
            }
        }
    }
}
