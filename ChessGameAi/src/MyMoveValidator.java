
import gr.tei.interf.GameRules;

public class MyMoveValidator implements GameRules {

    private ChessEngine chessEngine;
    private Piece myPiece;
    private Piece opponentPiece;

    public MyMoveValidator(ChessEngine chessEngine) {
        this.chessEngine = chessEngine;
    }

    public boolean isMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        boolean isValid = false;
        myPiece = chessEngine.getNonCapturedPieceAtLocation(sourceRow, sourceColumn);
        opponentPiece = chessEngine.getNonCapturedPieceAtLocation(targetRow, targetColumn);

        if (myPiece.getColor() != chessEngine.getGameState()) {
            System.out.println("Its not your turn");
            return false;
        }

        if (myPiece == null) {
            System.out.println("No pawn in this location");
        }

        if (myPiece.getType() == 5) {
            isValid = isPawnMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);
        }
        if (myPiece.getType() == 1) {

            isValid = isRookMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);

        }

        if (myPiece.getType() == 2) {

            isValid = isBishopMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);
        }

        if (myPiece.getType() == 3) {

            isValid = isQueenMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);
        }

        if (myPiece.getType() == 4) {

            isValid = isKingMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);
        }

        return isValid;

    }

    private boolean isTargetLocationFree() {
        return opponentPiece == null;
    }

    private boolean isTargetLocationCaptureable() {
        if (opponentPiece == null) {
            return false;
        } else if (opponentPiece.getColor() != myPiece.getColor()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isPawnMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        boolean isValid = false;

        if (isTargetLocationFree()) {
            if (sourceColumn == targetColumn) {
                if (myPiece.getColor() == 0) { //white
                    if (sourceRow + 1 == targetRow) {
                        isValid = true;
                    } else {

                        if ((sourceColumn == 0 && sourceRow == 1 || (sourceColumn == 1 && sourceRow == 1)) || (sourceColumn == 2 && sourceRow == 1) || (sourceColumn == 3 && sourceRow == 1) || (sourceColumn == 4 && sourceRow == 1) || (sourceColumn == 5 && sourceRow == 1)) {

                            if (sourceRow + 2 == targetRow) {
                                isValid = true;
                            }

                        } else {

                            // System.out.println("White Pawn doesnt moveeee");
                            isValid = false;
                        }
                    }
                } else { //black
                    if (sourceRow - 1 == targetRow) {
                        isValid = true;
                    } else {
                        if ((sourceColumn == 0 && sourceRow == 6 || (sourceColumn == 1 && sourceRow == 6)) || (sourceColumn == 2 && sourceRow == 6) || (sourceColumn == 3 && sourceRow == 6) || (sourceColumn == 4 && sourceRow == 6) || (sourceColumn == 5 && sourceRow == 6)) {
                            if (sourceRow - 2 == targetRow) {
                                isValid = true;
                            }

                        } else {
                            // System.out.println("Black Pawn doesnt move");
                            isValid = false;
                        }

                    }

                }
            } else {
                // System.out.println("No in the same column");
                isValid = false;
            }

        } else { //an Uparxei kapio adipalo pioni
            if (isTargetLocationCaptureable()) {
                if (sourceColumn + 1 == targetColumn || sourceColumn - 1 == targetColumn) { //right / left

                    if (myPiece.getColor() == 0) { //White
                        if (sourceRow + 1 == targetRow) {
                            isValid = true;
                        } else {
                            //  System.out.println("White Pawn doesnt move");
                            isValid = false;
                        }

                    } else {   //black

                        if (sourceRow - 1 == targetRow) {
                            isValid = true;

                        } else {
                            //System.out.println("Black Pawn doesnt move");
                            isValid = false;
                        }

                    }

                }

            } else {
                // System.out.println("Wrong column");
                isValid = false;
            }

        }

        return isValid;

    }

   

    private boolean checkForBeetweenPieces(int sourceRow, int sourceColumn,
            int targetRow, int targetColumn, int rowIncrementPerStep, int columnIncrementPerStep) {

        int currentRow = sourceRow + rowIncrementPerStep;
        int currentColumn = sourceColumn + columnIncrementPerStep;
        while (true) {
            if (currentRow == targetRow && currentColumn == targetColumn) {
                break;
            }
            if (currentRow < 0 || currentRow > 8
                    || currentColumn < 0 || currentColumn > 6) {
                break;
            }

            if(currentRow==targetRow && currentColumn==targetColumn){
                break;
            }    
            
            if (this.chessEngine.isAnotherPieceAtLocation(myPiece.getColor(), currentRow, currentColumn) || chessEngine.isAnotherEnemyPieceAtLocation(myPiece.getColor(), currentRow, currentColumn)) {
                //System.out.println("other pirce");
               
                return true;
            }

            currentRow += rowIncrementPerStep;
            currentColumn += columnIncrementPerStep;
        }
        return false;
    }

   

    @Override
    public boolean isRookMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        if (isTargetLocationFree() || isTargetLocationCaptureable()) {
            //ok
        } else {
           
            return false;
        }

        boolean isValid = false;

       
        int diffRow = targetRow - sourceRow;
        int diffColumn = targetColumn - sourceColumn;

        if (diffRow == 0 && diffColumn > 0) {
            // right
            isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, 0, +1);

        } else if (diffRow == 0 && diffColumn < 0) {
            // left
            isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, 0, -1);

        } else if (diffRow > 0 && diffColumn == 0) {
            // up
            isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, +1, 0);

        } else if (diffRow < 0 && diffColumn == 0) {
            // down
            isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, -1, 0);

        } else {
           
            isValid = false;
        }

        return isValid;

    }

    @Override
    public boolean isBishopMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        int differRows = targetRow - sourceRow;
        int differColumns = targetColumn - sourceColumn;
        boolean isValid = false;

        if (isTargetLocationFree() || isTargetLocationCaptureable()) {

            if (differRows == differColumns && differColumns > 0) {
                // moving  up-right
                isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, +1, +1);

            }

            if (differRows == differColumns && differColumns < 0) {

                // moving  downleft
                isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, -1, -1);
            }

            if (differRows == -differColumns && differColumns > 0) {
                // moving  down-right
                isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, -1, +1);
            }
            if (differRows == -differColumns && differColumns < 0) {
                // moving  up-left
                isValid = !checkForBeetweenPieces(sourceRow, sourceColumn, targetRow, targetColumn, +1, -1);
            }
        }
        return isValid;

    }

    @Override
    public boolean isQueenMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        boolean bishopMove;
        boolean rookMove;

        bishopMove = isBishopMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);
        rookMove = isRookMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);

        if (bishopMove == true) {
            return bishopMove;
        } else {
            return rookMove;
        }

    }

    @Override
    public boolean isKingMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        boolean isValid = false;
        if (isTargetLocationFree() || isTargetLocationCaptureable()) {

            if (sourceRow + 1 == targetRow && sourceColumn == targetColumn) { //up
                isValid = true;
            }

            if (sourceColumn + 1 == targetColumn && sourceRow == targetRow) { //right
                isValid = true;
            }

            if (targetColumn + 1 == sourceColumn && sourceRow == targetRow) { //left
                isValid = true;
            }

            if (targetRow + 1 == sourceRow && sourceColumn == targetColumn) { //down
                isValid = true;
            }

            if (sourceColumn + 1 == targetColumn && sourceRow + 1 == targetRow) { //top right
                isValid = true;
            }

            if (sourceColumn - 1 == targetColumn && sourceRow + 1 == targetRow) { //top left
                isValid = true;
            }

            if (sourceColumn + 1 == targetColumn && sourceRow - 1 == targetRow) { //bottom right
                isValid = true;
            }
            if (targetColumn + 1 == sourceColumn && sourceRow - 1 == targetRow) { //bottom left
                isValid = true;
            }

        }
        return isValid;

    }

}
