import java.awt.BorderLayout;
import java.awt.Color;
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

        //what buttonPanel looks
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        buttonPanel.setBackground(Color.pink); //TODO placeholder
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
        bottom.setBackground(Color.blue);
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