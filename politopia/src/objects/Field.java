package objects;

import ui.Button;

import java.awt.Image;
import java.awt.Toolkit;

public class Field extends Button {
    private FieldTypes fieldType;

    public Field(int width, int height, FieldTypes fieldType){
        super(null, width, height);
        this.fieldType = fieldType;
        this.setImageBasedOnType();


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

}
