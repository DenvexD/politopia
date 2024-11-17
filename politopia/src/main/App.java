package main;
public class App {
    private static double timePerFrame;
    private static long lastTimeFrame;
    public static void main(String[] args) throws Exception {



        GameWindow gameWindow = new GameWindow();
        gameLoop(gameWindow);

    }
    private static void gameLoop (GameWindow gameWindow){
        timePerFrame = 1000000000.0 / 60.0;
        lastTimeFrame = 0;
        while (true) {
            if(isReadyForNextImage()){
                lastTimeFrame = System.nanoTime();
                gameWindow.repaint();
            }
        }
    }
    private static boolean isReadyForNextImage(){
        return (System.nanoTime() - lastTimeFrame >= timePerFrame);
    }
}
