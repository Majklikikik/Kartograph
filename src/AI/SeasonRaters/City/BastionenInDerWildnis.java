package AI.SeasonRaters.City;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Area;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

import java.util.LinkedList;

public class BastionenInDerWildnis extends SingleRater {


    public BastionenInDerWildnis(float [] params){
        super(params);
        super.name="Bastionen in der Wildnis";
    }

    public BastionenInDerWildnis(){
        super();
        super.name="Bastionen in der Wildnis";
    }

    @Override
    public int rateCurrent(Field f) {
        int c=0;
        Tile t;
        LinkedList<Area> cities=new LinkedList<>();
        for (int x=0;x<11;x++){
            for (int y=0;y<11;y++){
                t=f.getTile(x,y);
                if (t.getType()== TileType.CITY){
                    if (!cities.contains(t.getArea())){
                        cities.add(t.getArea());
                        if (t.getArea().tiles.size()>=6){
                            c+=8;
                        }
                    }
                }
            }
        }
        return c;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        float c=0;
        Tile t;
        LinkedList<Area> cities=new LinkedList<>();
        for (int x=0;x<11;x++){
            for (int y=0;y<11;y++){
                t=f.getTile(x,y);
                if (t.getType()== TileType.CITY){
                    if (!cities.contains(t.getArea())){
                        cities.add(t.getArea());
                        if (t.getArea().tiles.size()>=6){
                            c+=8;
                        }
                        else if (f.surroundingAreaTypes(t.getArea()).contains(TileType.EMPTY)){
                            c+=timeRemaining*params[0]*t.getArea().tiles.size()/8f;
                        }
                    }
                }
            }
        }
        return c;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f};
    }
}
