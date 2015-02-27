package app.terrariedit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Taylor on 2/27/2015.
 */
public class ItemSelectionActivity extends ActionBarActivity {

    private Button itemSearchBtn;
    private EditText itemSearchEdit;
    private Spinner itemFilterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_selection_layout);

        itemSearchBtn = (Button) findViewById(R.id.itemSearchBtn);
        itemSearchEdit = (EditText) findViewById(R.id.itemSearchTxt);

        itemFilterSpinner = (Spinner) findViewById(R.id.itemFilterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemFilterSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
