import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Profile {

    public static JPanel createPanel(final App app) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // Header composed of a small top spacer (avatar + back) and the blue bar below it
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        // Top spacer: back button (top-left) and avatar circle
        JPanel topSpacer = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        topSpacer.setOpaque(false);
        topSpacer.setPreferredSize(new Dimension(0, 48));

        // Back button (small, top-left)
        JButton back = new JButton("\u2190"); // left arrow
        back.setFocusable(false);
        back.setPreferredSize(new Dimension(40, 28));
        back.setMargin(new java.awt.Insets(2, 6, 2, 6));
        back.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // return to the menu that opened Profile (staff or citizen)
                app.showCard(app.getProfileReturn());
            }
        });
        topSpacer.add(back);

        header.add(topSpacer, BorderLayout.NORTH);

        // header.add(topSpacer, BorderLayout.NORTH);

        // Blue bar with title "Profile"
        JPanel blueBar = new JPanel(new BorderLayout());
        blueBar.setBackground(new Color(0, 102, 204)); // blue color
        blueBar.setPreferredSize(new Dimension(0, 64));
        JLabel title = new JLabel("Profile");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        blueBar.add(title, BorderLayout.WEST);

        header.add(blueBar, BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);

        // Main empty content area (keeps look from the mock)
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        root.add(content, BorderLayout.CENTER);

        return root;
    }
}