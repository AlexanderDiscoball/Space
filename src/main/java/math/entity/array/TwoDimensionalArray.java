package math.entity.array;

import math.entity.SegmentPack;

import java.util.Collection;
import java.util.List;

public interface TwoDimensionalArray extends Iterable<SegmentPack> {
    void add(SegmentPack segmentPack);
    int size();
    SegmentPack get();
    SegmentPack get(int index);
    Collection<SegmentPack> getCollection();
    TwoDimensionalArray clone();
    List<Integer> setAreasId();
    void remove(int index);
    void remove(SegmentPack segmentPack);
}
