package AI.SeasonRaters.Forest;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

public class Duesterwald extends SingleRater {

    public Duesterwald(float [] params){
        super(params);
        super.name="Düsterwald";
    }

    public Duesterwald(){
        super();
        super.name="Düsterwald";
    }

    @Override
    public int rateCurrent(Field f) {
        int p=0;
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                if (f.getType(x, y)==TileType.FOREST){
                    boolean b=true;
                    for (Tile t:f.getNeighbours(x,y)){
                        if (t.getType()==TileType.EMPTY){
                            b=false;
                            break;
                        }
                    }
                    if (b) p++;
                }
            }
        }
        return p;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        float p=0;
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                if (f.getType(x, y)==TileType.FOREST){
                    float probToFill=0;
                    int missingTiles=0;
                    for (Tile t:f.getNeighbours(x,y)){
                        if (t.getType()==TileType.EMPTY){
                            missingTiles++;
                            probToFill+=f.probabilityToFill(x,y,s);
                        }
                    }
                    if (missingTiles==0) p++;
                    else p+=params[0]*(0.25f*(4-missingTiles+probToFill*params[1]))*timeRemaining;
                }
            }
        }
        return p;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f,0.9f};
    }
}
