
public class AiVsAi {
    
    public static void main(String[] args) {

        ChessEngine myEngine = new ChessEngine();
        
       
        AiPlayer ai = new AiPlayer(myEngine);
        AiPlayer ai2 = new AiPlayer(myEngine);
       
      
       

        myEngine.registerPlayers(0, ai); //white
        myEngine.registerPlayers(1, ai2); //black

        myEngine.startGame();

    }
    
}
