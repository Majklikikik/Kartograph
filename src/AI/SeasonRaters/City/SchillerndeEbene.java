package AI.SeasonRaters.City;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Area;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

import java.util.LinkedList;

public class SchillerndeEbene extends SingleRater {

    public SchillerndeEbene(float [] params){
        super(params);
        super.name="Schillernde Ebene";
    }

    public SchillerndeEbene(){
        super();
        super.name="Schillernde Ebene";
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
                    if (s.size()>3||(s.size()==3&&!s.contains(TileType.EMPTY))) {
                        c += 3;

                    }
                }
            }
        }
        return c;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season ss) {int c = 0;
        LinkedList<Area> cities = new LinkedList<>();
        for (Tile t : f.allTiles()) {
            if (t.getType() == TileType.CITY) {
                if (!cities.contains(t.getArea())) {
                    cities.add(t.getArea());
                    LinkedList<TileType> s=f.surroundingAreaTypes(t.getArea());
                    if (s.size()>3||(s.size()==3&&!s.contains(TileType.EMPTY))) {
                        c += 3;

                    }
                    else if (s.size()==3){
                        c+=3*timeRemaining*params[0];
                    }
                    else if (s.size()==2&&f.surroundingAreaTypes(t.getArea()).contains(TileType.EMPTY)){
                        c+=2*timeRemaining*params[1];
                    }
                    else if (s.size()==1&&f.surroundingAreaTypes(t.getArea()).contains(TileType.EMPTY)){
                        c+=timeRemaining*params[2];
                    }
                }
            }
        }
        return c;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f,0.1f,0.1f};
    }
}
