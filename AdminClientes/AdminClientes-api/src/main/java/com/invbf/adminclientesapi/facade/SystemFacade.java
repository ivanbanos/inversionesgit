/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.Usuarios;
import com.invbf.adminclientesapi.exceptions.ClavesNoConcuerdanException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoConectadoException;
import com.invbf.adminclientesapi.exceptions.UsuarioNoExisteException;

/**
 *
 * @author ideacentre
 */
public interface SystemFacade {

    public Usuarios iniciarSession(Usuarios usuario)throws ClavesNoConcuerdanException, UsuarioNoExisteException, UsuarioNoConectadoException ;

    
}
