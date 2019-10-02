package math.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class StackSegmentsSet implements StackSegments,Iterable<Segment> {
    Integer fullLength;
    private final int LineNumber;
    private TreeSet<Segment> segmentsSet;

    public StackSegmentsSet(int LineNumber) {
        this.LineNumber = LineNumber;
        segmentsSet = new TreeSet<>();
    }

    public <T> StackSegmentsSet(int i, Comparator<Segment> comparing) {
        segmentsSet = new TreeSet<>(comparing);
        LineNumber = i;
    }

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
    public void addAll(StackSegments stackSegments){
        segmentsSet.addAll(stackSegments.getCollection());
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
    public void removeAll(StackSegments stackSegments){
        segmentsSet.removeAll(stackSegments.getCollection());
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
    public int compareTo(StackSegments stackSegments) {
        Integer x1 = this.getFullLength();
        Integer x2 = stackSegments.getFullLength();
        int sComp = x1.compareTo(x2);
        if(sComp!=0){
            return sComp;
        }
        else {
            x1 = this.size();
            x2 = stackSegments.size();
            return x1.compareTo(x2);
        }
    }

    @Override
    public Iterator<Segment> iterator() {
        return segmentsSet.iterator();
    }
}
