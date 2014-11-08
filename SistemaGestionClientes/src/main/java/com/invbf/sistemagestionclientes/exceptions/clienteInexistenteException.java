/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.exceptions;

/**
 *
 * @author ideacentre
 */
public class clienteInexistenteException extends Exception {

    /**
     * Creates a new instance of
     * <code>clienteInexistenteException</code> without detail message.
     */
    public clienteInexistenteException() {
    }

    /**
     * Constructs an instance of
     * <code>clienteInexistenteException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public clienteInexistenteException(String msg) {
        super(msg);
    }
}
