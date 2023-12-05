package socket;

import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;




public class ReceiveMail {
    private String host;
    private String username;
    private String password;

    public ReceiveMail(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public List<CustomPair<String, String>> getRequirements() {
        try {
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
                String sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                String subject = message.getSubject();
                requirements.add(new CustomPair<>(sender, subject));
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

//    public static void main(String[] args) {
//        ReceiveMail getMail = new ReceiveMail("imap.gmail.com", "pvhoangnamzz@gmail.com", "drzd dpmu evff ejqj");
//        List<CustomPair<String, String>> x = getMail.getRequirements();
//        for (CustomPair<String, String> i : x) {
//            System.out.println(i.getKey() + ": " + i.getValue() + "h");
//        }
//    }
}
