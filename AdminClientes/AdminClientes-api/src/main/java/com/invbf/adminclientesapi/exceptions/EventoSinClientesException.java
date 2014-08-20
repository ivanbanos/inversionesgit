/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.exceptions;

/**
 *
 * @author ideacentre
 */
public class EventoSinClientesException extends Exception {

    /**
     * Creates a new instance of
     * <code>EventoSinClientesException</code> without detail message.
     */
    public EventoSinClientesException() {
        super("Evento sin clientes");
    }

    /**
     * Constructs an instance of
     * <code>EventoSinClientesException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public EventoSinClientesException(String msg) {
        super(("Evento sin clientes"));
    }
}
