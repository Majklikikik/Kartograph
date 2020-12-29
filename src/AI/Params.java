package AI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Params implements Serializable {
    private HashMap<Class<?>, float[]> p;
    public int points=0;
    public Params(){
        p=new HashMap<>();
    }
    public  Params(HashMap<Class<?>,float[]> pp){
        p=pp;
    }
    public float[] get(Class<?> c){
        return p.get(c);
    }

    public Set<Class<?>> Keys() {
        return p.keySet();
    }

    public Params copy() {
        HashMap<Class<?>, float[]> ret=new HashMap<>();
        for (Class<?> c :p.keySet()){
            float[] r=new float[p.get(c).length];
            for (int i=0;i<r.length;i++){
                r[i]=p.get(c)[i];
            }
            ret.put(c,r);
        }
        return new Params(ret);
    }
}
