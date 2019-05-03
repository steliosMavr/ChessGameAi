
public class Move {

    public int sourceRow;
    public int sourceColumn;
    public int targetRow;
    public int targetColumn;
    public Piece capturedPiece;
    public Move(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        this.sourceRow = sourceRow;
        this.sourceColumn = sourceColumn;
        this.targetRow = targetRow;
        this.targetColumn = targetColumn;
    }

    public int getSourceRow() {
        return sourceRow;
    }

    public Move clone() {
        return new Move(sourceRow, sourceColumn, targetRow, targetColumn);
    }

    public void setSourceRow(int sourceRow) {
        this.sourceRow = sourceRow;
    }

    public int getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(int sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    public int getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(int targetColumn) {
        this.targetColumn = targetColumn;
    }

}
