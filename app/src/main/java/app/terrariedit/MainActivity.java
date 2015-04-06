package app.terrariedit;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private View itemDetailsView;
    private Button itemEditButton;
    public EditText itemCountText;
    private Spinner itemPrefixSpinner;
    public TextView itemNameText;
    public ImageView itemImage;
    private GridView gridview;
    private MainActivity self = this;
    private List<Item> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsList = new ArrayList<>();

        itemDetailsView = findViewById(R.id.itemDetailsPanel);

        itemEditButton = (Button) findViewById(R.id.itemEdit);
        itemEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(self, ItemSelectionActivity.class);
                startActivity(newIntent);
            }
        });

        itemCountText = (EditText) findViewById(R.id.itemCount);

        itemPrefixSpinner = (Spinner) findViewById(R.id.itemPrefix);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_prefixes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemPrefixSpinner.setAdapter(adapter);

        itemNameText = (TextView) findViewById(R.id.itemName);

        itemImage = (ImageView) findViewById(R.id.itemImage);

        gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this, itemsList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_init_db) {
            initDB();
        } else if (id == R.id.action_open_file) {
            openFile();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openFile() {
        PlayerFileParser parser = new PlayerFileParser(this);
        byte[] bin = new byte[0];
        try {
            bin = parser.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[][] binItems = parser.parseInput(bin);
        ItemsDataSource ids = new ItemsDataSource(this);
        PrefixDataSource pds = new PrefixDataSource(this);
        ids.open();
        pds.open();

        for (byte[] bytes : binItems) {
            Item it = ItemParser.ParseItem(bytes);
            it.setName(ids.getItemName(Integer.valueOf(it.getId())));
            it.setPrefix(pds.getPrefixName(Integer.valueOf(it.getPrefix())));
            itemsList.add(it);

            //TODO: remove log once UI is connected
            Log.d("Item:", it.toString());
        }

        ids.close();
        pds.close();
        gridview.setAdapter(new ImageAdapter(this, itemsList));
    }

    private void initDB() {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            Log.e("IO Exception", "Unable to create database");
        }

        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            Log.e("SQL Exception", e.getMessage());
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private List<Item> items;
        private Context c;

        public ImageAdapter(Context context, List<Item> itemList) {
            c = context;
            items = itemList;
        }

        @Override
        public int getCount() {
            return 40;
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageButton b;
            if (convertView == null) {
                b = new ImageButton(c);
                b.setLayoutParams(new GridView.LayoutParams(185, 185));
            } else {
                b = (ImageButton) convertView;
            }

            if (!items.isEmpty()) {
                Item it = items.get(position);
                int imageId = MainActivity.this.getResources().getIdentifier(it.getImageName(), "drawable", getPackageName());
                b.setImageResource(imageId);
                b.setOnClickListener(new ImageButtonClickListener(self, it));
            }

            return b;
        }
    }
}
