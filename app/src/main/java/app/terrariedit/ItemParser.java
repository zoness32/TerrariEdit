package app.terrariedit;

/**
 * Created by teernisse on 3/24/2015.
 */
public class ItemParser {
    public static Item ParseItem(byte[] array) {
        int id, prefix, count;

        int firstByte, secondByte;
        firstByte = morphInt(array[0]);
        secondByte = morphInt(array[1]);
        id = (secondByte << 8) | firstByte;

        firstByte = morphInt(array[2]);
        secondByte = morphInt(array[3]);
        count = (secondByte << 8) | firstByte;

        prefix = morphInt(array[4]);

        if (id <= 0) {
            return new Item();
        }

        return new Item("", Integer.toString(id), Integer.toString(prefix), Integer.toString(count));
    }

    private static int morphInt(int num) {
        int mask = 0xFFFFFF00;
        int checkNegMask = 0x00000080;
        if ((num & checkNegMask) >> 7 == 1) {
            num = num ^ mask;
        }
        return num;
    }
}
