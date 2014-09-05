/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.exceptions;

/**
 *
 * @author ideacentre
 */
public class MensajeNoEnviadoException extends Exception {

    /**
     * Creates a new instance of
     * <code>MensajeNoEnviadoException</code> without detail message.
     */
    public MensajeNoEnviadoException() {
    }

    /**
     * Constructs an instance of
     * <code>MensajeNoEnviadoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MensajeNoEnviadoException(String msg) {
        super(msg);
    }
}
