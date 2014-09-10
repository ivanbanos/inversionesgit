/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.AtributosFacadeLocal;
import com.InvBF.EntityFacade.CasinosFacadeLocal;
import com.InvBF.EntityFacade.CategoriasFacadeLocal;
import com.InvBF.EntityFacade.ClientesFacadeLocal;
import com.InvBF.EntityFacade.ClientesatributosFacadeLocal;
import com.InvBF.EntityFacade.EstadosclienteFacadeLocal;
import com.InvBF.EntityFacade.EventosFacadeLocal;
import com.InvBF.EntityFacade.TiposjuegosFacadeLocal;
import com.invbf.adminclientesapi.entity.Atributos;
import com.invbf.adminclientesapi.entity.Casinos;
import com.invbf.adminclientesapi.entity.Categorias;
import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Clientesatributos;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Tiposjuegos;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ideacentre
 */
@Stateless
public class MarketingUserFacadeImpl implements MarketingUserFacade {

    @EJB
    ClientesFacadeLocal clientesFacadeLocal;
    @EJB
    ClientesatributosFacadeLocal clientesatributosFacadeLocal;
    @EJB
    EstadosclienteFacadeLocal estadosclienteFacadeLocal;
    @EJB
    CategoriasFacadeLocal categoriasFacadeLocal;
    @EJB
    AtributosFacadeLocal atributosFacadeLocal;
    @EJB
    TiposjuegosFacadeLocal tiposjuegosFacadeLocal;
    @EJB
    CasinosFacadeLocal casinosFacadeLocal;
    @EJB
    EventosFacadeLocal eventosFacadeLocal;

    @Override
    public List<Clientes> findAllClientes() {
        return clientesFacadeLocal.findAll();
    }

    @Override
    public List<Estadoscliente> findAllEstadosClietes() {
        return estadosclienteFacadeLocal.findAll();
    }

    @Override
    public Estadoscliente findByIdEstadoCliente(int idEstadoCliente) {
        return estadosclienteFacadeLocal.find(new Estadoscliente(idEstadoCliente));
    }

    @Override
    public void deleteEstadoCliente(Estadoscliente estadoCliente) {
        estadosclienteFacadeLocal.remove(estadoCliente);
    }

    @Override
    public boolean guardarEstadoCliente(Estadoscliente elemento) {
        if (elemento.getIdEstadoCliente() == null) {
            estadosclienteFacadeLocal.create(elemento);
            return false;
        } else {
            estadosclienteFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Categorias> findAllCategorias() {
        return categoriasFacadeLocal.findAll();
    }

    @Override
    public void deleteCategorias(Categorias elemento) {
        categoriasFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCategorias(Categorias elemento) {
        if (elemento.getIdCategorias() == null) {
            categoriasFacadeLocal.create(elemento);
            return false;
        } else {
            categoriasFacadeLocal.edit(elemento);
            return true;
        }

    }

    @Override
    public List<Atributos> findAllAtributos() {
        return atributosFacadeLocal.findAll();
    }

    @Override
    public void deleteAtributos(Atributos elemento) {
        List<Clientesatributos> listaca = clientesatributosFacadeLocal.findByAtributo(elemento);
        List<Clientes> clientes = clientesFacadeLocal.findAll();
        for (Clientes c : clientes) {
            c.getClientesatributosList().remove(new Clientesatributos(c.getIdCliente(), elemento.getIdAtributo()));
        }
        for (Clientesatributos ca : listaca) {

            clientesatributosFacadeLocal.remove(ca);
        }
        atributosFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarAtributos(Atributos elemento) {
        if (elemento.getIdAtributo() == null) {
            atributosFacadeLocal.create(elemento);
            List<Clientes> clientes = clientesFacadeLocal.findAll();
            List<Clientesatributos> clientesatributos = new ArrayList<>();
            for (Clientes c : clientes) {
                Clientesatributos ca = new Clientesatributos(c.getIdCliente(), elemento.getIdAtributo());
                ca.setAtributos(elemento);
                ca.setClientes(c);
                clientesatributosFacadeLocal.create(ca);
                clientesatributos.add(ca);
            }
            elemento.setClientesatributosList(clientesatributos);
            atributosFacadeLocal.edit(elemento);
            return false;
        } else {
            atributosFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTiposjuegos(Tiposjuegos elemento) {
        tiposjuegosFacadeLocal.remove(elemento);
    }

    @Override
    public List<Tiposjuegos> findAllTiposjuegos() {
        return tiposjuegosFacadeLocal.findAll();
    }

    @Override
    public boolean guardarTiposjuegos(Tiposjuegos elemento) {
        if (elemento.getIdTipoJuego() == null) {
            tiposjuegosFacadeLocal.create(elemento);
            return true;
        } else {
            tiposjuegosFacadeLocal.edit(elemento);
            return true;
        }

    }

    @Override
    public void deleteCasinos(Casinos elemento) {
        casinosFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCasinos(Casinos elemento) {
        if (elemento.getIdCasino() == null) {
            casinosFacadeLocal.create(elemento);
            return false;
        } else {
            casinosFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Casinos> findAllCasinos() {
        return casinosFacadeLocal.findAll();
    }

    @Override
    public List<Eventos> findAllEventos() {
        return eventosFacadeLocal.findAll();
    }

    @Override
    public void deleteEventos(Eventos elemento) {
        eventosFacadeLocal.remove(elemento);
    }

    @Override
    public void deleteClientes(Clientes elemento) {
        List<Clientesatributos> listaca = clientesatributosFacadeLocal.findByCliente(elemento);
        for (Clientesatributos ca : listaca) {
            clientesatributosFacadeLocal.remove(ca);
        }
        clientesFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarClientes(Clientes elemento) {
        if (elemento.getIdCliente() == null) {
            clientesFacadeLocal.create(elemento);
            List<Atributos> atributos = atributosFacadeLocal.findAll();
            List<Clientesatributos> clientesatributos = new ArrayList<>();
            for (Atributos a : atributos) {
                Clientesatributos ca = new Clientesatributos(elemento.getIdCliente(), a.getIdAtributo());
                ca.setAtributos(a);
                ca.setClientes(elemento);
                clientesatributosFacadeLocal.create(ca);
                clientesatributos.add(ca);
            }
            elemento.setClientesatributosList(clientesatributos);
            clientesFacadeLocal.edit(elemento);
            return false;
        } else {
            clientesFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Tiposjuegos> getTiposJuegosNoClientes(Integer idCliente) {
        Clientes cliente = clientesFacadeLocal.find(idCliente);
        List<Tiposjuegos> tiposjuego = tiposjuegosFacadeLocal.findAll();
        Iterator<Tiposjuegos> iter = tiposjuego.iterator();
        while (iter.hasNext()) {
            if (iter.next().getClientesList().contains(cliente)) {
                iter.remove();
            }
        }
        return tiposjuego;
    }

    @Override
    public Clientes findCliente(Integer integer) {
        return clientesFacadeLocal.find(integer);
    }

    @Override
    public Eventos findEvento(Integer integer) {
        return eventosFacadeLocal.find(integer);
    }

    @Override
    public Estadoscliente findByNombreEstadoCliente(String iniciado) {
        return estadosclienteFacadeLocal.findByNombreEstadoCliente(iniciado);
    }

    @Override
    public Eventos guardarEventos(Eventos elemento) {
        if (elemento.getIdEvento() == null) {

            eventosFacadeLocal.create(elemento);
            return elemento;
        } else {
            eventosFacadeLocal.edit(elemento);
            return elemento;
        }
    }

    @Override
    public void guardarImagen(byte[] contents, Integer idEvento, String fileName) {
        try {

            File imagen = null;
            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("APP_CONF"))
                    .append(System.getProperty("file.separator"))
                    .append("images").append(System.getProperty("file.separator"))
                    .append("inversiones").append(System.getProperty("file.separator")).append(idEvento).append(fileName);
            imagen = new File(sb.toString());
            if (imagen.exists()) {
                imagen.delete();
            }
            OutputStream stream = null;
            stream = new FileOutputStream(sb.toString());
            if (contents != null) {
                stream.write(contents);
            }
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MarketingUserFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MarketingUserFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
