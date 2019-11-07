package math.entity.LineSegments;

import math.entity.interval.Interval;
import math.entity.SegmentPack;

import java.util.*;

public class LineSet implements Line,Iterable<Interval>,Comparable<SegmentPack> {
    Integer fullLength;
    private final int LineNumber;
    private TreeSet<Interval> segmentsSet;

    public LineSet(int LineNumber) {
        this.LineNumber = LineNumber;
        segmentsSet = new TreeSet<>();
    }

    public <T> LineSet(int i, Comparator<Interval> comparing) {
        segmentsSet = new TreeSet<>(comparing);
        LineNumber = i;
    }
    @Override
    public void add(Interval interval){
        segmentsSet.add(interval);
    }
    public TreeSet<Interval> getTreeSet(){
        return segmentsSet;
    }
    public Interval getLastSegment(){
        return segmentsSet.last();
    }
    public Interval getFirstSegment(){
        return segmentsSet.first();
    }
    public void addAll(SegmentPack segmentPack){
        segmentsSet.addAll(segmentPack.getCollection());
    }
    public int size(){
        return segmentsSet.size();
    }

    @Override
    public Interval get(int index) {
        return null;
    }

    public TreeSet<Interval> getCollection(){
        return segmentsSet;
    }
    @Override
    public void removeAll(SegmentPack segmentPack){
        segmentsSet.removeAll(segmentPack.getCollection());
    }
    public void remove(Interval interval){
        segmentsSet.remove(interval);
    }

    public void setFullLength(){
        int sum = 0;
        for (Interval interval :segmentsSet) {
            sum += interval.getLength();
        }
        fullLength = sum;
    }

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
            sComp = x1.compareTo(x2);
            if(sComp!=0){
                return sComp;
            }
            else{
                return 1;
            }
        }
    }

    @Override
    public Iterator<Interval> iterator() {
        return segmentsSet.iterator();
    }
    @Override
    public Iterator<Interval> descendingIterator() {
        return segmentsSet.descendingIterator();
    }

    @Override
    public int getLine() {
        return LineNumber;
    }

    @Override
    public String toString(){
        return
                LineNumber
                + segmentsSet.toString() ;
    }
}
