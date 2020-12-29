package AI;

import AI.SeasonRaters.City.BastionenInDerWildnis;
import AI.SeasonRaters.City.Metropole;
import AI.SeasonRaters.City.SchildDesReiches;
import AI.SeasonRaters.City.SchillerndeEbene;
import AI.SeasonRaters.CoinRater;
import AI.SeasonRaters.Forest.Duesterwald;
import AI.SeasonRaters.Forest.Gruenflaeche;
import AI.SeasonRaters.Forest.Pfad_des_Waldes;
import AI.SeasonRaters.Forest.Schildwald;
import AI.SeasonRaters.PlacabilityRater;
import AI.SeasonRaters.Shape.DieKessel;
import AI.SeasonRaters.Shape.DieLangeStrasse;
import AI.SeasonRaters.Shape.Grenzland;
import AI.SeasonRaters.Shape.UnzugaenglicheBaronie;
import AI.SeasonRaters.WaterField.AusgedehnteStraende;
import AI.SeasonRaters.WaterField.Bewaesserungskanal;
import AI.SeasonRaters.WaterField.GoldenerKornspeicher;
import AI.SeasonRaters.WaterField.TalDerMagier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ParamManager {

    public static Params getRandomParams(){
        Params ret=getDefaultParams();
        for (Class<?>c:ret.Keys()){
            for (int i=0;i<ret.get(c).length;i++){
                ret.get(c)[i]=(float)Math.random();
            }
        }
        return ret;
    }

    public static Params getDefaultParams() {
        HashMap<Class<?>, float[]> ret = new HashMap<>();
        ret.put(BastionenInDerWildnis.class, new BastionenInDerWildnis().getDefaultParams());
        ret.put(Metropole.class, new Metropole().getDefaultParams());
        ret.put(SchildDesReiches.class, new SchildDesReiches().getDefaultParams());
        ret.put(SchillerndeEbene.class, new SchillerndeEbene().getDefaultParams());

        ret.put(Duesterwald.class, new Duesterwald().getDefaultParams());
        ret.put(Gruenflaeche.class, new Gruenflaeche().getDefaultParams());
        ret.put(Pfad_des_Waldes.class, new Pfad_des_Waldes().getDefaultParams());
        ret.put(Schildwald.class, new Schildwald().getDefaultParams());

        ret.put(DieKessel.class, new DieKessel().getDefaultParams());
        ret.put(DieLangeStrasse.class, new DieLangeStrasse().getDefaultParams());
        ret.put(Grenzland.class, new Grenzland().getDefaultParams());
        ret.put(UnzugaenglicheBaronie.class, new UnzugaenglicheBaronie().getDefaultParams());

        ret.put(AusgedehnteStraende.class, new AusgedehnteStraende().getDefaultParams());
        ret.put(Bewaesserungskanal.class, new Bewaesserungskanal().getDefaultParams());
        ret.put(GoldenerKornspeicher.class, new GoldenerKornspeicher().getDefaultParams());
        ret.put(TalDerMagier.class, new TalDerMagier().getDefaultParams());

        ret.put(PlacabilityRater.class, new PlacabilityRater().getDefaultParams());
        ret.put(CoinRater.class, new CoinRater().getDefaultParams());
        return new Params(ret);
    }

    public static void saveMultipleParams(String fileName, Params [] p){
        try {
            FileOutputStream out = new FileOutputStream(new File(fileName));
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(p);
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveParams(String fileName, Params p) {
        try {
            FileOutputStream out = new FileOutputStream(new File(fileName));
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(p);
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Params readParams(String fileName) {
        Params p = null;
        try {
            FileInputStream in = new FileInputStream(new File(fileName));
            ObjectInputStream i = new ObjectInputStream(in);
            p = (Params) i.readObject();
            i.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static Params[] readMultipleParams(String fileName) {
        Params [] p = null;
        try {
            FileInputStream in = new FileInputStream(new File(fileName));
            ObjectInputStream i = new ObjectInputStream(in);
            p = (Params[]) i.readObject();
            i.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void mutate(Params p, float mutProbTot, float mutProbSin, float bas) {
        if (Math.random() > mutProbTot)
            return;
        for (Class<?> c: p.Keys()){
            for (int i=0;i<p.get(c).length;i++){
                if (Math.random()<mutProbSin){
                    p.get(c)[i]*=Math.pow(bas,Math.random()*2-1);
                }
            }
        }
    }


    public static Params[] cross(Params l, Params r, float crossProb1, float crossProb2){
        Params [] ret=new Params[]{l.copy(),r.copy()};
        if (Math.random()>crossProb1){
            return ret;
        }
        for (Class<?> c: l.Keys()){
            for (int i=0;i<l.get(c).length;i++){
                if (Math.random()<crossProb2){
                    float tmp= ret[0].get(c)[i];
                    ret[0].get(c)[i]=ret[1].get(c)[i];
                    ret[1].get(c)[i]=tmp;
                }
            }
        }
        return ret;
    }

    public static Params[] multiply(Params[] parents, int factor, float mutProb1, float mutProb2, float mutBas, float crossProb1, float crossProb2){
        Params[] ret= new Params[parents.length*factor];
        for (int i=0;i<parents.length;i++){
            ret[i]=parents[i].copy();
        }
        ArrayList<Integer> indices=new ArrayList<Integer>(parents.length);
        for (int i=0;i<parents.length;i++){
            indices.add(i,i);
        }
        Params[] tmp;
        for (int i=1;i<factor;i++){
            Collections.shuffle(indices);
            for (int j=0;j<parents.length;j+=2){
                tmp=cross(parents[indices.get(j)],parents[indices.get(j+1)],crossProb1,crossProb2);
                ret[i*parents.length+j]=tmp[0];
                ret[i*parents.length+j+1]=tmp[1];
                mutate(tmp[0],mutProb1, mutProb2, mutBas);
                mutate(tmp[1],mutProb1, mutProb2, mutBas);
            }
        }
        return ret;
    }

}
