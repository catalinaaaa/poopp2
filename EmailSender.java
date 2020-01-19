/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
    // Se definen las variables a utilizar para mandar el 
    private String username = "mapacheRENTACAR10@gmail.com";
    private String password = "mapache123";
    
    // Constructores
    public EmailSender(){
        
    }
    
    /**Método que permite cambiar el usuario del correo
     *@param  username almacena el nombre usuario de la empresa mapache
     *@param  password almacena la contrseña 
     */
    public EmailSender(String username, String password){
        this.username = username;
        this.password = password;
    }
    // Setters y getters
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
    
    /**
     * Método que permite enviar la el correo
     * @param  destinatario almacena el correo del destinatio 
     * @param direccionArchivo almacena la ruta del archivo que se va a adjuntar al correo
     * @return true en caso de que se envie el correo
     */
    public boolean SendEmail(String destinatario, String direccionArchivo){
        // Correo desde donde se va a mandar 
        String fromEmail = "mapacheRENTACAR10@gmail.com";

        // Autenticación 
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                }
        });
        
        // Crear el email
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            // Título para el correo
            msg.setSubject("Información Reserva");

            Multipart emailContent = new MimeMultipart();

            // Crea el texto del mensaje
            MimeBodyPart text = new MimeBodyPart();
            text.setText("A continuación se adjunta la infomación de la reserva.");

            // Carga el archivo adjunto
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(direccionArchivo);
            
            // Crea el codigo del mensaje
            MimeBodyPart codigo = new MimeBodyPart();
            codigo.setText(direccionArchivo);

            // Agrega las partes al contenido
            emailContent.addBodyPart(text);
            //emailContent.addBodyPart(attachment);
            emailContent.addBodyPart(codigo);

            // Agrega el contenido al mensaje
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            System.out.println("Error al intentar enviar el correo.");
            return false;
        } catch (IOException e) {
            System.out.println("Error al intentar abrir el archivo");
            return false;
        }
        return true;
    }
}