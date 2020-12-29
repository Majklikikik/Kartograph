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
import javafx.geometry.Pos;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import static AI.SeasonRaters.Season.*;


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
    private HashMap<Season, HashMap<TileType,Float>>[][] probs= new HashMap[11][11];

    public HashMap<TileType, Float> zeroMap(){
        HashMap<TileType,Float> ret= new HashMap<>();
        ret.put(TileType.WATER,0f);
        ret.put(TileType.FIELD,0f);
        ret.put(TileType.FOREST,0f);
        ret.put(TileType.CITY,0f);
        return ret;
    }

    public void recalculateProbs(int x_from, int y_from, int x_to, int y_to){
        HashMap<TileType, Float> probsAvailableCards[][]= new HashMap[y_to-y_from+1][x_to-x_from+1];
        HashMap<TileType, Float> probsAllCards[][]= new HashMap[y_to-y_from+1][x_to-x_from+1];
        for (int i=0;i<x_to-x_from+1;i++){
            for (int j=0;j<y_to-y_from+1;j++){
                probsAllCards[j][i]=zeroMap();
                probsAvailableCards[j][i]=zeroMap();
                probs[j+y_from][i+x_from]=new HashMap<>();
            }
        }
        for (Card c: CardGenerator.generateCardsExceptRuins()){
            HashMap<TileType, Boolean> [][] possible=new HashMap[y_to-y_from+1][x_to-x_from+1];
            for (int i=x_from;i<x_to+1;i++){
                for (int j=y_from;j<y_to+1;j++){
                    possible[j-y_from][i-x_from]=new HashMap<>();
                }
            }
            for (Possibility p:getAllPossibleActions(c,x_from,y_from,x_to,y_to)){
                if (p.getX()>=x_from-3&&p.getX()<=x_to
                        && p.getY()>=y_from-3&&p.getY()<=y_to){
                    for (Point pt:p.getFilledCoords()){
                        if (pt.x>=x_from&&pt.x<=x_to&&pt.y>=y_from&&pt.y<=y_to){
                            possible[pt.y-y_from][pt.x-x_from].put(p.getType(),true);
                        }
                    }
                }
            }
            for (int x=x_from;x<x_to+1;x++){
                for (int y=y_from;y<y_to+1;y++){
                    for (TileType t:Tile.allPlacableTypes())
                        if (possible[y-y_from][x-x_from].containsKey(t)&&possible[y-y_from][x-x_from].get(t)){
                            probsAllCards[y-y_from][x-x_from].put(t,probsAllCards[y-y_from][x-x_from].get(t)+1.0f/CardGenerator.fullCardCount);
                            if (availableCards.contains(c))
                                probsAvailableCards[y-y_from][x-x_from].put(t,probsAvailableCards[y-y_from][x-x_from].get(t)+1.0f/availableCards.size());
                        }
                }
            }
        }

        float remSpr=t.getTimeRemainingToSeasonEnd(SPRING);
        float remSum=t.getTimeRemainingToSeasonEnd(SUMMER);
        float remAut=t.getTimeRemainingToSeasonEnd(AUTUMN);
        float remWin=t.getTimeRemainingToSeasonEnd(WINTER);
        float rems=t.getTimeRemainingToNextSeason();
        for (int y=y_from;y<=y_to;y++){
            for (int x=x_from;x<=x_to;x++){
                probsAvailableCards[y-y_from][x-x_from]=mult(probsAvailableCards[y-y_from][x-x_from],rems);
                if (remSpr>0) probs[y][x].put(SPRING,mult(sum(probsAvailableCards[y-y_from][x-x_from],mult(probsAllCards[y-y_from][x-x_from],remSpr-rems)),1.0f/remSpr));
                else probs[y][x].put(SPRING,zeroMap());
                if (remSum>0) probs[y][x].put(SUMMER,mult(sum(probsAvailableCards[y-y_from][x-x_from],mult(probsAllCards[y-y_from][x-x_from],remSum-rems)),1.0f/remSum));
                else probs[y][x].put(SUMMER,zeroMap());
                if (remAut>0) probs[y][x].put(AUTUMN,mult(sum(probsAvailableCards[y-y_from][x-x_from],mult(probsAllCards[y-y_from][x-x_from],remAut-rems)),1.0f/remAut));
                else probs[y][x].put(AUTUMN,zeroMap());
                if (remWin>0) probs[y][x].put(WINTER,mult(sum(probsAvailableCards[y-y_from][x-x_from],mult(probsAllCards[y-y_from][x-x_from],remWin-rems)),1.0f/remWin));
                else probs[y][x].put(WINTER,zeroMap());
            }
        }
    }

    private LinkedList<Possibility> getAllPossibleActions(Card c, int x_from, int y_from, int x_to, int y_to) {
        LinkedList<Possibility> ret=new LinkedList<Possibility>();
        //Possible Card Choices
        for (Possibility p1:c.getPossibilities()){
            //Possible Orientations
            for (Possibility p2:p1.getAllShapeRotations()){
                //Possible Locations
                for (int y=y_from;y<=y_to-p2.getHeight()+1;y++){
                    for (int x=x_from;x<=x_to-p2.getWidth()+1;x++){
                        if (isPossible(p2,x,y,c.onRuin)){
                            ret.add(p2.addLocation(x,y));
                        }
                    }
                }
            }
        }
        return ret;
    }

    public HashMap<TileType, Float> sum(HashMap<TileType,Float> l, HashMap<TileType,Float> r){
        HashMap<TileType, Float> ret=new HashMap<>();
        for (TileType t:l.keySet()){
            ret.put(t,l.get(t)+r.get(t));
        }
        return ret;
    }

    public HashMap<TileType, Float> mult(HashMap<TileType,Float> orig, float m){
        HashMap<TileType, Float> ret=new HashMap<>();
        for (TileType t:orig.keySet()){
            ret.put(t,orig.get(t)*m);
        }
        return ret;
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
                if (!ret.contains(s.getType())&&a.type!=s.getType()){
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


        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                f.probs[y][x]=new HashMap<>();
                for (Season s:probs[y][x].keySet()){
                    f.probs[y][x].put(s,new HashMap<>());
                    for (TileType t:probs[y][x].get(s).keySet())
                        f.probs[y][x].get(s).put(t,probs[y][x].get(s).get(t));
                }
            }
        }
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
        f.recalculateProbs(p.getX(),p.getY(),p.getX()+p.getWidth()-1,p.getY()+p.getHeight()-1);
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
        f.availableCards=CardGenerator.generateAndShuffleCards();
        f.recalculateProbs(0,0,10,10);
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
        f.recalculateProbs(0,0,10,10);
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
        return getAllPossibleActions(c,0,0,10,10);
    }

    public LinkedList<Tile> getNeighbours(int x, int y){
        LinkedList<Tile> ret=new LinkedList<>();
        if (x>0) ret.add(map[y][x-1]);
        if (x<w-1) ret.add(map[y][x+1]);
        if (y>0) ret.add(map[y-1][x]);
        if (y<h-1) ret.add(map[y+1][x]);
        return ret;
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

    public float probabilityToFill(int x, int y, Season s){
        float f=0.0f;
        for (TileType t:probs[y][x].get(s).keySet()){
            f+=probs[y][x].get(s).get(t);
        }
        return f;
    }

    public float probabilityToFill(int x, int y, Season s, TypeToBool tt){
        float f=0.0f;
        for (TileType t:probs[y][x].get(s).keySet()){
            if (tt.operation(t)){
                f+=probs[y][x].get(s).get(t);
            }
        }
        return f;
    }

    public FieldWindow getFw() {
        return fw;
    }

    public boolean isPossible(Possibility p, int x, int y, boolean onRuin){
        if (x<0||y<0||x+p.getWidth()>w||y+p.getHeight()>h) return false; //out of bounds
        boolean ruinFound=false;
        for (int x2=0;x2<p.getWidth();x2++){
            for (int y2=0;y2<p.getHeight();y2++){
                if (p.getToPlace()[y2][x2]&&map[y+y2][x+x2].getType()!=TileType.EMPTY){
                    return false;
                }
                if (p.getToPlace()[y2][x2]&&map[y+y2][x+x2].ruin){
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
