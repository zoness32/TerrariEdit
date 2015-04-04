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
        a.itemNameText.setText(i.getName());
        a.itemCountText.setText(i.getCount());
        int imageId = a.getResources().getIdentifier(i.getImageName(), "drawable", a.getPackageName());
        if (imageId > 0) {
            Drawable new_image = a.getResources().getDrawable(imageId);
            a.itemImage.setBackgroundDrawable(new_image);
        } else {
            a.itemImage.setBackgroundDrawable(null);
        }
    }
}
