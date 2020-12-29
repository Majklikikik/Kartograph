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
        return c+f.coinsFromCards;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        int c=0;
        int b;
        for (int i=0;i<f.Mountains.length;i++){
            b=0;
            for (Tile t:f.getNeighbours(f.Mountains[i].x,f.Mountains[i].y)){
                if (t.getType()== TileType.EMPTY) b++;
            }
            if (b==0) c++;
            else b+=params[b-1];
        }
        return c+f.coinsFromCards;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.5f,0.2f,0.1f,0.05f};
    }
}
