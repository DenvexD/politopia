package structures;

import java.awt.Toolkit;

import objects.Field;

public class Forest extends Structure{
    public Forest(Field field){
        super(field);
        this.img = Toolkit.getDefaultToolkit().getImage("politopia/src/main/res/Imperius forest.png");;
    }

    @Override
    public void mouseClicked() {
        setDisplaysActions();
    }
    @Override
    public void unclick(){
        super.display.setVisable(false);
    }
    @Override
    public void isClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isClicked'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void destroy(){
        this.field.setStructure(null);
    }
    public void levelup(){
        this.levelOfset -= 10;
    }
}