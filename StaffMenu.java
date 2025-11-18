import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StaffMenu {
    public static JPanel createPanel(final App app) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // Blue bar at TOP with title on left and view profile (+ rectangle) on right
        JPanel blueBar = new JPanel(new BorderLayout());
        blueBar.setBackground(new Color(0, 102, 204));
        blueBar.setPreferredSize(new Dimension(0, 64));

        JLabel title = new JLabel("Staff", SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        blueBar.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        right.setOpaque(false);

        // View Profile button (TOp Right)
        JButton viewProfile = new JButton("View Profile");
        viewProfile.setFocusable(false);
        viewProfile.setPreferredSize(new Dimension(120, 28));
        viewProfile.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showProfileFromStaff();
            }
        });
        right.add(viewProfile);

        blueBar.add(right, BorderLayout.EAST);
        root.add(blueBar, BorderLayout.NORTH);

        // Body
        JPanel bodyWrap = new JPanel(new BorderLayout());
        bodyWrap.setOpaque(false);
        JPanel btnCol = new JPanel(new GridLayout(3, 1, 16, 16));
        btnCol.setOpaque(false);
        btnCol.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));

        JButton bRequests = new JButton("View Assigned Requests");
        JButton bAvailability = new JButton("Availability");
        JButton bReports = new JButton("Generate Reports");

        Dimension btnSize = new Dimension(240, 48);
        bRequests.setPreferredSize(btnSize);
        bAvailability.setPreferredSize(btnSize);
        bReports.setPreferredSize(btnSize);

        bRequests.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { app.showCard("choice"); }
        });
        bAvailability.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { app.showCard("choice"); }
        });
        bReports.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { app.showCard("choice"); }
        });

        btnCol.add(bRequests);
        btnCol.add(bAvailability);
        btnCol.add(bReports);

        bodyWrap.add(btnCol, BorderLayout.NORTH); // keep buttons toward top area
        root.add(bodyWrap, BorderLayout.CENTER);

        return root;
    }
}