package AI.SeasonRaters.WaterField;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

public class TalDerMagier extends SingleRater {

    public TalDerMagier(float [] params){
        super(params);
        super.name="Tal der Magier";
    }

    public TalDerMagier(){
        super();
        super.name="Tal der Magier";
    }

    @Override
    public int rateCurrent(Field f) {
        return 2*f.countOfTilesWithPropertyAndNeighboursWithProperty((Tile t)->t.getType()== TileType.WATER,(Tile t)->t.getType()==TileType.MOUNTAIN)+
                f.countOfTilesWithPropertyAndNeighboursWithProperty((Tile t)->t.getType()==TileType.FIELD,(Tile t)->t.getType()==TileType.MOUNTAIN);
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        float ret=0f;
        for (int i=0;i<5;i++){
            if (f.getType(f.Mountains[i].x-1,f.Mountains[i].y)==TileType.EMPTY) {
                ret+=1*f.probabilityToFill(f.Mountains[i].x-1,f.Mountains[i].y,s,(TileType t)-> t==TileType.FIELD)*params[0];
                ret+=2*f.probabilityToFill(f.Mountains[i].x-1,f.Mountains[i].y,s,(TileType t)-> t==TileType.WATER)*params[1];
            }
            if (f.getType(f.Mountains[i].x+1,f.Mountains[i].y)==TileType.EMPTY) {
                ret+=1*f.probabilityToFill(f.Mountains[i].x+1,f.Mountains[i].y,s,(TileType t)-> t==TileType.FIELD)*params[0];
                ret+=2*f.probabilityToFill(f.Mountains[i].x+1,f.Mountains[i].y,s,(TileType t)-> t==TileType.WATER)*params[1];
            }
            if (f.getType(f.Mountains[i].x,f.Mountains[i].y-1)==TileType.EMPTY) {
                ret+=1*f.probabilityToFill(f.Mountains[i].x,f.Mountains[i].y-1,s,(TileType t)-> t==TileType.FIELD)*params[0];
                ret+=2*f.probabilityToFill(f.Mountains[i].x,f.Mountains[i].y-1,s,(TileType t)-> t==TileType.WATER)*params[1];
            }
            if (f.getType(f.Mountains[i].x,f.Mountains[i].y+1)==TileType.EMPTY) {
                ret+=1*f.probabilityToFill(f.Mountains[i].x,f.Mountains[i].y+1,s,(TileType t)-> t==TileType.FIELD)*params[0];
                ret+=2*f.probabilityToFill(f.Mountains[i].x,f.Mountains[i].y+1,s,(TileType t)-> t==TileType.WATER)*params[1];
            }
        }
        return ret+rateCurrent(f)*timeRemaining;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f,0.1f};
    }

}
