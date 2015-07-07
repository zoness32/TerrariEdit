package app.terrariedit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by teernisse on 4/21/2015.
 */
public class SearchListArrayAdapter extends ArrayAdapter<ArrayList<Item>> {
    private final Context context;
    private final ArrayList<Item> values;

    public SearchListArrayAdapter(Context context, ArrayList<Item> objects) {
        super(context, R.layout.search_list_view_item_layout);
        this.context = context;
        this.values = objects;
    }

//TODO: optimize listview

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.search_list_view_item_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.searchItemName);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.searchItemImage);
            viewHolder.id = (TextView) rowView.findViewById(R.id.searchIdText);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Item i = this.values.get(position);

        holder.text.setText(i.getName());
        holder.image.setImageResource(context.getResources().getIdentifier(i.getImageName(), "drawable", context.getPackageName()));
        holder.id.setText("id: " + i.getId());

        return rowView;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
        public TextView id;
    }
}
