package math.entity.LineSegments;

import math.entity.Segment.Segment;
import math.entity.SegmentPack;

import java.util.*;

public class LineSet implements Line,Iterable<Segment>,Comparable<SegmentPack> {
    Integer fullLength;
    private final int LineNumber;
    private TreeSet<Segment> segmentsSet;

    public LineSet(int LineNumber) {
        this.LineNumber = LineNumber;
        segmentsSet = new TreeSet<>();
    }

    public <T> LineSet(int i, Comparator<Segment> comparing) {
        segmentsSet = new TreeSet<>(comparing);
        LineNumber = i;
    }
    @Override
    public void add(Segment segment){
        segmentsSet.add(segment);
    }
    public TreeSet<Segment> getTreeSet(){
        return segmentsSet;
    }
    public Segment getLastSegment(){
        return segmentsSet.last();
    }
    public Segment getFirstSegment(){
        return segmentsSet.first();
    }
    public void addAll(SegmentPack segmentPack){
        segmentsSet.addAll(segmentPack.getCollection());
    }
    public int size(){
        return segmentsSet.size();
    }

    @Override
    public Segment get(int index) {
        return null;
    }

    public TreeSet<Segment> getCollection(){
        return segmentsSet;
    }
    @Override
    public void removeAll(SegmentPack segmentPack){
        segmentsSet.removeAll(segmentPack.getCollection());
    }
    public void remove(Segment segment){
        segmentsSet.remove(segment);
    }

    public void setFullLength(){
        int sum = 0;
        for (Segment segment :segmentsSet) {
            sum += segment.getLength();
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
    public Iterator<Segment> iterator() {
        return segmentsSet.iterator();
    }
    @Override
    public Iterator<Segment> descendingIterator() {
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
