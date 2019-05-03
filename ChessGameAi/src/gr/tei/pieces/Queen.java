package gr.tei.pieces;

public class Queen {

    private int color; //if color=0; tote einai white an eine color =1 eine black;
    private int row;
    private int column;
    
    private int type = 3;

    public Queen(int color, int row, int column) {
        this.color = color;
        this.row = row;
        this.column = column;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

  

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
