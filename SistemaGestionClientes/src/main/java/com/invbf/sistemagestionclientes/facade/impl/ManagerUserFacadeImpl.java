/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade.impl;

import com.invbf.sistemagestionclientes.dao.CasinoDao;
import com.invbf.sistemagestionclientes.dao.CategoriaDao;
import com.invbf.sistemagestionclientes.dao.ClienteDao;
import com.invbf.sistemagestionclientes.dao.PermisosDao;
import com.invbf.sistemagestionclientes.dao.TipoDocumentoDao;
import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Permiso;
import com.invbf.sistemagestionclientes.exceptions.clienteInexistenteException;
import com.invbf.sistemagestionclientes.facade.ManagerUserFacade;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public class ManagerUserFacadeImpl implements ManagerUserFacade {

    @Override
    public List<Permiso> getAllPermisos() {
        return PermisosDao.findAll();
    }

    @Override
    public void eliminarPermiso(Permiso permiso) {
        PermisosDao.remove(permiso);
    }

    @Override
    public String ejecutarPermiso(Permiso permiso) throws clienteInexistenteException {
        if (permiso.getTipo().equals("ELIMINAR")) {
            Cliente cliente = ClienteDao.find(Integer.parseInt(permiso.getId()));
            if (cliente == null) {
                throw new clienteInexistenteException();
            } else {
                ClienteDao.remove(cliente);
                return "Cliente removido";
            }
        } else if (permiso.getTipo().equals("EDITAR")) {
            Cliente cliente = ClienteDao.find(Integer.parseInt(permiso.getId()));
            if (cliente == null) {
                throw new clienteInexistenteException();
            } else {
                if (permiso.getCampo().equals("nombres")) {
                    cliente.setNombres(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("apellidos")) {
                    cliente.setApellidos(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("idCasinoPreferencial")) {
                    cliente.setIdCasinoPreferencial(CasinoDao.find(Integer.parseInt(permiso.getNuevoValor())));
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("idCategorias")) {
                    cliente.setIdCategorias(CategoriaDao.find(Integer.parseInt(permiso.getNuevoValor())));
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("telefono1")) {
                    cliente.setTelefono1(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("telefono2")) {
                    cliente.setTelefono2(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("identificacion")) {
                    cliente.setIdentificacion(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("correo")) {
                    cliente.setCorreo(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("cumpleanos")) {
                    cliente.setCumpleanos(new Date(Long.parseLong(permiso.getNuevoValor())));
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("pais")) {
                    cliente.setPais(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("direccion")) {
                    cliente.setDireccion(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("ciudad")) {
                    cliente.setCiudad(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("bonoFidelizacion")) {
                    cliente.setBonoFidelizacion(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("genero")) {
                    cliente.setGenero(permiso.getNuevoValor());
                    ClienteDao.edit(cliente);
                }else if (permiso.getCampo().equals("idTipoDocumento")) {
                    cliente.setIdTipoDocumento(TipoDocumentoDao.find(Integer.parseInt(permiso.getNuevoValor())));
                    ClienteDao.edit(cliente);
                }
                return "Cliente actualizado";
            }
        }
        return null;
    }

    @Override
    public void addPermiso(Permiso permiso) {
        PermisosDao.create(permiso);
    }
}
