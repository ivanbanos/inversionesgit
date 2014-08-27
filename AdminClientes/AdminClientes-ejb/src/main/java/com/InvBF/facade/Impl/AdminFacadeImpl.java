/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.FormulariosFacadeLocal;
import com.InvBF.EntityFacade.PerfilesFacadeLocal;
import com.InvBF.EntityFacade.UsuariosFacadeLocal;
import com.InvBF.EntityFacade.VistasFacadeLocal;
import com.InvBF.util.EncryptUtil;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.exceptions.NombreUsuarioExistenteException;
import com.invbf.adminclientesapi.exceptions.PerfilExistenteException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ideacentre
 */
@Stateless
public class AdminFacadeImpl implements AdminFacade {

    @EJB
    UsuariosFacadeLocal usuariosFacadeLocal;
    @EJB
    PerfilesFacadeLocal perfilesFacadeLocal;
    @EJB
    FormulariosFacadeLocal formulariosFacadeLocal;
    @EJB
    VistasFacadeLocal vistasFacadeLocal;

    @Override
    public List<Usuarios> findAllUsuarios() {
        return usuariosFacadeLocal.findAll();
    }

    @Override
    public void deleteUsuarios(Usuarios elemento) {
        usuariosFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarUsuarios(Usuarios elemento) throws NombreUsuarioExistenteException{
        if (elemento.getIdUsuario() == null) {
            try {
                elemento.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
            } catch (NoSuchAlgorithmException ex) {
            }
            if(usuariosFacadeLocal.findByNombreUsuario(elemento.getNombreUsuario())!=null){
                throw new NombreUsuarioExistenteException();
            }
            usuariosFacadeLocal.create(elemento);
            return false;
        } else {
            try {
                elemento.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
            } catch (NoSuchAlgorithmException ex) {
            }
            usuariosFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deletePerfiles(Perfiles elemento) {
        perfilesFacadeLocal.remove(elemento);
    }

    @Override
    public List<Perfiles> findAllPerfiles() {
        return perfilesFacadeLocal.findAll();
    }

    @Override
    public boolean guardarPerfiles(Perfiles elemento) throws PerfilExistenteException{
        if (elemento.getIdPerfil() == null) {
            if(perfilesFacadeLocal.findByNombre(elemento.getNombre())!=null){
                throw new PerfilExistenteException();
            }
            perfilesFacadeLocal.create(elemento);
            
            return false;
        } else {
            for(Formularios f : elemento.getFormulariosList()){
                f = formulariosFacadeLocal.find(f.getIdFormulario());
            }
            for(Vistas l : elemento.getVistasList()){
                l = vistasFacadeLocal.find(l.getIdVista());
            }
            perfilesFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Formularios> findAllFormularios() {
        return formulariosFacadeLocal.findAll();
    }

    @Override
    public void deleteFormularios(Formularios elemento) {
        formulariosFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarFormularios(Formularios elemento) {
        if (elemento.getIdFormulario() == null) {
            formulariosFacadeLocal.create(elemento);
            return false;
        } else {
            formulariosFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Vistas> findAllVistas() {
        return vistasFacadeLocal.findAll();
    }

    @Override
    public void deleteVistas(Vistas elemento) {
        vistasFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarVistas(Vistas elemento) {
        if (elemento.getIdVista() == null) {
            vistasFacadeLocal.create(elemento);
            return false;
        } else {
            vistasFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public Perfiles findPerfil(Integer idPerfil) {
        return perfilesFacadeLocal.find(idPerfil);
    }

    @Override
    public List<Usuarios> findAllUsuariosHostess() {
        return usuariosFacadeLocal.findAllHostess();
    }

    @Override
    public Usuarios findUsuarios(Integer idUsuario) {
        return usuariosFacadeLocal.find(idUsuario);
    }

    @Override
    public void agregarEventoUsuarios(Usuarios s, Eventos elemento) {
        Usuarios usuario = usuariosFacadeLocal.find(s.getIdUsuario());
        if(usuario.getEventosList()==null){
            usuario.setEventosList(new ArrayList<Eventos>(0));
        }
        usuario.getEventosList().add(elemento);
    }

    @Override
    public Vistas findVistasByNombre(String nombre) {
        return vistasFacadeLocal.findByNombre(nombre);
    }

    @Override
    public Formularios findFormularioByAccionAndTabla(String accion, String tabla) {
      return formulariosFacadeLocal.findByAccionAndTabla(accion, tabla);
    }


}
