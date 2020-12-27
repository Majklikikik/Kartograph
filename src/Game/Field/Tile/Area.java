package Game.Field.Tile;

import java.util.LinkedList;

public class Area {
    public LinkedList<Tile> tiles;
    public TileType type;
    public Area(TileType t){
        type=t;
        tiles=new LinkedList<Tile>();
    }

    public void add(Tile t){
        tiles.add(t);
        t.setArea(this);
    }

    public Area joinTo(Area a){
        a.join(this);
        return a;
    }

    public void join (Area a){
        for (Tile t: a.tiles){
            add(t);
        }
    }
}
