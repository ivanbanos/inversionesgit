/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.AtributoFacadeLocal;
import com.InvBF.EntityFacade.CasinoFacadeLocal;
import com.InvBF.EntityFacade.CategoriaFacadeLocal;
import com.InvBF.EntityFacade.ClienteFacadeLocal;
import com.InvBF.EntityFacade.ClienteatributoFacadeLocal;
import com.InvBF.EntityFacade.EstadoclienteFacadeLocal;
import com.InvBF.EntityFacade.EventoFacadeLocal;
import com.InvBF.EntityFacade.TipoJuegoFacadeLocal;
import com.InvBF.EntityFacade.TipoeventoFacadeLocal;
import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteatributo;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.entity.Tipoevento;
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
    ClienteFacadeLocal clienteFacadeLocal;
    @EJB
    ClienteatributoFacadeLocal clienteatributoFacadeLocal;
    @EJB
    EstadoclienteFacadeLocal estadoclienteFacadeLocal;
    @EJB
    CategoriaFacadeLocal categoriaFacadeLocal;
    @EJB
    AtributoFacadeLocal atributoFacadeLocal;
    @EJB
    TipoJuegoFacadeLocal tipoJuegoFacadeLocal;
    @EJB
    CasinoFacadeLocal casinoFacadeLocal;
    @EJB
    EventoFacadeLocal eventoFacadeLocal;
    @EJB
    TipoeventoFacadeLocal tipoeventoFacadeLocal;

    @Override
    public List<Cliente> findAllClientes() {
        return clienteFacadeLocal.findAll();
    }

    @Override
    public List<Estadocliente> findAllEstadosClietes() {
        return estadoclienteFacadeLocal.findAll();
    }

    @Override
    public Estadocliente findByIdEstadoCliente(int idEstadoCliente) {
        return estadoclienteFacadeLocal.find(new Estadocliente(idEstadoCliente));
    }

    @Override
    public void deleteEstadoCliente(Estadocliente estadoCliente) {
        estadoclienteFacadeLocal.remove(estadoCliente);
    }

    @Override
    public boolean guardarEstadoCliente(Estadocliente elemento) {
        if (elemento.getIdEstadoCliente() == null) {
            estadoclienteFacadeLocal.create(elemento);
            return false;
        } else {
            estadoclienteFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return categoriaFacadeLocal.findAll();
    }

    @Override
    public void deleteCategorias(Categoria elemento) {
        categoriaFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCategorias(Categoria elemento) {
        if (elemento.getIdCategorias() == null) {
            categoriaFacadeLocal.create(elemento);
            return false;
        } else {
            categoriaFacadeLocal.edit(elemento);
            return true;
        }

    }

    @Override
    public List<Atributo> findAllAtributos() {
        return atributoFacadeLocal.findAll();
    }

    @Override
    public void deleteAtributos(Atributo elemento) {
        List<Clienteatributo> listaca = clienteatributoFacadeLocal.findByAtributo(elemento);
        List<Cliente> clientes = clienteFacadeLocal.findAll();
        for (Cliente c : clientes) {
            c.getClientesatributosList().remove(new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo()));
        }
        for (Clienteatributo ca : listaca) {

            clienteatributoFacadeLocal.remove(ca);
        }
        atributoFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarAtributos(Atributo elemento) {
        if (elemento.getIdAtributo() == null) {
            atributoFacadeLocal.create(elemento);
            List<Cliente> clientes = clienteFacadeLocal.findAll();
            List<Clienteatributo> clientesatributos = new ArrayList<>();
            for (Cliente c : clientes) {
                Clienteatributo ca = new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo());
                ca.setAtributos(elemento);
                ca.setClientes(c);
                clienteatributoFacadeLocal.create(ca);
                clientesatributos.add(ca);
            }
            elemento.setClientesatributosList(clientesatributos);
            atributoFacadeLocal.edit(elemento);
            return false;
        } else {
            atributoFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTiposjuegos(TipoJuego elemento) {
        tipoJuegoFacadeLocal.remove(elemento);
    }

    @Override
    public List<TipoJuego> findAllTiposjuegos() {
        return tipoJuegoFacadeLocal.findAll();
    }

    @Override
    public boolean guardarTiposjuegos(TipoJuego elemento) {
        if (elemento.getIdTipoJuego() == null) {
            tipoJuegoFacadeLocal.create(elemento);
            return true;
        } else {
            tipoJuegoFacadeLocal.edit(elemento);
            return true;
        }

    }

    @Override
    public void deleteCasinos(Casino elemento) {
        casinoFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCasinos(Casino elemento) {
        if (elemento.getIdCasino() == null) {
            casinoFacadeLocal.create(elemento);
            return false;
        } else {
            casinoFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Casino> findAllCasinos() {
        return casinoFacadeLocal.findAll();
    }

    @Override
    public List<Evento> findAllEventos() {
        return eventoFacadeLocal.findAll();
    }

    @Override
    public void deleteEventos(Evento elemento) {
        eventoFacadeLocal.remove(elemento);
    }

    @Override
    public void deleteClientes(Cliente elemento) {
        List<Clienteatributo> listaca = clienteatributoFacadeLocal.findByCliente(elemento);
        if (listaca != null) {
            for (Clienteatributo ca : listaca) {
                clienteatributoFacadeLocal.remove(ca);
            }
        }
        clienteFacadeLocal.remove(elemento);
    }

    @Override
    public Cliente guardarClientes(Cliente elemento) {
        if (elemento.getIdCliente() == null) {
            clienteFacadeLocal.create(elemento);
            return elemento;
        } else {
            clienteFacadeLocal.edit(elemento);
            return elemento;
        }
    }

    @Override
    public List<TipoJuego> getTiposJuegosNoClientes(Integer idCliente) {
        Cliente cliente = clienteFacadeLocal.find(idCliente);
        List<TipoJuego> tiposjuego = tipoJuegoFacadeLocal.findAll();
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
        return clienteFacadeLocal.find(integer);
    }

    @Override
    public Evento findEvento(Integer integer) {
        return eventoFacadeLocal.find(integer);
    }

    @Override
    public Estadocliente findByNombreEstadoCliente(String iniciado) {
        return estadoclienteFacadeLocal.findByNombreEstadoCliente(iniciado);
    }

    @Override
    public Evento guardarEventos(Evento elemento) {
        if (elemento.getIdEvento() == null) {

            eventoFacadeLocal.create(elemento);
            return elemento;
        } else {
            eventoFacadeLocal.edit(elemento);
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

    @Override
    public List<Tipoevento> findAllTipoevento() {
        return tipoeventoFacadeLocal.findAll();
    }

    @Override
    public void deleteTipoevento(Tipoevento elemento) {
        tipoeventoFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarTipoevento(Tipoevento elemento) {
        if (elemento.getIdTiposeventos() == null) {

            tipoeventoFacadeLocal.create(elemento);
            return false;
        } else {
            tipoeventoFacadeLocal.edit(elemento);
            return true;
        }
    }
}
