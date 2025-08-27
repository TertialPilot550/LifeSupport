package sammc.client.gui.components;

public class DataLabel {

    public String name;
    public int id;

    public DataLabel(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + id + "]: " + name;
    }

    
}
