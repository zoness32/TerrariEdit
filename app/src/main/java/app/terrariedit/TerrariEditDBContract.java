package app.terrariedit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by teernisse on 2/10/2015.
 */
public class TerrariEditDBContract {
    public TerrariEditDBContract() {
        // here to prevent accidental object construction
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ITEMS =
            "CREATE TABLE " + Items.TABLE_NAME + " (" +
                    Items._ID + " INTEGER PRIMARY KEY," +
                    Items.COLUMN_NAME_ITEM_NAME + TEXT_TYPE + COMMA_SEP +
                    Items.COLUMN_NAME_ITEM_ID + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_ITEMS =
            "DEOP TABLE IF EXISTS " + Items.TABLE_NAME;

    private static final String SQL_CREATE_PREFIXES =
            "CREATE TABLE " + Prefixes.TABLE_NAME + " (" +
                    Prefixes._ID + " INTEGER PRIMARY KEY, " +
                    Prefixes.COLUMN_NAME_PREFIX_NAME + TEXT_TYPE + COMMA_SEP +
                    Prefixes.COLUMN_NAME_PREFIX_ID + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_PREFIXES =
            "DEOP TABLE IF EXISTS " + Prefixes.TABLE_NAME;

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

    public class TerrariEditDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "TerrariEdit.db";

        public TerrariEditDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ITEMS);
            db.execSQL(SQL_CREATE_PREFIXES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ITEMS);
            db.execSQL(SQL_DELETE_PREFIXES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
