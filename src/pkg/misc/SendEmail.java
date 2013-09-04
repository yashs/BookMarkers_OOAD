package pkg.misc;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

public class SendEmail
{
	private static String HOST = "smtp.gmail.com";
    private static String USER = "bookmakersscu@gmail.com";
    private static String PASSWORD = "Admin123!";
    private static String PORT = "465";
    private static String FROM = "bookmakersscu@gmail.com";
    private static String TO = null;
 
    private static String STARTTLS = "true";
    private static String AUTH = "true";
    private static String DEBUG = "true";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static String SUBJECT = "Bookmarkers Library Registration Confirmation Code";
   // private static String TEXT = "This is a Validation message from Clinical Trials. Please click the below link to confirm your registration.\n";
 
    public static synchronized void send(String to, String confirmCode) {
        
    	TO = to;
    	String TEXT = null;
    	TEXT = "This is a Validation message from BookMarkers Library. Confirmation Code is as Below:\n\n";
    	TEXT = TEXT + confirmCode;
    	//Use Properties object to set environment properties
        Properties props = new Properties();
        
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.user", USER);
 
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);
        props.put("mail.smtp.debug", DEBUG);
 
        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
 
        try {
 
            //Obtain the default mail session
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
 
            //Construct the mail message
            MimeMessage message = new MimeMessage(session);
            message.setText(TEXT);
            message.setSubject(SUBJECT);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(RecipientType.TO, new InternetAddress(TO));
            message.saveChanges();
 
            //Use Transport to deliver the message
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    public static void main(String[] args) {
    	SendEmail.send("riteshhiremath@gmail.com", "TEST");
       System.out.println("Mail sent successfully!");
    }
}