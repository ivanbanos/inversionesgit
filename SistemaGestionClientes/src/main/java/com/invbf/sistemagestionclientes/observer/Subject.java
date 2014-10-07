/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.observer;

/**
 *
 * @author ideacentre
 */
public interface Subject {
    public void removeObserver(Observer o);
    public void registerObserver(Observer o);
    public void notifyObserver(String tabla);
    
}
