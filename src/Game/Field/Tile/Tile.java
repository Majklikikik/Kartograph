package Game.Field.Tile;

import java.util.LinkedList;

public class Tile {
    public final static int TILE_EMPTY=0;
    public final static int TILE_FIELD=1;
    public final static int TILE_WATER=2;
    public final static int TILE_CITY=3;
    public final static int TILE_FOREST=4;
    public final static int TILE_HILL=5;
    public final static int TILE_OBSTACLE=6;
    public final static int TILE_ASSHOLE=7;

public boolean ruin=false;
    TileType type;
    Area area;
    public int x;
    public int y;



    public Tile(int x, int y){
        type=TileType.EMPTY;
        area=null;
        this.x=x;
        this.y=y;
    }

    public static LinkedList<TileType> allPlacableTypes(){
        LinkedList<TileType> ret=new LinkedList<>();
        ret.add(TileType.FIELD);
        ret.add(TileType.WATER);
        ret.add(TileType.CITY);
        ret.add(TileType.FOREST);
        return ret;
    }

    public static LinkedList<TileType> allTypesExceptEmpty(){
        LinkedList<TileType> ret=allPlacableTypes();
        ret.add(TileType.ASSHOLE);
        ret.add(TileType.MOUNTAIN);
        ret.add(TileType.OBSTACLE);
        return ret;
    }

    public static LinkedList<TileType> allTiles() {
        LinkedList<TileType> ret = allTypesExceptEmpty();
        ret.add(TileType.EMPTY);
        return ret;
    }


    @Override public String toString(){
        return ""+this.type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
