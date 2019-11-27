package math;

import math.entity.areasegments.AreaList;
import math.entity.array.*;
import math.entity.linesegments.LineList;
import math.entity.linesegments.Track;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import math.spring.Sepa;

import java.util.*;

public class Separator implements Sepa {

    public LineList separation(LineList allIntervals, int start, int end){
        LineList subLine;
        int[] indexes = getIndexes(allIntervals,new Interval(start,end,-1,-1)) ;
        int indexStart  = indexes[0];
        int indexEnd  = indexes[1];
        if(indexEnd == allIntervals.size()) {
            indexEnd = allIntervals.size() - 1;
        }
        if(indexEnd == -1 || indexStart == -1){
            subLine = new LineList(-1);
            return subLine;
        }
        subLine = createSubLine(allIntervals,indexStart,indexEnd+1);
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
        subLine.getCollection().sort(Comparator.comparing(Interval::getRoll));
        int line = subLine.get(0).getRoll();
        SegmentPack segmentPack = new LineList(line);
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArrayList();
        for (Interval interval :subLine) {
            if(interval.getRoll() != line){
                twoDimensionalArray.add(segmentPack);
                line = interval.getRoll();
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

    public HashMap<Integer, Track> separationArrays(ArrayHash mainArray, int start, int end) {

        Track buf;
        HashMap<Integer, Track> separatePack = new HashMap<>();
        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            separatePack.put(line,new Track(line));
        }

        for (AreaList segments :mainArray.values()) {

            if(segments.getCollection().isEmpty())continue;
            int startIndex = -1;
            for (int i = 0; i < segments.size(); i++) {
                if(segments.get(i).getFirstDot() >= start){
                    startIndex = i;
                    break;
                }
            }

            if(startIndex == -1){
                return separatePack;
            }

            int index;
            for (index = startIndex; index < segments.size(); index++) {
                Interval interval = segments.get(index);
                if(interval.getSecondDot() > end){
                    break;
                }
                buf = separatePack.get(interval.getRoll());
                buf.add(interval);

            }
            ((List) segments.getCollection()).subList(0, index).clear();
        }
        separatePack.values().removeIf(track -> track.getRangeOfIntervals().isEmpty());
        return separatePack;
    }

    private int findStartIndex(SegmentPack segments, int start) {
        for (int i = 0; i < segments.size(); i++) {
            if(segments.get(i).getFirstDot() >= start){
                return i;
            }
        }
        return -1;
    }

    public LineList separationArrays222(ArrayHash mainArray,int start, int end) {
        LineList stump = new LineList(-1);
        for (AreaList segments :mainArray.values()) {
            int i;
            for (i = 0; i < segments.size(); i++) {
                Interval interval = segments.get(i);
                if(interval.getSecondDot() > end){
                    break;
                }
                else if(interval.getFirstDot() >= start) {
                    stump.add(interval);
                }
            }
            ((List) segments.getCollection()).subList(0, i).clear();
        }
        return stump;
    }

    public Selection createSelection(LineList subLine) {
        subLine.getCollection().sort(Comparator.comparing(Interval::getPriority));
        int line = subLine.get(0).getRoll();
        Track track = new Track(line);
        Selection selection = new Selection();
        for (Interval interval :subLine) {
            if(interval.getRoll() != line){
                selection.add(track);
                line = interval.getRoll();
                track = new Track(line);
            }
            track.add(interval);
        }
        selection.add(track);
        for (Track intervals :selection) {
            intervals.getRangeOfIntervals().sort(Comparator.comparing(Interval::getSecondDot));
        }

        return selection;
    }


}
