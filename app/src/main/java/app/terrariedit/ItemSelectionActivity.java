package app.terrariedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Taylor on 2/27/2015.
 */
public class ItemSelectionActivity extends ActionBarActivity {

    private Button itemSearchBtn;
    private EditText itemSearchEdit;
    private Spinner itemFilterSpinner;
    private ListView itemSearchList;

    private ArrayList<Item> searchResults;
    private SearchListArrayAdapter slAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_selection_layout);

        searchResults = new ArrayList<>();

        itemSearchBtn = (Button) findViewById(R.id.itemSearchBtn);
        itemSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForItem(itemSearchEdit.getText().toString());
            }
        });

        itemSearchEdit = (EditText) findViewById(R.id.itemSearchTxt);

        itemFilterSpinner = (Spinner) findViewById(R.id.itemFilterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemFilterSpinner.setAdapter(adapter);

        itemSearchList = (ListView) findViewById(R.id.itemSearchList);
        slAdapter = new SearchListArrayAdapter(this, searchResults);
        itemSearchList.setAdapter(slAdapter);
        itemSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedItem.getInstance().setSelectedItem(searchResults.get(position));
                exit();
            }
        });
    }

    private void exit() {
        finish();
    }

    private void searchForItem(String text) {
        ItemsDataSource ids = new ItemsDataSource(this);
        ids.open();
        searchResults = ids.getItemNamesFromSearchTerm(text);
        ids.close();

        slAdapter = new SearchListArrayAdapter(this, searchResults);
        itemSearchList.setAdapter(slAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
