package AI.SeasonRaters;

import Game.Field.Field;

public class NullRater extends SingleRater {
    @Override
    public int rateCurrent(Field f) {
        return 0;
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
