package AI.SeasonRaters.Shape;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.TileType;

public class UnzugaenglicheBaronie extends SingleRater {

    public UnzugaenglicheBaronie(float [] params){
        super(params);
        super.name="Unzugängliche Baronie";
    }

    public UnzugaenglicheBaronie(){
        super();
        super.name="Unzugängliche Baronie";
    }

    @Override
    public int rateCurrent(Field f) {
        int max=0;
        for (int y=0;y<11;y++){
            for (int x=0;x<11;x++){
                int q_size=Math.min(10-y,10-x);
                for (int k=0;k<=q_size;k++){
                    for (int l=0;l<=q_size;l++){
                        if (f.getType(x+k,y+l)== TileType.EMPTY){
                            q_size=Math.max(k,l)-1;
                            continue;
                        }
                    }
                }
                if (q_size>max){
                    max=q_size;
                }
            }
        }
        return max*3;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season s) {
        int max=rateCurrent(f)/3;
        float p=0;
        for (int i=max+1;i<11;i++){
            p+=mostProbableSquareOfSize(i,f,s);
        }
        return 3*max*params[1]*timeRemaining+p;
    }

    public float mostProbableSquareOfSize(int i, Field f, Season s){
        //min= sum of probs not to fill
        int c;
        float min=0;
            for (int y=0;y<12-i;y++){
                for (int x=0;x<12-i;x++){
                    c=0;
                    float p=0;
                    for (int k=0;k<i;k++){
                        for (int l=0;l<i;l++){
                            if (f.getType(x+k,y+l)== TileType.EMPTY){
                                c++;
                                p+=1-f.probabilityToFill(x+k,y+l,s)+params[0];
                            }
                        }
                    }
                    if (p+c*params[1]<min){
                        min=p+c*params[1];
                    }
                }
            }
            return -min;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f,0.1f};
    }
}
