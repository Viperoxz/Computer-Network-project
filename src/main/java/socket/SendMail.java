package socket;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendMail {
    static final String[] from = {"pvhoangnamzz@gmail.com", "onghoangcodebug01@gmail.com"};
    static final String[] password = {"drzd dpmu evff ejqj", "reddragonDT"};
    public static void clientsendEmail(String subject)  {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP host
        props.put("mail.smtp.port", "587"); //TLS 587, SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from[0], password[0]);
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
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(from[0]));
            //Tieu de email
            msg.setSubject(subject);
            System.out.println("Send "+subject);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void serversendEmail(String to, String subject, String attachment_path, String content) {

        //Properties: Khai bao cac thuoc tinh
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP host
        props.put("mail.smtp.port", "587"); //TLS 587, SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Create Authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from[0], password[0]);
            }
        });

        //Gui mail
        try {
            MimeMessage msg = new MimeMessage(session);
            //Kieu noi dung
//            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            //Nguoi gui
            msg.setFrom(from[0]);
            //Nguoi nhan
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            //Tieu de email
            msg.setSubject(subject);


            //Body
            Multipart multipart = new MimeMultipart();

            // Create the attachment part
            if (attachment_path != "") {
                String filename = attachment_path;
                DataSource source = new FileDataSource(filename);
                BodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(filename);
                multipart.addBodyPart(attachmentBodyPart);
            }

            //HTML part
            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(content, "text/html; charset=utf-8"); //5
            multipart.addBodyPart(htmlBodyPart);

            msg.setContent(multipart);

            //Noi dung
            Transport.send(msg);
            System.out.println("Send email success!");
        } catch (Exception e) {
            System.out.println("Send email failed!");
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        SendMail.serversendEmail("pvhn191004@gmail.com", "Di ngu thoi", "D:\\Mạng máy tính\\Slide_En\\Chapter_1_v8.1- Introduction.pptx",
//                "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "<title>Page Title</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<h1>This is a Heading</h1>\n" +
//                "<p>This is a paragraph.</p>\n" +
//                "\n" +
//                "<img src=\"https://img.cdn-pictorem.com/uploads/collection/I/IB5PAB9RBI/900_Anime_7_1608090041.5705.jpg\" alt=\"Naruto\">+" +
//                "</body>\n" +
//                "</html>");
//    }
}
