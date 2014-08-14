/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Usuarios;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface HostessFacade {

    public List<Eventos> findEventosHostess(Usuarios usuario);
}
