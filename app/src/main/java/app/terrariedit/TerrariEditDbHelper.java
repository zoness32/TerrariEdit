package app.terrariedit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by teernisse on 3/24/2015.
 */
public class TerrariEditDbHelper extends SQLiteOpenHelper {
    public static final String TABLE_ITEMS = "items";
    public static final String ITEM_DB_ID = "id";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_NAME = "item_name";

    public static final String TABLE_PREFIX = "prefixes";
    public static final String PREFIX_DB_ID = "id";
    public static final String COLUMN_PREFIX_ID = "prefix_id";
    public static final String COLUMN_PREFIX_NAME = "prefix_name";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TerrariEdit.db";

    public static final String findItemBySearchTermQuery = "SELECT * FROM items WHERE item_name LIKE ? ORDER BY item_name ASC";

    public TerrariEditDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // do nothing
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }
}
