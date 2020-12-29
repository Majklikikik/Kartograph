package Game.Card;

import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;
import Utility.Utilities;

import java.awt.*;
import java.util.LinkedList;

import static Utility.Utilities.*;

public class Possibility {
    private boolean [][] toPlace;
    TileType type;
    boolean getCoin;
    int x;
    int y;

    public Possibility(){
        this.toPlace=null;
        this.type=TileType.EMPTY;
        this.getCoin=false;
    }

    public Possibility (Possibility fromPos){
        this.toPlace= Utilities.deepCopy(fromPos.toPlace);
        this.type=fromPos.type;
        this.getCoin=fromPos.getCoin;
        this.x=fromPos.x;
        this.y=fromPos.y;
    }

    public boolean[][] getToPlace() {
        return toPlace;
    }

    public boolean valid(){
        return this.type!=TileType.EMPTY&&this.toPlace!=null;
    }

    public LinkedList<Point> getFilledCoords(){
        LinkedList<Point> ret=new LinkedList<>();
        for (int y=0;y<toPlace.length;y++){
            for (int x=0;x<toPlace[0].length;x++){
                if (toPlace[y][x]) ret.add(new Point(x+this.x,y+this.y));
            }
        }
        return ret;
    }

    public Possibility otherOrientation(boolean [][] newOr){
        Possibility ret=new Possibility();
        ret.setType(this.type);
        ret.setGetCoin(this.getCoin);
        ret.setToPlace(newOr);
        return ret;
    }

    public Possibility addLocation(int x, int y){
        Possibility ret= new Possibility(this);
        ret.setX(x);
        ret.setY(y);
        return ret;
    }


    //Getters, Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public LinkedList<Possibility> getAllShapeRotations(){
        LinkedList<Possibility> ret=new LinkedList<Possibility>();
        for (int i=0;i<4;i++){
            ret.add(otherOrientation(mrot(toPlace,i)));
            ret.add(otherOrientation(mirror(mrot(toPlace,i))));
        }
        return ret;
    }

    public int getHeight(){
        return toPlace.length;
    }

    public int getWidth(){
        return toPlace[0].length;
    }

    public void setToPlace(boolean[][] toPlace) {
        this.toPlace = toPlace;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isGetCoin() {
        return getCoin;
    }

    public void setGetCoin(boolean getCoin) {
        this.getCoin = getCoin;
    }

    @Override
    public String toString(){
        return this.x+":"+ this.y+"\n"+Utilities.toString(this.toPlace, this.type);
    }
}
