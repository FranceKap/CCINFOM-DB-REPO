import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class LoginRegis {

    private static void addRow(JPanel p, GridBagConstraints c, int row, String labelText, java.awt.Component comp) {
        c.gridx = 0; c.gridy = row; c.gridwidth = 1; c.anchor = GridBagConstraints.WEST;
        p.add(new JLabel(labelText), c);
        c.gridx = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        p.add(comp, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;
    }

    private static JButton createButton(String text, ActionListener al) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(160, 36));
        b.addActionListener(al);
        return b;
    }

    public static JPanel createChoicePanel(final App app) {
        JPanel choice = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;

        // Logo (Manila City Logo --- pls make sure u have the Manila_Logo.png for this to show)
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
        gc.insets = new Insets(6, 6, 12, 6);
        JLabel title = new JLabel("Manila Online Service Request", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize() + 6f));
        choice.add(title, gc);

        // Buttons
        gc.gridy = 2;
        gc.insets = new Insets(4, 4, 4, 4);
        JPanel col = new JPanel(new GridLayout(2, 1, 8, 12));
        col.setOpaque(false);
        col.add(createButton("Login", new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { 
                app.showCard("login"); 
            }
        }));
        col.add(createButton("Register", new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { 
                app.showCard("register"); 
            }
        }));
        choice.add(col, gc);
        
        return choice;
    }

    // creates the Login form panel
    public static JPanel createLoginPanel(final App app, DbConnection db) {
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
        backFromLogin.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard("choice");
            }
        });
        JButton doLogin = new JButton("Login");
        doLogin.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String loginEmail = emailField.getText().trim();
                String loginPassword = new String(passField.getPassword());

                if (db.CitizenLogin(loginEmail, loginPassword)) {
                    JOptionPane.showMessageDialog(app.getFrame(),
                    db.getCitizenLoginName(loginEmail, loginPassword),
                    "Login", JOptionPane.INFORMATION_MESSAGE);
                }

                /* 
                JOptionPane.showMessageDialog(app.getFrame(),
                    "Attempt login for: " + loginEmail,
                    "Login", JOptionPane.INFORMATION_MESSAGE);
                */
            }
        });
        loginBtns.add(backFromLogin);
        loginBtns.add(doLogin);
        loginForm.add(loginBtns, l);

        return loginForm;
    }

    // creates the Register form panel
    public static JPanel createRegisterPanel(final App app, DbConnection db) {
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
        backFromReg.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard("choice");
            }
        });
        JButton doRegister = new JButton("Register");
        doRegister.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String user = regUser.getText().trim();
                String email = regEmail.getText().trim();
                String password = new String(regPass.getPassword());

                //CitizenRegister(String firstName, String lastName, int contactNbr, String email, String address, String password)
                db.CitizenRegister("firstName", "lastName", 0, email, user, password);
                //TODO placeholder strings, pls complete LoginRegis to include the other attributes

                JOptionPane.showMessageDialog(app.getFrame(),
                    "Attempt register user: " + user,
                    "Register", JOptionPane.INFORMATION_MESSAGE);
            }
        });    
        regBtns.add(backFromReg);
        regBtns.add(doRegister);
        registerForm.add(regBtns, r);

        return registerForm;
    }
}