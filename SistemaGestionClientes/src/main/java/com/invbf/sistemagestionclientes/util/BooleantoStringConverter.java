/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ideacentre
 */
@FacesConverter("stringtoboolean")
public class BooleantoStringConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value.toLowerCase().equals("true")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Boolean)value).toString();
    }
    
}
