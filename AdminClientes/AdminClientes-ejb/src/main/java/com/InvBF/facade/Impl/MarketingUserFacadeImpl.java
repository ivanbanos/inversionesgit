/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.AccionFacadeLocal;
import com.InvBF.EntityFacade.AtributoFacadeLocal;
import com.InvBF.EntityFacade.CasinoFacadeLocal;
import com.InvBF.EntityFacade.CategoriaFacadeLocal;
import com.InvBF.EntityFacade.ClienteFacadeLocal;
import com.InvBF.EntityFacade.ClienteatributoFacadeLocal;
import com.InvBF.EntityFacade.EventoFacadeLocal;
import com.InvBF.EntityFacade.TareasFacadeLocal;
import com.InvBF.EntityFacade.TipoDocumentoFacadeLocal;
import com.InvBF.EntityFacade.TipoJuegoFacadeLocal;
import com.InvBF.EntityFacade.TipostareasFacadeLocal;
import com.InvBF.EntityFacade.UsuarioFacadeLocal;
import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteatributo;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.TipoDocumento;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.entity.Tipotarea;
import com.invbf.adminclientesapi.entity.Usuario;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ideacentre
 */
@Stateless
public class MarketingUserFacadeImpl implements MarketingUserFacade {

    @EJB
    ClienteFacadeLocal clienteFacadeLocal;
    @EJB
    AccionFacadeLocal accionFacadeLocal;
    @EJB
    ClienteatributoFacadeLocal clienteatributoFacadeLocal;
    @EJB
    CategoriaFacadeLocal categoriaFacadeLocal;
    @EJB
    AtributoFacadeLocal atributoFacadeLocal;
    @EJB
    TipoJuegoFacadeLocal tipoJuegoFacadeLocal;
    @EJB
    TipostareasFacadeLocal tipostareasFacadeLocal;
    @EJB
    TareasFacadeLocal tareasFacadeLocal;
    @EJB
    CasinoFacadeLocal casinoFacadeLocal;
    @EJB
    EventoFacadeLocal eventoFacadeLocal;
    @EJB
    UsuarioFacadeLocal usuarioFacadeLocal;
    @EJB
    TipoDocumentoFacadeLocal tipoDocumentoFacadeLocal;

    @Override
    public List<Cliente> findAllClientes() {
        return clienteFacadeLocal.findAll();
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return categoriaFacadeLocal.findAll();
    }

    @Override
    public void deleteCategorias(Categoria elemento) {
        categoriaFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCategorias(Categoria elemento) {
        if (elemento.getIdCategorias() == null) {
            categoriaFacadeLocal.create(elemento);
            return false;
        } else {
            categoriaFacadeLocal.edit(elemento);
            return true;
        }

    }

    @Override
    public List<Atributo> findAllAtributos() {
        return atributoFacadeLocal.findAll();
    }

    @Override
    public void deleteAtributos(Atributo elemento) {
        List<Clienteatributo> listaca = clienteatributoFacadeLocal.findByAtributo(elemento);
        List<Cliente> clientes = clienteFacadeLocal.findAll();
        for (Cliente c : clientes) {
            c.getClientesatributosList().remove(new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo()));
        }

        if (listaca != null) {
            for (Clienteatributo ca : listaca) {

                clienteatributoFacadeLocal.remove(ca);
            }
        }
        atributoFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarAtributos(Atributo elemento) {
        if (elemento.getIdAtributo() == null) {
            atributoFacadeLocal.create(elemento);
            List<Cliente> clientes = clienteFacadeLocal.findAll();
            List<Clienteatributo> clientesatributos = new ArrayList<>();
            for (Cliente c : clientes) {
                Clienteatributo ca = new Clienteatributo(c.getIdCliente(), elemento.getIdAtributo());
                ca.setAtributos(elemento);
                ca.setClientes(c);
                clienteatributoFacadeLocal.create(ca);
                clientesatributos.add(ca);
            }
            elemento.setClientesatributosList(clientesatributos);
            atributoFacadeLocal.edit(elemento);
            return false;
        } else {
            atributoFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTiposjuegos(TipoJuego elemento) {
        tipoJuegoFacadeLocal.remove(elemento);
    }

    @Override
    public List<TipoJuego> findAllTiposjuegos() {
        return tipoJuegoFacadeLocal.findAll();
    }

    @Override
    public boolean guardarTiposjuegos(TipoJuego elemento) {
        if (elemento.getIdTipoJuego() == null) {
            tipoJuegoFacadeLocal.create(elemento);
            return true;
        } else {
            tipoJuegoFacadeLocal.edit(elemento);
            return true;
        }

    }

    @Override
    public void deleteCasinos(Casino elemento) {
        casinoFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCasinos(Casino elemento) {
        if (elemento.getIdCasino() == null) {
            casinoFacadeLocal.create(elemento);
            return false;
        } else {
            casinoFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Casino> findAllCasinos() {
        return casinoFacadeLocal.findAll();
    }

    @Override
    public List<Evento> findAllEventos() {
        return eventoFacadeLocal.findAll();
    }

    @Override
    public void deleteEventos(Evento elemento) {
        File imagen = null;
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("APP_CONF"))
                .append(System.getProperty("file.separator"))
                .append("images").append(System.getProperty("file.separator"))
                .append("inversiones").append(System.getProperty("file.separator")).append(elemento.getImagen());
        imagen = new File(sb.toString());
        if (imagen.exists()) {
            imagen.delete();
        }
        eventoFacadeLocal.remove(elemento);
    }

    @Override
    public void deleteClientes(Cliente elemento) {
        List<Clienteatributo> listaca = clienteatributoFacadeLocal.findByCliente(elemento);
        if (listaca != null) {
            for (Clienteatributo ca : listaca) {
                clienteatributoFacadeLocal.remove(ca);
            }
        }
        clienteFacadeLocal.remove(elemento);
    }

    @Override
    public Cliente guardarClientes(Cliente elemento) {
        if (elemento.getIdCliente() == null) {
            clienteFacadeLocal.create(elemento);
            return elemento;
        } else {
            clienteFacadeLocal.edit(elemento);
            return elemento;
        }
    }

    @Override
    public List<TipoJuego> getTiposJuegosNoClientes(Integer idCliente) {
        Cliente cliente = clienteFacadeLocal.find(idCliente);
        List<TipoJuego> tiposjuego = tipoJuegoFacadeLocal.findAll();
        Iterator<TipoJuego> iter = tiposjuego.iterator();
        while (iter.hasNext()) {
            if (iter.next().getClientesList().contains(cliente)) {
                iter.remove();
            }
        }
        return tiposjuego;
    }

    @Override
    public Cliente findCliente(Integer integer) {
        return clienteFacadeLocal.find(integer);
    }

    @Override
    public Evento findEvento(Integer integer) {
        return eventoFacadeLocal.find(integer);
    }

    @Override
    public Evento guardarEventos(Evento elemento) {
        if (elemento.getIdEvento() == null) {

            eventoFacadeLocal.create(elemento);
            return elemento;
        } else {
            eventoFacadeLocal.edit(elemento);
            return elemento;
        }
    }

    @Override
    public void guardarImagen(byte[] contents, Integer idEvento, String fileName) {
        try {

            File imagen = null;
            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("APP_CONF"))
                    .append(System.getProperty("file.separator"))
                    .append("images").append(System.getProperty("file.separator"))
                    .append("inversiones").append(System.getProperty("file.separator")).append(idEvento).append(fileName);
            imagen = new File(sb.toString());
            if (imagen.exists()) {
                imagen.delete();
            }
            OutputStream stream = null;
            stream = new FileOutputStream(sb.toString());
            if (contents != null) {
                stream.write(contents);
            }
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MarketingUserFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MarketingUserFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Accion> findAllAcciones() {
        return accionFacadeLocal.findAll();
    }

    @Override
    public void deleteAccion(Accion elemento) {
        accionFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarAccion(Accion elemento) {
        if (elemento.getIdAccion() == null) {

            accionFacadeLocal.create(elemento);
            return false;
        } else {
            accionFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Tipotarea> findAllTipotarea() {
        return tipostareasFacadeLocal.findAll();
    }

    @Override
    public boolean guardarTipotarea(Tipotarea elemento) {
        if (elemento.getIdTipotarea() == null) {

            tipostareasFacadeLocal.create(elemento);
            return false;
        } else {
            tipostareasFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTipotarea(Tipotarea elemento) {
        tipostareasFacadeLocal.remove(elemento);
    }

    @Override
    public void deleteTarea(Tarea tarea) {
        List<Usuario> usuarios = usuarioFacadeLocal.findAll();
        for (Usuario u : usuarios) {
            if (u.getTareasList().contains(tarea)) {
                u.getTareasList().remove(tarea);
                usuarioFacadeLocal.edit(u);
            }
        }
        tareasFacadeLocal.remove(tarea);
    }

    @Override
    public Tarea guardarTarea(Tarea elemento) {
        if (elemento.getIdTarea() == null) {

            tareasFacadeLocal.create(elemento);
            return elemento;
        } else {
            tareasFacadeLocal.edit(elemento);
            return elemento;
        }
    }

    @Override
    public Accion findByNombreAccion(String nombre) {
        return accionFacadeLocal.findByNombreAccion(nombre);
    }

    @Override
    public List<Tarea> findAllTareas() {
        return tareasFacadeLocal.findAll();
    }

    @Override
    public Tarea findTarea(Integer integer) {
        return tareasFacadeLocal.find(integer);
    }

    @Override
    public List<TipoDocumento> findAllTipoDocumentos() {
        return tipoDocumentoFacadeLocal.findAll();
    }

    @Override
    public boolean guardarTipoDocumentos(TipoDocumento elemento) {
        if (elemento.getIdTipoDocumento() == null) {

            tipoDocumentoFacadeLocal.create(elemento);
            return false;
        } else {
            tipoDocumentoFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTipoDocumentos(TipoDocumento elemento) {
        tipoDocumentoFacadeLocal.remove(elemento);
    }

    @Override
    public String findNombreAccion(Integer idAccion) {
        Accion a = accionFacadeLocal.find(idAccion);
        if (a == null) {
            return "";
        } else {
            return a.getNombre();
        }
    }
}
