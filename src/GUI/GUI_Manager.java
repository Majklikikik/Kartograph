package GUI;

import AI.Time;
import Game.Card.Card;
import Game.Card.Possibility;
import Game.Field.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static Utility.Utilities.slp;

public class GUI_Manager {
    private GameStats s;
    private JFrame window;
    FieldWindow fw;
    CardWindow p1,p2;
    private JLabel labelPoints;
    private int cardWindowCount=0;
private int initComponentCount;
    private JLabel pt, tim, xp, coins;

    public void actualizeGameStats(){
        pt.setText("Points: "+s.Points);
        tim.setText(s.t.toString());
        xp.setText("Expeced: "+s.expectedPoints);
        coins.setText("Coins: "+s.coin);
        window.revalidate();
    }

    public void setCard(Card c){
        removeAllCards();
        for (Possibility p:c.getPossibilities()){
            addCardWindow(p,c.getName(),c.onRuin);
        }
    }

    public FieldWindow makeGUI(GameStats s, Field f) {
        this.s=s;
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(00, 00, 1400, 700);
        fw=new FieldWindow(f);
        fw.setBounds(0,0,600,600);
        window.add(fw);

        //if (s.wait) {
            JButton bb = new JButton("Place");
            bb.setBounds(10, 600, 95, 30);
            bb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    s.notRunning=false;
                }
            });
            window.add(bb);
        //}

        JButton b = new JButton("Remove Cards");
        b.setBounds(110, 600, 95, 30);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAllCards();
            }
        });
        window.add(b);
        pt=new JLabel();
        pt.setText("Points: 0");
        JPanel jp = new JPanel();
        jp.add(pt);
        pt.setFont(new Font("TimesRoman",Font.BOLD,20));
        jp.setBounds(220,600,150,30);
        window.add(jp);

        tim=new JLabel();
        tim.setText(s.t.toString());
        JPanel jp2 = new JPanel();
        jp2.add(tim);
        tim.setFont(new Font("TimesRoman",Font.BOLD,20));
        jp2.setBounds(380,600,150,30);
        window.add(jp2);

        xp=new JLabel();
        xp.setText("Expected: 0");
        JPanel jp3 = new JPanel();
        jp3.add(xp);
        xp.setFont(new Font("TimesRoman",Font.BOLD,20));
        jp3.setBounds(540,600,150,30);
        window.add(jp3);

        coins=new JLabel();
        coins.setText("Coins: 0");
        JPanel jp4 = new JPanel();
        jp4.add(coins);
        coins.setFont(new Font("TimesRoman",Font.BOLD,20));
        jp4.setBounds(700,600,150,30);
        window.add(jp4);

        /*labelPoints=new JLabel();
        //labelPoints.setBounds(700,600,50,10);
        //labelPoints.setText("0 Points");
        //labelPoints.setFont(new Font("TimesRoman",Font.BOLD,20));
        //JPanel newPan=new JPanel();
        //newPan.setBounds(200,800,100,30);
        //newPan.add(labelPoints);
        //window.getContentPane().add(newPan);
        window.getContentPane().add(labelPoints);*/

        /*JLabel pt=new JLabel();
        JPanel pl=new JPanel();
        pt.setText("0 Points");
        pt.setFont(new Font("TimesRoman", Font.BOLD,20));
        pl.add(pt);
        pl.setBounds(590,30,100,50);
        window.getContentPane().add(pl);*/



        window.add(new JLabel());
        window.setVisible(true);
        //System.out.println(window.getContentPane().getComponentCount());
        initComponentCount=window.getComponentCount()+6;
        return fw;
    }

    public void removeAllCards(){
        cardWindowCount=0;
        while (window.getContentPane().getComponentCount()>=initComponentCount+1){
            window.getContentPane().remove(initComponentCount);
        }
        window.revalidate();
        window.repaint();
    }

    public void addCardWindow(Possibility p, String name,boolean ruin){
        CardWindow w;
        int offset=210;
        int count=3;
        if (cardWindowCount<count) w=new CardWindow(600,110+offset*cardWindowCount);
        else w=new CardWindow(900,110+offset*(cardWindowCount)-offset*count);
        cardWindowCount++;
        w.setPossibility(p, name,ruin);
        window.getContentPane().add(w);
        w.revalidate();
        window.revalidate();
    }

    public void repaint() {
        window.repaint();
    }

    public void close() {
        window.setVisible(false);
        window.dispose();
    }
}
