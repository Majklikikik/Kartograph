package AI.SeasonRaters;

import Game.Field.Field;
import Game.Field.Tile.TileType;

public class PlacabilityRater extends SingleRater {
    public PlacabilityRater() {
        super.name = "Placability";
    }

    public PlacabilityRater(float[] params) {
        this.params = params;
        super.name = "Placability";
    }

    @Override
    public int rateCurrent(Field f) {
        return 0;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        float ret = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (f.getType(x, y) == TileType.EMPTY) {
                    ret += 1 - f.probabilityToFill(x, y, s)+params[1];
                }
            }
        }
        return -ret * params[0];
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f,0.1f};
    }
}
