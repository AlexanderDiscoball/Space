package math.entity;

import java.util.Collection;

public interface StackSegments extends Iterable<Segment> {
    void add(Segment segment);
    Segment getLastSegment();
    Segment getFirstSegment();
    void addAll(StackSegments stackSegments);
    int size();
    Segment get(int index);
    Collection<Segment> getCollection();
    void removeAll(StackSegments stackSegments);
    void setFullLength();
    Integer getFullLength();
    int compareTo(StackSegments stackSegments);
}
