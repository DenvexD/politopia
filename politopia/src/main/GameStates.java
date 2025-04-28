package main;

public enum GameStates {
    MENU, SETTINGS, PLAYING, UU;
    
    public static GameStates GameState = MENU;
    public static GameStates listenerReadyGameState = UU;
}
