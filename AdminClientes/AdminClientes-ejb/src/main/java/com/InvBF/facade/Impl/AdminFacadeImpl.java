/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.FormularioFacadeLocal;
import com.InvBF.EntityFacade.PerfilFacadeLocal;
import com.InvBF.EntityFacade.TareasFacadeLocal;
import com.InvBF.EntityFacade.UsuarioFacadeLocal;
import com.InvBF.EntityFacade.VistaFacadeLocal;
import com.InvBF.util.EncryptUtil;
import com.invbf.adminclientesapi.entity.Formulario;
import com.invbf.adminclientesapi.entity.Perfil;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.entity.Vista;
import com.invbf.adminclientesapi.exceptions.NombreUsuarioExistenteException;
import com.invbf.adminclientesapi.exceptions.PerfilExistenteException;
import com.invbf.adminclientesapi.facade.AdminFacade;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
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
    UsuarioFacadeLocal usuarioFacadeLocal;
    @EJB
    PerfilFacadeLocal perfilFacadeLocal;
    @EJB
    FormularioFacadeLocal formularioFacadeLocal;
    @EJB
    VistaFacadeLocal vistaFacadeLocal;
    @EJB
    TareasFacadeLocal tareasFacadeLocal;

    @Override
    public List<Usuario> findAllUsuarios() {
        return usuarioFacadeLocal.findAll();
    }

    @Override
    public void deleteUsuarios(Usuario elemento) {
        List<Tarea> tareas = tareasFacadeLocal.findAll();
        for(Tarea t : tareas){
            if(t.getUsuarioList().contains(elemento)){
                t.getUsuarioList().remove(elemento);
                tareasFacadeLocal.edit(t);
            }
        }
        usuarioFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarUsuarios(Usuario elemento) throws NombreUsuarioExistenteException{
        if (elemento.getIdUsuario() == null) {
            try {
                elemento.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
            } catch (NoSuchAlgorithmException ex) {
            }
            if(usuarioFacadeLocal.findByNombreUsuario(elemento.getNombreUsuario())!=null){
                throw new NombreUsuarioExistenteException();
            }
            usuarioFacadeLocal.create(elemento);
            return false;
        } else {
            try {
                elemento.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
            } catch (NoSuchAlgorithmException ex) {
            }
            usuarioFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deletePerfiles(Perfil elemento) {
        perfilFacadeLocal.remove(elemento);
    }

    @Override
    public List<Perfil> findAllPerfiles() {
        return perfilFacadeLocal.findAll();
    }

    @Override
    public boolean guardarPerfiles(Perfil elemento) throws PerfilExistenteException{
        if (elemento.getIdPerfil() == null) {
            if(perfilFacadeLocal.findByNombre(elemento.getNombre())!=null){
                throw new PerfilExistenteException();
            }
            perfilFacadeLocal.create(elemento);
            
            return false;
        } else {
            for(Formulario f : elemento.getFormulariosList()){
                f = formularioFacadeLocal.find(f.getIdFormulario());
            }
            for(Vista l : elemento.getVistasList()){
                l = vistaFacadeLocal.find(l.getIdVista());
            }
            perfilFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Formulario> findAllFormularios() {
        return formularioFacadeLocal.findAll();
    }

    @Override
    public void deleteFormularios(Formulario elemento) {
        formularioFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarFormularios(Formulario elemento) {
        if (elemento.getIdFormulario() == null) {
            formularioFacadeLocal.create(elemento);
            return false;
        } else {
            formularioFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Vista> findAllVistas() {
        return vistaFacadeLocal.findAll();
    }

    @Override
    public void deleteVistas(Vista elemento) {
        vistaFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarVistas(Vista elemento) {
        if (elemento.getIdVista() == null) {
            vistaFacadeLocal.create(elemento);
            return false;
        } else {
            vistaFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public Perfil findPerfil(Integer idPerfil) {
        return perfilFacadeLocal.find(idPerfil);
    }

    @Override
    public List<Usuario> findAllUsuariosHostess() {
        List<Usuario> usuarios = findAllUsuarios();
        Vista v = findVistasByNombre("ManejadorEventosHostess");
        for (Iterator<Usuario> it = usuarios.iterator(); it.hasNext();) {
            Usuario usuario = it.next();
            if(!usuario.getIdPerfil().getVistasList().contains(v)){
                it.remove();
            }
        }
        return usuarios;
    }

    @Override
    public Usuario findUsuarios(Integer idUsuario) {
        return usuarioFacadeLocal.find(idUsuario);
    }

    @Override
    public void agregarTareaUsuarios(Usuario s, Tarea elemento) {
        Usuario usuario = usuarioFacadeLocal.find(s.getIdUsuario());
        if(usuario.getTareasList()==null){
            usuario.setTareasList(new ArrayList<Tarea>(0));
        }
        usuario.getTareasList().add(elemento);
        usuarioFacadeLocal.edit(usuario);
    }

    @Override
    public Vista findVistasByNombre(String nombre) {
        return vistaFacadeLocal.findByNombre(nombre);
    }

    @Override
    public Formulario findFormularioByAccionAndTabla(String accion, String tabla) {
      return formularioFacadeLocal.findByAccionAndTabla(accion, tabla);
    }


}
