package main;

import scenes.Menu;
import scenes.Play;

public class Game implements Runnable{
    private static double timePerFrame;
    private static long lastTimeFrameNano;
    private static long lastTimeFrameMillis;
    private static long fps;
    private static float FPS = (float)120.0;
    private static float UPS = (float)60.0;

    private static double timePerUpdate;
    private static long lastTimeUpdateNano;
    private static long lastTimeUpdateMillis;
    private static long ups;
    private GameWindow gameWindow;

    private static Thread gameThread;

    private static Menu menu;
    private static Play play;

    private int fieldWidth = 120;
    private int fieldHeight = 120;
    private int windowHeight = 640;
    private int windowWidth = 640;
    private int boardWidthInFields = 20;
    private int boardHeightInFields = 20;
    private int velocityMovementFramesDuration = 200;
    private float maxBoardWindowSizeRatio = (float)0.7;




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
        switch (GameStates.gameState) {
            case MENU:
                
                break;
            case PLAYING:
                getPlay().update();
                break;
            default:
                break;
        }
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


        timePerFrame = 1000000000.0 / FPS;
        lastTimeFrameNano = 0;

        timePerUpdate = 1000000000.0 / UPS;
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
        menu = new Menu(this);
        play = new Play(this);
    }

    public static Menu getMenu(){
        return menu;
    }
    public static Play getPlay(){
        return play;
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
    public int getBoardWidthInFields(){
        return this.boardWidthInFields;
    }
    public int getBoardHeightInFields(){
        return boardHeightInFields;
    }
    public void setBoardWidthInFields(int boardWidthInFields){
        this.boardWidthInFields = boardWidthInFields;
    }
    public void setBoardHeightInFields(int boardHeightInFields){
        this.boardHeightInFields = boardHeightInFields;
    }
    public int getvelocityMovementFramesDuration(){
        return this.velocityMovementFramesDuration;
    }
    public void setvelocityMovementFramesDuration(int velocityMovementFramesDuration){
        this.velocityMovementFramesDuration = velocityMovementFramesDuration;
    }
    public float getMaxBoardWindowSizeRatio(){
        return this.maxBoardWindowSizeRatio;
    }

}

