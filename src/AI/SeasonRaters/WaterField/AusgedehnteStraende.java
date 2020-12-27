package AI.SeasonRaters.WaterField;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Area;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

import java.util.LinkedList;

import static Game.Field.Tile.TileType.*;

public class AusgedehnteStraende extends SingleRater {

    public AusgedehnteStraende(float [] params){
        super(params);
        super.name="Ausgedehnte Strände";
    }

    public AusgedehnteStraende(){
        super();
        super.name="Ausgedehnte Strände";
    }

    @Override
    public int rateCurrent(Field f) {
        int c=0;
        LinkedList<Area> l=new LinkedList<>();
        for (int x=1;x<10;x++){
            for (int y=1;y<10;y++){
                if (f.getType(x, y)==FIELD&&!l.contains(f.getTile(x,y).getArea())){
                    l.add(f.getTile(x,y).getArea());
                    if (!f.surroundingAreaTypes(f.getTile(x,y).getArea()).contains(WATER))
                        c+=3;
                }else if (f.getType(x, y)==WATER&&!l.contains(f.getTile(x,y).getArea())){
                    l.add(f.getTile(x,y).getArea());
                    if (!f.surroundingAreaTypes(f.getTile(x,y).getArea()).contains(FIELD))
                        c+=3;
                }
            }
        }
        return c;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        int c=0;
        for (int x=1;x<10;x++){
            for (int y=1;y<10;y++){
                if (f.getType(x, y)==EMPTY&&!f.hasNeighbourWho(x,y,(Tile t)->t.getType()==WATER)){
                    c+=timeRemaining*params[0]*f.probabilityToFill(x,y,s,(TileType t)->t==WATER);
                }
                else if (f.getType(x, y)==EMPTY&&!f.hasNeighbourWho(x,y,(Tile t)->t.getType()==FIELD)){
                    c+=timeRemaining*params[0]*f.probabilityToFill(x,y,s,(TileType t)->t==FIELD);;
                }
            }
        }
        LinkedList<Area> l=new LinkedList<>();
        for (int x=1;x<10;x++){
            for (int y=1;y<10;y++){
                if (f.getType(x, y)==FIELD&&!l.contains(f.getTile(x,y).getArea())){
                    l.add(f.getTile(x,y).getArea());
                    if (!f.surroundingAreaTypes(f.getTile(x,y).getArea()).contains(WATER))
                        c+=3;
                }else if (f.getType(x, y)==WATER&&!l.contains(f.getTile(x,y).getArea())){
                    l.add(f.getTile(x,y).getArea());
                    if (!f.surroundingAreaTypes(f.getTile(x,y).getArea()).contains(FIELD))
                        c+=3;
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
