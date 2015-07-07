package app.terrariedit;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by teernisse on 3/26/2015.
 */
public class ImageButtonClickListener implements View.OnClickListener {
    Item i;
    MainActivity a;

    public ImageButtonClickListener(MainActivity activity, Item item) {
        i = item;
        a = activity;
    }

    @Override
    public void onClick(View v) {
        if (i.isEmpty()) {
            a.itemCountText.setText("0");
            a.itemImage.setImageResource(0);
            a.itemNameText.setText("Empty item slot");
        } else {
            a.itemNameText.setText(i.getName());
            a.itemCountText.setText(i.getCount());
            int imageId = a.getResources().getIdentifier(i.getImageName(), "drawable", a.getPackageName());
            if (imageId > 0) {
                a.itemImage.setImageResource(imageId);
            }
        }
        a.setSelectedItemLoc(i.getGridPosition());
    }
}
