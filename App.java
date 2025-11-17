import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class App {
    private JFrame frame;
    private CardLayout cards;
    private JPanel cardPanel;
    private Dimension windowedSize;
    private boolean fullscreen = false;
    private JButton fsToggle;

    public void start() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        windowedSize = new Dimension((int)(screen.width * 0.6), (int)(screen.height * 0.6));

        frame = new JFrame("My Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createTopBar(), BorderLayout.NORTH);
        frame.add(createCardPanel(), BorderLayout.CENTER);
        frame.add(createDevPanel(), BorderLayout.SOUTH); // developer-only buttons bottom-right

        frame.setSize(windowedSize);
        frame.setResizable(false); // fixed (windowed) or fullscreen via toggle
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

        JLabel title = new JLabel("My App");
        topBar.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        fsToggle = new JButton("Fullscreen");
        fsToggle.addActionListener(this::onToggleFullscreen);
        right.add(fsToggle);

        topBar.add(right, BorderLayout.EAST);
        return topBar;
    }

    private JPanel createCardPanel() {
        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        // center choice: only Login and Register buttons
        cardPanel.add(createChoicePanel(), "choice");

        // Use LoginRegis to create both the login and register panels
        cardPanel.add(LoginRegis.createLoginPanel(this), "login");
        cardPanel.add(LoginRegis.createRegisterPanel(this), "register");

        cardPanel.add(CitizenMenu.createPanel(this), "citizen");
        cardPanel.add(StaffMenu.createPanel(this), "staff");

        cards.show(cardPanel, "choice");
        return cardPanel;
    }

    // Center choice panel: Login + Register in the middle
    private JPanel createChoicePanel() {
        JPanel choice = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);

        JPanel col = new JPanel(new GridLayout(2,1,8,12));
        JButton bLogin = new JButton("Login");
        bLogin.setPreferredSize(new Dimension(160, 36));
        bLogin.addActionListener(this::onShowLogin);

        JButton bRegister = new JButton("Register");
        bRegister.setPreferredSize(new Dimension(160, 36));
        bRegister.addActionListener(this::onShowRegister);

        col.add(bLogin);
        col.add(bRegister);

        choice.add(col, gc);
        return choice;
    }

    // developer-only panel placed at bottom-right (not final)
    private JPanel createDevPanel() {
        JPanel dev = new JPanel(new BorderLayout());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        JButton viewCitizen = new JButton("Dev: View Citizen");
        viewCitizen.addActionListener(e -> showCard("citizen"));
        JButton viewStaff = new JButton("Dev: View Staff");
        viewStaff.addActionListener(e -> showCard("staff"));
        right.add(viewCitizen);
        right.add(viewStaff);
        dev.add(right, BorderLayout.EAST);

        JLabel note = new JLabel("developer testing buttons (temporary)");
        note.setBorder(BorderFactory.createEmptyBorder(0,6,0,0));
        dev.add(note, BorderLayout.WEST);

        return dev;
    }

    // navigation handlers
    private void onShowLogin(ActionEvent e)   { showCard("login"); }
    private void onShowRegister(ActionEvent e){ showCard("register"); }
    private void onShowCitizen(ActionEvent e) { showCard("citizen"); }
    private void onShowStaff(ActionEvent e)   { showCard("staff"); }

    public void showCard(String name) {
        if (cards != null && cardPanel != null) cards.show(cardPanel, name);
    }

    // fullscreen toggle (called via method reference)
    private void onToggleFullscreen(ActionEvent e) { toggleFullscreen(); }

    private void toggleFullscreen() {
        fullscreen = !fullscreen;
        frame.dispose();                 // required to change undecorated
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

    // allow pages to use frame for dialogs if needed
    public JFrame getFrame() { 
        return frame; 
    }
}