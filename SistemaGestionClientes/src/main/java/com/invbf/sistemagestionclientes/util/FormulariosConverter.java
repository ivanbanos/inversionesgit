/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.Formulario;
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
@FacesConverter("formcom")
public class FormulariosConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            return ((Formulario)value).toString();
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
       
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Formulario formulario;
        formulario = new Formulario();
        formulario.setIdFormulario(Integer.parseInt(value.split(" ")[0]));
        formulario.setTabla(value.split(" ")[1]);
        formulario.setAccion(value.split(" ")[2]);
        return formulario;
    }
}
