/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.Vista;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ideacentre
 */
@FacesConverter("vistacom")
public class VistasConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            return ((Vista)value).toString();
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
       
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Vista vistas;
        vistas = new Vista();
        vistas.setIdVista(Integer.parseInt(value.split(" ")[0]));
        vistas.setNombreVista(value.split(" ")[1]);
        return vistas;
    }
}
