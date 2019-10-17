package math;

import math.entity.AreaSegments.AreaList;
import math.entity.LineSegments.*;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.Array.TwoDimensionalArraySet;
import math.entity.Segment.Segment;
import math.entity.Segment.ZeroSegment;
import math.entity.SegmentPack;

import java.lang.reflect.Array;
import java.util.*;

public class Algorithms {

    public static TwoDimensionalArraySet dynamicAlgorithm(TwoDimensionalArray twoDimensionalArray){
        TwoDimensionalArraySet ways = new TwoDimensionalArraySet(SegmentPack::compareTo);
        TwoDimensionalArraySet functions;

        SegmentPack buff;

        LineSet allIntervals = putAllIntervalsInList(twoDimensionalArray);

        while (allIntervals.size() > 0) {
            functions = new TwoDimensionalArraySet(SegmentPack::compareTo);
            for (Segment segment : allIntervals.getCollection()) {
                functions.add(addMaxWayIfCanBeIn(functions, segment));
            }
            //ДОБАВЛЕНИЕ
            buff = functions.getTreeSet().pollLast();
            ways.add(buff);
            //УДАЛЕНИЕ
            allIntervals.removeAll(buff);
        }
        return ways;
    }

    public static LineSet addMaxWayIfCanBeIn(TwoDimensionalArraySet functions, Segment fromAll) {
        LineSet b = new LineSet(-1);
        b.add(fromAll);
        Line s;
        Iterator iterator = functions.getTreeSet().descendingIterator();
        while (iterator.hasNext()){
            s = (Line) iterator.next();
            if(s.getLastSegment().getSecondDot() <= fromAll.getFirstDot()){
                b.addAll(s);
                b.setFullLength();
                //System.out.println(b.getTreeSet());
                return b;
            }
        }
        b.setFullLength();
        return b;
    }

    public static TwoDimensionalArraySet dynamicAlgorithmSize(TwoDimensionalArray twoDimensionalArray){
        TwoDimensionalArraySet ways = new TwoDimensionalArraySet(SegmentPack::compareTo);
        TwoDimensionalArraySet functions;

        SegmentPack buff;

        LineSet allIntervals = putAllIntervalsInList(twoDimensionalArray);

        while (allIntervals.size() > 0) {
            functions = new TwoDimensionalArraySet(Comparator.comparing(SegmentPack::size));
            for (Segment segment : allIntervals.getCollection()) {
                functions.add(addMaxWayIfCanBeInSize(functions, segment));
            }
            //ДОБАВЛЕНИЕ
            buff = functions.getTreeSet().pollLast();
            ways.add(buff);
            //УДАЛЕНИЕ
            allIntervals.removeAll(buff);
        }
        return ways;
    }

    public static LineSet addMaxWayIfCanBeInSize(TwoDimensionalArraySet functions, Segment fromAll) {
        LineSet b = new LineSet(-1);
        b.add(fromAll);
        Line s;
        Iterator iterator = functions.getTreeSet().descendingIterator();
        while (iterator.hasNext()){
            s = (Line) iterator.next();
            if(s.getLastSegment().getSecondDot() <= fromAll.getFirstDot()){
                b.addAll(s);
                b.setFullLength();
                //System.out.println(b.getTreeSet());
                return b;
            }
        }
        b.setFullLength();
        return b;
    }

    public static LineSet putAllIntervalsInList(TwoDimensionalArray twoDimensionalArray) {
        LineSet sss = new LineSet(-1);
        for (SegmentPack line : twoDimensionalArray.getCollection()) {
            sss.addAll(line);
        }
        return sss;
    }


    public static void removeUsedSegmentsFromMainMatrix(TwoDimensionalArray mainArray,List<Integer> indexes, TwoDimensionalArray buf) {
        int index;

        for (Segment segment :buf.get(0)) {
            index = indexes.indexOf(segment.getLine());

            if(index != -1){
                indexes.remove(index);
                mainArray.remove(index);
            }
        }
        buf.remove(0);

        for (SegmentPack segmentPack :buf) {
            for (Segment segment :segmentPack) {
                index = indexes.indexOf(segment.getLine());
                if(index != -1){
                SegmentPack segments = mainArray.get(index);
                segments.remove(segment);
                }
            }
        }
    }

    public static TwoDimensionalArrayList greedyAlgorithm(TwoDimensionalArray twoDimensionalArray) {
        TwoDimensionalArrayList ways = new TwoDimensionalArrayList();
        LineList way;

        LineSet allIntervals = putAllIntervalsInList(twoDimensionalArray);

        while (allIntervals.size() > 0) {
            way = new LineList(-1);
            Segment buf = allIntervals.getFirstSegment();
            way.add(buf);
            allIntervals.remove(buf);
            for (Segment segment :allIntervals) {

                if(way.getLastSegment().getSecondDot() <= segment.getFirstDot()){
                    way.add(segment);
                }
            }
            //ДОБАВЛЕНИЕ
            ways.add(way);
            //УДАЛЕНИЕ
            allIntervals.removeAll(way);
        }
        return ways;
    }

    public static void showCoordinateMatrix(TwoDimensionalArrayList matrixList) {
        for (int i = 0; i < matrixList.size(); i++) {
            for (int j = 0; j < matrixList.get(i).size(); j++) {
                String b = (matrixList.get(i).get(j)).getFirstDot() + "," + (matrixList.get(i).get(j)).getSecondDot() + "  ";
                System.out.print(b);
            }
            System.out.println();
        }
    }

    private static void sortSegmentByPriority(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getPriority);
        list.sort(comparator);
    }

    private static void sortSegmentByLength(ArrayList<Segment> list) {
        Comparator<Segment> comparator = Comparator.comparing(Segment::getLength);
        list.sort(comparator);
        Collections.reverse(list);
    }



    //DELIMITER

    public static TwoDimensionalArray separation(LineList allIntervals, int start, int end){
        LineList buf;
        TwoDimensionalArray output;
        int[] indexes = getIndex(allIntervals,new Segment(start,end,-1)) ;
        int indexStart  = indexes[0];
        int indexEnd  = indexes[1];
        if(indexEnd == allIntervals.size()) {
            indexEnd = allIntervals.size() - 1;
        }
        else {
            while (!(allIntervals.get(indexEnd + 1).getSecondDot() > end)) {
                indexEnd++;
            }
        }
        buf = createSubLine(allIntervals,indexStart,indexEnd);
        System.out.println("Все интервалы от "+start +" до "+end +" "+buf);
        output = createLineMatrix(buf);
        cutOff(allIntervals,indexStart,indexEnd);
        return output;
    }

    private static void cutOff(LineList allIntervals,int indexStart,int indexEnd) {
        //System.out.println(indexStart +" end " +indexEnd);
        //System.out.println("До удаления "+allIntervals);
        allIntervals.getCollection().subList(indexStart, indexEnd+1).clear();
        //System.out.println("После удаления "+allIntervals);
    }

    public static LineList createSubLine(LineList allIntervals, int start, int end){
        LineList subLine = new LineList(-1);
        for (int i = start; i <= end; i++) {
            subLine.add(allIntervals.get(i));
        }
        return subLine;
    }

    private static TwoDimensionalArray createLineMatrix(LineList buf) {
        buf.getCollection().sort(Comparator.comparing(Segment::getLine));
        int line = buf.get(0).getLine();
        SegmentPack segmentPack = new LineList(line);
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArrayList();
        twoDimensionalArray.add(segmentPack);
        for (Segment segment :buf) {
            if(segment.getLine() != line){
                twoDimensionalArray.add(segmentPack);
                line = segment.getLine();
                segmentPack = new LineList(line);
            }
            segmentPack.add(segment);
        }
        return twoDimensionalArray;
    }

    public static LineList getAllIntervals(TwoDimensionalArray twoDimensionalArray) {
        LineList sss = new LineList(-1);
        for (SegmentPack line : twoDimensionalArray.getCollection()) {
            sss.addAll(line);
        }
        return sss;
    }

    private static int[] getIndex(LineList allFirstSegments, Segment limit) {
        int[] indexes = new int[2];
        indexes[1] = searchEndBorderIndex(allFirstSegments,limit);
        indexes[0] = searchStartBorderIndex(allFirstSegments,limit);
        return indexes;
    }

    private static int searchEndBorderIndex(LineList allFirstSegments, Segment limit){
        int index = Collections.binarySearch(allFirstSegments.getCollection(), limit,Comparator.comparing(Segment::getSecondDot));
        if (index > 0) {
            return index;
        }
        return -index - 1;
    }

    private static int searchStartBorderIndex(LineList allFirstSegments, Segment limit){
        int index = Collections.binarySearch(allFirstSegments.getCollection(), limit,Comparator.comparing(Segment::getFirstDot));
        if (index > 0) {
            return index;
        }
        return -index - 1;
    }

}
