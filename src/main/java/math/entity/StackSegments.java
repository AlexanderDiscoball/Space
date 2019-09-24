package math.entity;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class StackSegments {

    private ArrayList<Segment> segmentsList;
    private final int LineNumber;

    public StackSegments(int LineNumber){
        this.LineNumber = LineNumber;
        segmentsList = new ArrayList<>();
    }

    public void add(Segment segment){
        if(isLineEquals(segment)) {
            if (segmentsList.isEmpty()) {
                segmentsList.add(segment);
            } else {
                if (isSegmentCanBeInList(segment)) {
                    segmentsList.add(segment);
                }
            }
        }
    }

    public void addWithoutLineEquals(Segment segment){
        if(segmentsList.isEmpty()){
            segmentsList.add(segment);
        }
        else{
            if(isSegmentCanBeInList(segment)){
                segmentsList.add(segment);
            }
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
        for (Segment segmentInList : segmentsList) {
            if((segment.getFirstDot()<segmentInList.getSecondDot()
                    && segment.getSecondDot()>segmentInList.getFirstDot())){
                return false;
            }
        }
        return true;
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

    public int getSize(){
        return segmentsList.size();
    }

    public boolean isEmpty(){
        return this.segmentsList.isEmpty();
    }
}
