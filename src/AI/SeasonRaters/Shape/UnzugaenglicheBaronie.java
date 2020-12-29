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
                for (int k=0;k<q_size;k++){
                    for (int l=0;l<q_size;l++){
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



        float [][]  ps=new float[11][11];
        for (int y=0;y<10;y++){
            for (int x=0;x<10;x++){
                if (f.getType(x,y)!=TileType.EMPTY) ps[y][x]=0;
                else ps[y][x]=1-f.probabilityToFill(x,y,s)+params[11];
            }
        }
        float squares[][][]= new float[11][][];
        squares[0]=ps;
        for (int size=2;size<=11;size++){
            squares[size-1]=new float[12-size][12-size];
            for (int y=0;y<12-size;y++){
                for (int x=0;x<12-size;x++){
                    squares[size-1][y][x]=squares[size-2][y][x];
                    for (int z=0;z<size;z++){
                        squares[size-1][y][x]+=ps[y+size-1][x+z]+ps[y+z][x+size-1];
                    }
                    squares[size-1][y][x]+=ps[y+size-1][x+size-1];
                }
            }
        }
        //float mins[]=new float[10];
        for (int i=0;i<10;i++){
            //mins[i]=Float.POSITIVE_INFINITY;
            float min=Float.POSITIVE_INFINITY;
            for (int y=0;y<10-i;y++){
                for (int x=0;x<10-i;x++){
                    if (squares[i][y][x]<min){//s[i]){
                        min/*s[i]*/=squares[i][y][x];
                    }
                }
            }
            p-=min*params[i];
        }

        return 3*max*params[1]*timeRemaining+p;
    }

    @Override
    public float[] getDefaultParams() {
        return new float[]{0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.1f,0.05f};
    }
}
