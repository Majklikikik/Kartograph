package GUI;

import Game.Field.Field;
import Utility.Utilities;

import javax.swing.*;
import java.awt.*;

public class FieldWindow extends JComponent {
    private Field f;
    Boolean b;
    public FieldWindow(Field f){
        this.f=f;
    }

    public void setF(Field f) {
        this.f = f;
        repaint();
    }

    public void paint(Graphics g) {
        g.fillRect(10,10,580,580);
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                switch (f.getType(x, y)){
                    case EMPTY: g.setColor(Color.white); break;
                    case FIELD: g.setColor(Color.yellow); break;
                    case WATER: g.setColor(Color.blue); break;
                    case CITY: g.setColor(Color.red); break;
                    case FOREST: g.setColor(Color.green); break;
                    case MOUNTAIN: g.setColor(Color.gray); break;
                    case OBSTACLE: g.setColor(Color.darkGray); break;
                    case ASSHOLE: g.setColor(Color.magenta); break;
                }
                g.fillRect(20+50*x,20+50*y,50,50);
                g.setColor(Color.black);
                //g.drawRect(20+50*x,20+50*y,50,50);
                if (f.isRuin(x, y)){
                    g.fillRect(40+50*x,40+50*y,10,10);
                }
            }
        }
        Utilities.paintLines(g,f.getMap(),20,20,50);

    }

    public void setWaiter(Boolean b) {
        this.b=b;
    }

    public Boolean waitt(){
        this.b= new Boolean(false);
        return b;
    }
}