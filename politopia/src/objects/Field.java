package objects;

import ui.Button;

import java.awt.Image;
import java.awt.Toolkit;

public class Field extends Button {
    private FieldTypes fieldType;
    public Field left;
    public Field right;
    public Field top;
    public Field bottom;
    public int number;

    public Field(int width, int height, FieldTypes fieldType){
        super(null, width, height);
        this.fieldType = fieldType;
        this.setImageBasedOnType();
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
    }
    public void setType(FieldTypes fieldType){
        this.fieldType = fieldType;
    }
    private void setImageBasedOnType(){
        Image img;
        switch (this.fieldType) {
            case DEEP_WATER:
                img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/deep.png");   
            default:
                img = null;
        }

        this.setImage(img);
    }

    public int getNumberOfNeighbours(){
        int i = 0;
        if (this.left != null) {
            i++;
        }
        if (this.right != null) {
            i++;
        }
        if (this.bottom != null) {
            i++;
        }
        if (this.top != null) {
            i++;
        }
        return i;
    }
    
    
}
