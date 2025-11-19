import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FileNewRequest {
    public static JPanel createPanel(final App app, DbConnection db) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("File New Request (placeholder)", SwingConstants.CENTER), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.pink);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        //Input Address
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        form.add(new JLabel("Address:"), c);
        c.gridx = 1; c.weightx = 1.0;
        JTextField addressField = new JTextField(30);
        form.add(addressField, c);

        // Input Service
        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        form.add(new JLabel("Service Type:"), c);
        c.gridx = 1; c.weightx = 1.0;
        String[] serviceOptions = {"1: General Maintenance", "2: Sanitation", "3: Street Lights", "4: Public Safety"};
        JComboBox<String> serviceBox = new JComboBox<>(serviceOptions);
        serviceBox.setEditable(false);
        form.add(serviceBox, c);

        //Input Description
        c.gridx = 0; c.gridy = 2; c.weightx = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Description:"), c);
        c.gridx = 1; c.weightx = 1.0;
        JTextArea descArea = new JTextArea(5, 30);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        form.add(descArea, c);


        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setOpaque(false);
        centerWrapper.add(form);
        p.add(centerWrapper, BorderLayout.CENTER);

        //Submit button
        JButton submitBtn = new JButton("Submit Request");
        submitBtn.setBackground(new Color(0, 153, 76)); // Greenish
        submitBtn.setForeground(Color.WHITE);
        
        submitBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                User currentUser = app.getUserLoginInfo();
                
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(app.getFrame(), "Error: You must be logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String address = addressField.getText().trim();
                String description = descArea.getText().trim();
                
                // Parse Service ID from combo box string (e.g., "1: General..." -> 1)
                String selectedService = (String) serviceBox.getSelectedItem();
                int serviceId = 1; // Default
                try {
                    if (selectedService != null && selectedService.contains(":")) {
                        serviceId = Integer.parseInt(selectedService.split(":")[0]);
                    } else if (selectedService != null) {
                        serviceId = Integer.parseInt(selectedService);
                    }
                } catch (NumberFormatException ex) {
                    serviceId = 1;
                }

                if (address.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(app.getFrame(), "Please fill in all fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                db.InputServiceRequest(currentUser.getID(), serviceId, address, selectedService);

                JOptionPane.showMessageDialog(app.getFrame(), "Request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                addressField.setText("");
                descArea.setText("");
                app.showCard("citizen");
            }
        });


        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.blue);
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard("citizen");
            }
        });
        bottom.add(back);
        bottom.add(submitBtn);
        p.add(bottom, BorderLayout.SOUTH);

        return p;
    }
}