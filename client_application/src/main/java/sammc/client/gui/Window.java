package sammc.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
    
    public Window() {
        setTitle("Database Portal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 600));
        setLocationRelativeTo(null);
        setVisible(true);

        DatabasePanel pane = new DatabasePanel();
        add(pane, BorderLayout.CENTER);

        repaint();
    }

}
