/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author iabaños
 * 
 * SessionBean para administrar la coneccion con la base de datos
 */
@Stateful
@LocalBean
public class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class);
    @Resource(name = "jdbc/AdminClientesInversiones")
    private DataSource monitorCertDigDS; //Datasourse del pool de conecciones a AS400
    private Connection connection;// Objeto que referencia la coneccion

    /**
     * metodo que retorna la coneccion a la base de datos
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                LOGGER.debug("obteniendo coneccion");
                connection = monitorCertDigDS.getConnection();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }

        return connection;
    }

    @PreDestroy
    public void shutdown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                LOGGER.error(ex);
            }
        }
    }
}
