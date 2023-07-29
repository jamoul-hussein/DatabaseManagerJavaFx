// importing Java AWT class

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AnmeldeGui {

    static GraphicsConfiguration graphicsConfiguration;

    public static void launchGui() {
        JFrame window = new JFrame(graphicsConfiguration);
        Label wellcomeTitle = new Label("Willkommen bei Database Manager");
        wellcomeTitle.setFont(new Font("Serif", Font.BOLD, 50));
        wellcomeTitle.setBounds(250, 50, 10000, 150);

        //==============================================================
        // Registrieren section
        //==============================================================
        Button buttonRegister = new Button("Registrieren");
        buttonRegister.setSize(20, 20);
        buttonRegister.setFont(new Font("Serif", Font.BOLD, 30));
        buttonRegister.setBounds(50, 300, 250, 100);
        buttonRegister.setBackground(Color.decode("#EEEEEE"));

        // USERNAME
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 25));
        usernameLabel.setBounds(400, 200, 200, 100);

        TextField textFieldRegister = new TextField();
        textFieldRegister.setFont(new Font("Serif", Font.BOLD, 25));
        textFieldRegister.setBounds(400, 300, 400, 100);

        // PASSWORD
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 25));
        passwordLabel.setBounds(900, 200, 200, 100);

        JPasswordField textFieldPassword = new JPasswordField();
        textFieldPassword.setFont(new Font("Serif", Font.BOLD, 25));
        textFieldPassword.setBounds(900, 300, 400, 100);

        window.add(usernameLabel);
        window.add(buttonRegister);
        window.add(textFieldRegister);
        window.add(passwordLabel);
        window.add(textFieldPassword);

        //==============================================================
        // Einloggen section
        //==============================================================
        Button buttonEinloggen = new Button("Einloggen");
        buttonEinloggen.setSize(20, 20);
        buttonEinloggen.setFont(new Font("Serif", Font.BOLD, 30));
        buttonEinloggen.setBounds(50, 500, 250, 100);
        buttonEinloggen.setBackground(Color.decode("#EEEEEE"));

        // USERNAME
        Label usernameLabelEinloggen = new Label("Username");
        usernameLabelEinloggen.setFont(new Font("Serif", Font.BOLD, 25));
        usernameLabelEinloggen.setBounds(400, 400, 400, 100);

        TextField textFieldEinloggenUsername = new TextField();
        textFieldEinloggenUsername.setBounds(400, 500, 400, 100);


        // PASSWORD
        Label passwordLabelEinloggen = new Label("Password");
        passwordLabelEinloggen.setFont(new Font("Serif", Font.BOLD, 25));
        passwordLabelEinloggen.setBounds(900, 400, 400, 100);

        JPasswordField textFieldPasswordEinloggen = new JPasswordField();
        textFieldPasswordEinloggen.setFont(new Font("Serif", Font.BOLD, 25));
        textFieldPasswordEinloggen.setBounds(900, 500, 400, 100);


        window.add(buttonEinloggen);
        window.add(usernameLabelEinloggen);
        window.add(textFieldEinloggenUsername);
        window.add(passwordLabelEinloggen);
        window.add(textFieldPasswordEinloggen);


        //==============================================================
        // Output section
        //==============================================================
        Border blackline = BorderFactory.createLineBorder(Color.black);

        JLabel outputLabel = new JLabel();
        outputLabel.setBounds(400, 700, 900, 400);
        outputLabel.setBackground(Color.white);
        outputLabel.setBorder(blackline);

        window.add(outputLabel);
        //==============================================================

        window.add(wellcomeTitle);
        window.setSize(1500, 1500);
        window.setPreferredSize(new Dimension(1500, 1500));

        window.setLayout(null);
        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);



    }

    public static void main(String args[]) {
        launchGui();
    }
}
