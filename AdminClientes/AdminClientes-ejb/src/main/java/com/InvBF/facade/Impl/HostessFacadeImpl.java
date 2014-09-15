/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.ClienteeventoFacadeLocal;
import com.InvBF.EntityFacade.ConfiguracionFacadeLocal;
import com.InvBF.EntityFacade.EstadoclienteFacadeLocal;
import com.InvBF.EntityFacade.EventoFacadeLocal;
import com.InvBF.EntityFacade.TipoeventoFacadeLocal;
import com.invbf.adminclientesapi.entity.Clienteevento;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Tipoevento;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesPorRevisarException;
import com.invbf.adminclientesapi.facade.HostessFacade;
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
public class HostessFacadeImpl implements HostessFacade {

    @EJB
    EventoFacadeLocal eventoFacadeLocal;
    @EJB
    ConfiguracionFacadeLocal configuracionFacadeLocal;
    @EJB
    EstadoclienteFacadeLocal estadoclienteFacadeLocal;
    @EJB
    ClienteeventoFacadeLocal clienteeventoFacadeLocal;
    @EJB
    TipoeventoFacadeLocal tipoeventoFacadeLocal;

    @Override
    public List<Evento> findEventosHostess(Usuario usuario) {
        List<Evento> eventosHostess;
        eventosHostess = eventoFacadeLocal.findAll();
        Iterator<Evento> iterator = eventosHostess.iterator();
        Tipoevento tipo = tipoeventoFacadeLocal.findBynombre("EMAIL");
        while (iterator.hasNext()) {
            Evento e = iterator.next();
            if(!e.getUsuariosList().contains(usuario)||!e.getEstado().equals("ACTIVO")||e.getTipo().equals(tipo)){
                iterator.remove();
            }
        }
        return eventosHostess;
    }

    @Override
    public List<Clienteevento> findClienteEventosHostess(Integer integer) throws EventoSinClientesPorRevisarException {
        Evento evento = eventoFacadeLocal.find(integer);
        List<Clienteevento> clientes = evento.getListasclienteseventoList();
        List<Clienteevento> clientesAEnviar = new ArrayList<>();
        Iterator<Clienteevento> iterator = clientes.iterator();

        int cantidadClientes = findCantidadClientes();
        Estadocliente inicial = estadoclienteFacadeLocal.findByNombreEstadoCliente("Inicial");
        for (int i = 0; i < cantidadClientes; i++) {

            uncliente:
            while (iterator.hasNext()) {
                Clienteevento lce = iterator.next();
                if (lce.getIdEstadoCliente().equals(inicial)) {
                    clienteeventoFacadeLocal.edit(lce);
                    clientesAEnviar.add(lce);
                    break uncliente;
                }
            }
        }
        if (clientesAEnviar.isEmpty()) {
            throw new EventoSinClientesPorRevisarException();
        } else {
            return clientesAEnviar;
        }

    }

    @Override
    public int findCantidadClientes() {
        return Integer.parseInt(configuracionFacadeLocal.findByNombre("CantidadClientes").getValor());
    }

    @Override
    public void guardarLCE(Clienteevento listasclientesevento) {
        clienteeventoFacadeLocal.edit(listasclientesevento);
    }

    @Override
    public Clienteevento nuevoLCE(Integer index, List<Clienteevento> clientes, Clienteevento l) throws EventoSinClientesPorRevisarException {
        Evento evento = eventoFacadeLocal.find(index);
        List<Clienteevento> clientesEv = evento.getListasclienteseventoList();
        Iterator<Clienteevento> iterator = clientesEv.iterator();

        Estadocliente inicial = estadoclienteFacadeLocal.findByNombreEstadoCliente("Inicial");
        
            while (iterator.hasNext()) {
                Clienteevento lce = iterator.next();
                if (lce.getIdEstadoCliente().equals(inicial)) {
                    if (!clientes.contains(lce)&&!lce.equals(l)) {
                        return lce;
                    }
                }
            }
        
        throw new EventoSinClientesPorRevisarException();

    }

    @Override
    public Estadocliente findEstadoClientesByName(String idEstadoCliente) {
        return estadoclienteFacadeLocal.findByNombreEstadoCliente(idEstadoCliente);
    }
}
