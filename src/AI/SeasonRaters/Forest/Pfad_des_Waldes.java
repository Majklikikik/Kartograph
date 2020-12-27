package AI.SeasonRaters.Forest;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Field.Field;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

public class Pfad_des_Waldes extends SingleRater {

    public Pfad_des_Waldes(float [] params){
        super(params);
        super.name="Pfad des Waldes";
    }

    public Pfad_des_Waldes(){
        super();
        super.name="Pfad des Waldes";
    }

    @Override
    public int rateCurrent(Field f) {
        int p=0;
        boolean [] b={false,false,false,false,false};
        for (int i=0;i<5;i++){
            for (Tile t: f.getNeighbours(f.Mountains[i].x,f.Mountains[i].y)){
                if (t.getType()== TileType.FOREST){
                    for (int j=i+1;j<5;j++){
                        for (Tile s:f.getNeighbours(f.Mountains[j].x,f.Mountains[j].y)){
                            if (s.getType()==TileType.FOREST&&s.getArea()==t.getArea()){
                                b[i]=true;
                                b[j]=true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i=0;i<5;i++){
            if (b[i]) p+=3;
        }
        return p;
    }

    @Override
    public float rateExpected(Field f, float timeRemaining, Season ss) {
        return rateCurrent(f);
    }

    @Override
    public float[] getDefaultParams() {
        return new float[0];
    }
}
