package AI;

import Game.Match;
import Utility.Utilities;
import sun.awt.image.ImageWatched;

import java.util.*;

import static Utility.Utilities.slp;


public class Evo {
    public final static float MUT_PROB_TOT=0.8f;
    public final static float MUT_PROB_SIN=0.4f;
    public final static float MUT_BASIS=2f;
    public final static float CROSS_PROB_TOT=0.5f;
    public final static float CROSS_PROB_SIN=0.3f;
    public final static int MULT_FACT=5;
    public final static boolean HARDMODE=false;
    public final static boolean SHOW=true;
    public final static int THREAD_COUNT=8;
    public final static int GEN_COUNT=3;
    public final static int POP_SIZE=10;
    public final static boolean PRINT_PROGRESS=true;


    public static void EvoPlay(String loadBestFrom, String saveBestTo){
        EvoPlay(ParamManager.readMultipleParams(loadBestFrom),saveBestTo);
    }
    public static void EvoPlay(String saveBestTo){
        Params[] begin=new Params[POP_SIZE];
        for (int i=0;i<POP_SIZE;i++){
            begin[i]=ParamManager.getRandomParams();
        }
        EvoPlay(begin,saveBestTo);
    }
    public static void EvoPlay(Params[] firstParams, String saveBestTo){
        EvoStats s=new EvoStats();
        for (int i=0;i<GEN_COUNT;i++){
            if (PRINT_PROGRESS){
                System.out.println("Generation "+(i+1)+"/"+GEN_COUNT);
            }
            firstParams=nextGen(firstParams,s);
        }
        s.printStats();
        ParamManager.saveMultipleParams(saveBestTo,firstParams);
    }

    public static Params[] nextGen(Params[] lastGen, EvoStats stat){
        Params[] next=ParamManager.multiply(lastGen,MULT_FACT,MUT_PROB_TOT,MUT_PROB_SIN,MUT_BASIS,CROSS_PROB_TOT,CROSS_PROB_SIN);
        Long randSeed=new Random().nextLong();
        Queue<Thread> threads=new LinkedList<>();
int c=0;

        for (Params p:next){
            while (threads.size()>=THREAD_COUNT){
                if (!threads.peek().isAlive()){
                    threads.remove();
                    System.out.println(++c+"/"+lastGen.length*MULT_FACT);
                    break;
                }
                slp(50);
            }
            Thread t=new Thread(new PlayMatchThread(p,new Random(randSeed)),"Carl");
            t.start();
            threads.add(t);
        }
        while (threads.size()>0){
            if (!threads.peek().isAlive()){
                threads.remove();
                System.out.println(++c+"/"+lastGen.length*MULT_FACT);
                break;
            }
            slp(50);
        }

        Arrays.sort(next,new ParamComparator());
        Params[] ret=new Params[lastGen.length];
        for (int i=0;i<ret.length;i++){
            ret[i]=next[i];
        }

        calcEvoStats(next, ret, stat);
        System.out.println("Generation finished!");
        return ret;
    }

    private static void calcEvoStats(Params[] next, Params[] ret, EvoStats stat) {
        float avgBest=0;
        float avgAll=0;
        float devBest=0;
        float devAll=0;
        avgBest= Utilities.average(ret);
        avgAll=Utilities.average(next);
        devBest=Utilities.deviation(ret,avgBest);
        devAll=Utilities.deviation(next,avgAll);
        stat.add(avgAll,devAll,avgBest,devBest);
    }


}
