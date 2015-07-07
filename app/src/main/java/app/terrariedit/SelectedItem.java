package app.terrariedit;

/**
 * Created by teernisse on 4/22/2015.
 */
public class SelectedItem {
    private Item selectedItem;

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item i) {
        this.selectedItem = i;
    }

    private static final SelectedItem holder = new SelectedItem();

    public static SelectedItem getInstance() {
        return holder;
    }
}
