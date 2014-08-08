package com.InvBF.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Clase para cargar el Log4j
 * @author mfmartinez
 */
@Startup
@Singleton
public class Log4jStartupBean {

    private static final Logger LOGGER =
        Logger.getLogger(Log4jStartupBean.class);
    private static final String APP_CONF_VAR = "APP_CONF";
    private static final String APP_NAME = "AdminClientes";
    private static final Long LOG_WATCH_DELAY = 30000L;

    @PostConstruct
    public void initLogging() {
        String appConfiguration = System.getProperty(APP_CONF_VAR);
        if (appConfiguration == null || appConfiguration.isEmpty()) {
            appConfiguration = System.getenv(APP_NAME);
        } else {
            String fileSeparator = System.getProperty("file.separator");
            String logConf = appConfiguration + fileSeparator + "config"
                + fileSeparator + APP_NAME + fileSeparator + "log4j.xml";
            
            DOMConfigurator.configureAndWatch(logConf, LOG_WATCH_DELAY);
            LOGGER.info("LOG 4J CONFIGURADO");
        }
    }
}