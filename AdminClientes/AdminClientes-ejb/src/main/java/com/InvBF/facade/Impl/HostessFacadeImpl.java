/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.ConfiguracionesFacadeLocal;
import com.InvBF.EntityFacade.EstadosclienteFacadeLocal;
import com.InvBF.EntityFacade.EventosFacadeLocal;
import com.InvBF.EntityFacade.ListasclienteseventoFacadeLocal;
import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Listasclientesevento;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesException;
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
        for (Eventos e : eventosHostess) {
            e.getUsuariosList().contains(usuario);
        }
        return eventosHostess;
    }

    @Override
    public Listasclientesevento findClienteEventosHostess(Integer integer) throws EventoSinClientesException {
        Eventos evento = eventosFacadeLocal.find(integer);
        List<Listasclientesevento> clientes = (List<Listasclientesevento>) ((ArrayList<Listasclientesevento>) evento.getListasclienteseventoList()).clone();
        Iterator<Listasclientesevento> iterator = clientes.iterator();

        Estadoscliente inicial = estadosclienteFacadeLocal.findByNombreEstadoCliente("Inicial");
        Estadoscliente enRevision = estadosclienteFacadeLocal.findByNombreEstadoCliente("En revision");
        while (iterator.hasNext()) {
            Listasclientesevento lce = iterator.next();
            if (lce.getCount() == null) {
                lce.setCount(0);
            }
            if (!lce.getIdEstadoCliente().equals(inicial)) {
                iterator.remove();
            }
        }
        if (clientes.isEmpty()) {
            throw new EventoSinClientesException();
        } else {
            int i = 0;
            while (true) {
                for (Listasclientesevento lce : clientes) {
                    if (lce.getCount().intValue() == i) {
                        lce.setCount(lce.getCount() + 1);
                        lce.setIdEstadoCliente(enRevision);
                        listasclienteseventoFacadeLocal.edit(lce);
                        return lce;
                    }
                }
                i++;
                if (i == 20) {
                    Listasclientesevento lce = clientes.get(0);
                    lce.setCount(lce.getCount() + 1);
                    lce.setIdEstadoCliente(enRevision);
                    listasclienteseventoFacadeLocal.edit(lce);
                    return lce;
                }
            }
        }
    }

    @Override
    public int findCantidadClientes() {
        return Integer.parseInt(configuracionesFacadeLocal.findByNombre("CantidadClientes").getValor());
    }
}
