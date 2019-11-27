package math.entity.linesegments;

import math.InputData;
import math.annotation.AlgorithmsType;
import math.annotation.GetMethodTime;
import math.entity.areasegments.AreaList;
import math.entity.array.*;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import math.spring.Algo;
import java.util.*;


@AlgorithmsType
public class Algorithms implements Algo {

    private static List<Track> resultsAll = new ArrayList<>();
    public static List<Track> getResultsAll() {
        return resultsAll;
    }

    /**
     * Dynamic algorithm. Check pdf file in directory Space2 for more details.
     * "Максимальные интервалы пересечения - algorithm _ Qaru"
     */
    static SegmentPack buf = new LineList(-1);

    public TwoDimensionalArrayList dynamicAlgorithm(TwoDimensionalArray twoDimensionalArrayList){
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

            buff = functions.get(functions.size()-1);
            ways.add(buff);

            allIntervals.removeAll(buff);
        }

        return ways;
    }

    public static LineList addMaxWayIfCanBeIn(TwoDimensionalArrayList functions, Interval fromAll) {
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
        for (SegmentPack segments :twoDimensionalArrayList) {
            stackSegmentsList.addAll(segments);
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
            for (Interval interval : allIntervals.getCollection()) {
                functions.add(addMaxWayIfCanBeInSize(functions, interval));
            }

            buff = functions.getTreeSet().pollLast();
            ways.add(buff);

            allIntervals.removeAll(buff);
        }
        return ways;
    }

    public static LineSet addMaxWayIfCanBeInSize(TwoDimensionalArraySet functions, Interval fromAll) {
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

    public static void removeUsedSegmentsFromMainMatrix(TwoDimensionalArray mainArray, LineList way,TwoDimensionalArray buf) {
        for (Interval interval :way) {
            for (SegmentPack segments :mainArray) {
                segments.remove(interval);
            }
        }
        for (SegmentPack segments :buf) {
            for (Interval interval :segments) {
                for (SegmentPack segmentsMain :mainArray) {
                    segmentsMain.remove(interval);
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

        LineList allIntervals = putAllIntervalsInList(twoDimensionalArray);
        allIntervals.sort();

        while (allIntervals.size() > 0) {
            way = new LineList(-1);
            Interval buf = allIntervals.getFirstSegment();
            way.add(buf);
            allIntervals.remove(buf);
            for (Interval interval :allIntervals) {

                if(way.getLastSegment().getSecondDot() <= interval.getFirstDot()){
                    way.add(interval);
                }
            }

            ways.add(way);

            allIntervals.removeAll(way);
        }
        return ways;
    }


    public static Track nadirAlgorithm(HashMap<Integer, Track> separateArray, ArrayHash mainArray){
        Map.Entry<Integer,Track> entry = separateArray.entrySet().iterator().next();
        Track solution = entry.getValue();
        separateArray.remove(entry.getKey());
        solution.getRangeOfIntervals().sort(Comparator.comparing(Interval::getSecondDot));
        Iterator<Interval> iter = solution.getRangeOfIntervals().iterator();
        while (iter.hasNext()){
            int interval = iter.next().getAreaId();
            if(mainArray.contain(interval)) {
                mainArray.remove(interval);
            }
            else{
                iter.remove();
            }
        }
        int size = solution.size();
        int stopCounter = 0;
        for(Track mergedTrack: separateArray.values()) {
            solution.mergeWithoutCrossings(mergedTrack,mainArray);
            if(size == solution.size()){
                stopCounter++;
            }
            else {
                size = solution.size();
            }
            if(InputData.isWithCriteria()) {
                if (stopCounter == InputData.getVoluntaristCriteria()) {
                    break;
                }
            }
        }
        return solution;
    }



    public static int resultPasses = 0;

    @GetMethodTime
    public ArrayList nadirAlgorithmAll(Selection bunchOfTracks,int sizeOfBunchOfTracks, int trackToStartNo,  int passageNum, int criterionValue){
        int conter = 0;
        Track allSolution = new Track(-1);
        while(trackToStartNo <= (sizeOfBunchOfTracks - 1)) {
            boolean cross = false;
            boolean changed = false;
            boolean bunchOfTracksIsEmpty = true;
            Track solution = bunchOfTracks.getTrackNo(trackToStartNo).clone();
            if(!solution.getRangeOfIntervals().isEmpty())
                passageNum++;
            else {
                trackToStartNo++;
                continue;
            }
            solution.takeIntoAccountSolutionInfo();
            int lastIncludedTrackNumber = trackToStartNo;
            int numberOfTracksFullyOffTheSolution=0;
            trackToStartNo++;
            for(int i = trackToStartNo; i < sizeOfBunchOfTracks; i++) { //replace i on smth sensible
                if(bunchOfTracks.getTrackNo(i).getRangeOfIntervals().isEmpty())
                    continue;
                else {
                    bunchOfTracksIsEmpty = false;
                    Info mergeResultInfo = solution.mergeWithoutCrossingsAll(bunchOfTracks.getTrackNo(i));
                    if(mergeResultInfo.cross) { // true - solution and examined track cross
                        if(!changed){
                            trackToStartNo = i;
                            changed = true;
                        }
                    }
                    else{ // false - solution and examined track don't cross
                        if(!changed && i != (sizeOfBunchOfTracks - 1))
                            trackToStartNo++;
                        else if(!changed && i == (sizeOfBunchOfTracks - 1)) {
                            trackToStartNo++;
                            bunchOfTracksIsEmpty = true;
                        }
                    }
                    lastIncludedTrackNumber += mergeResultInfo.numOfTracksWithInclIntervals;
                    numberOfTracksFullyOffTheSolution += mergeResultInfo.numOfTracksFullyOffTheSolution;
                }

                //a voluntarist criteria
                if(InputData.isWithCriteria()) {
                    if (numberOfTracksFullyOffTheSolution == criterionValue)
                        break;
                }
            }
            allSolution.addAll(solution);
            resultsAll.add(solution);
            if(bunchOfTracksIsEmpty == true) {
                break;
            }
        }

        ArrayList list = new ArrayList();
        list.add(passageNum);
        list.add(allSolution);

    //System.out.println("allSolutions "+allSolutions);
        return  list;
    }

    private static long greedyTest(TwoDimensionalArray greedy) {
        long timeStart = System.currentTimeMillis();
        Algorithms algorithms = new Algorithms();
        greedy = algorithms.greedyAlgorithm(greedy);
        System.out.println(greedy.size() + " Количество проходов жадным алгоритмом");
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
    }

    public static LineList greedyAlgorithmForHashSimulation(TwoDimensionalArray lineArray, ArrayHash mainArray) {
        LineList solution = new LineList(-1);

        LineList allIntervals = putAllIntervalsInList(lineArray);
        allIntervals.sort();

        List<Integer> addedObjectId = new ArrayList<>();

        Interval buf = allIntervals.getFirstSegment();
        solution.add(buf);
        allIntervals.remove(buf);
        addedObjectId.add(buf.getAreaId());
        mainArray.remove(buf.getAreaId());

        for (Interval interval :allIntervals) {

            if(solution.getLastSegment().getSecondDot() <= interval.getFirstDot()){
                solution.add(interval);
                if(addedObjectId.contains(interval.getAreaId())){
                    continue;
                }
                addedObjectId.add(interval.getAreaId());
                mainArray.remove(interval.getAreaId());
            }
        }

        return solution;
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

    private static void sortSegmentByPriority(ArrayList<Interval> list) {
        Comparator<Interval> comparator = Comparator.comparing(Interval::getPriority);
        list.sort(comparator);
    }

    private static void sortSegmentByLength(ArrayList<Interval> list) {
        Comparator<Interval> comparator = Comparator.comparing(Interval::getLength);
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
        for (AreaList line : arrayHash.values()) {
            for (Interval o :line) {
                sss.add(o);
            }
        }
        return sss;
    }
}
