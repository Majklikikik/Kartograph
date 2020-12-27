package AI.SeasonRaters.WaterField;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

public class Bewaesserungskanal extends SingleRater {

    public Bewaesserungskanal(float [] params){
        super(params);
        super.name="Bewässerungskanal";
    }

    public Bewaesserungskanal(){
        super();
        super.name="Bewässerungskanal";
    }

    @Override
    public int rateCurrent(Field f) {
        boolean b=false;
        int p=0;
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                if (f.getType(x, y)== TileType.WATER){
                    for (Tile t:f.getNeighbours(x,y)){
                        if (t.getType()==TileType.FIELD){
                            b=true;
                            break;
                        }
                    }
                }
                else if (f.getType(x, y)==TileType.FIELD){
                    for (Tile t:f.getNeighbours(x,y)){
                        if (t.getType()==TileType.FIELD){
                            b=true;
                            break;
                        }
                    }
                }
                if (b){
                    b=false;
                    p++;
                }
            }
        }
        return p;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        boolean b=false;
        float p=0;
        int c=0;
        for (int x=0;x<11;x++){
            for (int y=0;y<11;y++){
                if (f.getType(x, y)== TileType.WATER){
                    for (Tile t:f.getNeighbours(x,y)){
                        if (t.getType()==TileType.FIELD){
                            b=true;
                            break;
                        }
                        else if (t.getType()==TileType.EMPTY){
                            c+=f.probabilityToFill(x,y,s,(TileType tt)->tt==TileType.FIELD);
                        }
                    }
                }
                else if (f.getType(x, y)==TileType.FIELD){
                    for (Tile t:f.getNeighbours(x,y)){
                        if (t.getType()==TileType.WATER){
                            b=true;
                            break;
                        }
                        else if (t.getType()==TileType.EMPTY){
                            c+=f.probabilityToFill(x,y,s,(TileType tt)->tt==TileType.WATER);
                        }
                    }
                }
                if (b){
                    b=false;
                    p++;
                    c=0;
                }
                p+=c*0.25f*params[0]*timeRemaining;
                c=0;
            }
        }
        return p;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f};
    }
}
