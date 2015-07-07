package app.terrariedit;

/**
 * Created by teernisse on 3/24/2015.
 */
public class ItemParser {
    public static Item ParseByteArray(byte[] array) {
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

    public static byte[] ParseItem(Item i) {
        byte[] arr = new byte[5];
        int firstByteMask = 0x00001100;
        int secondByteMask = 0x00000011;

        int id = Integer.valueOf(i.getId());
        int count = Integer.valueOf(i.getCount());
        int prefix = Integer.valueOf(i.getPrefix());

        arr[0] = (byte) (id & firstByteMask);
        arr[1] = (byte) (id & secondByteMask);
        arr[2] = (byte) (count & firstByteMask);
        arr[3] = (byte) (count & secondByteMask);
        arr[4] = (byte) prefix;

        return arr;
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
