import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ReportView {

    public static JPanel createPanel(final App app, final String titleText, final String returnCard) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // top: back + blue header
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JButton back = new JButton("Back");
        back.setFocusable(false);
        back.setMargin(new Insets(6, 12, 6, 12));
        back.addActionListener(e -> app.showCard(returnCard));
        JPanel backHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backHolder.setOpaque(false);
        backHolder.add(back);
        top.add(backHolder, BorderLayout.NORTH);

        JPanel blue = new JPanel(new BorderLayout());
        blue.setBackground(new Color(0, 102, 204));
        blue.setPreferredSize(new Dimension(0, 64));
        JLabel title = new JLabel(titleText, SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        blue.add(title, BorderLayout.WEST);
        top.add(blue, BorderLayout.CENTER);

        root.add(top, BorderLayout.NORTH);

        // center: placeholder for report content
        JPanel center = new JPanel();
        center.setBorder(BorderFactory.createEmptyBorder(32, 48, 32, 48));
        center.add(new JLabel("TODO: implement report view for: " + titleText));
        root.add(center, BorderLayout.CENTER);

        return root;
    }
}