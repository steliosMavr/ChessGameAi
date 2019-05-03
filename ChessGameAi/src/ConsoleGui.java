
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleGui implements IPlayerHandler {

    private ChessEngine MychessGame;

    public ConsoleGui(ChessEngine chessGame) {

        MychessGame = chessGame;
        printCurrentGameState(MychessGame);

    }

    public static void printCurrentGameState(ChessEngine game) {
        
        System.out.println("  a  b  c  d  e  f ");
        for (int row = 7; row >= 0; row--) {

            System.out.println(" +--+--+--+--+--+--+");
            String strRow = (row + 1) + "|";
            for (int column = 0; column <= 5; column++) {
                Piece piece = game.getNonCapturedPieceAtLocation(row, column);
                String pieceStr = getNameOfPiece(piece);
                strRow += pieceStr + "|";
            }
            System.out.println(strRow + (row + 1));
        }
        System.out.println(" +--+--+--+--+--+--+");
        System.out.println("  a  b  c  d  e  f ");
        
        if(game.WHO_WIN==0){
            System.out.println("White Win the game");
        }
        
        if(game.WHO_WIN==1){
            System.out.println("Black Win the game");
        }

        String turnColor= (game.getGameState() == game.GAME_STATE_BLACK ? "black" : "white");
        System.out.println("turn: " + turnColor);
    }

    private Move handleMove(String input) {

       
        String strSourceColumn = input.substring(0, 1);
        String strSourceRow = input.substring(1, 2);
        String strTargetColumn = input.substring(3, 4);
        String strTargetRow = input.substring(4, 5);

        int sourceColumn = 0;
        int sourceRow = 0;
        int targetColumn = 0;
        int targetRow = 0;

        sourceColumn = convertColumnStrToColumnInt(strSourceColumn);
        sourceRow = convertRowStrToRowInt(strSourceRow);
        targetColumn = convertColumnStrToColumnInt(strTargetColumn);
        targetRow = convertRowStrToRowInt(strTargetRow);

      
        return new Move(sourceRow, sourceColumn, targetRow, targetColumn);

    }

    private int convertRowStrToRowInt(String input) {
        if (input.equalsIgnoreCase("1")) {
            System.out.println("1");
            return 0;
        }

        if (input.equalsIgnoreCase("2")) {
            return 1;
        }

        if (input.equalsIgnoreCase("3")) {
            System.out.println("3");
            return 2;
        }
        if (input.equalsIgnoreCase("4")) {
            return 3;
        }
        if (input.equalsIgnoreCase("5")) {
            return 4;
        }
        if (input.equalsIgnoreCase("6")) {
            return 5;
        }
        if (input.equalsIgnoreCase("7")) {
            return 6;
        }

        if (input.equalsIgnoreCase("8")) {
            return 7;
        }

        return 0;
    }

    private int convertColumnStrToColumnInt(String input) {
        if (input.equalsIgnoreCase("a")) {
            System.out.println("a");
            return 0;
        }

        if (input.equalsIgnoreCase("b")) {

            return 1;
        }

        if (input.equalsIgnoreCase("c")) {
            System.out.println("c");
            return 2;
        }
        if (input.equalsIgnoreCase("d")) {

            return 3;
        }
        if (input.equalsIgnoreCase("e")) {
            return 4;
        }

        if (input.equalsIgnoreCase("f")) {

            return 5;

        }

        return 0;
    }

    private static String getNameOfPiece(Piece piece) {
        String pawnType = "";
        String strColor = "";
        if (piece == null) {
            return "  ";
        }

        if (piece.getColor() == 0) {
            strColor = "W";
        } else {
            if (piece.getColor() == 1) {
                strColor = "B";
            }
        }

        if (piece.getType() == 1) {
            pawnType = "R";
        }
        if (piece.getType() == 2) {
            pawnType = "B";
        }

        if (piece.getType() == 4) {
            pawnType = "K";
        }

        if (piece.getType() == 3) {
            pawnType = "Q";
        }

        if (piece.getType() == 5) {
            pawnType = "P";
        }
        return strColor + pawnType;

    }

    @Override
    public Move getMove() {
        String input = "";
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        Move move = null;

        System.out.println("your move (e.g. e2-e4): ");
        try {

            input = inputReader.readLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Close");
            } else {
                move = handleMove(input);
             

            }
        } catch (Exception e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        }
        return move;

    }

    @Override
    public void moveSuccessfullyExecuted() {
        printCurrentGameState(MychessGame);
        
        
    }

}
