/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.ConfiguracionesFacadeLocal;
import com.InvBF.EntityFacade.EstadosclienteFacadeLocal;
import com.InvBF.EntityFacade.EventosFacadeLocal;
import com.InvBF.EntityFacade.ListasclienteseventoFacadeLocal;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Listasclientesevento;
import com.invbf.adminclientesapi.entity.Usuarios;
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
    EventosFacadeLocal eventosFacadeLocal;
    @EJB
    ConfiguracionesFacadeLocal configuracionesFacadeLocal;
    @EJB
    EstadosclienteFacadeLocal estadosclienteFacadeLocal;
    @EJB
    ListasclienteseventoFacadeLocal listasclienteseventoFacadeLocal;

    @Override
    public List<Eventos> findEventosHostess(Usuarios usuario) {
        List<Eventos> eventosHostess;
        eventosHostess = eventosFacadeLocal.findAll();
        Iterator<Eventos> iterator = eventosHostess.iterator();
        while (iterator.hasNext()) {
            Eventos e = iterator.next();
            if(!e.getUsuariosList().contains(usuario)||!e.getEstado().equals("Activo")||e.getTipo()!=1){
                iterator.remove();
            }
        }
        return eventosHostess;
    }

    @Override
    public List<Listasclientesevento> findClienteEventosHostess(Integer integer) throws EventoSinClientesPorRevisarException {
        Eventos evento = eventosFacadeLocal.find(integer);
        List<Listasclientesevento> clientes = evento.getListasclienteseventoList();
        List<Listasclientesevento> clientesAEnviar = new ArrayList<>();
        Iterator<Listasclientesevento> iterator = clientes.iterator();

        int cantidadClientes = findCantidadClientes();
        Estadoscliente inicial = estadosclienteFacadeLocal.findByNombreEstadoCliente("Inicial");
        for (int i = 0; i < cantidadClientes; i++) {

            uncliente:
            while (iterator.hasNext()) {
                Listasclientesevento lce = iterator.next();
                if (lce.getIdEstadoCliente().equals(inicial)) {
                    listasclienteseventoFacadeLocal.edit(lce);
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
        return Integer.parseInt(configuracionesFacadeLocal.findByNombre("CantidadClientes").getValor());
    }

    @Override
    public void guardarLCE(Listasclientesevento listasclientesevento) {
        listasclienteseventoFacadeLocal.edit(listasclientesevento);
    }

    @Override
    public Listasclientesevento nuevoLCE(Integer index, List<Listasclientesevento> clientes, Listasclientesevento l) throws EventoSinClientesPorRevisarException {
        Eventos evento = eventosFacadeLocal.find(index);
        List<Listasclientesevento> clientesEv = evento.getListasclienteseventoList();
        Iterator<Listasclientesevento> iterator = clientesEv.iterator();

        Estadoscliente inicial = estadosclienteFacadeLocal.findByNombreEstadoCliente("Inicial");
        
            while (iterator.hasNext()) {
                Listasclientesevento lce = iterator.next();
                if (lce.getIdEstadoCliente().equals(inicial)) {
                    if (!clientes.contains(lce)&&!lce.equals(l)) {
                        return lce;
                    }
                }
            }
        
        throw new EventoSinClientesPorRevisarException();

    }

    @Override
    public Estadoscliente findEstadoClientesByName(String idEstadoCliente) {
        return estadosclienteFacadeLocal.findByNombreEstadoCliente(idEstadoCliente);
    }
}
