
import gr.tei.pieces.Bishop;
import gr.tei.pieces.King;
import gr.tei.pieces.Pawn;
import gr.tei.pieces.Queen;
import gr.tei.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

public class ChessEngine {

    private int gameState = GAME_STATE_WHITE;

    public static final int GAME_STATE_WHITE = 0;
    public static final int GAME_STATE_BLACK = 1;

    private static int WHITE = 0;
    private static int BLACK = 1;

    public static int WHO_WIN = -1;

    private MyMoveValidator moveValidator;
    private static int ROOK = 1;
    private static int BISHOP = 2;
    private static int KING = 4;
    private static int QUEEN = 3;
    private static int PAWN = 5;

    private Piece piece;

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    private List<Piece> pieces = new ArrayList<Piece>();
    private List<Piece> capturedPieces = new ArrayList<Piece>();

    private Move move;
    private IPlayerHandler whitePlayer;
    private IPlayerHandler blackPlayer;
    private IPlayerHandler activePlayer;

    public void registerPlayers(int color, IPlayerHandler playerHandler) {
        if (color == WHITE) {
            whitePlayer = playerHandler;
        }

        if (color == BLACK) {
            blackPlayer = playerHandler;
        }

    }

    public void startGame() {

        if (whitePlayer == null || blackPlayer == null) {
            System.out.println("You have to register the players");
        }
        activePlayer = whitePlayer;

        while (!isGameEndConditionReached()) {
            waitForMove();
            swapPlayers();
        }

        if (WHO_WIN == 0) {
            System.out.println("White Win the game");
        }

        if (WHO_WIN == 1) {
            System.out.println("Black Win the game");
        }

    }

    private void swapPlayers() {
        if (activePlayer == whitePlayer) {
            activePlayer = blackPlayer;

        } else {
            activePlayer = whitePlayer;
        }

        //  changeGameState();
    }

    private void waitForMove() {
        move = null;
        boolean isMoveExcecuted = false;

        move = activePlayer.getMove();

        while (true) {
            if (move != null && this.moveValidator.isMoveValid(move.sourceRow, move.sourceColumn, move.targetRow, move.targetColumn)) {

                break;
            } else {
                System.out.println("Wrong Move Give Again");
                move = activePlayer.getMove();

            }
        }

        isMoveExcecuted = movePiece(move);
        if (isMoveExcecuted == true) {

            whitePlayer.moveSuccessfullyExecuted(); //notify white Player
            changeGameState();
            blackPlayer.moveSuccessfullyExecuted(); //notify black player
            // changeGameState();

        } else {
            System.out.println("Something going wrong");
        }

    }

    private boolean isGameEndConditionReached() {
        for (Piece piece : this.capturedPieces) {
            if (piece.getType() == 4) {
                if (piece.getColor() == 0) {
                    WHO_WIN = 1;

                } else {
                    WHO_WIN = 0;
                }
                return true;
            } else {
                // continue iterating
            }
        }

        return false;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public ChessEngine() {
        moveValidator = new MyMoveValidator(this);
        createAndAddPiece(WHITE, ROOK, 0, 0);
        createAndAddPiece(WHITE, BISHOP, 0, 1);
        createAndAddPiece(WHITE, QUEEN, 0, 2);
        createAndAddPiece(WHITE, KING, 0, 3);
        createAndAddPiece(WHITE, BISHOP, 0, 4);
        createAndAddPiece(WHITE, ROOK, 0, 5);

        int currentColumn = 0;
        for (int i = 0; i < 6; i++) {
            createAndAddPiece(WHITE, PAWN, 1, currentColumn);
            currentColumn++;
        }

        createAndAddPiece(BLACK, ROOK, 7, 0);
        createAndAddPiece(BLACK, BISHOP, 7, 1);
        createAndAddPiece(BLACK, QUEEN, 7, 2);
        createAndAddPiece(BLACK, KING, 7, 3);
        createAndAddPiece(BLACK, BISHOP, 7, 4);
        createAndAddPiece(BLACK, ROOK, 7, 5);

        currentColumn = 0;
        for (int i = 0; i < 6; i++) {
            createAndAddPiece(BLACK, PAWN, 6, currentColumn);
            currentColumn++;
        }

    }

    private void createAndAddPiece(int color, int type, int row, int column) {

        if (type == ROOK) {
            Rook rook = new Rook(color, row, column);
            piece = new Piece(rook.getColor(), ROOK, rook.getRow(), rook.getColumn());
        }

        if (type == BISHOP) {
            Bishop bishop = new Bishop(color, row, column);
            piece = new Piece(bishop.getColor(), BISHOP, bishop.getRow(), bishop.getColumn());
        }

        if (type == KING) {
            King king = new King(color, row, column);
            piece = new Piece(king.getColor(), KING, king.getRow(), king.getColumn());
        }

        if (type == QUEEN) {
            Queen queen = new Queen(color, row, column);
            piece = new Piece(queen.getColor(), QUEEN, queen.getRow(), queen.getColumn());
        }

        if (type == PAWN) {
            Pawn pawn = new Pawn(color, row, column);
            piece = new Piece(pawn.getColor(), PAWN, pawn.getRow(), pawn.getColumn());
        }

        pieces.add(piece);

    }

    public Piece getNonCapturedPieceAtLocation(int row, int column) {
        for (Piece piece : this.pieces) {
            if (piece.getRow() == row
                    && piece.getColoumn() == column
                    && piece.isCaptured() == false) {
                return piece;
            }
        }
        return null;
    }

    public boolean isAnotherPieceAtLocation(int color, int row, int column) {

        for (Piece piece : this.pieces) {
            if (piece.getRow() == row
                    && piece.getColoumn() == column
                    && piece.getColor() == color) {
                return true;
            }

        }
        return false;
    }

    public boolean isAnotherEnemyPieceAtLocation(int color, int row, int column) {
        int opponentColor;
        if (color == 0) {
            opponentColor = 1;
        } else {
            opponentColor = 0;
        }
        for (Piece piece : this.pieces) {
            if (piece.getRow() == row
                    && piece.getColoumn() == column
                    && piece.getColor() == opponentColor) {
                return true;
            }

        }
        return false;
    }

    public void changeGameState() {
        switch (this.gameState) {
            case GAME_STATE_BLACK:
                this.gameState = GAME_STATE_WHITE;
                break;
            case GAME_STATE_WHITE:
                this.gameState = GAME_STATE_BLACK;

                break;
            default:
                throw new IllegalStateException("unknown game state:" + this.gameState);
        }
    }

    public void undoMove(Move move) {
        Piece piece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);

        piece.setRow(move.sourceRow);
        piece.setColoumn(move.sourceColumn);

        if (move.capturedPiece != null) {
            move.capturedPiece.setRow(move.targetRow);
            move.capturedPiece.setColoumn(move.targetColumn);
            move.capturedPiece.isCaptured(false);
            this.capturedPieces.remove(move.capturedPiece);
            this.pieces.add(move.capturedPiece);
        }

        if (piece.getColor() == Piece.COLOR_BLACK) {
            this.gameState = ChessEngine.GAME_STATE_BLACK;
        } else {
            this.gameState = ChessEngine.GAME_STATE_WHITE;
        }
    }

    public boolean movePiece(Move move) {

        move.capturedPiece = this.getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);

        Piece piece = getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

        int opponentColor = (piece.getColor() == Piece.COLOR_BLACK ? Piece.COLOR_WHITE
                : Piece.COLOR_BLACK);
        if (isAnotherPieceAtLocation(opponentColor, move.targetRow, move.targetColumn)) {

            Piece opponentPiece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
            this.pieces.remove(opponentPiece);
            this.capturedPieces.add(opponentPiece);
            opponentPiece.isCaptured(true);
        }

        //new position
        piece.setRow(move.targetRow);
        piece.setColoumn(move.targetColumn);

        return true;
    }

    public MyMoveValidator getMoveValidator() {
        return moveValidator;
    }

}
