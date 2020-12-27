package AI.SeasonRaters.Forest;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.TileType;

public class Schildwald extends SingleRater {

    public Schildwald(float [] params){
        super(params);
        super.name="Schildwald";
    }

    public Schildwald(){
        super();
        super.name="Schildwald";
    }

    @Override
    public int rateCurrent(Field f) {
        int p=0;
        for (int i=0;i<10;i++){
            if (f.getType(p, 0)== TileType.FOREST) p++;
            if (f.getType(10-p, 10)== TileType.FOREST) p++;
            if (f.getType(10, p)== TileType.FOREST) p++;
            if (f.getType(0, 10-p)== TileType.FOREST) p++;
        }
        return p;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season ss) {
        int p=0;
        for (int i=0;i<10;i++){
            if (f.getType(i, 0)== TileType.FOREST) p++;
            if (f.getType(10-i, 10)== TileType.FOREST) p++;
            if (f.getType(10, i)== TileType.FOREST) p++;
            if (f.getType(0, 10-i)== TileType.FOREST) p++;

            if (f.getType(i, 0)== TileType.EMPTY) p+=timeRemaining*params[0]*f.probabilityToFill(i,0,ss,(TileType t)->t==TileType.FOREST);
            if (f.getType(10-i, 10)== TileType.EMPTY) p+=timeRemaining*params[0]*f.probabilityToFill(10-i,10,ss,(TileType t)->t==TileType.FOREST);
            if (f.getType(10, i)== TileType.EMPTY) p+=timeRemaining*params[0]*f.probabilityToFill(10,i,ss,(TileType t)->t==TileType.FOREST);
            if (f.getType(0, 10-i)== TileType.EMPTY) p+=timeRemaining*params[0]*f.probabilityToFill(0,10-i,ss,(TileType t)->t==TileType.FOREST);
        }
        return p;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f};
    }
}
