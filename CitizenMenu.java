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