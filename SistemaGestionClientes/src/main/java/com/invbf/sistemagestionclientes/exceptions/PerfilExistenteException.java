/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.exceptions;

/**
 *
 * @author ideacentre
 */
public class PerfilExistenteException extends Exception {

    /**
     * Creates a new instance of
     * <code>PerfilExistenteException</code> without detail message.
     */
    public PerfilExistenteException() {
    }

    /**
     * Constructs an instance of
     * <code>PerfilExistenteException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PerfilExistenteException(String msg) {
        super(msg);
    }
}
