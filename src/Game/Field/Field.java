package Game.Field;

import AI.Rater;
import AI.SeasonRaters.Season;
import AI.Time;
import GUI.GUI_Manager;
import GUI.GameStats;
import Game.Card.Card;
import Game.Card.CardGenerator;
import Game.Card.Possibility;
import Game.Field.Tile.Area;
import GUI.FieldWindow;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;
import Utility.TileToBoolean;
import Utility.TypeToBool;
import Utility.Utilities;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;


public class Field {
    public GUI_Manager GUI;
    public int coinsFromCards;
    public Point[] Mountains;
    public Time t;
    public float pointsExpected;
    Tile[][] map;
    int w,h;
    boolean hardMode;
    FieldWindow fw;
    LinkedList<Card> availableCards;
    HashMap<Season, HashMap<TileType,Float>>[][] probs= new HashMap[11][11];

    public void recalculateProbs(int x_from, int y_from, int x_to, int y_to){
        HashMap<TileType, Float> probsAvailableCards;
        HashMap<TileType, Float> probsAllCards;
        for (Card c: availableCards){

        }
    }

    public int countOfEmptyNeighboursOfTileWithType(TileType t){
        int c=0;
        for (int i=0;i<11;i++){
            for (int j=0;j<11;j++){
                c+=countOfNeighboursWith(i,j,(Tile tmp)-> tmp.getType()==t);
            }
        }
        return c;
    }

    public Tile getTile(int x, int y){
        return map[y][x];
    }

    public LinkedList<TileType> surroundingAreaTypes(Area a){
        LinkedList<TileType> ret= new LinkedList<>();
        for (Tile t:a.tiles){
            for (Tile s:getNeighbours(t.x,t.y)){
                if (!ret.contains(s.getType())){
                    ret.add(s.getType());
                }
            }
        }
        return ret;
    }

    public LinkedList<Tile> Border(){
        LinkedList<Tile> ret=new LinkedList<>();
        for (int i=0;i<10;i++){
            ret.add(map[i][0]);
            ret.add(map[10-i][10]);
            ret.add(map[0][10-i]);
            ret.add(map[10][i]);
        }
        return ret;
    }

    public int countOfTilesWithPropertyAndNeighboursWithProperty(TileToBoolean center, TileToBoolean neighbour){
        int c=0;
        for (int i=0;i<11;i++){
            for (int j=0;j<11;j++){
                if (center.operation(map[i][j])&&hasNeighbourWho(j,i,neighbour))
                    c++;
            }
        }
        return c;
    }

    public int countOfTilesWithNeighbourWithProperty(TileToBoolean b){
        int c=0;
        for (int i=0;i<11;i++){
            for (int j=0;j<11;j++){
                if (hasNeighbourWho(i,j,b)){
                    c++;
                }
            }
        }
        return c;
    }

    public boolean hasNeighbourWho(int x, int y,TileToBoolean b){
        for (Tile t:this.getNeighbours(x,y)){
            if (b.operation(t)){
                return true;
            }
        }
        return false;
    }

    public int countOfNeighboursWith(int x, int y,TileToBoolean b){
        int i=0;
        for (Tile t:this.getNeighbours(x,y)){
            if (b.operation(t)){
                i++;
            }
        }
        return i;
    }

    public Field(int w, int h, boolean hardMode, Time t, Point[] Mountains, int cfc){
        this.t=t;
        this.w=w;
        this.h=h;
        this.hardMode=hardMode;
        this.map=new Tile[h][w];
        for (int i=0;i<h;i++){
            for (int j=0;j<w;j++){
                map[i][j]=new Tile(j,i);
                map[i][j]=new Tile(j,i);
            }
        }
        this.Mountains=Mountains;
        this.coinsFromCards=cfc;
    }

    public Field getAndUseBest(Card c, Rater r){
        Field f=usePossibility(r.bestPossibility(this,c));
        f.t.add(c.getDuration());
        replaceInGUI(f);
        System.out.println(r.rateExpected(f));
        return f;
    }

    public void replaceInGUI(Field f){
        if (fw!=null) this.fw.setF(f);
        f.fw=fw;
    }

    public void setRuin(int x, int y){
        map[y][x].ruin=true;
    }

    public Field deepCopy(){
        Field f=new Field(w,h,hardMode,t.copy(),Mountains,coinsFromCards);
        for (int y=0;y<h;y++){
            for (int x=0;x<w;x++){
                if (map[y][x].getType()!=TileType.EMPTY){
                    f.set(x, y, map[y][x].getType());
                }
                if (map[y][x].ruin) f.setRuin(x, y);
            }
        }
        f.pointsExpected=pointsExpected;
        f.availableCards=new LinkedList<>();
        if (availableCards!=null)for (Card c:availableCards){
            f.availableCards.add(c);
        }
        f.GUI=GUI;
        return f;
    }




    public Field usePossibility(Possibility p){
        Area a=new Area(p.getType());
        TileType t=p.getType();
        Field f=this.deepCopy();
        for (int y=0;y<p.getHeight();y++){
            for (int x=0;x<p.getWidth();x++){
                if (p.getToPlace()[y][x]){
                    int xpos=x+p.getX();
                    int ypos=y+p.getY();
                    f.map[ypos][xpos].setType(p.getType());
                    a.add(f.map[ypos][xpos]);
                    if (ypos > 0 && f.map[ypos-1][xpos].getType()==t&&f.map[ypos-1][xpos].getArea()!=a){
                        a.join(f.map[ypos-1][xpos].getArea());
                    }
                    if (ypos < h -1&& f.map[ypos+1][xpos].getType()==t&&f.map[ypos+1][xpos].getArea()!=a){
                        a.join(f.map[ypos+1][xpos].getArea());
                    }
                    if (xpos > 0 && f.map[ypos][xpos-1].getType()==t&&f.map[ypos][xpos-1].getArea()!=a){
                        a.join(f.map[ypos][xpos-1].getArea());
                    }
                    if (xpos < w -1&& f.map[ypos][xpos+1].getType()==t&&f.map[ypos][xpos+1].getArea()!=a){
                        a.join(f.map[ypos][xpos+1].getArea());
                    }
                }
            }
        }
        if (p.isGetCoin()){
            f.coinsFromCards++;
        }
        return f;
    }



    public TileType getType(int x, int y){
        return map[y][x].getType();
    }

    public void set(int x, int y, TileType type){
        map[y][x].setType(type);
        Area a=new Area(type);
        a.add(map[y][x]);
        if (y>0&&map[y-1][x].getType()==type&&a!=map[y-1][x].getArea()){
            a=a.joinTo(map[y-1][x].getArea());
        }
        if (y<h-1&&map[y+1][x].getType()==type&&a!=map[y+1][x].getArea()){
            a=a.joinTo(map[y+1][x].getArea());
        }
        if (x>0&&map[y][x-1].getType()==type&&a!=map[y][x-1].getArea()){
            a=a.joinTo(map[y][x-1].getArea());
        }
        if (x<w-1&&map[y][x+1].getType()==type&&a!=map[y][x+1].getArea()){
            a.joinTo(map[y][x+1].getArea());
        }

        if (GUI!=null) GUI.repaint();
    }

    public static Field standardField(){
        Field f=new Field(11,11,false,new Time(), new Point[]{new Point(3,1), new Point(8,2), new Point(5,5), new Point(2,8),
                new Point(7,9)},0);
        f.set(3, 1, TileType.MOUNTAIN);
        f.set(8, 2, TileType.MOUNTAIN);
        f.set(5, 5, TileType.MOUNTAIN);
        f.set(2, 8, TileType.MOUNTAIN);
        f.set(7, 9, TileType.MOUNTAIN);
        f.setRuin(5, 1);
        f.setRuin(1, 2);
        f.setRuin(9, 2);
        f.setRuin(1, 8);
        f.setRuin(9, 8);
        f.setRuin(5, 9);
        return f;
    }

    public boolean isRuin(int x, int y){
        return map[y][x].ruin;
    }

    public static Field hardmodeField(){
        Field f=new Field(11,11,true, new Time(), new Point[]{new Point(8,1), new Point(3,2), new Point(5,7),
                new Point(9,8), new Point(2,9)},0);
        f.set(8, 1, TileType.MOUNTAIN);
        f.set(3, 2, TileType.MOUNTAIN);
        f.set(5, 7, TileType.MOUNTAIN);
        f.set(9, 8, TileType.MOUNTAIN);
        f.set(2, 9, TileType.MOUNTAIN);
        f.set(5, 3, TileType.OBSTACLE);
        f.set(4, 4, TileType.OBSTACLE);
        f.set(5, 4, TileType.OBSTACLE);
        f.set(4, 5, TileType.OBSTACLE);
        f.set(5, 5, TileType.OBSTACLE);
        f.set(5, 6, TileType.OBSTACLE);
        f.setRuin(6, 1);
        f.setRuin(2, 2);
        f.setRuin(6, 4);
        f.setRuin(1, 6);
        f.setRuin(8, 7);
        f.setRuin(3, 9);

        return f;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void makeGuiWindow(GameStats s){
        GUI=new GUI_Manager();
        fw= GUI.makeGUI(s,this);

    }

    @Override
    public String toString(){
        StringBuilder ret= new StringBuilder("Field:\n");
        for (int i=0;i<map.length;i++){
            for (int j=0;j<map[i].length;j++){
                ret.append(Utilities.TileTypeToInt(map[i][j].getType()));
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    public LinkedList<Possibility> getAllPossibleActions(Card c){
        LinkedList<Possibility> ret=new LinkedList<Possibility>();
        //Possible Card Choices
        for (Possibility p1:c.getPossibilities()){
            //Possible Orientations
            for (Possibility p2:p1.getAllShapeRotations()){
                //Possible Locations
                for (int y=0;y<=h-p2.getHeight();y++){
                    for (int x=0;x<=w-p2.getWidth();x++){
                        if (isPossible(p2,x,y,c.onRuin)){
                            ret.add(p2.addLocation(x,y));
                        }
                    }
                }
            }
        }
        return ret;
    }

    public LinkedList<Tile> getNeighbours(int x, int y){
        LinkedList<Tile> ret=new LinkedList<>();
        if (x>0) ret.add(map[y][x-1]);
        if (x<w-1) ret.add(map[y][x+1]);
        if (y>0) ret.add(map[y-1][x]);
        if (y<h-1) ret.add(map[y+1][x]);
        return ret;
    }

    public float ProbToFill(int x, int y){
        return 0.01f;
    }

    public LinkedList<Tile> allTiles(){
        LinkedList<Tile> ret=new LinkedList<>();
        for (int i=0;i<h;i++){
            ret.addAll(Arrays.asList(map[i]).subList(0, w));
        }
        return ret;
    }

    public boolean canFill(int x, int y, Possibility p){
        for (int i=1-p.getWidth();i<=0;i++){
            for (int j=1-p.getHeight();j<=0;j++){
                if (isPossible(p,x+i,x+j,false)&&p.getToPlace()[-j][-i]) return true;
            }
        }
        return false;
    }

    public float probToFill(int x, int y, LinkedList<Card> l, TypeToBool t){
        float f=0f;
        boolean b;
        for (Card c:l){
            b=false;
            for (Possibility p:c.getPossibilities()){
                if (t.operation(p.getType())&&canFill(x,y,p))
                    b=true;
            }
            if (b) f++;
        }
        return f/l.size();
    }

    public float sumProbToFill(TypeToBool what, Season s){
return sumProbToFill(what,(x)->true,s);
    }

    public float sumProbToFill(TypeToBool what, TypeToBool op, Season s){
        float ret = 0;
        for (int x=0;x<11;x++){
            for (int y=0;y<11;y++){
                if (what.operation(map[y][x].getType())){
                    for (Tile till:getNeighbours(x,y)){
                        if (op.operation(till.getType())){
                            ret+=probabilityToFill(x,y,s,op);
                        }
                    }
                }
            }
        }
        return ret;
    }

    private float probToFill(int x, int y, LinkedList<Card> l){
        float f=0f;
        boolean b;
        int ruinCount=0;
        for (Card c:l){
            if (c.ruinCard){
                ruinCount++;
                continue;
            }
            b=false;
            for (Possibility p:c.getPossibilities()){
                if (canFill(x,y,p))
                    b=true;
            }
            if (b) f++;
        }
        return f/(l.size()-ruinCount);
    }

    public float probabilityToFill(int x, int y, Season s){
        float remtot=t.getTimeRemainingToSeasonEnd(s);
        float p1=probToFill(x,y,availableCards);
        float p2=probToFill(x,y, CardGenerator.generateCardsExceptRuins());
        float rems=t.getTimeRemainingToNextSeason();

        float ret= (rems*p1+(remtot-rems)*p2)/remtot;
        if (Float.isNaN(ret)) throw new NullPointerException("WAAAAAAAH");
        return ret;
    }

    public float probabilityToFill(int x, int y, Season s, TypeToBool tt){
        float remtot=t.getTimeRemainingToSeasonEnd(s);
        float p1=probToFill(x,y,availableCards,tt);
        float p2=probToFill(x,y, CardGenerator.generateCardsExceptRuins(),tt);
        float rems=t.getTimeRemainingToNextSeason();
        return (rems*p1+(remtot-rems)*p2)/remtot;
    }

    public FieldWindow getFw() {
        return fw;
    }

    public boolean isPossible(Possibility p, int x, int y, boolean onRuin){
        if (x<0||y<0||x+p.getWidth()>w||y+p.getHeight()>=h) return false; //out of bounds
        boolean ruinFound=false;
        for (int j=0;j<p.getWidth();j++){
            for (int i=0;i<p.getHeight();i++){
                if (p.getToPlace()[i][j]&&map[y+i][x+j].getType()!=TileType.EMPTY){
                    return false;
                }
                if (p.getToPlace()[i][j]&&map[y+i][x+j].ruin){
                    ruinFound=true;
                }
            }
        }
        return !onRuin || ruinFound;
    }


    public void resetCards(Random rand) {
        this.availableCards=CardGenerator.generateAndShuffleCards(rand);
    }

    public Card popCard() {
        return availableCards.pop();
    }

    public Card peekCard() {
        return availableCards.peek();
    }
}
