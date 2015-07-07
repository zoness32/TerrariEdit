package app.terrariedit;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by teernisse on 3/24/2015.
 */
public class PrefixDataSource {
    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private String[] prefixNameColumn = {TerrariEditDbHelper.COLUMN_PREFIX_NAME};
    private String[] prefixIdColumn = {TerrariEditDbHelper.COLUMN_PREFIX_ID};

    public PrefixDataSource(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public String getPrefixName(int prefixId) {
        if (prefixId != 0) {
            String[] prefixid = {Integer.toString(prefixId)};
            Cursor cursor = null;
            cursor = database.query(TerrariEditDbHelper.TABLE_PREFIX,
                    prefixNameColumn, "prefix_id=?", prefixid, null, null, null);
            String name = "";

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                name = cursor.getString(0);
            } else {
                Log.d("Empty cursor", "COULD NOT FIND PREFIX " + prefixId);
            }
            // make sure to close the cursor
            cursor.close();
            return name;
        } else {
            return "";
        }
    }

    public String getPrefixId(String prefix) {
        String[] prefixName = {prefix};
        Cursor cursor = null;
        cursor = database.query(TerrariEditDbHelper.TABLE_PREFIX,
                prefixIdColumn, "prefix_id=?", prefixName, null, null, null);
        String prefixId = "";

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            prefixId = cursor.getString(0);
        } else {
            Log.d("Empty Cursor", "COULD NOT FIND PREFIX ID FOR PREFIX " + prefix);
        }

        cursor.close();
        return prefixId;
    }
}