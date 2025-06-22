package models.response.textModeration.submodules;

public class TextModerationsLegend {
     private final int index;
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
