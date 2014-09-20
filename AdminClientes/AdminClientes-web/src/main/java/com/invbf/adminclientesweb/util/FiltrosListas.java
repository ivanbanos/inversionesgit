/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.util;

import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Perfil;
import com.invbf.adminclientesapi.entity.Tipotarea;
import com.invbf.adminclientesapi.facade.AdminFacade;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Celula4
 */
@ManagedBean
@RequestScoped
public class FiltrosListas {

    @EJB
    AdminFacade adminFacade;
    @EJB
    MarketingUserFacade marketingUserFacade;

    public FiltrosListas() {
    }

    @PostConstruct
    public void init() {
    }

    public List<String> getPerfiles() {
        List<String> lista = new ArrayList<String>();
        List<Perfil> perfiles = adminFacade.findAllPerfiles();
        for (Perfil p : perfiles) {
            lista.add(p.getNombre());
        }
        return lista;
    }

    public List<String> getCategorias() {
        List<String> lista = new ArrayList<String>();
        List<Categoria> perfiles = marketingUserFacade.findAllCategorias();
        for (Categoria p : perfiles) {
            lista.add(p.getNombre());
        }
        return lista;
    }

    public List<String> getEstados() {
        List<String> lista = new ArrayList<String>();
        lista.add("POR INICIAR");
        lista.add("ACTIVO");
        lista.add("VENCIDO");
        return lista;
    }

    public List<String> getTipostareas() {
        List<String> lista = new ArrayList<String>();
        List<Tipotarea> perfiles = marketingUserFacade.findAllTipotarea();
        for (Tipotarea p : perfiles) {
            lista.add(p.getNombre());
        }
        return lista;
    }

    public List<String> getTipoident() {
        List<String> lista = new ArrayList<String>();
        lista.add("CC");
        lista.add("Pasaporte");
        lista.add("Otro");
        return lista;

    }
}
