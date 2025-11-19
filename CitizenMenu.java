import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CitizenMenu {
    public static JPanel createPanel(final App app) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("Citizen Page (placeholder)", SwingConstants.CENTER), BorderLayout.CENTER);

        // small top-right View Profile button so citizen can view their profile
        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        topRight.setOpaque(false);
        JButton viewProfile = new JButton("View Profile");
        viewProfile.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showProfileFromCitizen();
            }
        });
        topRight.add(viewProfile);
        p.add(topRight, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        p.add(buttonPanel);

        JButton fileRequestBtn = new JButton("File New Service Request");
        fileRequestBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard("newRequest");
            }
        });
        
        buttonPanel.add(fileRequestBtn);

        JButton viewRequestsBtn = new JButton("View Your Service Requests");
        viewRequestsBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard("viewCitizenRequest");
            }
        });
        buttonPanel.add(viewRequestsBtn);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard("choice");
            }
        });
        bottom.add(back);
        p.add(bottom, BorderLayout.SOUTH);

        return p;
    }
}