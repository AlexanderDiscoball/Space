package math;

import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.LineSegments.LineList;
import math.entity.Segment.Segment;
import math.entity.SegmentPack;

import java.util.Collections;
import java.util.Comparator;

public class Separator {

    public LineList separation(LineList allIntervals, int start, int end){
        LineList subLine;
        int[] indexes = getIndexes(allIntervals,new Segment(start,end,-1,-1)) ;
        int indexStart  = indexes[0];
        int indexEnd  = indexes[1];
        if(indexEnd == allIntervals.size()) {
            indexEnd = allIntervals.size() - 1;
        }
        System.out.println(indexes[0]+"::"+indexes[1]);
        if(indexEnd == -1 || indexStart == -1){
            subLine = new LineList(-1);
            return subLine;
        }
        subLine = createSubLine(allIntervals,indexStart,indexEnd);
        //System.out.println("Все интервалы от " + start + " до " + end + " " + subLine);
        return subLine;
    }

    public void removeChosenSegments(LineList allIntervals,LineList buf){
        allIntervals.getCollection().removeAll(buf.getCollection());
    }

    public void cutOff(LineList allIntervals,int indexStart,int indexEnd) {
//        System.out.println(indexStart +" end " +indexEnd);
//        System.out.println("До удаления "+allIntervals);
          allIntervals.getCollection().subList(indexStart,indexEnd+1).clear();
//        System.out.println("После удаления "+allIntervals);
    }

    public LineList createSubLine(LineList allIntervals, int start, int end){
        LineList out = new LineList(-1);
        if(start == end || start > end){
            if(allIntervals.get(start).getSecondDot() < end && allIntervals.get(start).getSecondDot() > start) {
                out.add(allIntervals.get(start));
            }
        }
        else {
            out.addAll(new LineList(allIntervals.getCollection().subList(start, end)));
        }
        return out;
    }

    public TwoDimensionalArray createLineArray(LineList subLine) {
        subLine.getCollection().sort(Comparator.comparing(Segment::getLine));
        int line = subLine.get(0).getLine();
        SegmentPack segmentPack = new LineList(line);
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArrayList();
        twoDimensionalArray.add(segmentPack);
        for (Segment segment :subLine) {
            if(segment.getLine() != line){
                twoDimensionalArray.add(segmentPack);
                line = segment.getLine();
                segmentPack = new LineList(line);
            }
            segmentPack.add(segment);
        }
        return twoDimensionalArray;
    }

    public int[] getIndexes(LineList allSegments, Segment limit) {
        int[] indexes = new int[2];
        indexes[0] = searchStartBorderIndex(allSegments,limit);
        while(indexes[0] != 0 && allSegments.get(indexes[0] - 1).getFirstDot() > limit.getFirstDot()){
            indexes[0]--;
        }
        indexes[1] = searchEndBorderIndex(allSegments,limit);
        while(indexes[1] + 1 != allSegments.size() && allSegments.get(indexes[1] + 1).getSecondDot() < limit.getSecondDot()){
            indexes[1]++;
        }
        if(indexes[0] > indexes[1]){
            indexes[0] = -1;
            indexes[1] = -1;
        }
        return indexes;
    }

    public int searchEndBorderIndex(LineList allSegments, Segment limit){
        int index = Collections.binarySearch(allSegments.getCollection(), limit,Comparator.comparing(Segment::getSecondDot));
        if (index >= 0) {
            return index;
        }
        if (index == -1) {
            return -index - 1;
        }
        return -index - 2;
    }

    public int searchStartBorderIndex(LineList allSegments, Segment limit){
        int index = Collections.binarySearch(allSegments.getCollection(), limit,Comparator.comparing(Segment::getFirstDot));
        if (index >= 0) {
            return index;
        }
        return -index - 1;
    }

}
