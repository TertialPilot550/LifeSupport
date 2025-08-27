package sammc.client.gui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AreaPanel extends JPanel {
     public JLabel label;
        public JTextArea field;

        public AreaPanel(String field_name) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(Color.WHITE);
            setMinimumSize(new Dimension(600, 800));
            label = new JLabel(field_name);
            label.setBackground(Color.WHITE);
            label.setPreferredSize(new Dimension(80, 25));
            field = new JTextArea();
            field.setMinimumSize(new Dimension(1000, 400));
            add(label);
            add(field);            
        }
}
