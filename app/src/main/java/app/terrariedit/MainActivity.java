package app.terrariedit;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private View itemDetailsView;
    private Button editButton;
    private EditText countTextEdit;
    private Spinner prefixSpinner;
    private TextView itemNameText;
    private ImageView itemImage;
    private GridView gridview;
    private MainActivity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemDetailsView = findViewById(R.id.itemDetailsPanel);
        editButton = (Button) findViewById(R.id.editButton);
        countTextEdit = (EditText) findViewById(R.id.countTextEdit);

        prefixSpinner = (Spinner) findViewById(R.id.prefixSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_prefixes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefixSpinner.setAdapter(adapter);

        itemNameText = (TextView) findViewById(R.id.itemName);
        itemNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(self, ItemSelectionActivity.class);
                startActivity(newIntent);
            }
        });
        itemImage = (ImageView) findViewById(R.id.itemImage);

        gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
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

        return super.onOptionsItemSelected(item);
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private int i = 0;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return 40;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Button button;
            if (convertView == null) {
                button = new Button(mContext);
                button.setLayoutParams(new GridView.LayoutParams(100, 100));
            } else {
                button = (Button) convertView;
            }

            return button;
        }
    }
}
