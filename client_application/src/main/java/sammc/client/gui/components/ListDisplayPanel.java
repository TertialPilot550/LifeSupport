package sammc.client.gui.components;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import sammc.client.data.LooselyTypedData;
import sammc.client.gui.DatabasePanel;

/**
 * Display all of the different datapoints in the database right now
 * 
 * @author sammc
 */
public class ListDisplayPanel extends JPanel {

    public final static String no_connection_message = "No Connection to Server";

    private LooselyTypedData selected;
    private DatabasePanel parent;
    private DefaultListModel<DataLabel> listModel;
    private JList<DataLabel> list;
    private JTextField typeField;

    public ListDisplayPanel(DatabasePanel parent) {
        this.parent = parent;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 400));

        typeField = new JTextField();
        typeField.getDocument().addDocumentListener(new TextFieldListener() {

            @Override
            protected void refresh() {
                relabel();
            }
            
        });
        typeField.setMaximumSize(new Dimension(1000, 50));
        add(typeField);

        listModel = new DefaultListModel<>();
        list = new JList<>();
        list.setModel(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) { // Check if an item is actually selected
                // select the one that is actually selected
                parent.fullRefresh();
            }
        });
        JScrollPane scroll_pane = new JScrollPane(list);
        add(scroll_pane);
        relabel();
    }

    public LooselyTypedData getSelected() {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) { // Check if an item is actually selected
            // select the one that is actually selected
            DataLabel selected_label = listModel.get(selectedIndex);
            return parent.db.read(selected_label.id);
        }
        return null;
    }

    public void relabel() {
        String type = typeField.getText();

        DataLabel[] data;
        if (type.equals("")) {
            data = parent.db.listLabels();
        } else {
            data = parent.db.listLabelsByType(type);
        }

        listModel.clear();
        if (data == null ) data = new DataLabel[] { new DataLabel (no_connection_message, -1)};
        for (DataLabel s : data) {
            listModel.addElement(s);
        }

        repaint();
        parent.repaint();
    }
    
}
