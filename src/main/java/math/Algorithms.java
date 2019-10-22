package math;

import math.entity.AreaSegments.AreaList;
import math.entity.Array.ArrayHash;
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
    /**
     * Dynamic algorithm. Check pdf file in directory Space2 for more details.
     * "Максимальные интервалы пересечения - algorithm _ Qaru"
     */
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

    /**
     * Dynamic algorithm but another sort principle.
     * We sort all SegmentPack with size() parameter.
     */
    public static TwoDimensionalArraySet dynamicAlgorithmSize(TwoDimensionalArray twoDimensionalArray){
        TwoDimensionalArraySet ways = new TwoDimensionalArraySet(SegmentPack::compareTo);
        TwoDimensionalArraySet functions;

        SegmentPack buff;

        LineSet allIntervals = putAllIntervalsInSet(twoDimensionalArray);

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

    public static LineSet putAllIntervalsInSet(TwoDimensionalArray twoDimensionalArray) {
        LineSet sss = new LineSet(-1);
        for (SegmentPack line : twoDimensionalArray.getCollection()) {
            sss.addAll(line);
        }
        return sss;
    }


    public static void removeUsedSegmentsFromMainMatrix(TwoDimensionalArray mainArray,List<Integer> indexes, LineList result) {
        int index;

        for (Segment segment :result) {
            index = indexes.indexOf(segment.getLine());
            if(index != -1){
                indexes.remove(index);
                mainArray.remove(index);
            }
        }

//        buf.remove(0);
//
//        for (SegmentPack segmentPack :buf) {
//            for (Segment segment :segmentPack) {
//                index = indexes.indexOf(segment.getLine());
//                if(index != -1){
//                SegmentPack segments = mainArray.get(index);
//                segments.remove(segment);
//                }
//            }
//        }
    }
    public static void removeUsedSegmentsFromMainMatrix(TwoDimensionalArray mainArray, LineList way,TwoDimensionalArray buf) {
        for (Segment segment :way) {
            for (SegmentPack segments :mainArray) {
                segments.remove(segment);
            }
        }
        for (SegmentPack segments :buf) {
            for (Segment segment :segments) {
                for (SegmentPack segmentsMain :mainArray) {
                    segmentsMain.remove(segment);
                }
            }

        }

    }

    /**
     * Greedy algorithm. Check pdf file in directory Space2 for more details.
     * "Жадные алгоритмы. Глава 16. фрагмент книги Кормен Т. и др. - Алгоритмы"
     */

    public static TwoDimensionalArrayList greedyAlgorithm(TwoDimensionalArray twoDimensionalArray) {
        TwoDimensionalArrayList ways = new TwoDimensionalArrayList();
        LineList way;

        LineSet allIntervals = putAllIntervalsInSet(twoDimensionalArray);

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

    public static LineList greedyAlgorithmForSimulation(TwoDimensionalArray twoDimensionalArray, TwoDimensionalArray mainArray,ArrayList<Integer> mask) {
        TwoDimensionalArrayList ways = new TwoDimensionalArrayList();
        LineList way;

        LineSet allIntervals = putAllIntervalsInSet(twoDimensionalArray);

            way = new LineList(-1);
            Segment buf = allIntervals.getFirstSegment();
            way.add(buf);
            int index;
            SegmentPack segmentPack;
            allIntervals.remove(buf);
            for (Segment segment :allIntervals) {
//                    index = mask.indexOf(segment.getAreaId());
//                    if(index > 0) {
//                        mainArray.get(index).remove(segment);
//                    }
                if(way.getLastSegment().getSecondDot() <= segment.getFirstDot()){
                    way.add(segment);
                    index = mask.indexOf(segment.getAreaId());
                    if(index > 0) {
                        mainArray.remove(index);
                        mask.remove(index);
                    }
                }
            }

        return way;
    }

   public static LineList greedyAlgorithmForHashSimulation(TwoDimensionalArray twoDimensionalArray, ArrayHash arrayHash) {
        TwoDimensionalArrayList ways = new TwoDimensionalArrayList();
        LineList way;

        LineSet allIntervals = putAllIntervalsInSet(twoDimensionalArray);

            way = new LineList(-1);
            Segment buf = allIntervals.getFirstSegment();
            way.add(buf);
            int index;
            SegmentPack segmentPack;
            allIntervals.remove(buf);
            for (Segment segment :allIntervals) {
//                    index = mask.indexOf(segment.getAreaId());
//                    if(index > 0) {
//                        mainArray.get(index).remove(segment);
//                    }
                if(way.getLastSegment().getSecondDot() <= segment.getFirstDot()){
                    way.add(segment);
                    arrayHash.getHashPack().remove(segment.getAreaId());
                }
            }

        return way;
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

    public static LineList getAllIntervals(TwoDimensionalArray twoDimensionalArray) {
        LineList sss = new LineList(-1);
        for (SegmentPack line : twoDimensionalArray.getCollection()) {
            sss.addAll(line);
        }
        return sss;
    }
    public static LineList getAllIntervals(ArrayHash arrayHash) {
        LineList sss = new LineList(-1);
        for (SegmentPack line : arrayHash.getHashPack().values()) {
            sss.addAll(line);
        }
        return sss;
    }

    public static LineList getAllMergeIntervals(TwoDimensionalArray twoDimensionalArray){
        LineList lineList = new LineList(-1);
        lineList.addAll(twoDimensionalArray.get(0));
        System.out.println(twoDimensionalArray.size());
        twoDimensionalArray.remove(0);
        System.out.println(twoDimensionalArray.size());
        for (SegmentPack line : twoDimensionalArray.getCollection()) {
            merge(lineList,line);
        }
        return lineList;
    }

    public static void merge(LineList lineList, SegmentPack line) {
        int endCycle = lineList.size() + line.size();
        int lineListIndex = 0;
        int lineIndex = 0;
        int i = 0;
        int pointer = 0;

        while (pointer < endCycle) {
            if(lineList.get(pointer).getSecondDot() < line.get(lineIndex).getSecondDot()){
                lineListIndex++;
            }
            else {
                lineList.add(pointer,line.get(lineIndex++));
            }
            pointer++;
            if(lineIndex == line.size()){
                break;
            }
            if(pointer == lineList.size()){
                lineList.addAll(pointer,((LineList)line).subListToMerge(lineIndex));
                break;
            }
        }
    }

    public static void nadir
}
