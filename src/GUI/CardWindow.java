package GUI;

import Game.Card.Possibility;
import Game.Field.Field;
import Utility.Utilities;

import javax.swing.*;
import java.awt.*;

public class CardWindow extends JComponent {
    private Possibility p;
    private boolean ruin;
    Boolean b;
int x_offset;
int y_offset;
String name;

public CardWindow(int xo, int yo){
    x_offset=xo;
    y_offset=yo;
}
    public void setPossibility(Possibility p, String name, boolean ruin) {
        this.p = p;
        this.name=name;
        this.ruin=ruin;
        repaint();
    }

    public void paint(Graphics g) {
if (p==null) {
    return;
}

        //g.setColor(Color.white);
        //g.fillRect( x_offset+60,y_offset,50*p.getWidth(),50*p.getHeight());
        //g.fillRect(0,0,1000,1000);

switch (p.getType()){
            case EMPTY: g.setColor(Color.white); break;
            case FIELD: g.setColor(Color.yellow); break;
            case WATER: g.setColor(Color.blue); break;
            case CITY: g.setColor(Color.red); break;
            case FOREST: g.setColor(Color.green); break;
            case MOUNTAIN: g.setColor(Color.gray); break;
            case OBSTACLE: g.setColor(Color.darkGray); break;
            case ASSHOLE: g.setColor(Color.magenta); break;
        }
        g.fillRect(x_offset,y_offset,50,50);

        for (int y=0;y<p.getHeight();y++){
            for (int x=0;x<p.getWidth();x++){
                if (p.getToPlace()[y][x]){
                    g.fillRect(x_offset+60+50*x,y_offset+50*y,50,50);
                }
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(x_offset,y_offset,50,50);
        g.drawRect( x_offset+60,y_offset,50*p.getWidth(),50*p.getHeight());
        //g.setColor(Color.RED);
        Utilities.paintLines(g,p.getToPlace(),x_offset+60,y_offset,50);
        if (p.isGetCoin()){
            g.setColor(Color.YELLOW);
            g.fillOval(x_offset+10,y_offset+10+50,30,30);
            g.setColor(Color.BLACK);
            g.drawOval(x_offset+10,y_offset+10+50,30,30);
        }
        if (ruin){
            g.fillRect(x_offset+20,y_offset+20,10,10);
        }
    }
}
