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

import java.util.HashMap;

public class ParamManager {

    public static HashMap<Class<?>,float[]> getDefaultParams(){
        HashMap<Class<?>,float[]> ret=new HashMap<>();
        ret.put(BastionenInDerWildnis.class,new BastionenInDerWildnis().getDefaultParams());
        ret.put(Metropole.class,new Metropole().getDefaultParams());
        ret.put(SchildDesReiches.class,new SchildDesReiches().getDefaultParams());
        ret.put(SchillerndeEbene.class,new SchillerndeEbene().getDefaultParams());

        ret.put(Duesterwald.class,new Duesterwald().getDefaultParams());
        ret.put(Gruenflaeche.class,new Gruenflaeche().getDefaultParams());
        ret.put(Pfad_des_Waldes.class,new Pfad_des_Waldes().getDefaultParams());
        ret.put(Schildwald.class,new Schildwald().getDefaultParams());

        ret.put(DieKessel.class,new DieKessel().getDefaultParams());
        ret.put(DieLangeStrasse.class,new DieLangeStrasse().getDefaultParams());
        ret.put(Grenzland.class,new Grenzland().getDefaultParams());
        ret.put(UnzugaenglicheBaronie.class,new UnzugaenglicheBaronie().getDefaultParams());

        ret.put(AusgedehnteStraende.class,new AusgedehnteStraende().getDefaultParams());
        ret.put(Bewaesserungskanal.class,new Bewaesserungskanal().getDefaultParams());
        ret.put(GoldenerKornspeicher.class,new GoldenerKornspeicher().getDefaultParams());
        ret.put(TalDerMagier.class,new TalDerMagier().getDefaultParams());
        return ret;
    }

}
