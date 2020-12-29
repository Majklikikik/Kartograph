package Game;

import AI.*;
import AI.SeasonRaters.Season;
import AI.SeasonRaters.Shape.UnzugaenglicheBaronie;
import GUI.FieldWindow;
import GUI.GameStats;
import Game.Field.Field;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import static Utility.Utilities.*;


public class Match {

    public static void main(String[] args) {
        /*Field f=Field.standardField();
        f.makeGuiWindow();
        slp(500);
        f.set(0, 0, TileType.FOREST);
        slp(500);
        Card c=CardGenerator.Baumwipfeldorf();

        Rater r= new Rater(new Gruenflaeche(), new NullRater(), new NullRater(), new NullRater());
        //Field f2=f.deepCopy();
        LinkedList<Card> cards= CardGenerator.generateAndShuffleCards();
        //f.replaceInGUI(f2);
        slp(500);
        for (int i=0;i<20;i++){
            //slp(500);
            c=cards.pop();
            if (c.ruinCard) continue;
            f=f.getAndUseBest(c,r);
        }
        Random rand= new Random();*/
        Random rand=new Random(2345654123456l);
        LinkedList<Rater> r=RaterGenerator.getFourRandomRaters(ParamManager.getDefaultParams(),rand);
        //System.out.println(playMatch(r.pop(),rand, false, true, 0,false,false));
        //testTime(r.pop(),rand,false,true,0,false, 2, true);


        //System.out.println(playMatch(r.pop(),rand, false, true, 0,false,false));
        //System.out.println(playMatch(r.pop(),rand, false, true, 0,false,false));
        //System.out.println(playMatch(r.pop(),rand, false, true, 0,false,false));


        Date t= new Date();
        int [] pts=new int[50];
        /*for (int i=0;i<50;i++) pts[i]=playMatch(RaterGenerator.getFourRandomRaters(ParamManager.getDefaultParams(),new Random()).pop(),new Random(),false,false,0,false,false);
        System.out.println(new Date().getTime()-t.getTime());
        float avg=0, dev=0;
        for (int i=0;i<50;i++){
            avg+=pts[i]/50f;
        }
        for (int i=0;i<50;i++){
            dev+=(avg-pts[i])*(avg-pts[i])/50.0f;
        }
        dev=(float)Math.sqrt(dev);
        System.out.println(avg+" : "+dev);
        */

        //playRandomMatch(false,true,false,true);

        /*Params p=ParamManager.getDefaultParams();
        ParamManager.saveParams("Params.params",p);
        Params p2=ParamManager.readParams("Params.params");
        for (float f:p2.get(UnzugaenglicheBaronie.class)){
            System.out.println(f);
        }*/

        Evo.EvoPlay("Best.params",true);
    }

    private static void playRandomMatch(boolean hard, boolean show, boolean waitAlways, boolean waitRating){
        playMatch(RaterGenerator.getFourRandomRaters(ParamManager.getDefaultParams(),new Random()).pop(),new Random(),hard,show,0,waitAlways,waitRating);
    }

    private static void testTime(Rater rate, Random rand, boolean hardMode, boolean show, int msBetweenCards, boolean waitForEnter, int cardCount, boolean resetBetweenCards) {
        Field f_old;
        Field f_new;
        GameStats game=new GameStats();
        game.wait=waitForEnter;
        game.notRunning=true;
        game.t=new Time();

        System.out.println("Raters: \n"+rate.getRatersString());
        if (hardMode) f_old=Field.hardmodeField();
        else f_old=Field.standardField();
        f_old.resetCards(rand);

        FieldWindow fw=null;

        if (show) {
            f_old.makeGuiWindow(game);
            fw=f_old.getFw();
        }
        int cc=0;
        rate.setTimers();
        while(true&&cc<cardCount){
            cc++;
            if (resetBetweenCards){
                rate.resetTime();
            }
            rate.printUsedTime();
            slp(msBetweenCards);
            game.setCard(f_old.popCard());
            while (game.getCard().ruinCard){
                game.setCard(f_old.popCard());
                game.getCard().onRuin=true;
                System.out.println("RUIN");
            }
            //System.out.println("Next Card!");
            System.out.println(f_old.t);
            System.out.println(game.getCard());
            f_old.GUI.setCard(game.getCard());

            if (waitForEnter){
                //new Scanner(System.in).nextLine();
                while (game.notRunning){
                    slp(1);
                }
                game.notRunning=true;
            }
            f_new=f_old.getAndUseBest(game.getCard(),rate);
            if (f_new.t.getS()!=f_old.t.getS()){
                game.Points+=rate.rate(f_new,f_old.t.getS());
                f_new.resetCards(rand);
                //System.out.println(f_old.t.getS()+" : "+game.Points+" points in total");
                if (f_new.t.getS()== Season.SPRING){
                    if (waitForEnter){
                        while (game.notRunning){
                            slp(1);
                        }
                        game.notRunning=true;
                    }
                    return;// game.Points;
                }
            }
            f_old=f_new;
            game.t=f_old.t;
            game.expectedPoints=f_old.pointsExpected;
            f_old.GUI.actualizeGameStats();
            System.out.println("Value right now in Season "+f_old.t.getS()+"  is "+rate.rate(f_old,f_old.t.getS()));
        }
    }

    public static int playMatch(Rater rate, Random rand, boolean hardMode, boolean show, int msBetweenCards, boolean waitForEnter, boolean waitWhenRating){
        Field f_old;
        Field f_new;
        GameStats game=new GameStats();
        game.wait=waitForEnter;
        game.notRunning=true;
        game.t=new Time();

        //System.out.println("Raters: \n" + rate.getRatersString());
        if (hardMode) f_old=Field.hardmodeField();
        else f_old=Field.standardField();
        f_old.resetCards(rand);

        FieldWindow fw=null;

        if (show) {
            f_old.makeGuiWindow(game);
            fw=f_old.getFw();
        }
        while(true){
            slp(msBetweenCards);
            game.setCard(f_old.popCard());
            while (game.getCard().ruinCard){
                game.setCard(f_old.popCard());
                game.getCard().onRuin=true;
                //System.out.println("RUIN");
            }
            //System.out.println("Next Card!");
            //System.out.println(f_old.t);
            //System.out.println(game.getCard());
            if (show) f_old.GUI.setCard(game.getCard());

            if (waitForEnter){
                //new Scanner(System.in).nextLine();
                while (game.notRunning){
                    slp(1);
                }
                game.notRunning=true;
            }
            f_new=f_old.getAndUseBest(game.getCard(),rate);
            if (f_new.t.getS()!=f_old.t.getS()){
                game.Points+=rate.rate(f_new,f_old.t.getS());
                f_new.resetCards(rand);
                game.notRunning=true;
                if (show&&waitWhenRating) {
                    f_new.GUI.actualizeGameStats();

                    while (game.notRunning){
                        slp(1);
                    }
                }
                //System.out.println(f_old.t.getS()+" : "+game.Points+" points in total");
                if (f_new.t.getS()== Season.SPRING){
                    if (waitForEnter){
                        while (game.notRunning){
                            slp(1);
                        }
                        game.notRunning=true;
                    }
                    if (show) f_old.GUI.close();
                    return game.Points;
                }
            }
            f_old=f_new;
            game.t=f_old.t;
            game.coin=f_old.coinsFromCards;
            game.expectedPoints=f_old.pointsExpected;
            if (show) f_old.GUI.actualizeGameStats();
            //System.out.println("Value right now in Season "+f_old.t.getS()+"  is "+rate.rate(f_old,f_old.t.getS()));
        }
    }
}
