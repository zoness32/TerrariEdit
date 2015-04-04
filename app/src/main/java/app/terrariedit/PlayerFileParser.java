package app.terrariedit;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by teernisse on 3/23/2015.
 */
public class PlayerFileParser {
    private static String playerFile = "test.player";
    private Context context;

    public PlayerFileParser(Context c) {
        context = c;
    }

    public byte[] readFile() throws IOException {
        byte[] buffer = new byte[2048];
        InputStream input = context.getAssets().open(playerFile);
        input.read(buffer);

        return buffer;
    }

    public byte[][] parseInput(byte[] bytes) {
        byte[][] binItems = new byte[40][];
        for (int i = 89, j = 0; i < 289; i = i + 5) {
            byte[] item = new byte[5];
            item[0] = bytes[i];
            item[1] = bytes[i+1];
            item[2] = bytes[i+2];
            item[3] = bytes[i+3];
            item[4] = bytes[i+4];
            binItems[j++] = item;
        }

        return binItems;
    }
}
