package com.chainsys.util;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;

public class Mail {

    static Session newSession = null;

    public static void main(String[] args) {
        try {
            setProperties();
            setMailBody();
            System.out.println("Email sent successfully.");
        } catch (Exception ex) {
            System.out.println("Failed to send email.");
            ex.printStackTrace();
        }
    }

    public static void setProperties() {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        newSession = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kishorkishor@gmail.com", "acls xrcz qgzp uqjr");
            }
        });
    }

    public static void setMailBody() throws AddressException, MessagingException {
        String[] emailRecipients = { "thiruvikramarajan@gmail.com" };
        String emailSubject = "Credit Card Approval";
        String emailBody = "Test body";

        Message mimeMessage = new MimeMessage(newSession);
        mimeMessage.setFrom(new InternetAddress("your-email@example.com"));

        for (String recipient : emailRecipients) {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }

        mimeMessage.setSubject(emailSubject);
        mimeMessage.setText(emailBody);

        Transport.send(mimeMessage);
    }
    public static void sendEmail(String recipientEmail, String emailSubject, String emailBody) 
            throws AddressException, MessagingException {
        setProperties();

        Message mimeMessage = new MimeMessage(newSession);
        mimeMessage.setFrom(new InternetAddress("kishorkishor2003@gmail.com"));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        mimeMessage.setSubject(emailSubject);
        mimeMessage.setText(emailBody);

        Transport.send(mimeMessage);
    }
}
