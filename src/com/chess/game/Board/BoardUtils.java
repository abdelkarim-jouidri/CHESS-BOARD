package com.chess.game.Board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);

    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;

    private BoardUtils(){
        throw new RuntimeException("Cannot be instantiated");
    }
    public static boolean isValidCoordinate(int coordinate) {
        return coordinate < 64 && coordinate >= 0;
    }

    private static boolean[] initColumn(int columnNumber) {
        boolean[] column = new boolean[64];
        do{
            column[columnNumber] = true;
            columnNumber++;
        }while (columnNumber<64);
        return column;
    }
}
