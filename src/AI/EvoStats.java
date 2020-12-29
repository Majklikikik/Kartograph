package AI;

import java.util.LinkedList;

public class EvoStats {
    LinkedList<Float> avgAll=new LinkedList<>();
    LinkedList<Float> devAll=new LinkedList<>();
    LinkedList<Float> avgBest=new LinkedList<>();
    LinkedList<Float> devBest=new LinkedList<>();
    public void add(float avgAll,float devAll, float avgBest, float devBest){
        this.avgAll.add(avgAll);
        this.devAll.add(devAll);
        this.avgBest.add(avgBest);
        this.devBest.add(devBest);
    }

    public void printStats(){
        System.out.println("Average Points of all Raters: ");
        for (Float f:avgAll) System.out.println(f);
        System.out.println("Deviation of Points of all Raters: ");
        for (Float f:devAll) System.out.println(f);
        System.out.println("Average Points of best Raters: ");
        for (Float f:avgBest) System.out.println(f);
        System.out.println("Deviation of Points of best Raters: ");
        for (Float f:devBest) System.out.println(f);
    }
}
