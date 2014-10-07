/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.exceptions;

/**
 *
 * @author ideacentre
 */
public class NombreUsuarioExistenteException extends Exception {

    /**
     * Creates a new instance of
     * <code>NombreUsuarioExistenteException</code> without detail message.
     */
    public NombreUsuarioExistenteException() {
    }

    /**
     * Constructs an instance of
     * <code>NombreUsuarioExistenteException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NombreUsuarioExistenteException(String msg) {
        super(msg);
    }
}
