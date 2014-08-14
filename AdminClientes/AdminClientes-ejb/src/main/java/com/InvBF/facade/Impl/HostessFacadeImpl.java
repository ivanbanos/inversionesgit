/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.EventosFacadeLocal;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.facade.HostessFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ideacentre
 */
@Stateless
public class HostessFacadeImpl implements HostessFacade{
    
    @EJB
    EventosFacadeLocal eventosFacadeLocal;

    @Override
    public List<Eventos> findEventosHostess(Usuarios usuario) {
        List<Eventos> eventosHostess;
        eventosHostess = eventosFacadeLocal.findAll();
        for(Eventos e : eventosHostess){
            e.getUsuariosList().contains(usuario);
        }
        return eventosHostess;
    }
    
}
