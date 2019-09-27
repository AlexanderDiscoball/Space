package math.entity;

import java.util.*;

public class StackSegments implements Iterable<Segment> {

    private List<Segment> segmentsList;
    private final int LineNumber;

    public StackSegments(int LineNumber){
        this.LineNumber = LineNumber;
        segmentsList = new ArrayList<>();
    }

    public void addWithCheckLineEquals(Segment segment){
            if (isLineEquals(segment) && isSegmentCanBeInList(segment)) {
                segmentsList.add(segment);
        }
    }

    public List<Segment> getSegmentsList() {
        return segmentsList;
    }

    public int getFullLength(){
        int sum = 0;
        for (Segment segment :segmentsList) {
            sum = segment.getLength();
        }

        return sum;
    }

    public Segment getLastSegment(){
        if(segmentsList.size() == 0){
            return ZeroSegment.getInstance();
        }
       return Collections.max(segmentsList,Comparator.comparing(Segment::getSecondDot));
    }

    public void add(Segment segment){
            segmentsList.add(segment);
    }

    public void addWithCheck(Segment segment){
        if(isSegmentCanBeInList(segment)){
            segmentsList.add(segment);
        }
    }

    public void addZeroSegments() {
        segmentsList.add(ZeroSegment.getInstance());
    }

    public boolean contain(Segment s){
        return segmentsList.contains(s);
    }

    public void sort(){
        Collections.sort(segmentsList);
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

    public void removeAll(){
        segmentsList.clear();
    }

    @Override
    public String toString(){
        return "Линия видимости "
                + LineNumber
                + segmentsList.toString() ;
    }

    public void addAll(StackSegments stackSegments){
        segmentsList.addAll(stackSegments.segmentsList);
    }

    public int getSize(){
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

    public void sortByLength(){
        segmentsList.sort(Comparator.comparing(Segment::getLength));
    }

    public void remove(int index){
        segmentsList.remove(index);
    }
    public void remove(Segment segment){
        segmentsList.remove(segment);
    }

}