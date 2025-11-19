import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StaffGenerateReport {
    public static JPanel createPanel(final App app) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // top: back + blue header
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JButton back = new JButton("Back");
        back.setFocusable(false);
        back.setMargin(new Insets(6, 12, 6, 12));
        back.addActionListener(e -> app.showCard("staff"));
        JPanel backHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backHolder.setOpaque(false);
        backHolder.add(back);
        top.add(backHolder, BorderLayout.NORTH);

        JPanel blue = new JPanel(new BorderLayout());
        blue.setBackground(new Color(0, 102, 204));
        blue.setPreferredSize(new Dimension(0, 64));
        JLabel title = new JLabel("Generate Report", SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        blue.add(title, BorderLayout.WEST);
        top.add(blue, BorderLayout.CENTER);

        root.add(top, BorderLayout.NORTH);

        // body: 4 labeled boxes with buttons
        JPanel body = new JPanel(new GridBagLayout());
        body.setBorder(BorderFactory.createEmptyBorder(28, 48, 28, 48));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(18, 12, 18, 12);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;

        // Helper to create a no-op ActionListener with a TODO
        ActionListener todoNoop = e -> {
            // TODO: implement report generation and navigation
        };

        // 1. Services Requested Report
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        body.add(new JLabel("Services Requested Report"), c);
        c.gridx = 1; c.weightx = 1;
        JButton b1 = new JButton("Generate");
        // TODO: implement generation logic
        b1.addActionListener(e -> app.showCard("reportServicesRequested"));
        body.add(b1, c);

        // 2. Services Status Report
        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        body.add(new JLabel("Services Status Report"), c);
        c.gridx = 1; c.weightx = 1;
        JButton b2 = new JButton("Generate");
        // TODO: implement generation logic
        b2.addActionListener(todoNoop);
        b2.addActionListener(e -> app.showCard("reportServicesStatus"));
        body.add(b2, c);

        // 3. Monthly Completed Report
        c.gridx = 0; c.gridy = 2; c.weightx = 0;
        body.add(new JLabel("Monthly Completed Report"), c);
        c.gridx = 1; c.weightx = 1;
        JButton b3 = new JButton("Generate");
        // TODO: implement generation logic
        b3.addActionListener(todoNoop);
        b3.addActionListener(e -> app.showCard("reportMonthlyCompleted"));
        body.add(b3, c);

        // 4. Staff Workload Report
        c.gridx = 0; c.gridy = 3; c.weightx = 0;
        body.add(new JLabel("Staff Workload Report"), c);
        c.gridx = 1; c.weightx = 1;
        JButton b4 = new JButton("Generate");
        // TODO: implement generation logic
        b4.addActionListener(todoNoop);
        b4.addActionListener(e -> app.showCard("reportStaffWorkload"));
        body.add(b4, c);

        root.add(body, BorderLayout.CENTER);
        return root;
    }
}