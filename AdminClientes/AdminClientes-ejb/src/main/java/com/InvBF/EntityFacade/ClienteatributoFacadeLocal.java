/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteatributo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface ClienteatributoFacadeLocal {

    void create(Clienteatributo clienteatributo);

    void edit(Clienteatributo clienteatributo);

    void remove(Clienteatributo clienteatributo);

    Clienteatributo find(Object id);

    List<Clienteatributo> findAll();

    List<Clienteatributo> findRange(int[] range);

    int count();

    public List<Clienteatributo> findByCliente(Cliente elemento);

    public List<Clienteatributo> findByAtributo(Atributo elemento);
    
}
