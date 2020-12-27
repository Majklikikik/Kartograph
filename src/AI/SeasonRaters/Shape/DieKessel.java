package AI.SeasonRaters.Shape;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

public class DieKessel extends SingleRater {

    public DieKessel(float [] params){
        super(params);
        super.name="Die Kessel";
    }

    public DieKessel(){
        super();
        super.name="Die Kessel";
    }

    @Override
    public int rateCurrent(Field f) {
        int k=0;
        boolean b;
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                if (f.getType(x,y)== TileType.EMPTY){
                    b=true;
                    for (Tile t:f.getNeighbours(x,y)){
                        if (t.getType()==TileType.EMPTY){
                            b=false;
                        }
                    }
                    if (b) k++;
                }
            }
        }
        return k;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        float k=0;
        int t;
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                if (f.getType(x,y)== TileType.EMPTY){
                    t=0;
                    for (Tile tt:f.getNeighbours(x,y)){
                        if (tt.getType()==TileType.EMPTY){
                            t++;
                        }
                    }
                    if (t==0) k++;
                    else k+=params[t-1];
                }
            }
        }
        return k;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.2f,0.1f,0.05f,0.02f};
    }
}
