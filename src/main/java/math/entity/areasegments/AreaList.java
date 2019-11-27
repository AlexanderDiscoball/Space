package math.entity.areasegments;

import math.entity.interval.Interval;
import math.entity.SegmentPack;

import java.io.Serializable;
import java.util.*;

public class AreaList implements Iterable<Interval> {

    private List<Interval> segmentsList;
    private int id;
    private Integer fullLength;

    public AreaList(int id){
        segmentsList = new ArrayList<>();
        this.id = id;
    }

    public void add(Interval interval) {
        segmentsList.add(interval);
    }

    public Interval getLastSegment() {
        return segmentsList.get(segmentsList.size()-1);
    }

    public Interval getFirstSegment() {
        return segmentsList.get(0);
    }

    public void addAll(SegmentPack segmentPack) {
        segmentsList.addAll(segmentPack.getCollection());
    }

    public int size() {
        return segmentsList.size();
    }

    public Interval get(int index) {
        return segmentsList.get(index);
    }

    public Collection<Interval> getCollection() {
        return segmentsList;
    }

    public Iterator<Interval> descendingIterator() {
        Collections.reverse(segmentsList);
        return segmentsList.iterator();
    }

    public void removeAll(SegmentPack segmentPack) {
        segmentsList.removeAll(segmentPack.getCollection());
    }
    public void remove(Interval interval) {
        segmentsList.remove(interval);
    }

    public void setFullLength() {
        int sum = 0;
        for (Interval interval :segmentsList) {
            sum += interval.getLength();
        }
        fullLength = sum;
    }

    public Integer getFullLength() {
        return fullLength;
    }

    public int compareTo(SegmentPack segmentPack) {
        Integer x1 = this.getFullLength();
        Integer x2 = segmentPack.getFullLength();
        int sComp = x1.compareTo(x2);
        if(sComp!=0){
            return sComp;
        }
        else {
            x1 = this.size();
            x2 = segmentPack.size();
            return x1.compareTo(x2);
        }
    }

    public Iterator<Interval> iterator() {
        return segmentsList.iterator();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAreaId() {
        return id;
    }

    @Override
    public String toString() {
        return ""+ segmentsList.toString();
    }
}
