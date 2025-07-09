package models.response.textModeration.submodules;

public class TextModerationsLegend {
    /**
     * The numerical index of the label.
     */
    private final int index;

    /**
     * A unique string identifier for the label.
     * This ID serves as a machine-readable way to identify the label type.
     */
    private final String id;

    public TextModerationsLegend(int index,String id) {
        this.index = index;
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }
}
