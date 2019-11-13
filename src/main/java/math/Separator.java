package math;

import math.entity.Array.ArrayHash;
import math.entity.Array.Selection;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.LineSegments.LineList;
import math.entity.LineSegments.Track;
import math.entity.interval.Interval;
import math.entity.SegmentPack;

import java.util.*;

public class Separator {

    public LineList separation(LineList allIntervals, int start, int end){
        LineList subLine;
        int[] indexes = getIndexes(allIntervals,new Interval(start,end,-1,-1)) ;
        int indexStart  = indexes[0];
        int indexEnd  = indexes[1];
        if(indexEnd == allIntervals.size()) {
            indexEnd = allIntervals.size() - 1;
        }
//        if(indexEnd > -1 && indexStart > -1) {
//            System.out.println(allIntervals.get(indexStart).getFirstDot() + "::" + allIntervals.get(indexEnd).getSecondDot());
//        }
//        else{
//            System.out.println("Нет интервалов");
//        }
        if(indexEnd == -1 || indexStart == -1){
            subLine = new LineList(-1);
            return subLine;
        }
        subLine = createSubLine(allIntervals,indexStart,indexEnd+1);
        //cutOff(allIntervals,0,indexEnd);
        //System.out.println("Все интервалы от " + start + " до " + end + " " + subLine);
        return subLine;
    }

    public void removeChosenSegments(LineList allIntervals,LineList buf){
        allIntervals.getCollection().removeAll(buf.getCollection());
    }

    public void cutOff(LineList allIntervals,int indexStart,int indexEnd) {
          allIntervals.getCollection().subList(indexStart, indexEnd + 1).clear();
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
        subLine.getCollection().sort(Comparator.comparing(Interval::getLine));
        int line = subLine.get(0).getLine();
        SegmentPack segmentPack = new LineList(line);
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArrayList();
        for (Interval interval :subLine) {
            if(interval.getLine() != line){
                twoDimensionalArray.add(segmentPack);
                line = interval.getLine();
                segmentPack = new LineList(line);
            }
            segmentPack.add(interval);
        }
        twoDimensionalArray.add(segmentPack);
        return twoDimensionalArray;
    }


    public int[] getIndexes(SegmentPack allSegments, Interval limit) {
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

    public int searchEndBorderIndex(SegmentPack allSegments, Interval limit) {
        int index = Collections.binarySearch((List) allSegments.getCollection(), limit, Comparator.comparing(Interval::getSecondDot));
        if (index >= 0) {
            return index;
        }
        if (index == -1) {
            return -index - 1;
        }
        return -index - 2;
    }

    public int searchStartBorderIndex(SegmentPack allSegments, Interval limit){
        int index = Collections.binarySearch((List)allSegments.getCollection(), limit,Comparator.comparing(Interval::getFirstDot));
        if (index >= 0) {
            return index;
        }
        return -index - 1;
    }

    public HashMap<Integer,Track> separationArrays(ArrayHash mainArray,int start, int end) {
        HashMap<Integer,Track> map = new HashMap<>();
        Track buf;
        int keyIndex;
        for (SegmentPack segments :mainArray.values()) {
            int i;
            for (i = 0; i < segments.size(); i++) {
                Interval interval = segments.get(i);
                if(interval.getSecondDot() > end){
                    break;
                }
                else if(interval.getFirstDot() >= start) {
                    keyIndex = interval.getLine();
                    if(map.containsKey(keyIndex)) {
                        // stump.add(interval);
                        buf = map.get(keyIndex);
                        buf.add(interval);
                    }
                    else{
                        map.put(interval.getLine(),new Track(keyIndex){{add(interval);}});
                    }
                }
            }
            ((List) segments.getCollection()).subList(0, i).clear();
        }
        return map;
    }

    public Selection createSelection(LineList subLine) {
        subLine.getCollection().sort(Comparator.comparing(Interval::getPriority));
        int line = subLine.get(0).getLine();
        Track track = new Track(line);
        Selection selection = new Selection();
        for (Interval interval :subLine) {
            if(interval.getLine() != line){
                selection.add(track);
                line = interval.getLine();
                track = new Track(line);
            }
            track.add(interval);
        }
        selection.add(track);
        return selection;
    }

    private int[] getIndexesStartEnd(SegmentPack segments, Map<Integer,Integer> indexMask, int end) {
        int index = indexMask.get(segments.getFirstSegment().getAreaId());
        int[] indexes = new int[2];
        indexes[0] = index;
        for (Interval interval :segments) {
          if(interval.getSecondDot() > end){
              indexes[1] = index-1;
              break;
          }
          index++;
          if(index == segments.size()){
              indexes[1] = segments.size()-1;
          }
        }
        return indexes;
    }

    public LineList separationArrays222(ArrayHash mainArray,Map<Integer,Integer> indexMask,int start, int end) {
        LineList stump = new LineList(-1);
        for (SegmentPack segments :mainArray.values()) {
            int[] stumpIndex = getIndexes(segments,new Interval(start,end,-1,-1));
            indexMask.replace(segments.getFirstSegment().getAreaId(),stumpIndex[1]);
            if(stumpIndex[0] == -1 || stumpIndex[1] == -1){
                continue;
            }
            stump.getCollection().addAll((((List)(segments.getCollection())).subList(stumpIndex[0],stumpIndex[1]+1)));
        }

        return stump;
    }

}
