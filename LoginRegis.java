import java.awt.*;
import javax.swing.*;

public class LoginRegis {
    // creates the Login form panel (shown immediately when app.showCard("login") is called)
    public static JPanel createLoginPanel(final App app) {
        JPanel loginForm = new JPanel(new GridBagLayout());
        GridBagConstraints l = new GridBagConstraints();
        l.insets = new Insets(6, 6, 6, 6);
        l.fill = GridBagConstraints.HORIZONTAL;

        l.gridx = 0; l.gridy = 0;
        loginForm.add(new JLabel("Email:"), l);
        l.gridx = 1;
        JTextField emailField = new JTextField(20);
        loginForm.add(emailField, l);

        l.gridx = 0; l.gridy = 1;
        loginForm.add(new JLabel("Password:"), l);
        l.gridx = 1;
        JPasswordField passField = new JPasswordField(20);
        loginForm.add(passField, l);

        l.gridx = 0; l.gridy = 2; l.gridwidth = 2;
        JPanel loginBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backFromLogin = new JButton("Back");
        backFromLogin.addActionListener(e -> app.showCard("choice"));
        JButton doLogin = new JButton("Login");
        doLogin.addActionListener(e -> {
            String email = emailField.getText().trim();
            JOptionPane.showMessageDialog(app.getFrame(),
                "Would attempt login for: " + email,
                "Login", JOptionPane.INFORMATION_MESSAGE);
        });
        loginBtns.add(backFromLogin);
        loginBtns.add(doLogin);
        loginForm.add(loginBtns, l);

        return loginForm;
    }

    // creates the Register form panel (shown immediately when app.showCard("register") is called)
    public static JPanel createRegisterPanel(final App app) {
        JPanel registerForm = new JPanel(new GridBagLayout());
        GridBagConstraints r = new GridBagConstraints();
        r.insets = new Insets(6, 6, 6, 6);
        r.fill = GridBagConstraints.HORIZONTAL;

        r.gridx = 0; r.gridy = 0;
        registerForm.add(new JLabel("Username:"), r);
        r.gridx = 1;
        JTextField regUser = new JTextField(20);
        registerForm.add(regUser, r);

        r.gridx = 0; r.gridy = 1;
        registerForm.add(new JLabel("Email:"), r);
        r.gridx = 1;
        JTextField regEmail = new JTextField(20);
        registerForm.add(regEmail, r);

        r.gridx = 0; r.gridy = 2;
        registerForm.add(new JLabel("Password:"), r);
        r.gridx = 1;
        JPasswordField regPass = new JPasswordField(20);
        registerForm.add(regPass, r);

        r.gridx = 0; r.gridy = 3; r.gridwidth = 2;
        JPanel regBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backFromReg = new JButton("Back");
        backFromReg.addActionListener(e -> app.showCard("choice"));
        JButton doRegister = new JButton("Register");
        doRegister.addActionListener(e -> {
            String user = regUser.getText().trim();
            JOptionPane.showMessageDialog(app.getFrame(),
                "Would register user: " + user,
                "Register", JOptionPane.INFORMATION_MESSAGE);
        });
        regBtns.add(backFromReg);
        regBtns.add(doRegister);
        registerForm.add(regBtns, r);

        return registerForm;
    }
}