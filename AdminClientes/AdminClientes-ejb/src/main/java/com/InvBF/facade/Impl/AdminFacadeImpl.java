/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.FormulariosFacadeLocal;
import com.InvBF.EntityFacade.PerfilesFacadeLocal;
import com.InvBF.EntityFacade.UsuariosFacadeLocal;
import com.InvBF.EntityFacade.VistasFacadeLocal;
import com.invbf.adminclientesapi.entity.Formularios;
import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.entity.Vistas;
import com.invbf.adminclientesapi.facade.AdminFacade;
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
    public void guardarUsuarios(Usuarios elemento) {
        if (elemento.getIdUsuario() == null) {
            usuariosFacadeLocal.create(elemento);
        } else {
            usuariosFacadeLocal.edit(elemento);
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
    public void guardarPerfiles(Perfiles elemento) {
        if (elemento.getIdPerfil() == null) {
            perfilesFacadeLocal.create(elemento);
        } else {
            for(Formularios f : elemento.getFormulariosList()){
                f = formulariosFacadeLocal.find(f.getIdFormulario());
            }
            for(Vistas l : elemento.getVistasList()){
                l = vistasFacadeLocal.find(l.getIdVista());
            }
            perfilesFacadeLocal.edit(elemento);
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
    public void guardarFormularios(Formularios elemento) {
        if (elemento.getIdFormulario() == null) {
            formulariosFacadeLocal.create(elemento);
        } else {
            formulariosFacadeLocal.edit(elemento);
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
    public void guardarVistas(Vistas elemento) {
        if (elemento.getIdVista() == null) {
            vistasFacadeLocal.create(elemento);
        } else {
            vistasFacadeLocal.edit(elemento);
        }
    }

    @Override
    public Perfiles findPerfil(Integer idPerfil) {
        return perfilesFacadeLocal.find(idPerfil);
    }

    @Override
    public List<Usuarios> findAllUsuariosHostess() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
