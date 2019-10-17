package math;

import math.entity.AreaSegments.AreaList;
import math.entity.Array.TwoDimensionalArraySet;
import math.entity.LineSegments.*;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.Segment.Segment;
import math.entity.Segment.ZeroSegment;
import math.entity.SegmentPack;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Refactor {

    static SegmentPack buf = new LineList(-1);
    public static TwoDimensionalArrayList dynamicAlgorithm(TwoDimensionalArray twoDimensionalArrayList){
        if(twoDimensionalArrayList instanceof TwoDimensionalArraySet){
           twoDimensionalArrayList = ((TwoDimensionalArraySet) twoDimensionalArrayList).castToList();
        }
        TwoDimensionalArrayList ways = new TwoDimensionalArrayList();
        TwoDimensionalArrayList functions;

        SegmentPack buff;

        LineList allIntervals = putAllIntervalsInList(twoDimensionalArrayList);
        allIntervals.sort();

        while (allIntervals.size() > 0) {
            functions = new TwoDimensionalArrayList();
            functions.add(new LineList(-1){{add(allIntervals.get(0));setFullLength();}});
            for (int i = 1; i < allIntervals.size(); i++) {
                buf = addMaxWayIfCanBeIn(functions, allIntervals.get(i));
                CustomAddToSortedList(functions,buf);
            }
            //ДОБАВЛЕНИЕ
            buff = functions.get(functions.size()-1);
            ways.add(buff);
            //УДАЛЕНИЕ
            allIntervals.removeAll(buff);
        }

        return ways;
    }

    public static LineList addMaxWayIfCanBeIn(TwoDimensionalArrayList functions, Segment fromAll) {
        LineList b = new LineList(-1);
        for (int i = functions.size() - 1; i >= 0; i--) {
            if(functions.get(i).getLastSegment().getSecondDot() <= fromAll.getFirstDot()){
                b.addAll(functions.get(i));
                b.add(fromAll);
                b.setFullLength();
                return b;
            }

        }
        b.add(fromAll);
        b.setFullLength();
        return b;
    }

    private static void CustomAddToSortedList(TwoDimensionalArrayList functions, SegmentPack buf) {
        int index = findIndex(functions);
        functions.add(index,buf);
    }

    private static int findIndex(TwoDimensionalArrayList functions) {
        if(buf.compareTo(functions.get(functions.size() - 1)) > 0){
            return functions.size();
        }
        return functions.size() - 1;
    }

    public static LineList putAllIntervalsInList(TwoDimensionalArray twoDimensionalArrayList) {
        LineList stackSegmentsList = new LineList(-2);
        for (int i = 0; i < twoDimensionalArrayList.size(); i++) {
            stackSegmentsList.addAll(twoDimensionalArrayList.get(i));
        }

        return stackSegmentsList;
    }
}
