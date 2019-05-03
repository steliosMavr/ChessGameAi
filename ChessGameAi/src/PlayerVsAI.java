
public class PlayerVsAI {

    public static void main(String[] args) {

        ChessEngine myEngine = new ChessEngine();
        
        ConsoleGui myGui = new ConsoleGui(myEngine);
        AiPlayer ai = new AiPlayer(myEngine);
       
      
       

        myEngine.registerPlayers(0, ai); //white
        myEngine.registerPlayers(1, myGui); //black

        myEngine.startGame();

    }

}
