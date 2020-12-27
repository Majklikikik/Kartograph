package AI;

import AI.SeasonRaters.Season;
import AI.SeasonRaters.SingleRater;
import Game.Card.Card;
import Game.Card.CardGenerator;
import Game.Card.Possibility;
import Game.Field.Field;
import Utility.TickTock;

public class Rater {
    SingleRater A;
    SingleRater B;
    SingleRater C;
    SingleRater D;
    TickTock tA;
    TickTock tB;
    TickTock tC;
    TickTock tD;

    public Rater(SingleRater A, SingleRater B, SingleRater C, SingleRater D){
        this.A=A;
        this.B=B;
        this.C=C;
        this.D=D;
    }

    public void setTimers(){
        tA=new TickTock();
        tB=new TickTock();
        tC=new TickTock();
        tD=new TickTock();
    }

    public int rate(Field f,Season s){
        switch (s){
            case SPRING: return A.rateCurrent(f)+B.rateCurrent(f);
            case SUMMER: return B.rateCurrent(f)+C.rateCurrent(f);
            case AUTUMN: return C.rateCurrent(f)+D.rateCurrent(f);
            case WINTER: return D.rateCurrent(f)+A.rateCurrent(f);
        }
        return 0;
    }

    public Possibility bestPossibility(Field f, Card c){
        float r=Float.NEGATIVE_INFINITY;
        Possibility p=null;
        if (f.getAllPossibleActions(c).size()==0){
            c.onRuin=false;
        }
        if (f.getAllPossibleActions(c).size()==0){
            c= CardGenerator.Splitterland();
        }
        for (Possibility p1: f.getAllPossibleActions(c)){
            Field fut=f.usePossibility(p1);
            float tmp=rateExpected(fut);
            if (tmp>r){
                r=tmp;
                p=p1;
            }
        }
        if (p==null){
            throw new NullPointerException("PENIS");
        }
        f.pointsExpected=r;
        return p;
    }

    public float rateExpected(Field f){
        return rateExpected(f,f.t);
    }

    public float rateExpected(Field f, Time currentTime){
        float points=0;
        float r1=currentTime.getTimeRemainingToSeasonEnd(Season.SPRING);
        float r2=currentTime.getTimeRemainingToSeasonEnd(Season.SUMMER);
        float r3=currentTime.getTimeRemainingToSeasonEnd(Season.AUTUMN);
        float r4=currentTime.getTimeRemainingToSeasonEnd(Season.WINTER);
        if (r1!=0f){
            if (tA!=null) tA.tick();
            points+=A.rateExpected(f,r1,Season.SPRING);
            if (tA!=null) tA.tock();
            if (tA!=null) tB.tick();
            points+=B.rateExpected(f,r1,Season.SPRING);
            if (tA!=null) tB.tock();
        }
        if (r2!=0f){
            if (tA!=null) tB.tick();
            points+=B.rateExpected(f,r1,Season.SUMMER);
            if (tA!=null) tB.tock();
            if (tA!=null) tC.tick();
            points+=C.rateExpected(f,r1,Season.SUMMER);
            if (tA!=null) tC.tock();
        }
        if (r3!=0f){
            if (tA!=null) tC.tick();
            points+=C.rateExpected(f,r1,Season.AUTUMN);
            if (tA!=null) tC.tock();
            if (tA!=null) tD.tick();
            points+=D.rateExpected(f,r1,Season.AUTUMN);
            if (tA!=null) tD.tock();
        }
        if (r4!=0f){
            if (tA!=null) tD.tick();
            points+=D.rateExpected(f,r1,Season.WINTER);
            if (tA!=null) tD.tock();
            if (tA!=null) tA.tick();
            points+=A.rateExpected(f,r1,Season.WINTER);
            if (tA!=null) tA.tock();
        }
        if (Float.isNaN(points)){
            throw new NullPointerException("NANANANANANANANA BatmNAN");
        }
        return points;
    }

    public void resetTime(){
        if (tA==null) return;
        tA.reset();
        tB.reset();
        tC.reset();
        tD.reset();
    }

    public void printUsedTime(){
        System.out.println("A: "+A.name+"  : "+tA);
        System.out.println("B: "+B.name+"  : "+tB);
        System.out.println("C: "+C.name+"  : "+tC);
        System.out.println("D: "+D.name+"  : "+tD);
    }

    public String getRatersString() {
        return A.name+"\n"+B.name+"\n"+C.name+"\n"+D.name;
    }
}
