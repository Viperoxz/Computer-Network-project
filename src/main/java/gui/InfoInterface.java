package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import gui.raven.chat.swing.Button;
import gui.raven.chat.swing.ChatEvent;
import gui.raven.chat.swing.RoundPanel;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import net.miginfocom.swing.MigLayout;
import services.HTMLGenerator;
import server.ServerProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import gui.raven.chat.swing.TextField;
import server.SendMail;

public class InfoInterface extends JPanel implements ActionListener {
    private List<ChatEvent> events = new ArrayList<>();
    private MigLayout layout;
    private JLayeredPane layeredPane,layeredPaneNew;
    private JPanel header,headerNew;
    private JPanel body,bodyNew;
    private JPanel bottom;
    private TextField textMessage;
    private Button add;
    private Button rmv;
    private JScrollPane scrollPane,scrollPaneNew;
//    Button del;
    private ArrayList<JPanel> listPanel=new ArrayList<>();
    private ArrayList<JPanel> listPanelNew=new ArrayList<>();
    private JPanel createUser( String name,int id,ArrayList<JPanel>list){
        Color []c={Color.green,Color.white};
        GoogleMaterialIcon []icon={
                new GoogleMaterialIcon(GoogleMaterialDesignIcon.CLOSE,GradientType.VERTICAL,new Color(204, 49, 73, 165),new Color(134, 13, 13, 234),30),
                new GoogleMaterialIcon(GoogleMaterialDesignIcon.CHECK,GradientType.VERTICAL,new Color(8, 238, 49, 165),new Color(39, 133, 19, 234),30)
        };
        JPanel panel= new JPanel();
        panel.setName(name);
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        Button del;
        del= new Button();

        del.setIcon(icon[id].toIcon());
        del.setVisible(false);
        del.setEnabled(false);
        if (id==0){
            del.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ServerProcess.users.remove(name);
                    updateTask(scrollPane,body,listPanel,0);
//                    JOptionPane.showMessageDialog(,"Removed user");
                }
            });
        }else{//id==1
            del.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ServerProcess.newUsers.remove(name);
                    ServerProcess.users.add(name);
                    updateTask(scrollPane,body,listPanel,0);
                    updateTask(scrollPaneNew,bodyNew,listPanelNew,1);
                    new Thread(()->SendMail.serversendEmail(name, "Reply for request control PC", "",
                            HTMLGenerator.generateHTML("You are now authorized", "",
                                    "Your upcoming requests will be proceeded"))).start();
                }
            });
        }

        JLabel textArea = new JLabel();

        textArea.setAlignmentX(LEFT_ALIGNMENT);
        textArea.setFont(new Font("Consolas",Font.PLAIN,17));
        textArea.setForeground(c[id]);


        textArea.setText(name);
        panel.add(textArea);
        panel.add(del);
        panel.setBackground(new Color(0,0,0,0));
        list.add(panel);
//        System.out.println(listPanel.size());
        return panel;
    }

    private void checkPanel(Point a,ArrayList<JPanel>list){
        for (JPanel panel: list ){
            boolean tmp=panel.getBounds().contains(a);

            panel.getComponent(1).setVisible(tmp);
            panel.getComponent(1).setEnabled(tmp);

//            System.out.println(panel.getComponent(1).isVisible());
        }
    }
    private JPanel createHeader(String title){
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new MigLayout("fill, inset 2 10 2 10"));
        JLabel header= new JLabel(title);
        header.setForeground(Color.white);
//        panel.setBackground(new Color(34, 93, 85, 81));
        header.setFont(new Font("Consolas",Font.BOLD,20));
        panel.add(header);
        return panel;
    }


    private JPanel createBody(ArrayList<JPanel> list){
        RoundPanel panel = new RoundPanel();
        panel.setBackground(new Color(62,66,68,255));
        panel.setLayout(new MigLayout("wrap,fillx"));
        panel.setOpaque(false);
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkPanel(e.getPoint(),list);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        return panel;


    }

    private JPanel createBottom(){
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new MigLayout("fill, inset 2", "[fill]3[fill,30!]", "[bottom]"));
        add = new Button();
        rmv= new Button();
        add.setFocusable(false);
        rmv.setFocusable(false);
        GoogleMaterialIcon addIcon= new GoogleMaterialIcon(GoogleMaterialDesignIcon.ADD,GradientType.VERTICAL,Color.green,Color.green,22);
        GoogleMaterialIcon rmvIcon= new GoogleMaterialIcon(GoogleMaterialDesignIcon.REMOVE,GradientType.VERTICAL,Color.orange,Color.orange,22);
        add.setIcon(addIcon.toIcon());
        rmv.setIcon(rmvIcon.toIcon());
        textMessage = new TextField();
        textMessage.setFont(new Font("Consolas",Font.PLAIN,14));
        textMessage.setHint("Enter new email ...");
        textMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                revalidate();
            }

            @Override
            public void keyTyped(KeyEvent ke) {
                runEventKeyTyped(ke);
            }
        });
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        scroll.setBackground(new Color(67, 121, 118, 61));
        scroll.setViewportView(textMessage);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        add.addActionListener(this);
        rmv.addActionListener(this);
        panel.add(scroll);
        panel.add(add,"height 30!");
//        panel.add(rmv);
        return panel;
    }

    private JScrollPane createScroll(JPanel body){
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setViewportView(body);
        scrollPane.setVerticalScrollBar(new raven.chat.swing.scroll.ScrollBar());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
    }
    private JLayeredPane createLayeredPane() {
        JLayeredPane layer = new JLayeredPane();
        MigLayout layoutLayered = new MigLayout("fill,inset 0", "[fill]", "[fill]");
        layer.setLayout(layoutLayered);
        return layer;
    }

    InfoInterface(){
        setOpaque(false);
        layout = new MigLayout("fill, wrap, inset 0", "[fill]", "[fill,40!][fill, 100!][fill,40!][fill,100%][shrink 0,::30%]");
        header = createHeader("Active users");
        headerNew= createHeader("Requested users");
        body = createBody(listPanel);
        bodyNew = createBody(listPanelNew);
        bottom= createBottom();
        scrollPane= createScroll(body);
        scrollPaneNew = createScroll(bodyNew);
        layeredPane= createLayeredPane();
        layeredPane.add(scrollPane);
        layeredPaneNew= createLayeredPane();
        layeredPaneNew.add(scrollPaneNew);
        setLayout(layout);
        add(headerNew);
        add(layeredPaneNew);
        add(header);
        add(layeredPane);
        add(bottom);
        new Thread(this::checkNewUser).start();
    }
    private void updateTask(JScrollPane scroll,JPanel bo,ArrayList<JPanel>list,int id){
        int values = scroll.getVerticalScrollBar().getValue();
        bo.removeAll();
        list.clear();
        if (id==0)
            for(int i = 0; i< ServerProcess.users.size(); i++){
                bo.add(createUser(ServerProcess.users.get(i),0,list));
            }
        else
            for (int i = 0; i< ServerProcess.newUsers.size(); i++){
                bo.add(createUser(ServerProcess.newUsers.get(i),1,list));
            }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                bo.revalidate();
                scroll.getVerticalScrollBar().setValue(values);
            }
        });
        bo.repaint();
        bo.revalidate();
        scroll.revalidate();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        runEventMousePressedSendButton(e);
        if (e.getSource() == add) {
            String nw = textMessage.getText();
            if (!nw.isEmpty()) {
                if (!nw.contains("@gmail.com") ) {
                    JOptionPane.showMessageDialog(this, "Invalid email address");
                } else if (ServerProcess.users.contains(nw)){
                    JOptionPane.showMessageDialog(this, "This users is already added");
                }
                else {
                    ServerProcess.users.add(nw);
                    updateTask(scrollPane,body,listPanel,0);
                    updateTask(scrollPaneNew,bodyNew,listPanelNew,1);
                    textMessage.setText("");
                    JOptionPane.showMessageDialog(this, "Added successfully");
                }


            }
        }
    }
    private void runEventMousePressedSendButton(ActionEvent evt) {
        for (ChatEvent event : events) {
            event.mousePressedSendButton(evt);
        }
    }
    private void runEventKeyTyped(KeyEvent evt) {
        for (ChatEvent event : events) {
            event.keyTyped(evt);
        }
    }

    private void checkNewUser(){
        try{
            while(true){
                updateTask(scrollPaneNew,bodyNew,listPanelNew,1);
                Thread.sleep(7000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame f= new JFrame();
        f.setSize(350,450);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ServerProcess.newUsers.add("a");
        ServerProcess.newUsers.add("b");
        ServerProcess.newUsers.add("c");

        f.add(new InfoInterface());
        f.setVisible(true);
//        f.pack();

    }
}

