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
public class UsuarioSinAccesoalSistemaException extends Exception {

    /**
     * Creates a new instance of <code>UsuarioSinAccesoalSistemaException</code>
     * without detail message.
     */
    public UsuarioSinAccesoalSistemaException() {
    }

    /**
     * Constructs an instance of <code>UsuarioSinAccesoalSistemaException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UsuarioSinAccesoalSistemaException(String msg) {
        super(msg);
    }
}
