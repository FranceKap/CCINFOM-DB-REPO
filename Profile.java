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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Profile {

    public static JPanel createPanel(final App app) {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // top spacer with back button
        JPanel topSpacer = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        topSpacer.setOpaque(false);
        topSpacer.setPreferredSize(new Dimension(0, 48));
        JButton back = new JButton("Back");
        back.setFocusable(false);
        back.setPreferredSize(new Dimension(80, 28));
        back.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                app.showCard(app.getProfileReturn());
            }
        });
        topSpacer.add(back);

        // blue title bar under spacer
        JPanel blueBar = new JPanel(new BorderLayout());
        blueBar.setBackground(new Color(0, 102, 204));
        blueBar.setPreferredSize(new Dimension(0, 64));
        JLabel title = new JLabel("Profile", SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        blueBar.add(title, BorderLayout.WEST);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(topSpacer, BorderLayout.NORTH);
        header.add(blueBar, BorderLayout.SOUTH);
        root.add(header, BorderLayout.NORTH);

        // Main form (labels + fields). We'll make labels equal width so fields align.
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(36, 60, 36, 160));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 12, 12, 12);

        Font labelFont = new JLabel().getFont().deriveFont(Font.BOLD, 20f);

        // Create labels
        JLabel lId = new JLabel("AccountID:");     lId.setFont(labelFont);
        JLabel lFirst = new JLabel("Firstname:");  lFirst.setFont(labelFont);
        JLabel lLast = new JLabel("Lastname:");    lLast.setFont(labelFont);
        JLabel lEmail = new JLabel("Email:");      lEmail.setFont(labelFont);
        JLabel lContact = new JLabel("Contact Number:"); lContact.setFont(labelFont);
        JLabel lAddress = new JLabel("Address:");  lAddress.setFont(labelFont);

        JLabel[] labels = new JLabel[] { lId, lFirst, lLast, lEmail, lContact, lAddress };
        int maxLabelW = 0;
        for (JLabel L : labels) {
            Dimension d = L.getPreferredSize();
            if (d.width > maxLabelW) maxLabelW = d.width;
        }
        for (JLabel L : labels) {
            Dimension d = L.getPreferredSize();
            L.setPreferredSize(new Dimension(maxLabelW, d.height));
        }

        int fieldCols = 36;

        // Row 0 - AccountID
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.LINE_END;
        form.add(lId, c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        JTextField tfId = new JTextField(); tfId.setColumns(fieldCols); tfId.setEditable(false);
        form.add(tfId, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;

        // Row 1 - Firstname
        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.LINE_END;
        form.add(lFirst, c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        JTextField tfFirst = new JTextField(); tfFirst.setColumns(fieldCols); tfFirst.setEditable(false);
        form.add(tfFirst, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;

        // Row 2 - Lastname
        c.gridx = 0; c.gridy = 2; c.anchor = GridBagConstraints.LINE_END;
        form.add(lLast, c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        JTextField tfLast = new JTextField(); tfLast.setColumns(fieldCols); tfLast.setEditable(false);
        form.add(tfLast, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;

        // Row 3 - Email
        c.gridx = 0; c.gridy = 3; c.anchor = GridBagConstraints.LINE_END;
        form.add(lEmail, c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        JTextField tfEmail = new JTextField(); tfEmail.setColumns(fieldCols); tfEmail.setEditable(false);
        form.add(tfEmail, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;

        // Row 4 - Contact Number
        c.gridx = 0; c.gridy = 4; c.anchor = GridBagConstraints.LINE_END;
        form.add(lContact, c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        JTextField tfContact = new JTextField(); tfContact.setColumns(20); tfContact.setEditable(false);
        form.add(tfContact, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;

        // Row 5 - Address (multi-line)
        c.gridx = 0; c.gridy = 5; c.anchor = GridBagConstraints.NORTHEAST;
        form.add(lAddress, c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        JTextArea taAddress = new JTextArea(3, fieldCols);
        taAddress.setEditable(false); taAddress.setLineWrap(true); taAddress.setWrapStyleWord(true);
        JScrollPane spAddr = new JScrollPane(taAddress);
        spAddr.setPreferredSize(new Dimension(540, 96));
        form.add(spAddr, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;

        // place form at left-of-center area
        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        JPanel leftContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftContainer.setOpaque(false);
        leftContainer.add(form);
        body.add(leftContainer, BorderLayout.WEST);
        root.add(body, BorderLayout.CENTER);

        return root;
    }
}