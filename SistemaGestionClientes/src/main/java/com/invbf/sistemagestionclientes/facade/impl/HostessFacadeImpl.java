/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.facade.impl;

import com.invbf.sistemagestionclientes.dao.AccionDao;
import com.invbf.sistemagestionclientes.dao.ConfiguracionDao;
import com.invbf.sistemagestionclientes.dao.ListasclientestareasDao;
import com.invbf.sistemagestionclientes.dao.TareasDao;
import com.invbf.sistemagestionclientes.dao.TipostareasDao;
import com.invbf.sistemagestionclientes.entity.Accion;
import com.invbf.sistemagestionclientes.entity.Listasclientestareas;
import com.invbf.sistemagestionclientes.entity.Tarea;
import com.invbf.sistemagestionclientes.entity.Tipotarea;
import com.invbf.sistemagestionclientes.entity.Usuario;
import com.invbf.sistemagestionclientes.exceptions.EventoSinClientesPorRevisarException;
import com.invbf.sistemagestionclientes.facade.HostessFacade;
import com.invbf.sistemagestionclientes.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ideacentre
 */
public class HostessFacadeImpl implements HostessFacade {

    @Override
    public int findCantidadClientes() {
        return Integer.parseInt(ConfiguracionDao.findByNombre("CantidadClientes").getValor());
    }

    @Override
    public List<Tarea> findTareaHostess(Usuario usuario) {
        List<Tarea> tareasHostess;
        tareasHostess = TareasDao.findAll();
        Iterator<Tarea> iterator = tareasHostess.iterator();
        Tipotarea tipo = TipostareasDao.findBynombre("EMAIL");
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
    public List<Listasclientestareas> findClienteTareaHostess(Integer integer) throws EventoSinClientesPorRevisarException {
        Tarea evento = TareasDao.find(integer);
        List<Listasclientestareas> clientes = evento.getListasclientestareasList();
        List<Listasclientestareas> clientesAEnviar = new ArrayList<Listasclientestareas>();
        Iterator<Listasclientestareas> iterator = clientes.iterator();

        int cantidadClientes = findCantidadClientes();
        Accion inicial = AccionDao.findByNombreAccion("INICIAL");
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
        return AccionDao.findByNombreAccion(idAccion);
    }

    @Override
    public void guardarLCE(Listasclientestareas l, Integer idAccion) {
        try {
            l.setIdAccion(AccionDao.find(idAccion));

            DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
            df.setTimeZone(timeZone);
            Calendar nowDate = Calendar.getInstance();
            nowDate.setTime(df2.parse(df.format(nowDate.getTime())));
            l.setFechaAtencion(nowDate.getTime());
            ListasclientestareasDao.edit(l);
        } catch (ParseException ex) {
            Logger.getLogger(HostessFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Listasclientestareas nuevoLCE(Integer index, List<Listasclientestareas> clientes, Listasclientestareas l) throws EventoSinClientesPorRevisarException {
        Tarea tarea = TareasDao.find(index);
        List<Listasclientestareas> clientesEv = tarea.getListasclientestareasList();
        Iterator<Listasclientestareas> iterator = clientesEv.iterator();

        Accion inicial = AccionDao.findByNombreAccion("Inicial");

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

        return TipostareasDao.findBynombre("EMAIL");
    }
}
