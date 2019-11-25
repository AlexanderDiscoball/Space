package math.entity.areasegments;

import math.entity.interval.Interval;
import math.entity.SegmentPack;

import java.util.*;

public class AreaList implements Area {

    private List<Interval> segmentsList;
    private int id;
    Integer fullLength;
    private boolean added;

    public AreaList(int id){
        segmentsList = new ArrayList<>();
        this.id = id;
    }

    @Override
    public void add(Interval interval) {
        segmentsList.add(interval);
    }


    @Override
    public Interval getLastSegment() {
        return segmentsList.get(segmentsList.size()-1);
    }

    @Override
    public Interval getFirstSegment() {
        return segmentsList.get(0);
    }

    @Override
    public void addAll(SegmentPack segmentPack) {
        segmentsList.addAll(segmentPack.getCollection());
    }

    @Override
    public int size() {
        return segmentsList.size();
    }

    @Override
    public Interval get(int index) {
        return segmentsList.get(index);
    }

    @Override
    public Collection<Interval> getCollection() {
        return segmentsList;
    }

    @Override
    public Iterator<Interval> descendingIterator() {
        Collections.reverse(segmentsList);
        return segmentsList.iterator();
    }

    @Override
    public void removeAll(SegmentPack segmentPack) {
        segmentsList.removeAll(segmentPack.getCollection());
    }
    public void remove(Interval interval) {
        segmentsList.remove(interval);
    }

    @Override
    public void setFullLength() {
        int sum = 0;
        for (Interval interval :segmentsList) {
            sum += interval.getLength();
        }
        fullLength = sum;
    }

    @Override
    public Integer getFullLength() {
        return fullLength;
    }

    @Override
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

    @Override
    public Iterator<Interval> iterator() {
        return segmentsList.iterator();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getAreaId() {
        return id;
    }

    @Override
    public boolean getAdded() {
        return added;
    }

    @Override
    public void setAdded(boolean add) {
        added = add;
    }

    @Override
    public String toString() {
        return ""+ segmentsList.toString();
    }
}
