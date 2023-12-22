package server;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class SendMail {

    static final String[] from = {"pvhoangnamzz@gmail.com","neildo0408@gmail.com", "computernetworkinggroup5@gmail.com"};
    static final String[] password = {"drzd dpmu evff ejqj","ixzg pazh pwmz bqaf", "vexk yroe jygo mqlh"};

    public static void clientsendEmail(String subject)  {
        new Thread(()->{
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP host
            props.put("mail.smtp.port", "587"); //TLS 587, SSL 465
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from[1], password[1]);
                }
            };
            Session session = Session.getInstance(props, auth);
            try {
                MimeMessage msg = new MimeMessage(session);
                //Kieu noi dung
//            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                //Nguoi gui
                msg.setFrom(new InternetAddress(from[1]));
                //Nguoi nhan
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(from[2]));
                //Tieu de email
                msg.setSubject(subject);
                msg.setText("hi");
                Transport.send(msg);
                System.out.println("Sent "+subject);
            }catch (Exception e){
                System.out.println("Sending failed");
                e.printStackTrace();
            }
        }).start();


    }
    public static void serversendEmail(String to, String subject, String attachment_path, String content) {
        System.out.println(subject);
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP host
        props.put("mail.smtp.port", "587"); //TLS 587, SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from[2], password[2]);
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(from[2]);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            Multipart multipart = new MimeMultipart();
            if (attachment_path != "") {
                String filename = attachment_path;
                DataSource source = new FileDataSource(filename);
                BodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(filename);
                multipart.addBodyPart(attachmentBodyPart);
            }
            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(content, "text/html; charset=utf-8"); //5
            multipart.addBodyPart(htmlBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("Send email success!");
        } catch (Exception e) {
            System.out.println("Send email failed!");
            e.printStackTrace();
        }
    }
}
