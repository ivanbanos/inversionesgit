/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.exceptions;

/**
 *
 * @author ideacentre
 */
public class UsuarioNoConectadoException extends Exception{

    public UsuarioNoConectadoException() {
        super("Usuario no conectado.");
    }

    public UsuarioNoConectadoException(String message) {
        super("Usuario no conectado. Causa: "+message);
    }
    
}
