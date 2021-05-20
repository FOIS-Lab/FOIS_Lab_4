package FOIS_LAB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    public static void main(String[] args) {
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("FOIS LAB 4");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setBackground(Color.black);

                JLabel url_label = new JLabel("Insert URL here: ");
                url_label.setFont(Font.getFont(Font.SANS_SERIF));

                JTextField url = new JTextField();
                url.setBackground(Color.white);
                url.setSize(600, 20);

                JPanel url_input = new JPanel();
                url_input.add(url_label);
                url_input.add(url);

                frame.add(url_input, BorderLayout.NORTH);

                frame.setSize(720, 1280);
                frame.setVisible(true);
            }
        };
                EventQueue.invokeLater(runner);


    }
}

class frame extends JFrame implements Runnable {

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void run() {
        frame fr = new frame();
        fr.setTitle("FOIS_LAB_4");
        fr.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
