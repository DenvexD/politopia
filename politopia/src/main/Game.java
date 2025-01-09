package main;

import scenes.Menu;
import scenes.Play;

public class Game implements Runnable{
    private static double timePerFrame;
    private static long lastTimeFrameNano;
    private static long lastTimeFrameMillis;
    private static long fps;

    private static double timePerUpdate;
    private static long lastTimeUpdateNano;
    private static long lastTimeUpdateMillis;
    private static long ups;
    private GameWindow gameWindow;

    private static Thread gameThread;

    private Menu menu;
    private Play play;

    private int fieldWidth = 60;
    private int fieldHeight = 60;
    private int windowHeight = 640;
    private int windowWidth = 640;




    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.initScenes();
        gameThread = new Thread(game){};
        gameThread.start();

        

    }


    private static void renderTheGameInThread(GameWindow gameWindow){

        if(isReadyForNextFrame()){
            lastTimeFrameNano = System.nanoTime();
            updateWindow(gameWindow);
        }
        printFPS();
    }
    private static void updateTheGameInThread(){

        if (isReadyForNextUpdate()) {
            lastTimeUpdateNano = System.nanoTime();
            updateGame();
        }
        printUPS();
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
        timePerFrame = 1000000000.0 / 120.0;
        lastTimeFrameNano = 0;

        timePerUpdate = 1000000000.0 / 60.0;
        lastTimeUpdateNano = 0;

        GameWindow gameWindow = new GameWindow(this);
        this.gameWindow = gameWindow;
        gameWindow.initInputs();
        while (true) {
            renderTheGameInThread(gameWindow);
            updateTheGameInThread();
        }
    }

    private void initScenes(){
        this.menu = new Menu(this);
        this.play = new Play(this);
    }

    public Menu getMenu(){
        return this.menu;
    }
    public Play getPlay(){
        return this.play;
    }

    public int getFieldHeight(){
        return this.fieldHeight;
    }
    public int getFieldWidth(){
        return this.fieldWidth;
    }
    public void setFieldHeight(int height){
        this.fieldHeight = height;
    }
    public void setFieldWidth(int width){
        this.fieldWidth = width;
    }

    public GameWindow getGameWindow(){
        return this.gameWindow;
    }
    public int getWindowWidth(){
        return this.windowWidth;
    }
    public int getWindowHeight(){
        return this.windowHeight;
    }

}

