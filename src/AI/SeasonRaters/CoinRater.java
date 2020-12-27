package AI.SeasonRaters;

import Game.Field.Field;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

public class CoinRater extends SingleRater {
    @Override
    public int rateCurrent(Field f) {
        int c=0;
        boolean b;
        for (int i=0;i<f.Mountains.length;i++){
            b=true;
            for (Tile t:f.getNeighbours(f.Mountains[i].x,f.Mountains[i].y)){
                if (t.getType()== TileType.EMPTY) b=false;
            }
            if (b) c++;
        }
        return c;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        return 0;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[0];
    }
}
