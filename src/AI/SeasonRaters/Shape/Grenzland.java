package AI.SeasonRaters.Shape;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.TileType;

public class Grenzland extends SingleRater {

    public Grenzland(float [] params){
        super(params);
        super.name="Grenzland";
    }

    public Grenzland(){
        super();
        super.name="Grenzland";
    }

    @Override
    public int rateCurrent(Field f) {
        int c=0;
        int s=0,z=0;
        boolean bs, bz;
        for (int i=0;i<11;i++){
            bs=true;
            bz=true;
            for (int j=0;j<11;j++){
                if (f.getType(j, i)==TileType.EMPTY){
                    bs=false;
                }
                if (f.getType(i, j)==TileType.EMPTY){
                    bz=false;
                }
            }
            if (bs) c++;
            if (bz) c++;
        }
        return c*6;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season ss) {
        int c=0;
        int s=0,z=0;
        int bs, bz;
        float fbs,fbz;
        for (int y=0;y<11;y++){
            bs=0;
            bz=0;
            fbs=0f;
            fbz=0f;
            for (int x=0;x<11;x++){
                if (f.getType(x, y)==TileType.EMPTY){
                    bs++;
                    fbs+=f.probabilityToFill(x,y,ss);
                }
                if (f.getType(y, x)==TileType.EMPTY){
                    bz++;
                    fbz+=f.probabilityToFill(x,y,ss);
                }
            }
            if (bs==0) c++;
            if (bz==0) c++;
            if (bs!=0) c+=params[bs-1]*timeRemaining*fbz;
            if (bz!=0) c+=params[bz-1]*timeRemaining*fbs;
        }
        return c*6;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{1,1,1,1,1,1,1,1,1,1,1};
    }
}
