import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/*
  StaffAssignedRequests - improved layout
  - Panels expand full width inside the scroll pane
  - Labels on left, inputs on right, description uses a larger textarea
  - Status dropdown + Update Status button on same row
*/
public class StaffAssignedRequests {

    private static JPanel createRequestPanel(int index) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.anchor = GridBagConstraints.WEST;

        // Index label (left column)
        JLabel idx = new JLabel(index + ".)");
        idx.setFont(idx.getFont().deriveFont(Font.BOLD, 20f));
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        p.add(idx, c);

        // Container for form fields (right of index)
        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(6, 6, 6, 6);
        f.anchor = GridBagConstraints.WEST;
        f.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Firstname
        f.gridx = 1; f.gridy = row; f.weightx = 0;
        p.add(new JLabel("Firstname:"), f);
        JTextField tfFirst = new JTextField();
        tfFirst.setEditable(false);
        tfFirst.setPreferredSize(new Dimension(320, 24));
        f.gridx = 2; f.weightx = 1.0;
        p.add(tfFirst, f);
        row++;

        // Lastname
        f.gridx = 1; f.gridy = row; f.weightx = 0;
        p.add(new JLabel("Lastname:"), f);
        JTextField tfLast = new JTextField();
        tfLast.setEditable(false);
        f.gridx = 2; f.weightx = 1.0;
        p.add(tfLast, f);
        row++;

        // Contact Number
        f.gridx = 1; f.gridy = row; f.weightx = 0;
        p.add(new JLabel("Contact Number:"), f);
        JTextField tfContact = new JTextField();
        tfContact.setEditable(false);
        tfContact.setPreferredSize(new Dimension(200, 24));
        f.gridx = 2; f.weightx = 1.0;
        p.add(tfContact, f);
        row++;

        // Email
        f.gridx = 1; f.gridy = row; f.weightx = 0;
        p.add(new JLabel("Email:"), f);
        JTextField tfEmail = new JTextField();
        tfEmail.setEditable(false);
        f.gridx = 2; f.weightx = 1.0;
        p.add(tfEmail, f);
        row++;

        // Address
        f.gridx = 1; f.gridy = row; f.weightx = 0;
        p.add(new JLabel("Address:"), f);
        JTextField tfAddress = new JTextField();
        tfAddress.setEditable(false);
        f.gridx = 2; f.weightx = 1.0;
        p.add(tfAddress, f);
        row++;

        // Description (textarea)
        f.gridx = 1; f.gridy = row; f.weightx = 0;
        p.add(new JLabel("Request Description:"), f);
        JTextArea taDesc = new JTextArea(4, 40);
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);
        taDesc.setEditable(false);
        taDesc.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        f.gridx = 2; f.weightx = 1.0;
        p.add(taDesc, f);
        row++;

        // Status + Update button on same row
        f.gridx = 1; f.gridy = row; f.weightx = 0;
        p.add(new JLabel("Status:"), f);
        String[] statuses = { "Pending", "Approved", "Declined", "Ongoing", "Resolved" };
        JComboBox<String> cbStatus = new JComboBox<>(statuses);
        cbStatus.setSelectedIndex(0);
        JPanel statusWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        statusWrap.setOpaque(false);
        statusWrap.add(cbStatus);
        JButton btnUpdate = new JButton("Update Status");
        btnUpdate.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String s = (String) cbStatus.getSelectedItem();
                JOptionPane.showMessageDialog(p,
                    "Status for request " + index + " updated to: " + s,
                    "Update Status", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        statusWrap.add(btnUpdate);

        f.gridx = 2; f.weightx = 1.0;
        p.add(statusWrap, f);

        // Make panel expand horizontally when placed into BoxLayout
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        return p;
    }

    public static JPanel createPanel(final App app) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // Top: back + blue bar
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
        JLabel title = new JLabel("View Assigned Requests", SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        blueBar.add(title, BorderLayout.WEST);
        topWrap.add(blueBar, BorderLayout.CENTER);

        root.add(topWrap, BorderLayout.NORTH);

        // Body: vertical list of request panels inside a JScrollPane
        JPanel listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);
        listContainer.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        // Add up to 3 request panels (placeholders)
        for (int i = 1; i <= 3; i++) {
            JPanel req = createRequestPanel(i);
            req.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            listContainer.add(req);
            listContainer.add(Box.createVerticalStrut(12));
        }

        JScrollPane sc = new JScrollPane(listContainer,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sc.getVerticalScrollBar().setUnitIncrement(16);
        sc.setBorder(BorderFactory.createEmptyBorder());
        root.add(sc, BorderLayout.CENTER);

        return root;
    }
}