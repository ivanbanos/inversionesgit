/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.AccionFacadeLocal;
import com.InvBF.EntityFacade.ConfiguracionFacadeLocal;
import com.InvBF.EntityFacade.EventoFacadeLocal;
import com.InvBF.EntityFacade.ListasclientestareasFacadeLocal;
import com.InvBF.EntityFacade.TareasFacadeLocal;
import com.InvBF.EntityFacade.TipostareasFacadeLocal;
import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Tipotarea;
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
    TareasFacadeLocal tareasFacadeLocal;
    @EJB
    ConfiguracionFacadeLocal configuracionFacadeLocal;
    @EJB
    AccionFacadeLocal accionFacadeLocal;
    @EJB
    TipostareasFacadeLocal tipostareasFacadeLocal;
    @EJB
    ListasclientestareasFacadeLocal listasclientestareasFacadeLocal;

    @Override
    public int findCantidadClientes() {
        return Integer.parseInt(configuracionFacadeLocal.findByNombre("CantidadClientes").getValor());
    }

    @Override
    public List<Tarea> findTareaHostess(Usuario usuario) {
        List<Tarea> tareasHostess;
        tareasHostess = tareasFacadeLocal.findAll();
        Iterator<Tarea> iterator = tareasHostess.iterator();
        Tipotarea tipo = tipostareasFacadeLocal.findBynombre("EMAIL");
        while (iterator.hasNext()) {
            Tarea e = iterator.next();
            if (e.getUsuarioList().contains(usuario) && e.getEstado().equals("ACTIVO") && !e.getTipo().equals(tipo)) {
            } else {
                iterator.remove();
            }
        }
        return tareasHostess;
    }

    @Override
    public List<Listasclientestareas> findClienteTareaHostess(Integer integer)throws EventoSinClientesPorRevisarException {
        Tarea evento = tareasFacadeLocal.find(integer);
        List<Listasclientestareas> clientes = evento.getListasclientestareasList();
        List<Listasclientestareas> clientesAEnviar = new ArrayList<>();
        Iterator<Listasclientestareas> iterator = clientes.iterator();

        int cantidadClientes = findCantidadClientes();
        Accion inicial = accionFacadeLocal.findByNombreAccion("INICIAL");
        for (int i = 0; i < cantidadClientes; i++) {

            uncliente:
            while (iterator.hasNext()) {
                Listasclientestareas lce = iterator.next();
                if (lce.getIdAccion().equals(inicial)) {
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
    public Accion findAccionByName(String idAccion) {
        return accionFacadeLocal.findByNombreAccion(idAccion);
    }

    @Override
    public void guardarLCE(Listasclientestareas l) {
        listasclientestareasFacadeLocal.edit(l);
    }

    @Override
    public Listasclientestareas nuevoLCE(Integer index, List<Listasclientestareas> clientes, Listasclientestareas l) throws EventoSinClientesPorRevisarException {
        Tarea tarea = tareasFacadeLocal.find(index);
        List<Listasclientestareas> clientesEv = tarea.getListasclientestareasList();
        Iterator<Listasclientestareas> iterator = clientesEv.iterator();

        Accion inicial = accionFacadeLocal.findByNombreAccion("Inicial");

        while (iterator.hasNext()) {
            Listasclientestareas lce = iterator.next();
            if (lce.getIdAccion().equals(inicial)) {
                if (!clientes.contains(lce) && !lce.equals(l)) {
                    return lce;
                }
            }
        }

        throw new EventoSinClientesPorRevisarException();
    }

    @Override
    public Tipotarea findByNombreTipoTarea(String emaiL) {
        
        return tipostareasFacadeLocal.findBynombre("EMAIL");
    }
}
