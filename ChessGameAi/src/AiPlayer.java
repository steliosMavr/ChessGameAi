
import java.util.ArrayList;
import java.util.List;

public class AiPlayer implements IPlayerHandler {

    private ChessEngine chessGame;
    private MyMoveValidator validator;

    public int maxDepth=2;

    public AiPlayer(ChessEngine chessGame) {
        this.chessGame = chessGame;
        this.validator = chessGame.getMoveValidator();
    }

    @Override
    public Move getMove() {
        return getBestMoves();

    }

    private Move getBestMoves() {

        System.out.println("getting best move");
        ConsoleGui.printCurrentGameState(chessGame);
        System.out.println("thinking...");
        List<Move> validMoves = generateMoves(false);
        int bestResult = Integer.MIN_VALUE;

        Move finalMove = null;

        for (Move validMove : validMoves) {
        //   System.out.println("SRows:" + validMove.getSourceRow() + "SColumn" + validMove.getSourceColumn());
        //    System.out.println("TRows:" + validMove.getTargetRow() + "TColumn" + validMove.getTargetColumn());

            executeMove(validMove);

            int result = -1 * negaMax(maxDepth);
            
            undoMove(validMove);
            if (result > bestResult) {
                bestResult = result;
                finalMove = validMove;
            }
        }
        System.out.println("BestMove");

        System.out.println("SRows:" + finalMove.getSourceRow() + "SColumn" + finalMove.getSourceColumn());
        System.out.println("TRows:" + finalMove.getTargetRow() + "TColumn" + finalMove.getTargetColumn());
        return finalMove;

    }

    private void undoMove(Move move) {

        this.chessGame.undoMove(move);

    }

    private void executeMove(Move move) {
        //System.out.println("executing move");
        this.chessGame.movePiece(move);
        this.chessGame.changeGameState();
    }

    private List<Move> generateMoves(boolean debug) {

        List<Piece> pieces = this.chessGame.getPieces();
        List<Move> validMoves = new ArrayList<Move>();
        Move testMove = new Move(0, 0, 0, 0);

        int pieceColor = (this.chessGame.getGameState() == 0
                ? 0
                : 1);

       
        for (Piece piece : pieces) {

          
            if (pieceColor == piece.getColor()) {

               
                testMove.sourceRow = piece.getRow();
                testMove.sourceColumn = piece.getColoumn();

               
                for (int targetRow = 0; targetRow <= 7; targetRow++) {
                    for (int targetColumn = 0; targetColumn <= 5; targetColumn++) {

                       
                        testMove.targetRow = targetRow;
                        testMove.targetColumn = targetColumn;

                        if (debug) {
                            System.out.println("testing move: " + testMove);
                        }

                       
                        if (this.validator.isMoveValid(testMove.sourceRow, testMove.sourceColumn, testMove.targetRow, testMove.targetColumn)) {
                            // valid move
                            validMoves.add(testMove.clone());
                        } else {
                            
                        }
                    }
                }

            }
        }

        return validMoves;
    }

    private int negaMax(int depth) {
        int max = Integer.MIN_VALUE;

        if (depth == 0) {
            return evaluateState();
        }

        List<Move> validMoves = generateMoves(false);

        for (Move validMove : validMoves) {
            executeMove(validMove);
            int score = -1 * negaMax(depth - 1);
            undoMove(validMove);
            if (score > max) {
                max = score;
            }

        }
        return max;
    }

    private int evaluateState() {

        
        int scoreWhite = 0;
        int scoreBlack = 0;
        for (Piece piece : this.chessGame.getPieces()) {
            if (piece.getColor() == 1) {
                scoreBlack
                        += getScoreForPieceType(piece.getType());
                scoreBlack
                        += getScoreForPiecePosition(piece.getRow(), piece.getColoumn());
            } else if (piece.getColor() == 0) {
                scoreWhite
                        += getScoreForPieceType(piece.getType());
                scoreWhite
                        += getScoreForPiecePosition(piece.getRow(), piece.getColoumn());
            } else {
                throw new IllegalStateException(
                        "unknown piece color found: " + piece.getColor());
            }
        }
       
       
        int gameState = this.chessGame.getGameState();

        if (gameState == chessGame.GAME_STATE_BLACK) {
            return scoreBlack - scoreWhite;

        } else if (gameState == chessGame.GAME_STATE_WHITE) {
            return scoreWhite - scoreBlack;

        } else {
            throw new IllegalStateException("unknown game state: " + gameState);
        }
    }

    private int getScoreForPiecePosition(int row, int column) {
        byte[][] positionWeight
                = {{1, 1, 1, 1, 1, 1}, {2, 2, 2, 2, 2, 2}, {2, 3, 3, 3, 3, 2}, {2, 3, 4, 4, 3, 2}, {2, 3, 4, 4, 3, 2}, {2, 3, 3, 3, 3, 2}, {2, 2, 2, 2, 2, 2}, {1, 1, 1, 1, 1, 1}
                };
        return positionWeight[row][column];
    }

    private int getScoreForPieceType(int type) {
        switch (type) {
            case 2:
                return 30;
            case 4:
                return 99999;
            case 5:
                return 10;
            case 3:
                return 100;
            case 1:
                return 50;
            default:
                throw new IllegalArgumentException("unknown piece type: " + type);
        }
    }

    @Override
    public void moveSuccessfullyExecuted() {

    }

}
