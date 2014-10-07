/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.exceptions;

/**
 *
 * @author ideacentre
 */
public class EventoSinClientesPorRevisarException extends Exception {

    /**
     * Creates a new instance of
     * <code>EventoSinClientesPorRevisarException</code> without detail message.
     */
    public EventoSinClientesPorRevisarException() {
        super("Evento sin clientes");
    }

    /**
     * Constructs an instance of
     * <code>EventoSinClientesPorRevisarException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public EventoSinClientesPorRevisarException(String msg) {
        super(("Evento sin clientes"));
    }
}
