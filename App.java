import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {
    private JFrame frame;
    private CardLayout cards;
    private JPanel cardPanel;
    private Dimension windowedSize;
    private boolean fullscreen = false;
    private JButton fsToggle;

    // form fields as instance variables so handlers can access them
    private JTextField loginUser;
    private JPasswordField loginPass;
    private JTextField createUser;
    private JTextField createEmail;
    private JPasswordField createPass;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() { new App().createAndShowGui(); }
        });
    }

    private void createAndShowGui() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        windowedSize = new Dimension((int)(screen.width * 0.6), (int)(screen.height * 0.6));

        frame = new JFrame("Authentication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createTopBar(), BorderLayout.NORTH);
        frame.add(createCardPanel(), BorderLayout.CENTER);

        frame.setSize(windowedSize);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JLabel title = new JLabel("My App");
        title.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        topBar.add(title, BorderLayout.WEST);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        fsToggle = new JButton("Fullscreen");
        // method reference (no anonymous class -> no "})")
        fsToggle.addActionListener(this::onToggleFullscreen);
        topRight.add(fsToggle);
        topBar.add(topRight, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createCardPanel() {
        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        cardPanel.add(createChoicePanel(), "choice");
        cardPanel.add(createLoginPanel(), "login");
        cardPanel.add(createCreatePanel(), "create");

        return cardPanel;
    }

    private JPanel createChoicePanel() {
        JPanel choice = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);

        JPanel column = new JPanel(new GridLayout(2, 1, 8, 12));
        JButton btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(160, 36));
        // method reference to instance method
        btnLogin.addActionListener(this::onShowLogin);

        JButton btnCreate = new JButton("Create Account");
        btnCreate.setPreferredSize(new Dimension(160, 36));
        btnCreate.addActionListener(this::onShowCreate);

        column.add(btnLogin);
        column.add(btnCreate);
        choice.add(column, gc);
        return choice;
    }

    private JPanel createLoginPanel() {
        JPanel login = new JPanel(new GridBagLayout());
        GridBagConstraints l = new GridBagConstraints();
        l.insets = new Insets(6, 6, 6, 6);
        l.fill = GridBagConstraints.HORIZONTAL;

        l.gridx = 0; l.gridy = 0;
        login.add(new JLabel("Username:"), l);
        l.gridx = 1;
        loginUser = new JTextField(20);
        login.add(loginUser, l);

        l.gridx = 0; l.gridy = 1;
        login.add(new JLabel("Password:"), l);
        l.gridx = 1;
        loginPass = new JPasswordField(20);
        login.add(loginPass, l);

        l.gridx = 0; l.gridy = 2; l.gridwidth = 2;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener(this::onShowChoice);
        JButton doLogin = new JButton("Login");
        doLogin.addActionListener(this::onDoLogin);
        buttons.add(back);
        buttons.add(doLogin);
        login.add(buttons, l);

        return login;
    }

    private JPanel createCreatePanel() {
        JPanel create = new JPanel(new GridBagLayout());
        GridBagConstraints r = new GridBagConstraints();
        r.insets = new Insets(6, 6, 6, 6);
        r.fill = GridBagConstraints.HORIZONTAL;

        r.gridx = 0; r.gridy = 0;
        create.add(new JLabel("Username:"), r);
        r.gridx = 1;
        createUser = new JTextField(20);
        create.add(createUser, r);

        r.gridx = 0; r.gridy = 1;
        create.add(new JLabel("Email:"), r);
        r.gridx = 1;
        createEmail = new JTextField(20);
        create.add(createEmail, r);

        r.gridx = 0; r.gridy = 2;
        create.add(new JLabel("Password:"), r);
        r.gridx = 1;
        createPass = new JPasswordField(20);
        create.add(createPass, r);

        r.gridx = 0; r.gridy = 3; r.gridwidth = 2;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener(this::onShowChoice);
        JButton createBtn = new JButton("Create");
        createBtn.addActionListener(this::onDoCreate);
        buttons.add(back);
        buttons.add(createBtn);
        create.add(buttons, r);

        return create;
    }

    // handler methods (method references avoid inline anonymous class closures)
    private void onShowLogin(ActionEvent e) { cards.show(cardPanel, "login"); }
    private void onShowCreate(ActionEvent e) { cards.show(cardPanel, "create"); }
    private void onShowChoice(ActionEvent e) { cards.show(cardPanel, "choice"); }

    private void onDoLogin(ActionEvent e) {
        String u = loginUser.getText().trim();
        String p = new String(loginPass.getPassword());
        JOptionPane.showMessageDialog(frame, "Would attempt login for: " + u, "Login", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onDoCreate(ActionEvent e) {
        String u = createUser.getText().trim();
        JOptionPane.showMessageDialog(frame, "Would create account for: " + u, "Create", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onToggleFullscreen(ActionEvent e) { toggleFullscreen(); }

    private void toggleFullscreen() {
        fullscreen = !fullscreen;
        frame.dispose(); // required to change undecorated property
        frame.setUndecorated(fullscreen);
        if (fullscreen) {
            fsToggle.setText("Windowed");
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            fsToggle.setText("Fullscreen");
            frame.setExtendedState(JFrame.NORMAL);
            frame.setSize(windowedSize);
            frame.setLocationRelativeTo(null);
        }
        frame.setResizable(false);
        frame.setVisible(true);
    }
}