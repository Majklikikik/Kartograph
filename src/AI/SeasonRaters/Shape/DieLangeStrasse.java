package AI.SeasonRaters.Shape;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.TileType;

public class DieLangeStrasse extends SingleRater {

    public DieLangeStrasse(float [] params){
        super(params);
        super.name="Die Lange Straße";
    }

    public DieLangeStrasse(){
        super();
        super.name="Die Lange Straße";
    }

    @Override
    public int rateCurrent(Field f) {
        int c=0;
        boolean tmp;
        for (int y=0;y<11;y++){
            tmp=true;
            for (int x=0;x<11-y;x++){
                if (f.getType(x, y+x)== TileType.EMPTY){
                    tmp=false;
                }
            }
            if (tmp) c++;
        }
        return c*3;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        float c=0;
        int tmp=0;
        float ftmp=0;
        for (int y=0;y<11;y++){
            tmp=0;
            ftmp=0;
            for (int x=0;x<11-y;x++){
                if (f.getType(x, y+x)== TileType.EMPTY){
                    tmp++;
                    ftmp+=f.probabilityToFill(x,y+x,s);
                }
            }
            if (tmp==0) c++;
            else c+=params[tmp-1]*ftmp;
        }
        return c*3;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0f,0f,0f,0f,0f,0f,0f,0f,0f,0f};
    }
}
