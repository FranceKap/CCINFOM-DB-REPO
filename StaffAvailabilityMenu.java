import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StaffAvailabilityMenu {
    public static JPanel createPanel(final App app) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // Top: back button + blue header bar (unchanged)
        JPanel topWrap = new JPanel(new BorderLayout());
        topWrap.setOpaque(false);

        JButton backBtn = new JButton("Back");
        backBtn.setFocusable(false);
        backBtn.setMargin(new Insets(6, 12, 6, 12));
        backBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard("staff");
            }
        });
        JPanel backHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backHolder.setOpaque(false);
        backHolder.add(backBtn);
        topWrap.add(backHolder, BorderLayout.NORTH);

        JPanel blueBar = new JPanel(new BorderLayout());
        blueBar.setBackground(new Color(0, 102, 204));
        blueBar.setPreferredSize(new Dimension(0, 64));
        JLabel title = new JLabel("Availability", SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        blueBar.add(title, BorderLayout.WEST);
        topWrap.add(blueBar, BorderLayout.CENTER);

        root.add(topWrap, BorderLayout.NORTH);

        // Body: centered content with dropdown (Available / Busy) and Change button
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(24, 48, 24, 48));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        body.add(Box.createVerticalGlue());

        JLabel heading = new JLabel("Set Your Availability");
        heading.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 22f));
        body.add(heading);

        body.add(Box.createVerticalStrut(24));

        // Dropdown with Available / Busy
        String[] options = { "Available", "Busy" };
        JComboBox<String> cb = new JComboBox<>(options);
        cb.setMaximumSize(new Dimension(260, 28));
        cb.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
        body.add(cb);

        body.add(Box.createVerticalStrut(18));

        // Change button
        JButton changeBtn = new JButton("Change");
        changeBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);
        changeBtn.setFocusable(false);
        changeBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String sel = (String) cb.getSelectedItem();
                // placeholder: update DB / state here
                JOptionPane.showMessageDialog(root,
                    "Availability set to: " + sel,
                    "Availability", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        body.add(changeBtn);

        body.add(Box.createVerticalGlue());

        root.add(body, BorderLayout.CENTER);

        return root;
    }
}