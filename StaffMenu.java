import java.awt.*;
import javax.swing.*;

public class StaffMenu {
    public static JPanel createPanel(final App app) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("Staff Page (placeholder)", SwingConstants.CENTER), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener(e -> app.showCard("choice"));
        bottom.add(back);
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }
}