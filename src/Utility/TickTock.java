package Utility;

import java.util.Date;

public class TickTock {
    private long timePassed=0l;
    private Date from;

    public void tick(){
        from=new Date();
    }
    public void tock(){
        timePassed+=new Date().getTime()-from.getTime();
    }
    public void reset(){
        timePassed=0l;
    }
    public long getTotTime(){
        return timePassed;
    }

    @Override public String toString(){
        long ms=timePassed%1000;
        long s=timePassed/1000;
        long m=s/60;
        s=s%60;
        String ret="";
        if (m!=0) ret+= m + "m, ";
        if (s!=0) ret+= s + "s, ";
        ret+=ms + "ms.";
        return ret;
    }
}
