package AI.SeasonRaters;

import Game.Field.Field;

public abstract class SingleRater {
public String name;
    public float[] params;



    public SingleRater(){
        params=getDefaultParams();
    }
    public SingleRater(float[] par){
        params=par;
    }

    public int paramCount(){
        return getDefaultParams().length;
    }
    public abstract int rateCurrent(Field f);
    public abstract float rateExpected(Field f, float timeRemaining, Season s);
    public abstract float[] getDefaultParams();
}
