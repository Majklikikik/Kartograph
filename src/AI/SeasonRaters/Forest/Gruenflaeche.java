package AI.SeasonRaters.Forest;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.TileType;

public class Gruenflaeche extends SingleRater {
    @Override
    public int paramCount() {
        return 2;
    }

    public Gruenflaeche(float [] params){
        super(params);
        super.name="Grünfläche";
    }

    public Gruenflaeche(){
        super();
        super.name="Grünfläche";
    }


    @Override
    public int rateCurrent(Field f) {
        int p=0;
        for (int i=0;i<11;i++){
            int z=0,s=0;
            for (int j=0;j<11;j++){
                if (f.getType(j, i)== TileType.FOREST){
                    z++;
                }
                if (f.getType(i, j)== TileType.FOREST){
                    s++;
                }
            }
            if (z>=1) p++;
            if (s>=1) p++;
        }
        return p;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season ss) {
        int p=0;
        float zl, sl;
        for (int y=0;y<11;y++){
            int z=0,s=0;
            int lz=0,ls=0;
            sl=0;
            zl=0;
            for (int x=0;x<11;x++){
                if (z>0&&s>0) break;
                if (f.getType(x, y)== TileType.FOREST){
                    z++;
                } else if (f.getType(x, y)==TileType.EMPTY){
                    lz++;
                    zl+=f.probabilityToFill(x,y,ss);
                }
                if (f.getType(y, x)== TileType.FOREST){
                    s++;
                } else if (f.getType(y, x)==TileType.EMPTY){
                    ls++;
                    sl+=f.probabilityToFill(x,y,ss);
                }
            }
            if (z>=1) p++; else p+=params[lz]*timeRemaining*zl;
            if (s>=1) p++; else p+=params[ls]*timeRemaining*sl;
        }
        return p;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.01f,0.01f,0.01f,0.01f,0.01f,0.01f,0.01f,0.01f,0.01f,0.01f,0.01f,0.1f};
    }
}
