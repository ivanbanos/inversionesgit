/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.util;

import com.invbf.adminclientesapi.entity.Estadocliente;
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
@FacesConverter("estadosClientescom")
public class estadosClientescom implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Estadocliente estadocliente;
        estadocliente = new Estadocliente();
        estadocliente.setIdEstadoCliente(Integer.parseInt(value.split(" ")[0]));
        estadocliente.setNombre(value.split(" ")[1]);
        return estadocliente;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            return ((Estadocliente)value).toString();
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
    }
    
}
