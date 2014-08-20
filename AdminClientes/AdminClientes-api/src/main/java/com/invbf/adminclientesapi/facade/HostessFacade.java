/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Listasclientesevento;
import com.invbf.adminclientesapi.entity.Usuarios;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface HostessFacade {

    public List<Eventos> findEventosHostess(Usuarios usuario);

    public Listasclientesevento findClienteEventosHostess(Integer integer)throws EventoSinClientesException;

    public int findCantidadClientes();
}
