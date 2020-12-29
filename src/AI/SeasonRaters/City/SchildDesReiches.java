package AI.SeasonRaters.City;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Area;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

import java.util.LinkedList;

public class SchildDesReiches extends SingleRater {

    public SchildDesReiches(float [] params){
        super(params);
        super.name="Schild des Reiches";
    }

    public SchildDesReiches(){
        super();
        super.name="Schild des Reiches";
    }

    @Override
    public int rateCurrent(Field f) {
        int b = 0;
        int c = 0;
        LinkedList<Area> cities = new LinkedList<>();
        for (Tile t : f.allTiles()) {
            if (t.getType() == TileType.CITY) {
                if (!cities.contains(t.getArea())) {
                    cities.add(t.getArea());
                    LinkedList<TileType> s=f.surroundingAreaTypes(t.getArea());
                    if (t.getArea().tiles.size()>=b){
                     c=b;
                     b=t.getArea().tiles.size();
                    }
                }
            }
        }
        return c*2;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
            int b = 0;
            int c = 0;
            LinkedList<Area> cities = new LinkedList<>();
            for (Tile t : f.allTiles()) {
                if (t.getType() == TileType.CITY) {
                    if (!cities.contains(t.getArea())) {
                        cities.add(t.getArea());
                        if (t.getArea().tiles.size()>=b){
                            c=b;
                            b=t.getArea().tiles.size();
                        }
                    }
                }
            }
            return c*2+b*2*timeRemaining*params[0];
        }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.2f};
    }
}
