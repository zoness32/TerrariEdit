package app.terrariedit;

import android.provider.BaseColumns;

/**
 * Created by teernisse on 2/10/2015.
 */
public class TerrariEditReaderContract {
    public TerrariEditReaderContract() {

    }

    private static final String TEXT_TYPE = " TEXT";

    public static abstract class Items implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_ITEM_NAME = "item_name";
    }

    public static abstract class Prefixes implements BaseColumns {
        public static final String TABLE_NAME = "prefixes";
        public static final String COLUMN_NAME_PREFIX_ID = "prefix_id";
        public static final String COLUMN_NAME_PREFIX_NAME = "prefix_name";
    }
}
