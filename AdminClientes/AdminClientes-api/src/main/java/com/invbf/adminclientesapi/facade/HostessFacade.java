/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Clienteevento;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesPorRevisarException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface HostessFacade {

    public List<Evento> findEventosHostess(Usuario usuario);

    public List<Clienteevento> findClienteEventosHostess(Integer integer)throws EventoSinClientesPorRevisarException;

    public int findCantidadClientes();

    public void guardarLCE(Clienteevento listasclientesevento);

    public Clienteevento nuevoLCE(Integer index, List<Clienteevento> clientes, Clienteevento l)throws EventoSinClientesPorRevisarException;

    public Estadocliente findEstadoClientesByName(String idEstadoCliente);
}
