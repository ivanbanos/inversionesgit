/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.exceptions;

/**
 *
 * @author ideacentre
 */
public class UsuarioNoCreadoException extends Exception{

    public UsuarioNoCreadoException() {
        super("Usuario no creado.");
    }

    public UsuarioNoCreadoException(String message) {
        super("Usuario no creado. Causa: "+message);
    }
    
}
