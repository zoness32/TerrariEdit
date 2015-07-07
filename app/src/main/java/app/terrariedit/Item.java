package app.terrariedit;

/**
 * Created by teernisse on 3/24/2015.
 */
public class Item {
    private String name;
    private String id;
    private String prefix;
    private String count;
    private String imageName;
    private boolean isEmpty;
    private int gridPosition;

    public Item() {
        this.name = "";
        this.id = "";
        this.prefix = "";
        this.count = "";
        this.imageName = "";
        this.isEmpty = true;
    }

    public Item(String _name, String _id, String _prefix, String _count) {
        this.name = _name;
        this.id = _id;
        this.prefix = _prefix;
        this.count = _count;
        this.setImageName();
        this.isEmpty = false;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "Empty item slot";
        }

        return this.getPrefix() + " " + this.getName() + " " + this.getCount() + " " + this.getId() + " " + this.getImageName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.isEmpty = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.isEmpty = false;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        this.isEmpty = false;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
        this.isEmpty = false;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName() {
        this.imageName = "item_" + this.id;
        this.isEmpty = false;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void setGridPosition(int pos) {
        this.gridPosition = pos;
    }

    public int getGridPosition() {
        return this.gridPosition;
    }
}
