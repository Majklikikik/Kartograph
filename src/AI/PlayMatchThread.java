package AI;

import Game.Match;

import java.util.LinkedList;
import java.util.Random;

public class PlayMatchThread implements Runnable {
    Params P;
    Random r;

    public PlayMatchThread(Params NP, Random R){
        P=NP;
        this.r=R;
    }
    @Override
    public void run() {
        for (int i=0;i<Evo.GAMESETS_PER_GENERATION;i++){
            LinkedList<Rater> raters=RaterGenerator.getFourRandomRaters(P,r);
            for (Rater rr:raters) {
                P.points+= Match.playMatch(rr,r,Evo.HARDMODE,Evo.SHOW,0,false,false);
            }
        }
    }
}
