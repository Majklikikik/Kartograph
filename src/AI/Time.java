package AI;

import AI.SeasonRaters.Season;

public class Time {
    Season s;
    int progress;
    final static int RATING_SPRING=8;
    final static int RATING_SUMMER=16;
    final static int RATING_AUTUMN=23;
    final static int RATING_WINTER=29;

    public Time(){
        s=Season.SPRING;
        progress=0;
    }
    public void add(int i){
        progress +=i;
        if (s==Season.SPRING&&progress>=8){
            progress=0;
            s=Season.SUMMER;
        }
        if (s==Season.SUMMER&&progress>=8){
            progress=0;
            s=Season.AUTUMN;
        }
        if (s==Season.AUTUMN&&progress>=7){
            progress=0;
            s=Season.WINTER;
        }
        if (s==Season.WINTER&&progress>=6){
            progress=0;
            s=Season.SPRING;
        }
    }

    public int toInt(){
        int i=progress;
        if (s!=Season.SPRING){
            i+=8;
            if (s!=Season.SUMMER){
                i+=8;
                if (s!=Season.AUTUMN){
                    i+=7;

                }
            }
        }
        return i;
    }

    public float getTimeRemainingToSeasonEnd(Season s){
        switch (s){
            case SPRING: return getTimeRemaining(RATING_SPRING);
            case SUMMER: return getTimeRemaining(RATING_SUMMER);
            case AUTUMN: return getTimeRemaining(RATING_AUTUMN);
            default: return getTimeRemaining(RATING_WINTER);
        }
    }

    public Season getS(){
        return this.s;
    }

    public float getTimeRemainingToNextSeason(){
        return getTimeRemainingToSeasonEnd(s);
    }

    public float getTimeRemaining (int to){
        return Math.max(0f,(to-this.toInt())/29f);
    }

    public Time copy() {
        Time t=new Time();
        t.s=this.s;
        t.progress=this.progress;
        return t;
    }

    @Override
    public String toString() {
        return s+"  "+progress;
    }
}
