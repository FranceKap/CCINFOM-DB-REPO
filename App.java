import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App {
    private JFrame frame;
    private CardLayout cards;
    private JPanel cardPanel;
    private Dimension windowedSize;
    private boolean fullscreen = false;
    private JButton fsToggle;
    private String profileReturn = "choice";

    private DbConnection db;

    private User userLoginInfo;

    public App(DbConnection db){
        this.db = db;
    }

    public void start() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        windowedSize = new Dimension((int)(screen.width * 0.6), (int)(screen.height * 0.6));

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createCardPanel(), BorderLayout.CENTER);
        frame.add(createBottomPanel(), BorderLayout.SOUTH);

        frame.setSize(windowedSize);
        frame.setResizable(false); // fixed (windowed) or fullscreen via toggle
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createCardPanel() {
        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        // choice + login + register panels are provided by LoginRegis
        cardPanel.add(LoginRegis.createChoicePanel(this), "choice");
        cardPanel.add(LoginRegis.createLoginPanel(this, db), "login");
        // TODO might need to make Login return CitizenID somehow (done?)
        cardPanel.add(LoginRegis.createRegisterPanel(this, db), "register");

        // Profile Page
        cardPanel.add(Profile.createPanel(this), "profile");

        // other pages
        cardPanel.add(CitizenMenu.createPanel(this), "citizen");
        cardPanel.add(StaffMenu.createPanel(this), "staff");
        cardPanel.add(StaffAssignedRequests.createPanel(this), "staffAssignedRequests");
        cardPanel.add(StaffAvailabilityMenu.createPanel(this), "staffAvailability");
        cardPanel.add(StaffGenerateReport.createPanel(this), "staffGenerateReport");
        cardPanel.add(ReportView.createPanel(this, "Services Requested Report", "staffGenerateReport"), "reportServicesRequested");
        cardPanel.add(ReportView.createPanel(this, "Services Status Report", "staffGenerateReport"), "reportServicesStatus");
        cardPanel.add(ReportView.createPanel(this, "Monthly Completed Report", "staffGenerateReport"), "reportMonthlyCompleted");
        cardPanel.add(ReportView.createPanel(this, "Staff Workload Report", "staffGenerateReport"), "reportStaffWorkload");

        // pages for CitizenMenu
        cardPanel.add(FileNewRequest.createPanel(this, db), "newRequest");
        cardPanel.add(CitizenViewRequest.createPanel(this), "viewCitizenRequest");

        cards.show(cardPanel, "choice");
        return cardPanel;
    }

    private JPanel createBottomPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(4, 6, 6, 6));

        // left
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        fsToggle = new JButton("Fullscreen");
        fsToggle.setPreferredSize(new Dimension(72, 24));
        fsToggle.setMargin(new java.awt.Insets(2, 2, 2, 2));
        fsToggle.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                onToggleFullscreen(e);
            }
        });
        left.add(fsToggle);
        container.add(left, BorderLayout.WEST);

        // right
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));
        JButton viewCitizen = new JButton("Dev: Citizen");
        viewCitizen.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                onShowCitizen(e);
            }
        });
        JButton viewStaff = new JButton("Dev: Staff");
        viewStaff.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                onShowStaff(e);
            }
        });
        right.add(viewCitizen);
        right.add(viewStaff);
        container.add(right, BorderLayout.EAST);

        // center
        container.add(new JLabel(""), BorderLayout.CENTER);
        return container;
    }
    
    public void showCard(String name) {
        if (cards != null && cardPanel != null) cards.show(cardPanel, name);
    }

    // fullscreen toggle
    private void onToggleFullscreen(ActionEvent e) { 
        toggleFullscreen(); 
    }

    private void toggleFullscreen() {
        fullscreen = !fullscreen;
        frame.dispose();            
        frame.setUndecorated(fullscreen);
        if (fullscreen) {
            fsToggle.setText("Windowed");
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } 
        else {
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

    public void onShowLogin(ActionEvent e) { 
        showCard("login"); 
    }

    public void onShowRegister(ActionEvent e) { 
        showCard("register"); 
    }

    public void onShowCitizen(ActionEvent e) { 
        showCard("citizen"); 
    }

    public void onShowStaff(ActionEvent e) { 
        showCard("staff"); 
    }
    
    public void onShowChoice(ActionEvent e) { 
        showCard("choice"); 
    }

    //TODO add onShow functions for the two citizenMenu cards?

    public void showProfileFromStaff() { 
        profileReturn = "staff"; showCard("profile"); 
    }
    public void showProfileFromCitizen() { 
        profileReturn = "citizen"; showCard("profile"); 
    }
    public String getProfileReturn() { 
        return profileReturn; 
    }

    public void setUserLoginInfo(User user) {
        userLoginInfo = user;
    }

    public User getUserLoginInfo(){
        return userLoginInfo;
    }
}