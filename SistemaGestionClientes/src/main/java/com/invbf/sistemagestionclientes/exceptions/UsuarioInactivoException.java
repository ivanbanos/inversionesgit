/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.exceptions;

/**
 *
 * @author ivan
 */
public class UsuarioInactivoException extends Exception {

    /**
     * Creates a new instance of <code>UsuarioInactivoException</code> without
     * detail message.
     */
    public UsuarioInactivoException() {
    }

    /**
     * Constructs an instance of <code>UsuarioInactivoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UsuarioInactivoException(String msg) {
        super(msg);
    }
}
