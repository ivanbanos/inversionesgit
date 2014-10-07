/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.Usuario;
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
@FacesConverter("usercom")
public class UsuariosConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        
        if (value == null) {
            return null;
        }
        // value must be of a type that can be cast to a String.
        try {
            return ((Usuario)value).toString();
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage("Error al convertir valor");
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }
       
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Usuario usuarios;
        usuarios = new Usuario();
        usuarios.setIdUsuario(Integer.parseInt(value.split(" ")[0]));
        usuarios.setNombreUsuario(value.split(" ")[1]);
        return usuarios;
    }
}