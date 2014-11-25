/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade;

import com.invbf.sistemagestionclientes.entity.Accion;
import com.invbf.sistemagestionclientes.entity.Atributo;
import com.invbf.sistemagestionclientes.entity.Casino;
import com.invbf.sistemagestionclientes.entity.Categoria;
import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Evento;
import com.invbf.sistemagestionclientes.entity.Listasclientestareas;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.TipoDocumento;
import com.invbf.sistemagestionclientes.entity.TipoJuego;
import com.invbf.sistemagestionclientes.entity.Tipotarea;
import com.invbf.sistemagestionclientes.entitySGB.Casinosdetalles;
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

    public Casino guardarCasinos(Casino elemento);

    public List<Casino> findAllCasinos();

    public List<Evento> findAllEventos();

    public void deleteEventos(Evento elemento);

    public Evento guardarEventos(Evento elemento);

    public void deleteClientes(Cliente elemento);

    public Cliente guardarClientes(Cliente elemento);

    public List<TipoJuego> getTiposJuegosNoClientes(Integer idCliente);

    public Cliente findCliente(Integer integer);

    public Evento findEvento(Integer integer);

    public void guardarImagen(byte[] contents, String fileName);

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

    public Casino findCasino(Integer idCasino);

    public Categoria findCategoria(Integer idCategorias);

    public TipoDocumento findTipoDocumento(Integer idTipoDocumento);

    public Casinosdetalles getDetalleCasinoById(Integer idCasino);

    public Casinosdetalles guardarDetalleCasino(Casinosdetalles detalleElemento);

    public void deleteDetalleCasino(Casinosdetalles detalleElemento);

}
