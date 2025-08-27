package sammc.client.gui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FieldPanel extends JPanel {
    public JLabel label;
    public JTextField field;

    public FieldPanel(String field_name) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 25));
        label = new JLabel(" " + field_name);
        label.setPreferredSize(new Dimension(80, 25));
        field = new JTextField();
        field.setMaximumSize(new Dimension(6000, 25));

        add(label);
        add(field);            
    }
    
}
