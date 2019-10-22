package math.entity.AreaSegments;

import math.entity.LineSegments.Line;
import math.entity.Segment.Segment;
import math.entity.SegmentPack;

import java.util.*;

public class AreaList implements Area {

    private List<Segment> segmentsList;
    private int id;
    Integer fullLength;

    public AreaList(int id){
        segmentsList = new LinkedList<>();
        this.id = id;
    }

    @Override
    public void add(Segment segment) {
        segmentsList.add(segment);
    }

    @Override
    public Segment getLastSegment() {
        return segmentsList.get(segmentsList.size()-1);
    }

    @Override
    public Segment getFirstSegment() {
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
    public Segment get(int index) {
        return segmentsList.get(index);
    }

    @Override
    public Collection<Segment> getCollection() {
        return segmentsList;
    }

    @Override
    public Iterator<Segment> descendingIterator() {
        Collections.reverse(segmentsList);
        return segmentsList.iterator();
    }

    @Override
    public void removeAll(SegmentPack segmentPack) {
        segmentsList.removeAll(segmentPack.getCollection());
    }
    public void remove(Segment segment) {
        segmentsList.remove(segment);
    }

    @Override
    public void setFullLength() {
        int sum = 0;
        for (Segment segment :segmentsList) {
            sum += segment.getLength();
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
    public Iterator<Segment> iterator() {
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
    public String toString() {
        return ""+ segmentsList.toString();
    }
}
