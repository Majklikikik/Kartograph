package AI;

import AI.SeasonRaters.City.BastionenInDerWildnis;
import AI.SeasonRaters.City.Metropole;
import AI.SeasonRaters.City.SchildDesReiches;
import AI.SeasonRaters.City.SchillerndeEbene;
import AI.SeasonRaters.Forest.Duesterwald;
import AI.SeasonRaters.Forest.Gruenflaeche;
import AI.SeasonRaters.Forest.Pfad_des_Waldes;
import AI.SeasonRaters.Forest.Schildwald;
import AI.SeasonRaters.Shape.DieKessel;
import AI.SeasonRaters.Shape.DieLangeStrasse;
import AI.SeasonRaters.Shape.Grenzland;
import AI.SeasonRaters.Shape.UnzugaenglicheBaronie;
import AI.SeasonRaters.SingleRater;
import AI.SeasonRaters.WaterField.AusgedehnteStraende;
import AI.SeasonRaters.WaterField.Bewaesserungskanal;
import AI.SeasonRaters.WaterField.GoldenerKornspeicher;
import AI.SeasonRaters.WaterField.TalDerMagier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class RaterGenerator {
    public static LinkedList<Rater>  getFourRandomRaters(Params params, Random rand){
        float r= rand.nextFloat();
        LinkedList<SingleRater> l=new LinkedList<>();

        LinkedList<SingleRater> city=new LinkedList<>();
        LinkedList<SingleRater> forest=new LinkedList<>();
        LinkedList<SingleRater> shape=new LinkedList<>();
        LinkedList<SingleRater> waterfield=new LinkedList<>();

        city.add(new BastionenInDerWildnis(params.get(BastionenInDerWildnis.class)));
        city.add(new Metropole(params.get(Metropole.class)));
        city.add(new SchildDesReiches(params.get(SchildDesReiches.class)));
        city.add(new SchillerndeEbene(params.get(SchillerndeEbene.class)));

        forest.add(new Duesterwald(params.get(Duesterwald.class)));
        forest.add(new Gruenflaeche(params.get(Gruenflaeche.class)));
        forest.add(new Pfad_des_Waldes(params.get(Pfad_des_Waldes.class)));
        forest.add(new Schildwald(params.get(Schildwald.class)));

        shape.add(new DieKessel(params.get(DieKessel.class)));
        shape.add(new DieLangeStrasse(params.get(DieLangeStrasse.class)));
        shape.add(new Grenzland(params.get(Grenzland.class)));
        shape.add(new UnzugaenglicheBaronie(params.get(UnzugaenglicheBaronie.class)));

        waterfield.add(new AusgedehnteStraende(params.get(AusgedehnteStraende.class)));
        waterfield.add(new Bewaesserungskanal(params.get(Bewaesserungskanal.class)));
        waterfield.add(new GoldenerKornspeicher(params.get(GoldenerKornspeicher.class)));
        waterfield.add(new TalDerMagier(params.get(TalDerMagier.class)));

        Collections.shuffle(city,rand);
        Collections.shuffle(forest,rand);
        Collections.shuffle(shape,rand);
        Collections.shuffle(waterfield,rand);

        LinkedList<Rater> ret=new LinkedList<>();
        LinkedList<SingleRater> sr;

        for (int i=0;i<4;i++) {
            sr = new LinkedList<>();
            sr.add(city.pop());
            sr.add(forest.pop());
            sr.add(shape.pop());
            sr.add(waterfield.pop());
            Collections.shuffle(sr,rand);
            ret.add(new Rater(sr.pop(),sr.pop(),sr.pop(),sr.pop()));
        }
        return ret;
    }
}
