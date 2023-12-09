package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import gui.raven.chat.component.ChatArea;
import gui.raven.chat.component.ChatBox;
import gui.raven.chat.model.ModelMessage;
import gui.raven.chat.swing.ChatEvent;
import socket.App;
import socket.SendMail;

/**
 *
 * @author RAVEN
 */
public class ChatInterface extends javax.swing.JPanel {

    /**
     * Creates new form Test
     */
    public ChatInterface() {
        initComponents();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
        chatArea.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                Icon icon = new ImageIcon(main.class.getResource("/icon/man.png"));
                String name ="Me";
                String date = df.format(new Date());
                String message = chatArea.getText().trim();
                if (!message.isEmpty()){
//                    System.out.println(message);
                    SendMail client= new SendMail();
//                    new Thread()
                    SendMail.clientsendEmail(message);
                    ChatArea.addChatBox(new ModelMessage(icon, name, date, message), ChatBox.BoxType.RIGHT);
                    chatArea.clearTextAndGrabFocus();
                }

            }

            @Override
            public void mousePressedFileButton(ActionEvent evt) {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        });
        Icon icon = new ImageIcon(main.class.getResource("/icon/reindeer.png"));
        String name ="Me";
        String date = df.format(new Date());
        ChatArea.addChatBox(new ModelMessage(icon,name,date,"Waiting for request..."), ChatBox.BoxType.LEFT);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setOpaque(false);
        background1 = new gui.raven.chat.swing.Background();
        chatArea = new gui.raven.chat.component.ChatArea();

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
                background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(background1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(chatArea, 340, 350, Short.MAX_VALUE)
                                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
                background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(background1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(chatArea, 450, 450, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background1, 350, 350, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background1, 460, 460, Short.MAX_VALUE)
        );

    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ChatInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ChatInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ChatInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ChatInterface().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.raven.chat.swing.Background background1;
    private gui.raven.chat.component.ChatArea chatArea;
    // End of variables declaration//GEN-END:variables
}