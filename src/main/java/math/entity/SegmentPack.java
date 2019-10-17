package math.entity;

import math.entity.LineSegments.Line;
import math.entity.Segment.Segment;

import java.util.Collection;
import java.util.Iterator;

public interface SegmentPack extends Iterable<Segment>,Comparable<SegmentPack> {
    void add(Segment segment);
    Segment getLastSegment();
    Segment getFirstSegment();
    void addAll(SegmentPack segmentPack);
    int size();
    Segment get(int index);
    Collection<Segment> getCollection();
    Iterator<Segment> descendingIterator();
    void removeAll(SegmentPack segmentPack);
    void remove(Segment segment);
    void setFullLength();
    Integer getFullLength();
    int compareTo(SegmentPack segmentPack);
}
