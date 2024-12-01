package main;
public class App implements Runnable{
    private static double timePerFrame;
    private static long lastTimeFrameNano;
    private static long lastTimeFrameMillis;
    private static long fps;

    private static double timePerUpdate;
    private static long lastTimeUpdateNano;
    private static long lastTimeUpdateMillis;
    private static long ups;

    private static Thread gameThread;

    public static void main(String[] args) throws Exception {
        App app = new App();
        gameThread = new Thread(app){};
        gameThread.start();

        

    }
    private static void gameLoop (GameWindow gameWindow){
        timePerFrame = 1000000000.0 / 120.0;
        lastTimeFrameNano = 0;

        timePerUpdate = 1000000000.0 / 60.0;
        lastTimeUpdateNano = 0;

        while (true) {
            if(isReadyForNextFrame()){
                lastTimeFrameNano = System.nanoTime();
                updateWindow(gameWindow);
            }
            if (isReadyForNextUpdate()) {
                lastTimeUpdateNano = System.nanoTime();
                updateGame();
            }
            printUPS();
            printFPS();
        }
    }

    private static void updateGame(){
        ups++;
        //updating the game
    }
    private static void updateWindow(GameWindow gameWindow){
        fps++;
        gameWindow.repaint();
    }

    private static void printUPS (){
        if(oneSecondSinceLastUPS()){
            System.out.println("UPS: " + ups);
            ups = 0;
            lastTimeUpdateMillis = System.currentTimeMillis();
        }
    }

    private static void printFPS(){
        if(oneSecondSinceLastFPS()){
            System.out.println(fps);
            fps = 0;
            lastTimeFrameMillis = System.currentTimeMillis();
        }
    }



    private static boolean isReadyForNextFrame(){
        return (System.nanoTime() - lastTimeFrameNano >= timePerFrame);
    }
    private static boolean isReadyForNextUpdate(){
        return(System.nanoTime() - lastTimeUpdateNano >= timePerUpdate);
    }
    private static boolean oneSecondSinceLastUPS(){
        return(System.currentTimeMillis() - lastTimeUpdateMillis >= 1000);
    }
    private static boolean oneSecondSinceLastFPS(){
        return(System.currentTimeMillis() - lastTimeFrameMillis >= 1000);
    }
 
    
    public void run() {
        GameWindow gameWindow = new GameWindow();
        gameLoop(gameWindow);
    }


}
