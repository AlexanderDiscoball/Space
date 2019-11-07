package math.entity;

import math.entity.interval.Interval;

import java.util.Collection;
import java.util.Iterator;

public interface SegmentPack extends Iterable<Interval>,Comparable<SegmentPack> {
    void add(Interval interval);
    Interval getLastSegment();
    Interval getFirstSegment();
    void addAll(SegmentPack segmentPack);
    int size();
    Interval get(int index);
    Collection<Interval> getCollection();
    Iterator<Interval> descendingIterator();
    void removeAll(SegmentPack segmentPack);
    void remove(Interval interval);
    void setFullLength();
    Integer getFullLength();
    int compareTo(SegmentPack segmentPack);
}
