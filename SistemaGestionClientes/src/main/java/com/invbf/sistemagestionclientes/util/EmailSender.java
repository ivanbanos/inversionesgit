/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.dao.ConfiguracionDao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author ideacentre
 */
public class EmailSender {

    private int port;
    private String host;
    private String from;
    private boolean auth = true;
    private String username;
    private String password;
    private Protocol protocol;
    private boolean debug = true;

    public EmailSender() {
    }

    public void sendEmail(String to, String subject, String mesaje, String nombre) throws MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        switch (protocol) {
            case SMTPS:
                props.put("mail.smtp.ssl.enable", "true");
                break;
                
            case SMTP:
            case TLS:
                props.put("mail.smtp.starttls.enable", "true");
                break;
        }

        Authenticator authenticator = null;
        if (auth) {
            props.put("mail.smtp.auth", "true");
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(username, password);

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }

        Session session = Session.getInstance(props, authenticator);
        session.setDebug(debug);
        
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        message.setRecipients(Message.RecipientType.TO, address);
        message.setSubject(subject);
        message.setSentDate(new Date());

        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<p>" + mesaje + "</p>";
        if (!nombre.equals("noimage")) {
            htmlText += "<img src=\"cid:image\">";
        }
        messageBodyPart.setContent(htmlText, "text/html");
        // add it
        multipart.addBodyPart(messageBodyPart);
        if (!nombre.equals("noimage")) {
            
            
            FTPClient client = new FTPClient();
        byte[] bytesArray = null;

        String remoteFile2 = nombre;
        try {
            String sFTP = ConfiguracionDao.findByNombre("FTP").getValor();
            String sUser = ConfiguracionDao.findByNombre("FTPuser").getValor();
            String sPassword = ConfiguracionDao.findByNombre("FTPpassword").getValor();

            client.connect(sFTP);
            boolean login = client.login(sUser, sPassword);

            int reply = client.getReplyCode();

            System.out.println("Respuesta recibida de conexión FTP:" + reply);

            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Conectado Satisfactoriamente");
            } else {
                System.out.println("Imposible conectarse al servidor");
            }
            client.changeWorkingDirectory("/home/easl4284/public_html/imagenes");
            client.setFileType(FTP.BINARY_FILE_TYPE);

            InputStream inputStream = client.retrieveFileStream(remoteFile2);
            bytesArray = IOUtils.toByteArray(inputStream);

            boolean success = client.completePendingCommand();
            if (success) {
                System.out.println("File has been downloaded successfully.");
            }
            inputStream.close();


        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {

            System.out.println(ex);
        } finally {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException ex) {

                System.out.println(ex);
            }
        }
        
            
            
            
            
            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            
            DataSource fds = new ByteArrayDataSource(bytesArray,new MimetypesFileTypeMap().getContentType(nombre));

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
        }
        // add image to the multipart
        multipart.addBodyPart(messageBodyPart);

        // put everything together
        message.setContent(multipart);

        Transport.send(message);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public void setProtocol(String protocol) {
        this.protocol = Protocol.valueOf(protocol);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
