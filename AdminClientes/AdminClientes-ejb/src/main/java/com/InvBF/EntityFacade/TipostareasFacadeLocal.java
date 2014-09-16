/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.EntityFacade;

import com.invbf.adminclientesapi.entity.Tipotarea;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ideacentre
 */
@Local
public interface TipostareasFacadeLocal {

    void create(Tipotarea tipostareas);

    void edit(Tipotarea tipostareas);

    void remove(Tipotarea tipostareas);

    Tipotarea find(Object id);

    List<Tipotarea> findAll();

    List<Tipotarea> findRange(int[] range);

    int count();

    public Tipotarea findBynombre(String emaiL);
    
}
