package math.entity.LineSegments;

import math.entity.Segment.Segment;
import math.entity.Segment.ZeroSegment;
import math.entity.SegmentPack;

import java.util.*;

public class LineList implements Iterable<Segment>, Line,Comparable<SegmentPack> {

    private List<Segment> segmentsList;
    private final int LineNumber;
    Integer fullLength;

    public LineList(int LineNumber){
        this.LineNumber = LineNumber;
        segmentsList = new ArrayList<>();
    }

    public LineList(List<Segment> segmentsList){
        if(segmentsList.isEmpty()){
            LineNumber = -1;
        }
        else {
            this.LineNumber = segmentsList.get(0).getLine();
        }
        this.segmentsList = segmentsList;
    }

    public void addWithCheckLineEquals(Segment segment){
            if (isLineEquals(segment) && isSegmentCanBeInList(segment)) {
                segmentsList.add(segment);
        }
    }

    public List<Segment> getCollection() {
        return segmentsList;
    }

    public void setFullLength(){
        int sum = 0;
        for (Segment segment :segmentsList) {
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
            return x1.compareTo(x2);
        }
    }

    public Segment getLastSegment(){
       return segmentsList.get(segmentsList.size() -1);
    }

    @Override
    public Segment getFirstSegment() {
        return segmentsList.get(0);
    }

    public void add(Segment segment){
            segmentsList.add(segment);
    }
    public void add(int index, Segment segment){
            segmentsList.add(index,segment);
    }

    public void addWithCheck(Segment segment){
        if(isSegmentCanBeInList(segment)){
            segmentsList.add(segment);
        }
    }

    public LineList subListToMerge(int index){
        return new LineList(segmentsList.subList(index,segmentsList.size()));
    }

    public void addZeroSegments() {
        segmentsList.add(ZeroSegment.getInstance());
    }

    public boolean contain(Segment s){
        return segmentsList.contains(s);
    }

    public void sort(){
        segmentsList.sort(Segment::compareTo);
    }

    public void reverse(){
        Collections.reverse(segmentsList);
    }

    private boolean isLineEquals(Segment segment){
        return segment.getLine() == LineNumber;
    }

    public Segment get(int index){
        return segmentsList.get(index);
    }

    public void set(int index,Segment segment){
         segmentsList.set(index,segment);
    }

    public int getNumberOfLine(){
        return LineNumber;
    }

    public boolean isSegmentCanBeInList(Segment segment){
        if(segmentsList.isEmpty()){
            return true;
        }
        for (Segment segmentInList : segmentsList) {
            if((segment.getFirstDot()<segmentInList.getSecondDot()
                    && segment.getSecondDot()>segmentInList.getFirstDot())){
                return false;
            }
        }
        return true;
    }

    public void set(Segment segment,int index){
        segmentsList.set(index,segment);
    }

    public void deleteAll(){
        segmentsList.clear();
    }

    @Override
    public String toString(){
        return
                LineNumber
                + segmentsList.toString() ;
    }

    public void addAll(SegmentPack segmentPack){
        segmentsList.addAll(segmentPack.getCollection());
    }
    public void addAll(int index, SegmentPack segmentPack){
        segmentsList.addAll(index,segmentPack.getCollection());
    }

    public int size(){
        return segmentsList.size();
    }

    public boolean isEmpty(){
        return this.segmentsList.isEmpty();
    }


    public Segment getMaxSegment(){
       return Collections.max(segmentsList, Comparator.comparing(Segment::getLength));
    }

    public Iterator<Segment> iterator() {
        return segmentsList.iterator();
    }
    @Override
    public Iterator<Segment> descendingIterator() {
        Collections.reverse(segmentsList);
        return segmentsList.iterator();
    }

    public void sortByLength(){
        segmentsList.sort(Comparator.comparing(Segment::getLength));
    }

    public int getSumLength(){
        int sum =0;
        for (int i = 0; i < segmentsList.size(); i++) {
            sum+=segmentsList.get(i).getLength();
        }
        return sum;
    }
    public void remove(int index){
        segmentsList.remove(index);
    }

    public void remove(Segment segment){
        segmentsList.remove(segment);
    }

    public void removeAll(SegmentPack segmentPack){
        segmentsList.removeAll(segmentPack.getCollection());
    }

    @Override
    public int getLine() {
        return LineNumber;
    }

}
