package socket;

import gui.main;
import gui.raven.chat.component.ChatArea;
import gui.raven.chat.component.ChatBox;
import gui.raven.chat.model.ModelMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import javax.swing.*;


class CustomPair<K, V> {
    private K key;
    private V value;

    private CustomPair(){}

    public CustomPair(K k, V v) {
        this.key = k;
        this.value = v;
    }
    public static <K, V> CustomPair of (K key, V value) {
        return new CustomPair<>(key, value);
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return value;
    }
}

public class ReceiveMail {
    private String host="imap.gmail.com";
    private String username;
    private String password;

//    public ReceiveMail(String host, String username, String password) {
//        this.host = host;
//        this.username = username;
//        this.password = password;
//    }

    public void getAttachments(int id) throws MessagingException, IOException {
        this.username= SendMail.from[id];
        this.password= SendMail.password[id];
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", "993");
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("imap");
        store.connect(host, username, password);

        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_WRITE);

        Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN),false));
//        System.out.println();
        for (Message message : messages) {
            // Check if the message has attachments
            System.out.println(message.getReplyTo()[0]);
            if (message.isMimeType("multipart/*") && message.getReplyTo()[0].toString().equals(SendMail.from[id]) ) {
                Multipart multipart = (Multipart) message.getContent();
                message.setFlag(Flags.Flag.SEEN,true);
                // Iterate through the parts of the multipart content

                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);

                    // Check if the part is an attachment
                    if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                        String originalFileName = MimeUtility.decodeText(bodyPart.getFileName());
                        String extension = "";
                        int dotIndex = originalFileName.lastIndexOf('.');
                        if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
                            extension = originalFileName.substring(dotIndex);
                        }
                        String fileName="";
                        if (extension.equalsIgnoreCase(".txt"))
                            fileName = "process"+ extension;
                        else if (extension.equalsIgnoreCase(".jpg"))
                            fileName = "image"+extension;
                        // Save the attachment to a file
                        String savePath = System.getProperty("user.home")+ "/Desktop/" +fileName;
                        System.out.println(savePath);
                        // Create a File object representing the desktop directory

                        File file = new File(savePath);
                        FileOutputStream outputStream = new FileOutputStream(file);
                        bodyPart.getInputStream().transferTo(outputStream);
                        outputStream.close();

                        System.out.println("Attachment saved: " + fileName);
                    }
                }
            }
        }
//        ChatArea.addChatBox(new ModelMessage(icon, name, date, "Executed"), ChatBox.BoxType.LEFT);
    }

    public List<CustomPair<String, String>> getRequirements() {
        try {
            this.username = SendMail.from[0];
            this.password = SendMail.password[0];
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imap");
            props.setProperty("mail.imap.host", host);
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.imap.socketFactory.fallback", "false");

            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imap");
            store.connect(host, username, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            List<CustomPair<String, String>> requirements = new ArrayList<>();
            Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
//                System.out.println(message.getFrom()[0]).getAddress.toString());
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");

                String sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                if (sender.equals(App.user)){
                    String subject = message.getSubject();
                    requirements.add(new CustomPair<>(sender, subject));
                    message.setFlag(Flags.Flag.SEEN,true);
                    Icon icon = new ImageIcon(main.class.getResource("/icon/reindeer.png"));

                    String name = "Bot";
                    String date = df.format(new Date());
                    String mess = "Received request: " + subject ;
                    ChatArea.addChatBox(new ModelMessage(icon, name, date, mess), ChatBox.BoxType.LEFT);
                    // Example server response handling
                }
                else{
//                    if (message.getFlags().contains(new Flags(Flags.Flag.RECENT)))
                        SendMail.serversendEmail(sender,"Server Busy","","Server is under control by "+App.user+"\nPlease comeback later.");
                }
                message.setFlag(Flags.Flag.SEEN,true);
            }

            emailFolder.close(false);
            store.close();
            return requirements;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws MessagingException, IOException {
        ReceiveMail getMail = new ReceiveMail();
        getMail.getAttachments(1);
//        List<CustomPair<String, String>> x = getMail.getRequirements(App.user);
//        for (CustomPair<String, String> i : x) {
//            System.out.println(i.getKey() + ": " + i.getValue() + "h");
//        }
    }
}
