package app.terrariedit;

import android.content.Context;
import android.widget.ImageButton;

/**
 * Created by teernisse on 4/23/2015.
 */
public class ItemImageButton extends ImageButton {
    private Item item;

    public ItemImageButton(Context context) {
        super(context);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item i) {
        item = i;
    }
}
