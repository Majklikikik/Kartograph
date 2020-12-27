package AI.SeasonRaters.City;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Area;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

import java.util.LinkedList;

public class Metropole extends SingleRater {

    public Metropole(float [] params){
        super(params);
        super.name="Metropole";
    }

    public Metropole(){
        super();
        super.name="Metropole";
    }

    @Override
    public int rateCurrent(Field f) {
        int c = 0;
        LinkedList<Area> cities = new LinkedList<>();
        for (Tile t : f.allTiles()) {
            if (t.getType() == TileType.CITY) {
                if (!cities.contains(t.getArea())) {
                    cities.add(t.getArea());
                    LinkedList<TileType> s=f.surroundingAreaTypes(t.getArea());
                    if (!s.contains(TileType.MOUNTAIN)&&t.getArea().tiles.size()>c)
                        c=t.getArea().tiles.size();
                }
            }
        }
        return c;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        int c = 0;
        boolean EmptyNext=false;
        LinkedList<Area> cities = new LinkedList<>();
        for (Tile t : f.allTiles()) {
            if (t.getType() == TileType.CITY) {
                if (!cities.contains(t.getArea())) {
                    cities.add(t.getArea());
                    LinkedList<TileType> ss=f.surroundingAreaTypes(t.getArea());
                    if (!ss.contains(TileType.MOUNTAIN)&&t.getArea().tiles.size()>c) {
                        c = t.getArea().tiles.size();
                        EmptyNext=f.surroundingAreaTypes(t.getArea()).contains(TileType.EMPTY);
                    }
                }
            }
        }
        return c+(EmptyNext?params[0]*timeRemaining:0);
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.5f};
    }
}
