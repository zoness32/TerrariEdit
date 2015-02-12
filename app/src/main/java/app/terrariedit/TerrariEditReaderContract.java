package app.terrariedit;

import android.provider.BaseColumns;

/**
 * Created by teernisse on 2/10/2015.
 */
public class TerrariEditReaderContract {
    public TerrariEditReaderContract() {

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ITEMS =
            "CREATE TABLE " + Items.TABLE_NAME + " (" +
                    Items._ID + " INTEGER PRIMARY KEY," +
                    Items.COLUMN_NAME_ITEM_NAME + TEXT_TYPE + COMMA_SEP +
                    Items.COLUMN_NAME_ITEM_ID + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_ITEMS = "";

    private static final String SQL_CREATE_PREFIXES =
            "CREATE TABLE " + Prefixes.TABLE_NAME + " (" +
                    Prefixes._ID + " INTEGER PRIMARY KEY, " +
                    Prefixes.COLUMN_NAME_PREFIX_NAME + TEXT_TYPE + COMMA_SEP +
                    Prefixes.COLUMN_NAME_PREFIX_ID + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_PREFIXES = "";

    public static abstract class Items implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_ITEM_NAME = "item_name";
    }

    public static abstract class Prefixes implements BaseColumns {
        public static final String TABLE_NAME = "prefixes";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_PREFIX_ID = "prefix_id";
        public static final String COLUMN_NAME_PREFIX_NAME = "prefix_name";
    }
}
