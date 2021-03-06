/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.TipoDocumento;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.entity.Tipotarea;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface MarketingUserFacade {

    public List<Cliente> findAllClientes();

    public List<Categoria> findAllCategorias();

    public void deleteCategorias(Categoria elemento);

    public boolean guardarCategorias(Categoria elemento);

    public List<Atributo> findAllAtributos();

    public void deleteAtributos(Atributo elemento);

    public boolean guardarAtributos(Atributo elemento);

    public void deleteTiposjuegos(TipoJuego elemento);

    public List<TipoJuego> findAllTiposjuegos();

    public boolean guardarTiposjuegos(TipoJuego elemento);

    public void deleteCasinos(Casino elemento);

    public boolean guardarCasinos(Casino elemento);

    public List<Casino> findAllCasinos();

    public List<Evento> findAllEventos();

    public void deleteEventos(Evento elemento);

    public Evento guardarEventos(Evento elemento);

    public void deleteClientes(Cliente elemento);

    public Cliente guardarClientes(Cliente elemento);

    public List<TipoJuego> getTiposJuegosNoClientes(Integer idCliente);

    public Cliente findCliente(Integer integer);

    public Evento findEvento(Integer integer);

    public void guardarImagen(byte[] contents, Integer idEvento, String fileName);

    public List<Accion> findAllAcciones();

    public void deleteAccion(Accion elemento);

    public boolean guardarAccion(Accion elemento);

    public List<Tipotarea> findAllTipotarea();

    public boolean guardarTipotarea(Tipotarea elemento);

    public void deleteTipotarea(Tipotarea elemento);

    public void deleteTarea(Tarea tarea);

    public Tarea guardarTarea(Tarea elemento);

    public Accion findByNombreAccion(String iniciaL);

    public List<Tarea> findAllTareas();

    public Tarea findTarea(Integer integer);

    public List<TipoDocumento> findAllTipoDocumentos();
    
    public boolean guardarTipoDocumentos(TipoDocumento elemento);

    public void deleteTipoDocumentos(TipoDocumento elemento);

    public String findNombreAccion(Integer idAccion);

    public Accion findAccion(Integer accion);

    public Date getLCTFecha(Listasclientestareas lct);

}
