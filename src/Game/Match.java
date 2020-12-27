package Game;

import AI.ParamManager;
import AI.Rater;
import AI.RaterGenerator;
import AI.SeasonRaters.Season;
import AI.Time;
import GUI.FieldWindow;
import GUI.GameStats;
import Game.Field.Field;

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

        System.out.println(playMatch(r.pop(),rand, false, false, 0,false));
        //testTime(r.pop(),rand,false,true,0,false, 2, true);


        //System.out.println(playMatch(r.pop(),rand, false, true, 0,true));
        //System.out.println(playMatch(r.pop(),rand, false, true, 0,true));
        //System.out.println(playMatch(r.pop(),rand, false, true, 0,true));

    }

    private static void testTime(Rater rate, Random rand, boolean hardMode, boolean show, int msBetweenCards, boolean waitForEnter, int cardCount, boolean resetBetweenCards) {
        Field f_old;
        Field f_new;
        GameStats game=new GameStats();
        game.wait=waitForEnter;
        game.notRunning=true;
        game.t=new Time();

        System.out.println("Raters: \n"
                +rate.getRatersString());
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
            System.out.println("Next Card!");
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

            System.out.println("Finding");
            f_new=f_old.getAndUseBest(game.getCard(),rate);
            if (f_new.t.getS()!=f_old.t.getS()){
                game.Points+=rate.rate(f_new,f_old.t.getS());
                f_new.resetCards(rand);
                System.out.println(f_old.t.getS()+" : "+game.Points+" points in total");
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

    public static int playMatch(Rater rate, Random rand, boolean hardMode, boolean show, int msBetweenCards, boolean waitForEnter){
        Field f_old;
        Field f_new;
        GameStats game=new GameStats();
        game.wait=waitForEnter;
        game.notRunning=true;
        game.t=new Time();

        System.out.println("Raters: \n"
                +rate.getRatersString());
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
                System.out.println("RUIN");
            }
            System.out.println("Next Card!");
            System.out.println(f_old.t);
            System.out.println(game.getCard());
            if (show) f_old.GUI.setCard(game.getCard());

            if (waitForEnter){
                //new Scanner(System.in).nextLine();
                while (game.notRunning){
                    slp(1);
                }
                game.notRunning=true;
            }

            System.out.println("Finding");
            f_new=f_old.getAndUseBest(game.getCard(),rate);
            if (f_new.t.getS()!=f_old.t.getS()){
                game.Points+=rate.rate(f_new,f_old.t.getS());
                f_new.resetCards(rand);
                System.out.println(f_old.t.getS()+" : "+game.Points+" points in total");
                if (f_new.t.getS()== Season.SPRING){
                    if (waitForEnter){
                        while (game.notRunning){
                            slp(1);
                        }
                        game.notRunning=true;
                    }
                    return game.Points;
                }
            }
            f_old=f_new;
            game.t=f_old.t;
            game.expectedPoints=f_old.pointsExpected;
            if (show) f_old.GUI.actualizeGameStats();
            //System.out.println("Value right now in Season "+f_old.t.getS()+"  is "+rate.rate(f_old,f_old.t.getS()));
        }
    }
}
