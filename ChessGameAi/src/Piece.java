
public class Piece {

    private boolean isCaptured = false;
    private int color;
    public static final int COLOR_WHITE = 0;
    public static final int COLOR_BLACK = 1;
    private int type;
    private int row;
    private int coloumn;

    public Piece(int color, int type, int row, int column) {
        this.color = color;
        this.type = type;
        this.row = row;
        this.coloumn = column;

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColoumn() {
        return coloumn;
    }

    public void setColoumn(int coloumn) {
        this.coloumn = coloumn;
    }

    public static String getColumnStr(int column) {
        String columnFind = null;
        if (column == 0) {
            columnFind = "A";
        }
        if (column == 1) {
            columnFind = "B";
        }

        if (column == 2) {
            columnFind = "C";
        }

        if (column == 3) {
            columnFind = "D";
        }

        if (column == 4) {
            columnFind = "E";
        }

        if (column == 5) {
            columnFind = "F";
        }
        return columnFind;

    }

    public static String getRowStr(int row) {
        String rowFind = null;
        if (row == 0) {
            rowFind = "1";
        }
        if (row == 1) {
            rowFind = "2";
        }

        if (row == 2) {
            rowFind = "3";
        }

        if (row == 3) {
            rowFind = "4";
        }

        if (row == 4) {
            rowFind = "5";
        }

        if (row == 5) {
            rowFind = "6";
        }
        return rowFind;

    }

    public String getPosition() {
        String playerColor = null;
        String pawnProperty = null;
        String row = null;
        String column = null;
        if (color == 0) {
            playerColor = "white";

        } else {
            playerColor = "black";
        }

        if (type == 1) {
            pawnProperty = "R";
        }
        if (type == 2) {
            pawnProperty = "B";
        }

        if (type == 3) {
            pawnProperty = "Q";
        }
        if (type == 4) {
            pawnProperty = "K";
        }
        if (type == 5) {
            pawnProperty = "P";
        }

        row = getRowStr(this.row);
        column = getColumnStr(this.coloumn);
        return playerColor + "" + pawnProperty + "" + row + "/" + column;

    }

    public void isCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }

    public boolean isCaptured() {
        return this.isCaptured;
    }

}
