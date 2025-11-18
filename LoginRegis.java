import java.awt.*;
import javax.swing.*;

public class LoginRegis {

    public static JPanel createChoicePanel(final App app) {
        JPanel choice = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;

        // Logo
        ImageIcon icon = new ImageIcon(LoginRegis.class.getResource("/Manila_Logo.png"));
        if (icon != null) {
            Image img = icon.getImage();
            int size = 120;
            Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            gc.gridy = 0;
            choice.add(imgLabel, gc);
        }

        // Title
        gc.gridy = 1;
        gc.insets = new Insets(6, 6, 12, 6); // extra space below title
        JLabel title = new JLabel("Manila Online Service Request", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize() + 6f));
        choice.add(title, gc);

        // Buttons
        gc.gridy = 2;
        gc.insets = new Insets(4, 4, 4, 4);
        JPanel col = new JPanel(new GridLayout(2, 1, 8, 12));
        JButton bLogin = new JButton("Login");
        bLogin.setPreferredSize(new Dimension(160, 36));
        bLogin.addActionListener(e -> app.showCard("login"));

        JButton bRegister = new JButton("Register");
        bRegister.setPreferredSize(new Dimension(160, 36));
        bRegister.addActionListener(e -> app.showCard("register"));

        col.add(bLogin);
        col.add(bRegister);
        choice.add(col, gc);

        return choice;
    }

    // creates the Login form panel
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

    // creates the Register form panel
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