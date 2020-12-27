package Game.Field.Tile;

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
