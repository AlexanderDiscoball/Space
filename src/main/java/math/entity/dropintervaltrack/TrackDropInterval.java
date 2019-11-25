package math.entity.dropintervaltrack;

import math.entity.interval.DropInterval;
import math.entity.interval.Interval;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TrackDropInterval implements Iterable<DropInterval> {
    private List<DropInterval> dropIntervals;

    public TrackDropInterval(){
        dropIntervals = new ArrayList<>();
    }

    public void add(DropInterval interval){
        dropIntervals.add(interval);
    }
    public void add(int index, DropInterval interval){
        dropIntervals.add(index, interval);
    }
    public DropInterval get(int index){
        return dropIntervals.get(index);
    }
    public Iterator<DropInterval> iterator(){
        return dropIntervals.iterator();
    }

    @Override
    public String toString() {
        return dropIntervals.toString();
    }
}
