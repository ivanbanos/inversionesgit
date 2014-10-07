/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

/**
 *
 * @author iabaños
 *
 * SessionBean para administrar la coneccion con la base de datos
 */
public class DBConnection {

    private DataSource monitorCertDigDS; //Datasourse del pool de conecciones a AS400
    private Connection connection;// Objeto que referencia la coneccion

    /**
     * metodo que retorna la coneccion a la base de datos
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdminClientesPU");
                
                Class.forName((String)emf.getProperties().get("javax.persistence.jdbc.driver"));
                connection = DriverManager.getConnection((String)emf.getProperties().get("javax.persistence.jdbc.url"), 
                        (String)emf.getProperties().get("javax.persistence.jdbc.user"), 
                        (String)emf.getProperties().get("javax.persistence.jdbc.password"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
        }

        return connection;
    }

    public void shutdown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }
}
