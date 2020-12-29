package AI.SeasonRaters.WaterField;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

public class GoldenerKornspeicher extends SingleRater {

    public GoldenerKornspeicher(float [] params){
        super(params);
        super.name="Goldener Kornspeicher";
    }

    public GoldenerKornspeicher(){
        super();
        super.name="Goldener Kornspeicher";
    }

    @Override
    public int rateCurrent(Field f) {
        int c=0;
        for (Tile t:f.allTiles()){
            if (t.ruin){
                if (t.getType()==TileType.FIELD) c+=3;
                for (Tile tt:f.getNeighbours(t.x,t.y)){
                    if (tt.getType()==TileType.WATER) c++;
                }
            }
            if (t.ruin&&t.getType()==TileType.FIELD) c+=3;
        }
        //return f.countOfTilesWithPropertyAndNeighboursWithProperty((Tile t)->t.getType()== TileType.WATER,(Tile t)->t.ruin)+c;
        return c;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        float ret=0;
        float retCur=0;
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                if (f.isRuin(x,y)){
                    if (f.getType(x,y)==TileType.EMPTY) retCur+=3;
                    if (f.getType(x,y)==TileType.EMPTY){
                        ret+=3*f.probabilityToFill(x,y,s,(TileType t)-> t==TileType.FIELD)*params[0];
                    }
                    if (x>0&&f.getType(x-1,y)==TileType.WATER) retCur++;
                    if (x>0&&f.getType(x-1,y)==TileType.EMPTY) {
                        ret+=f.probabilityToFill(x-1,y,s,(TileType t)-> t==TileType.WATER)*params[1];
                    }
                    if (x<10&&f.getType(x+1,y)==TileType.WATER) retCur++;
                    if (x<10&&f.getType(x+1,y)==TileType.EMPTY) {
                        ret+=f.probabilityToFill(x+1,y,s,(TileType t)-> t==TileType.WATER)*params[1];
                    }
                    if (y>0&&f.getType(x,y-1)==TileType.WATER) retCur++;
                    if (y>0&&f.getType(x,y-1)==TileType.EMPTY) {
                        ret+=f.probabilityToFill(x,y-1,s,(TileType t)-> t==TileType.WATER)*params[1];
                    }
                    if (y<10&&f.getType(x,y+1)==TileType.EMPTY) retCur++;
                    if (y<10&&f.getType(x,y+1)==TileType.EMPTY) {
                        ret+=f.probabilityToFill(x,y+1,s,(TileType t)-> t==TileType.WATER)*params[1];
                    }
                }
            }
        }
        return ret*timeRemaining+retCur;//+rateCurrent(f);
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f,0.1f};
    }
}
