package math.entity.SimulationSegments;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Region implements StackSegments {

    private List<Segment> region;

    @Override
    public void add(Segment segment) {
        region.add(segment);
    }

    @Override
    public Segment getLastSegment() {
        return null;
    }

    @Override
    public Segment getFirstSegment() {
        return null;
    }

    @Override
    public void addAll(StackSegments stackSegments) {

    }

    @Override
    public int size() {
        return region.size();
    }

    @Override
    public Segment get(int index) {
        return null;
    }

    @Override
    public Collection<Segment> getCollection() {
        return null;
    }

    @Override
    public void removeAll(StackSegments stackSegments) {

    }

    @Override
    public void setFullLength() {

    }

    @Override
    public Integer getFullLength() {
        return null;
    }

    @Override
    public int compareTo(StackSegments stackSegments) {
        return 0;
    }

    @Override
    public Iterator<Segment> iterator() {
        return null;
    }
}
