package objects;

public enum BoardClickedStates {
    HERO, STRUCTURE, FIELD, NULL;

    public static BoardClickedStates boardClickedState = HERO;
    public static void nextState(){
        switch (BoardClickedStates.boardClickedState) {
            case HERO:
                BoardClickedStates.boardClickedState = STRUCTURE;
                break;
            case STRUCTURE:
                BoardClickedStates.boardClickedState = FIELD;
                break;
            case FIELD:
                BoardClickedStates.boardClickedState = NULL;
            case NULL:
                BoardClickedStates.boardClickedState = HERO;
        }
    }
    public static void resetState(){
        BoardClickedStates.boardClickedState = NULL;
    }
}
