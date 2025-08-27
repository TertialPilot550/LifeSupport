package sammc.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import sammc.client.data.DataManager;
import sammc.client.data.LooselyTypedData;
import sammc.client.gui.components.AreaPanel;
import sammc.client.gui.components.FieldPanel;
import sammc.client.gui.components.ListDisplayPanel;

/**
 * JPanel subclass that displays an interface for interacting with the 
 * remote loosely typed database.
 */
public class DatabasePanel extends JPanel {
    
    public DataManager db;

    private ListDisplayPanel listDisplay;
    private FieldPanel name_fp;
    private FieldPanel id_fp;
    private FieldPanel type_fp;
    private FieldPanel datetime_fp;
    private AreaPanel data_ap;

    public DatabasePanel() {
        // want to be able to crud and read the list
        setSize(new Dimension(1200, 600));
        setFocusable(false);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setVisible(true);

        db = new DataManager("http://localhost:8080/data");

        // list panel
        listDisplay = new ListDisplayPanel(this);
        add(listDisplay, BorderLayout.WEST);

        // also have a view of one particular loosely typed data that updates to whatever the list panel is selecting
        JPanel container = new JPanel();
        container.setBackground(Color.WHITE);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setSize(800, 600);
        // show all the fields of loosely typed data
        
        name_fp = new FieldPanel("Name       ");
        id_fp = new FieldPanel(  "ID          ");
        type_fp = new FieldPanel("Type       ");
        datetime_fp = new FieldPanel("Datetime");
        data_ap = new AreaPanel("Data");

        container.add(name_fp);
        container.add(id_fp);
        container.add(type_fp);
        container.add(datetime_fp);
        container.add(data_ap);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton create = new JButton("Post");
        create.addActionListener(e -> {
            LooselyTypedData new_data = new LooselyTypedData();
            new_data.setName(name_fp.field.getText());
            new_data.setDatetime(datetime_fp.field.getText());
            new_data.setType(type_fp.field.getText());
            new_data.setData(data_ap.field.getText());

            db.create(new_data);

            fullRefresh();
        });
        buttonPanel.add(create);
        JButton update = new JButton("Revise");
        update.addActionListener(e -> {
            LooselyTypedData new_data = new LooselyTypedData();
            new_data.setName(name_fp.field.getText());
            new_data.setId(Integer.parseInt(id_fp.field.getText()));
            new_data.setDatetime(datetime_fp.field.getText());
            new_data.setType(type_fp.field.getText());
            new_data.setData(data_ap.field.getText());
            try {
                fullRefresh();
            } catch (Exception except) {
                except.printStackTrace();
            }
        });
        buttonPanel.add(update);
        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> {
            try {
                db.delete(Integer.parseInt(id_fp.field.getText()));
                fullRefresh();
            } catch (Exception except) {
                except.printStackTrace();
            }
        });
        buttonPanel.add(delete);
        container.add(buttonPanel);
        add(container, BorderLayout.CENTER);
    }  

    /**
     * Completely Refresh the interface
     * @param selected
     */
    public void fullRefresh() {
        if (listDisplay != null) {
            LooselyTypedData selected = listDisplay.getSelected();

            if (selected == null) {
                name_fp.field.setText("");
                id_fp.field.setText("");
                type_fp.field.setText("");
                datetime_fp.field.setText("");
                data_ap.field.setText("");

            } else {
                // If there is a real selection
                // For each field, set the text the objects value if it exists, otherwise set it to "";
                name_fp.field.setText((selected.getName() != null) ? selected.getName() : "");
                id_fp.field.setText((selected.getId() != -1) ? "" + selected.getId() : "");
                type_fp.field.setText((selected.getType() != null) ? selected.getType() : "");
                datetime_fp.field.setText((selected.getDatetime() != null) ? selected.getDatetime() : "");
                data_ap.field.setText((selected.getData() != null) ? selected.getData() : "");
            }

            listDisplay.relabel();
            listDisplay.repaint();
        } 
        
        repaint();
    }

}
