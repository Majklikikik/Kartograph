package AI;

import java.util.Comparator;

public class ParamComparator
        implements Comparator<Params> {
    @Override
    public int compare(Params o1, Params o2) {
        return -Integer.compare(o1.points,o2.points);
    }
}
