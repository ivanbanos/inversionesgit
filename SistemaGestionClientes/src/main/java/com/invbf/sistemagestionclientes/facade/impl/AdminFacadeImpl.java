/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade.impl;

import com.invbf.sistemagestionclientes.dao.CargoDao;
import com.invbf.sistemagestionclientes.dao.FormularioDao;
import com.invbf.sistemagestionclientes.dao.PerfilDao;
import com.invbf.sistemagestionclientes.dao.TareasDao;
import com.invbf.sistemagestionclientes.dao.UsuarioDao;
import com.invbf.sistemagestionclientes.dao.UsuarioDetalleDao;
import com.invbf.sistemagestionclientes.dao.VistaDao;
import com.invbf.sistemagestionclientes.entity.Formulario;
import com.invbf.sistemagestionclientes.entity.Perfil;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.entity.Vista;
import com.invbf.sistemagestionclientes.entitySGB.Cargos;
import com.invbf.sistemagestionclientes.entitySGB.Usuariosdetalles;
import com.invbf.sistemagestionclientes.exceptions.NombreUsuarioExistenteException;
import com.invbf.sistemagestionclientes.exceptions.PerfilExistenteException;
import com.invbf.sistemagestionclientes.facade.AdminFacade;
import com.invbf.sistemagestionclientes.util.EncryptUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public class AdminFacadeImpl implements AdminFacade {

    @Override
    public List<Usuario> findAllUsuarios() {
        return UsuarioDao.findAll();
    }

    @Override
    public void deleteUsuarios(Usuario elemento) {
        List<Tarea> tareas = TareasDao.findAll();
        for (Tarea t : tareas) {
            if (t.getUsuarioList().contains(elemento)) {
                t.getUsuarioList().remove(elemento);
                TareasDao.edit(t);
            }
        }
        UsuarioDao.remove(elemento);
    }

    @Override
    public Usuario guardarUsuarios(Usuario elemento) throws NombreUsuarioExistenteException {
        if (elemento.getIdUsuario() == null) {
            try {
                elemento.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
            } catch (NoSuchAlgorithmException ex) {
            }
            if (UsuarioDao.findByNombreUsuario(elemento.getNombreUsuario()) != null) {
                throw new NombreUsuarioExistenteException();
            }
            UsuarioDao.create(elemento);
            return elemento;
        } else {
            try {
                elemento.setContrasena(EncryptUtil.encryptPassword(elemento.getContrasena()));
            } catch (NoSuchAlgorithmException ex) {
            }
            UsuarioDao.edit(elemento);
            return elemento;
        }
    }

    @Override
    public void deletePerfiles(Perfil elemento) {
        PerfilDao.remove(elemento);
    }

    @Override
    public List<Perfil> findAllPerfiles() {
        return PerfilDao.findAll();
    }

    @Override
    public boolean guardarPerfiles(Perfil elemento) throws PerfilExistenteException {
        if (elemento.getIdPerfil() == null) {
            if (PerfilDao.findByNombre(elemento.getNombre()) != null) {
                throw new PerfilExistenteException();
            }
            PerfilDao.create(elemento);

            return false;
        } else {
            for (Formulario f : elemento.getFormulariosList()) {
                f = FormularioDao.find(f.getIdFormulario());
            }
            for (Vista l : elemento.getVistasList()) {
                l = VistaDao.find(l.getIdVista());
            }
            PerfilDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Formulario> findAllFormularios() {
        return FormularioDao.findAll();
    }

    @Override
    public void deleteFormularios(Formulario elemento) {
        FormularioDao.remove(elemento);
    }

    @Override
    public boolean guardarFormularios(Formulario elemento) {
        if (elemento.getIdFormulario() == null) {
            FormularioDao.create(elemento);
            return false;
        } else {
            FormularioDao.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Vista> findAllVistas() {
        return VistaDao.findAll();
    }

    @Override
    public void deleteVistas(Vista elemento) {
        VistaDao.remove(elemento);
    }

    @Override
    public boolean guardarVistas(Vista elemento) {
        if (elemento.getIdVista() == null) {
            VistaDao.create(elemento);
            return false;
        } else {
            VistaDao.edit(elemento);
            return true;
        }
    }

    @Override
    public Perfil findPerfil(Integer idPerfil) {
        return PerfilDao.find(idPerfil);
    }

    @Override
    public List<Usuario> findAllUsuariosHostess() {
        List<Usuario> usuarios = findAllUsuarios();
        Vista v = findVistasByNombre("ManejadorEventosHostess");
        for (Iterator<Usuario> it = usuarios.iterator(); it.hasNext();) {
            Usuario usuario = it.next();
            if (!usuario.getIdPerfil().getVistasList().contains(v)) {
                it.remove();
            }
        }
        return usuarios;
    }

    @Override
    public Usuario findUsuarios(Integer idUsuario) {
        return UsuarioDao.find(idUsuario);
    }

    @Override
    public void agregarTareaUsuarios(Usuario s, Tarea elemento) {
        Usuario usuario = UsuarioDao.find(s.getIdUsuario());
        if (usuario.getTareasList() == null) {
            usuario.setTareasList(new ArrayList<Tarea>(0));
        }
        usuario.getTareasList().add(elemento);
        UsuarioDao.edit(usuario);
    }

    @Override
    public Vista findVistasByNombre(String nombre) {
        return VistaDao.findByNombre(nombre);
    }

    @Override
    public Formulario findFormularioByAccionAndTabla(String accion, String tabla) {
        return FormularioDao.findByAccionAndTabla(accion, tabla);
    }

    @Override
    public List<Cargos> findAllCargos() {
        return CargoDao.findAll();
    }

    @Override
    public boolean guardarCargos(Cargos elemento) {
        if (elemento.getIdcargo() == null) {
            CargoDao.create(elemento);
            return false;
        } else {
            CargoDao.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteCargos(Cargos elemento) {
        CargoDao.remove(elemento);
    }

    @Override
    public Usuariosdetalles getDetalleUsuariosById(Integer idUsuario) {
        return UsuarioDetalleDao.find(idUsuario);
    }

    @Override
    public void deleteDetalleUsuarios(Usuariosdetalles detalleElemento) {
        UsuarioDetalleDao.remove(detalleElemento);
    }

    @Override
    public Usuariosdetalles guardarDetalleUsuarios(Usuariosdetalles detalleElemento) {
        if (detalleElemento.getIdUsuario() == null) {
            UsuarioDetalleDao.create(detalleElemento);
            return detalleElemento;
        } else {
            UsuarioDetalleDao.edit(detalleElemento);
            return detalleElemento;
        }
    }

}
