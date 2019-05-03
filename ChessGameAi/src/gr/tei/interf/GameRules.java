package gr.tei.interf;


public interface GameRules {

    boolean isPawnMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn);

    boolean isRookMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn);

    boolean isBishopMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn);

    boolean isQueenMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn);
    
    boolean isKingMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn);
    
    
    
}
