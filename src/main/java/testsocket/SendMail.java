package testsocket;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail {
    static final String from = "pvhoangnamzz@gmail.com";
    static final String password = "drzd dpmu evff ejqj";

    public static void sendEmail(String to, String subject, String content) {

        //Properties: Khai bao cac thuoc tinh
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP host
        props.put("mail.smtp.port", "587"); //TLS 587, SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        //Phien
        Session session = Session.getInstance(props, auth);

        //Gui mail
//        final String to = "pvhn191004@gmail.com";
        MimeMessage msg = new MimeMessage(session);
        try {
            //Kieu noi dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            //Nguoi gui
            msg.setFrom(from);
            //Nguoi nhan
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            //Tieu de email
            msg.setSubject(subject);
            //Noi dung
            msg.setContent(content, "text/html");
            Transport.send(msg);
            System.out.println("Send email success!");
        } catch (Exception e) {
            System.out.println("Send email failed!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SendMail.sendEmail("pvhn191004@gmail.com", "Di ngu thoi","<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Page Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>This is a Heading</h1>\n" +
                "<p>This is a paragraph.</p>\n" +
                "\n" +
                "<img src=\"https://img.cdn-pictorem.com/uploads/collection/I/IB5PAB9RBI/900_Anime_7_1608090041.5705.jpg\" alt=\"Naruto\">+" +
                "</body>\n" +
                "</html>");
    }
}
