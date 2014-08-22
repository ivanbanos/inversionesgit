/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.exceptions;

/**
 *
 * @author ideacentre
 */
public class NoCambioContrasenaException extends Exception {

    /**
     * Creates a new instance of
     * <code>NoCambioContrasenaException</code> without detail message.
     */
    public NoCambioContrasenaException() {
    }

    /**
     * Constructs an instance of
     * <code>NoCambioContrasenaException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NoCambioContrasenaException(String msg) {
        super(msg);
    }
}
