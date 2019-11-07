package math.entity.LineSegments;

import math.entity.interval.Interval;
import math.entity.interval.ZeroInterval;
import math.entity.SegmentPack;

import java.util.*;

public class LineList implements Iterable<Interval>, Line,Comparable<SegmentPack> {

    private List<Interval> segmentsList;
    private final int LineNumber;
    Integer fullLength;

    public LineList(int LineNumber){
        this.LineNumber = LineNumber;
        segmentsList = new ArrayList<>();
    }

    public LineList(List<Interval> segmentsList){
        if(segmentsList.isEmpty()){
            LineNumber = -1;
        }
        else {
            this.LineNumber = segmentsList.get(0).getLine();
        }
        this.segmentsList = segmentsList;
    }

    public void addWithCheckLineEquals(Interval interval){
            if (isLineEquals(interval) && isSegmentCanBeInList(interval)) {
                segmentsList.add(interval);
        }
    }

    public List<Interval> getCollection() {
        return segmentsList;
    }


    public void setFullLength(){
        int sum = 0;
        for (Interval interval :segmentsList) {
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
            return x1.compareTo(x2);
        }
    }

    public Interval getLastSegment(){
       return segmentsList.get(segmentsList.size() -1);
    }

    @Override
    public Interval getFirstSegment() {
        return segmentsList.get(0);
    }

    public void add(Interval interval){
            segmentsList.add(interval);
    }
    public void add(int index, Interval interval){
            segmentsList.add(index, interval);
    }

    public void addWithCheck(Interval interval){
        if(isSegmentCanBeInList(interval)){
            segmentsList.add(interval);
        }
    }

    public LineList subListToMerge(int index){
        return new LineList(segmentsList.subList(index,segmentsList.size()));
    }

    public LineList subList(int from,int to){
        return new LineList(segmentsList.subList(from,to));
    }

    public void addZeroSegments() {
        segmentsList.add(ZeroInterval.getInstance());
    }

    public boolean contain(Interval s){
        return segmentsList.contains(s);
    }

    public void sort(){
        segmentsList.sort(Interval::compareTo);
    }

    public void reverse(){
        Collections.reverse(segmentsList);
    }

    private boolean isLineEquals(Interval interval){
        return interval.getLine() == LineNumber;
    }

    public Interval get(int index){
        return segmentsList.get(index);
    }

    public void set(int index, Interval interval){
         segmentsList.set(index, interval);
    }

    public int getNumberOfLine(){
        return LineNumber;
    }

    public boolean isSegmentCanBeInList(Interval interval){
        if(segmentsList.isEmpty()){
            return true;
        }
        for (Interval intervalInList : segmentsList) {
            if((interval.getFirstDot()< intervalInList.getSecondDot()
                    && interval.getSecondDot()> intervalInList.getFirstDot())){
                return false;
            }
        }
        return true;
    }

    public void set(Interval interval, int index){
        segmentsList.set(index, interval);
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


    public Interval getMaxSegment(){
       return Collections.max(segmentsList, Comparator.comparing(Interval::getLength));
    }

    public Iterator<Interval> iterator() {
        return segmentsList.iterator();
    }
    @Override
    public Iterator<Interval> descendingIterator() {
        Collections.reverse(segmentsList);
        return segmentsList.iterator();
    }

    public void sortByLength(){
        segmentsList.sort(Comparator.comparing(Interval::getLength));
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

    public void remove(Interval interval){
        segmentsList.remove(interval);
    }

    public void removeAll(SegmentPack segmentPack){
        segmentsList.removeAll(segmentPack.getCollection());
    }

    @Override
    public int getLine() {
        return LineNumber;
    }

}
