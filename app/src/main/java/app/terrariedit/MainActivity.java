package app.terrariedit;

import android.app.DialogFragment;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class MainActivity extends ActionBarActivity implements PlayerFilePickerDialogFragment.PlayerFilePickerDialogListener {
    private View itemDetailsView;
    private Button itemEditButton;
    private Button itemSaveButton;
    public EditText itemCountText;
    private Spinner itemPrefixSpinner;
    public TextView itemNameText;
    public ImageView itemImage;
    private GridView gridview;
    private MainActivity self = this;
    private byte[][] binItems;
    private List<Item> itemsList;
    private int selectedItemLoc;
    public Item selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemDetailsView = findViewById(R.id.itemDetailsPanel);

        itemEditButton = (Button) findViewById(R.id.itemEdit);
        itemEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(self, ItemSelectionActivity.class);
                startActivityForResult(newIntent, RESULT_OK);
            }
        });

        itemSaveButton = (Button) findViewById(R.id.itemSave);
        itemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });

        itemCountText = (EditText) findViewById(R.id.itemCount);
        itemCountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCountText.setSelection(0, 3);
            }
        });

        itemPrefixSpinner = (Spinner) findViewById(R.id.itemPrefix);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_prefixes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemPrefixSpinner.setAdapter(adapter);

        itemNameText = (TextView) findViewById(R.id.itemName);

        itemImage = (ImageView) findViewById(R.id.itemImage);

        gridview = (GridView) findViewById(R.id.gridView);

        itemsList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Item i = SelectedItem.getInstance().getSelectedItem();
        if (i != null) {
            itemNameText.setText(i.getName());
            itemImage.setImageResource(MainActivity.this.getResources().getIdentifier(i.getImageName(), "drawable", getPackageName()));
        }
    }

    private void saveItem() {

    }

    public void showFilePickerDialog(DialogFragment filePicker) {
        filePicker.show(getFragmentManager(), "PlayerFilePickerDialogFragment");
    }

    @Override
    public void onPlayerFileSelected(DialogFragment dialog, String filename) {
        openFile(filename);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = menuItem.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_save) {
            //TODO: add logic to save file
            Item newItem = SelectedItem.getInstance().getSelectedItem();
            newItem.setCount(itemCountText.getText().toString());

            PrefixDataSource pds = new PrefixDataSource(this);
            pds.open();
            String prefixId = pds.getPrefixId(itemPrefixSpinner.getSelectedItem().toString());
            pds.close();

            newItem.setPrefix(prefixId);
            byte[] arr = ItemParser.ParseItem(newItem);
            Item i = ItemParser.ParseByteArray(arr);
            int y = 9;
        } else if (id == R.id.action_init_db) {
            initDB();
        } else if (id == R.id.action_open_file) {
            File[] fileList = getFilesDir().listFiles();
            List<String> filenames = new ArrayList<>();
            for (File file : fileList) {
                filenames.add(file.getName());
            }
            PlayerFilePickerDialogFragment filePicker = new PlayerFilePickerDialogFragment();
            filePicker.setFilenames(filenames.toArray(new String[filenames.size()]));
            showFilePickerDialog(filePicker);
        } else if (id == R.id.action_root) {
            if (RootTools.isAccessGiven()) {
                boolean copied = RootTools.copyFile("/data/data/com.and.games505.TerrariaPaid/files/*.player", getFilesDir().getPath(), true, true);
                if (copied) {
                    Toast.makeText(this, "Files copied", Toast.LENGTH_SHORT).show();
                        Command command = new Command(0, "su chmod 777 *.player");
                    try {
                        RootTools.getShell(true).add(command);
                    } catch (Exception e) {
                        Log.e("File permissions error", "Error changing file permissions " + e);
                    }
                } else {
                    Log.d("Copy", "Copy failed");
                }
            }
        }

        return super.

                onOptionsItemSelected(menuItem);
    }

    private void openFile(String file) {
        PlayerFileParser parser = new PlayerFileParser(this);
        byte[] bin;
        try {
            bin = parser.readFile(file);

            binItems = parser.parseInput(bin);
            ItemsDataSource ids = new ItemsDataSource(this);
            PrefixDataSource pds = new PrefixDataSource(this);
            ids.open();
            pds.open();

            for (int i = 0; i < binItems.length; i++) {
                Item it = ItemParser.ParseByteArray(binItems[i]);
                if (!it.isEmpty()) {
                    it.setName(ids.getItemName(Integer.valueOf(it.getId())));
                    it.setPrefix(pds.getPrefixName(Integer.valueOf(it.getPrefix())));
                }

                itemsList.add(it);

                //TODO: remove log once UI is connected
                Log.d("Item:", it.toString());
            }

            ids.close();
            pds.close();
            gridview.setAdapter(new ImageAdapter(this, itemsList));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        private List<Item> itemsList;
        private Context c;

        public ImageAdapter(Context context, List<Item> itemsList) {
            c = context;
            this.itemsList = itemsList;
        }

        @Override
        public int getCount() {
            return 40;
        }

        @Override
        public Object getItem(int position) {
            return itemsList.get(position);
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

            Item it = itemsList.get(position);
            it.setGridPosition(position);
            int imageId = MainActivity.this.getResources().getIdentifier(it.getImageName(), "drawable", getPackageName());
            b.setImageResource(imageId);
            b.setElevation(10);
            b.setOnClickListener(new ImageButtonClickListener(self, it));

            return b;
        }

    }

    public void modifyItem(int pos, byte[] item) {
        binItems[pos] = item;
    }

    public void setSelectedItemLoc(int loc) {
        selectedItemLoc = loc;
    }
}
