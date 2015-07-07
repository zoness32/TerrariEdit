package app.terrariedit;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teernisse on 3/24/2015.
 */
public class ItemsDataSource {
    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private String[] itemNameColumn = {TerrariEditDbHelper.COLUMN_ITEM_NAME};

    public ItemsDataSource(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public String getItemName(int itemId) {
        String[] itemid = {Integer.toString(itemId)};
        Cursor cursor = null;
        cursor = database.query(TerrariEditDbHelper.TABLE_ITEMS,
                itemNameColumn, "item_id=?", itemid, null, null, null);
        String name = "";

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            name = cursor.getString(0);
        } else {
            Log.d("Empty Cursor", "COULD NOT FIND ITEM " + itemId);
        }

        // make sure to close the cursor
        cursor.close();
        return name;
    }

    public ArrayList<Item> getItemNamesFromSearchTerm(String searchTerm) {
        ArrayList<Item> result = new ArrayList<>();
        String[] args = {"%" + searchTerm + "%"};
        Cursor cursor = null;
        cursor = database.rawQuery(TerrariEditDbHelper.findItemBySearchTermQuery, args);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Item i = new Item();
                i.setName(cursor.getString(1));
                i.setId(cursor.getString(2));
                i.setImageName();
                result.add(i);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return result;
    }
}

//TODO: fix boreal wood db issues
