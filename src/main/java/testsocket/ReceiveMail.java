package testsocket;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import com.sun.mail.pop3.POP3Store;


//Create Pair class
class CustomPair<K, V> {
    private K key;
    private V value;

    private CustomPair(){}

    private CustomPair(K k, V v) {
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

public class ReceiveMail{
    public static void receiveEmail(String hst, String stype,
                                    String user, String password) {
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "pop3");
            props.put("mail.pop3s.host", hst);
            props.put("mail.pop3s.port", "995");
            props.put("mail.pop3.starttls.enable", "true");

            Session sess = Session.getDefaultInstance(props);
            Store st = sess.getStore("pop3s");
            st.connect(hst, user, password);
            Folder emailFolder = st.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message[] messages = emailFolder.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("Welcome To Email");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
            }
            emailFolder.close(false);
            st.close();
        }
        catch (NoSuchProviderException e) {e.printStackTrace();}
        catch (MessagingException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
    }
    public static void main(String[] args) {
        String host = "pop.gmail.com";
        String stypes = "pop3";
        String username= "pvhoangnamzz@gmail.com";
        String password= "drzd dpmu evff ejqj";
        receiveEmail(host, stypes, username, password);
    }
}




