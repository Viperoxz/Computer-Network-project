package gui.raven.chat.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.formdev.flatlaf.FlatClientProperties;
import javaswingdev.FontAwesome;
import javaswingdev.FontAwesomeIcon;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;

import javax.swing.*;

import gui.raven.chat.animation.AnimationFloatingButton;
import gui.raven.chat.animation.AnimationScroll;
import gui.raven.chat.model.ModelMessage;
import gui.raven.chat.swing.Button;
import gui.raven.chat.swing.RoundPanel;
import gui.raven.chat.swing.TextField;
import raven.chat.swing.scroll.ScrollBar;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import gui.raven.chat.swing.ChatEvent;

public class ChatArea extends JPanel {

    private AnimationScroll animationScroll;
    private AnimationFloatingButton animationFloatingButton;
    private List<ChatEvent> events = new ArrayList<>();

    public void addChatEvent(ChatEvent event) {
        events.add(event);
    }

    public ChatArea() {
        init();
        initAnimator();
    }

    private void init() {
        setOpaque(false);
        layout = new MigLayout("fill, wrap, inset 0", "[fill]", "[fill,40!][fill, 100%][shrink 0,::30%]");
        header = createHeader();
        header.setOpaque(false);
        body = createBody();
        bottom = createBottom();
        layeredPane = createLayeredPane();
        scrollBody = createScroll();
        scrollBody.setViewportView(body);
        scrollBody.setVerticalScrollBar(new ScrollBar());
        scrollBody.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBody.getViewport().setOpaque(false);
        scrollBody.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            private int oldValues;

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = scrollBody.getVerticalScrollBar().getValue();
                int extent = scrollBody.getVerticalScrollBar().getModel().getExtent();
                if ((value + extent) >= scrollBody.getVerticalScrollBar().getMaximum() - 300) {
                    animationFloatingButton.hide();
                } else if (oldValues <= e.getValue()) {
                    if (!animationScroll.isRunning()) {
                        animationFloatingButton.show();
                    }
                }

            }
        });
        floatingButton = createFloatingButton();
//        layeredPane.setLayer(floatingButton, JLayeredPane.POPUP_LAYER);
//        layeredPane.add(floatingButton, "pos 100%-50 100%,h 40,w 40");
        layeredPane.add(scrollBody);
        setLayout(layout);
        header.setBackground(new Color(0,0,0,0));
        bottom.setBackground(new Color(0,0,0,0));
//        layeredPane.setBackground(new Color(200,101,110));
        add(header);
        add(layeredPane);
        add(bottom);
    }

    private void initAnimator() {
        animationScroll = new AnimationScroll(body);
        animationFloatingButton = new AnimationFloatingButton(layoutLayered, floatingButton);
    }

    private JPanel createHeader() {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new MigLayout("fill, inset 2"));
        panel.setBackground(new Color(255, 255, 255, 20));
        labelTitle = new JLabel();
        labelTitle.setFont(labelTitle.getFont().deriveFont(14f));
        labelTitle.setBorder(new EmptyBorder(2, 10, 2, 2));
        labelTitle.setForeground(new Color(240, 240, 240));
        panel.add(labelTitle);
        return panel;
    }

    private JPanel createBody() {
        RoundPanel panel = new RoundPanel();
        panel.setBackground(new Color(111, 0, 0, 0));
        panel.setLayout(new MigLayout("wrap,fillx"));
        return panel;
    }

    private JPanel createBottom() {
        RoundPanel panel = new RoundPanel();
        panel.setBackground(new Color(255, 255, 255, 20));
        panel.setLayout(new MigLayout("fill, inset 2", "[fill]3[fill,34!]", "[bottom]"));
//        GoogleMaterialIcon iconFile = new GoogleMaterialIcon(GoogleMaterialDesignIcon.ATTACH_FILE, GradientType.VERTICAL, new Color(210, 210, 210), new Color(255, 255, 255), 20);
        GoogleMaterialIcon iconSend = new GoogleMaterialIcon(GoogleMaterialDesignIcon.SEND, GradientType.VERTICAL, new Color(0, 133, 237), new Color(90, 182, 255), 25);
//        GoogleMaterialIcon iconEmot = new GoogleMaterialIcon(GoogleMaterialDesignIcon.INSERT_EMOTICON, GradientType.VERTICAL, new Color(210, 210, 210), new Color(255, 255, 255), 20);
        Button cmdFile = new Button();
        Button cmdSend = new Button();
        cmdFile.setFocusable(false);
        cmdSend.setFocusable(false);
        cmdSend.setIcon(iconSend.toIcon());

        textMessage = new TextField();
        textMessage.setFont(new Font("Consolas",Font.PLAIN,14));
//        textMessage.setBackground(new Color(29, 142, 176, 123));
        textMessage.setHint("What to do my highness ...");
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
        cmdSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEventMousePressedSendButton(e);
            }
        });
        cmdFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEventMousePressedFileButton(e);
            }
        });
        JScrollPane scroll = createScroll();
        scroll.setBackground(new Color(67, 121, 118, 61));
        scroll.setViewportView(textMessage);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        panel.add(scroll);
        panel.add(cmdSend, "height 34!");
        return panel;
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layer = new JLayeredPane();
        layoutLayered = new MigLayout("fill,inset 0", "[fill]", "[fill]");
        layer.setLayout(layoutLayered);
        return layer;
    }

    private Button createFloatingButton() {
        Button button = new Button();
        button.setBorder(null);
        FontAwesomeIcon icon = new FontAwesomeIcon(FontAwesome.ANGLE_DOWN, GradientType.VERTICAL, new Color(79, 79, 79, 240), new Color(248, 248, 248, 240), 35);
        button.setIcon(icon.toIcon());
        button.setRound(40);
        button.setBackground(new Color(100, 100, 100, 100));
        button.setPaintBackground(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
            }
        });
        return button;
    }

    private JScrollPane createScroll() {
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        return scroll;
    }

    public void addChatBox(ModelMessage message, ChatBox.BoxType type) {
        int values = scrollBody.getVerticalScrollBar().getValue();
        if (type == ChatBox.BoxType.LEFT) {
            body.add(new ChatBox(type, message), "width ::80%");
        } else {
            body.add(new ChatBox(type, message), "al right,width ::80%");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                body.revalidate();
                scrollBody.getVerticalScrollBar().setValue(values);
                bottom.revalidate();
            }
        });
        body.repaint();
        body.revalidate();
        scrollBody.revalidate();
        scrollToBottom();
    }

    public void clearChatBox() {
        body.removeAll();
        body.repaint();
        body.revalidate();
    }

    private void scrollToBottom() {
        animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
    }

    private void runEventMousePressedSendButton(ActionEvent evt) {
        for (ChatEvent event : events) {
            event.mousePressedSendButton(evt);
        }
    }

    private void runEventMousePressedFileButton(ActionEvent evt) {
        for (ChatEvent event : events) {
            event.mousePressedFileButton(evt);
        }
    }

    private void runEventKeyTyped(KeyEvent evt) {
        for (ChatEvent event : events) {
            event.keyTyped(evt);
        }
    }

    public String getText() {
        return textMessage.getText();
    }

    public void setTitle(String title) {
        labelTitle.setText(title);
    }

    public String getTitle() {
        return labelTitle.getText();
    }

    public void setText(String text) {
        textMessage.setText(text);
    }

    public void textGrabFocus() {
        textMessage.grabFocus();
    }

    public void clearTextAndGrabFocus() {
        textMessage.setText("");
        textMessage.grabFocus();
    }

    private MigLayout layout;
    private MigLayout layoutLayered;
    private JLayeredPane layeredPane;
    private JPanel header;
    private JPanel body;
    private JPanel bottom;
    private TextField textMessage;
    private JScrollPane scrollBody;
    private Button floatingButton;
    private JLabel labelTitle;


}
