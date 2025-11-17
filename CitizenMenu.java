import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class CitizenMenu {
    public static JPanel createPanel(final App app) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("Citizen Page (placeholder)", SwingConstants.CENTER), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener((ActionEvent e) -> app.showCard("choice"));
        bottom.add(back);
        p.add(bottom, BorderLayout.SOUTH);

        return p;
    }
}