/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.exceptions.EventoSinClientesPorRevisarException;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface HostessFacade {

    public List<Tarea> findTareaHostess(Usuario usuario);

    public int findCantidadClientes();

    public List<Listasclientestareas> findClienteTareaHostess(Integer integer);

    public Accion findAccionByName(String idAccion);

    public void guardarLCE(Listasclientestareas l);

    public Listasclientestareas nuevoLCE(Integer integer, List<Listasclientestareas> clientes, Listasclientestareas l)throws EventoSinClientesPorRevisarException;
}
