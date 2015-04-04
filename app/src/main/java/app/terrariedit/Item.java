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

    public Item() {
        this.name = "";
        this.id = "";
        this.prefix = "";
        this.count = "";
        this.imageName = "";
    }

    public Item(String _name, String _id, String _prefix, String _count) {
        this.name = _name;
        this.id = _id;
        this.prefix = _prefix;
        this.count = _count;
        this.setImageName();
    }

    @Override
    public String toString() {
        return this.getPrefix() + " " + this.getName() + " " + this.getCount() + " " + this.getId() + " " + this.getImageName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName() {
        this.imageName = "item_" + this.id;
    }
}
