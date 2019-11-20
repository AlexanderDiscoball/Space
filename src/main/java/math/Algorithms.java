package math;

import math.annotation.AlgorithmsType;
import math.annotation.GetMethodTime;
import math.entity.AreaSegments.Area;
import math.entity.Array.*;
import math.entity.LineSegments.*;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import math.spring.Algo;

import java.util.*;

import static math.Simulation.residuePoints;

@AlgorithmsType
public class Algorithms implements Algo {
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

    public static LineList putAllIntervalsInListHash(ArrayHash twoDimensionalArrayList) {
        LineList stackSegmentsList = new LineList(-2);
        for (SegmentPack segments :twoDimensionalArrayList.values()) {
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

    public static void fullSepaAlgo(){
        ArrayHash mainArray = Simulation.genSimulationForTest();
        System.out.println("Создание обьектов завершено");
        System.out.println("ШАГ "+Simulation.step);
        //        System.out.println("oppa");
        //        GraphLayout graphLayout = GraphLayout.parseInstance( mainArray);
        //        for (Class<?> key : graphLayout.getClasses()) {
        //            if(key.getName().contains("Segment.Segment")) {
        //                System.out.println(graphLayout.getClassCounts().count(key));
        //                System.out.println(graphLayout.getClassSizes().count(key));
        //            }
        //        }
        //        System.out.println(graphLayout.totalSize()/1024/1024 + " Мегабайт");

        Separator separator = new Separator();
        TwoDimensionalArray buf;

        LineList result = new LineList(-1);
        LineList solution;

        long resultTime = 0, timeStart, timeEnd, resultTimeGreedy = 0;
        int end;
        int start = 0;
        int gr = 0;
        int counterPasses = 0;


        Map<Integer,Integer> indexMask = new HashMap<>();

        initIndexMask(indexMask,mainArray);


        List<Integer> dropPoints = Simulation.dropPoints;

        //preparingStatistics(Algorithms.getAllIntervals(mainArray), mainArray, dropPoints);
        for (Integer dropPoint :dropPoints) {
            timeStart = System.currentTimeMillis();
            end = dropPoint;
            //solution = separator.separationArrays222(mainArray,indexMask,start,end);
            solution =null; //separator.separationArrays(mainArray,start,end);

            if(solution.size() == 0){
                start = end;
                continue;
            }
            counterPasses++;

            buf = separator.createLineArray(solution);

            if(gr == 0) {
                TwoDimensionalArray greedy = separator.createLineArray(solution);
                resultTimeGreedy = greedyTest(greedy);
                gr = 1;
            }

            solution = Algorithms.greedyAlgorithmForHashSimulation(buf,mainArray);
            result.addAll(solution);

            if(mainArray.size() == 0){
                break;
            }

            start = end;
            timeEnd = System.currentTimeMillis();
            resultTime += timeEnd - timeStart;
            //
        }


        resultTime = resultTime - resultTimeGreedy;
        System.out.println("Размер остатка " + mainArray.getHashPack().size());
        System.out.println("Остаток " + mainArray.getHashPack());
        System.out.println("residuePoints" + residuePoints);
        List<Integer> areasId = new ArrayList<>();
        for (SegmentPack area :mainArray.getHashPack().values()) {
            areasId.add(((Area)area).getAreaId());
        }
        for (Integer id :residuePoints) {
            if(!(areasId.contains(id))){
                System.out.println("Обьект сьемки "+id+" не содержится");
            }
        }


        System.out.println("Число ненулевых проходов (в которых был результат) "+ counterPasses);
        //System.out.println("Остаток " + mainArray.getHashPack().values()+ " - число обьектов");
        System.out.println("Результат "+result.size()+ " - число обьектов");
        List<Integer> list = new ArrayList<>();
        //        for (Segment segment :result) {
        //            if(list.contains(segment.getAreaId())){
        //                System.out.println(segment);
        //            }
        //            list.add(segment.getAreaId());
        //        }
        //System.out.println("Результат "+result);

        System.out.println("Общее время "+(resultTime) /1000+ " секунд");
        System.out.println();
    }

    public static Track nadirAlgorithm(SeparateArray separateArray, ArrayHash mainArray){
        Map.Entry<Integer,Track> entry = separateArray.getSeparatePack().entrySet().iterator().next();
        Track solution = entry.getValue();
        separateArray.remove(entry.getKey());

        solution.getRangeOfIntervals().sort(Comparator.comparing(Interval::getSecondDot));

        for (Interval interval : solution.getRangeOfIntervals()) {
            mainArray.remove(interval.getAreaId());
        }
        for(Track mergedTrack: separateArray.values()) {
            solution.mergeWithoutCrossings(mergedTrack,mainArray);
//            if(solution.getNumberOfTracksWithIntervalsOffTheSolution() > InputData.getVoluntaristCriteria()) {
//                solution.resetNumberOfTracksWithIntervalsOffTheSolution();
//                break;
//            }
        }
        return solution;
    }


    public static Track nadirAlgorithmWhenSort(Selection selection, ArrayHash mainArray){
        Track solution = selection.iterator().next();

        selection.remove(solution);
        for (Interval interval : solution.getRangeOfIntervals()) {
            mainArray.remove(interval.getAreaId());
        }

        for(Track mergedTrack: selection) { //replace i on smth sensible
            solution.mergeWithoutCrossingsDontNeedSort(mergedTrack,mainArray);
            if(solution.getNumberOfTracksWithIntervalsOffTheSolution() > InputData.getVoluntaristCriteria()) { //voluntarist criteria
                solution.resetNumberOfTracksWithIntervalsOffTheSolution();
                break;
            }
        }

        //System.out.println("Solution "+solution);
        return solution;
    }

    public static int resultPasses = 0;

    @GetMethodTime
    public ArrayList nadirAlgorithmAll(Selection bunchOfTracks,int sizeOfBunchOfTracks, int trackToStartNo,  int passageNum){
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
            trackToStartNo++;
            for(int i = trackToStartNo; i < sizeOfBunchOfTracks; i++) { //replace i on smth sensible
                if(bunchOfTracks.getTrackNo(i).getRangeOfIntervals().isEmpty()) {
                    continue;
                }
                else {
                    bunchOfTracksIsEmpty = false;
                    cross = solution.mergeWithoutCrossingsAll(bunchOfTracks.getTrackNo(i)); //true - cross or false - don't cross
                    if(cross == true && changed == false){
                        trackToStartNo = i;
                        changed = true;
                    }
                    else
                    if(changed == false && i != (sizeOfBunchOfTracks - 1))
                        trackToStartNo++;
                    else if(changed == false && i == (sizeOfBunchOfTracks - 1)) {
                        trackToStartNo++;
                        bunchOfTracksIsEmpty = true;
                    }
                }

//                if(solution.getNumberOfTracksWithIntervalsOffTheSolution() > InputData.getVoluntaristCriteria()) { //voluntarist criteria
//                    solution.resetNumberOfTracksWithIntervalsOffTheSolution();
//                    break;
//                }
            }
            allSolution.addAll(solution);
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

    private static void initIndexMask(Map<Integer, Integer> indexMask, ArrayHash mainArray) {
        for (SegmentPack segments :mainArray.values()) {
            indexMask.put(segments.getFirstSegment().getAreaId(),0);
        }
    }

    public static LineList greedyAlgorithmForSimulation(TwoDimensionalArray twoDimensionalArray, TwoDimensionalArray mainArray,ArrayList<Integer> mask) {
        TwoDimensionalArrayList ways = new TwoDimensionalArrayList();
        LineList way;

        LineSet allIntervals = putAllIntervalsInSet(twoDimensionalArray);

        way = new LineList(-1);
        Interval buf = allIntervals.getFirstSegment();
        way.add(buf);
        int index;
        SegmentPack segmentPack;
        allIntervals.remove(buf);
        for (Interval interval :allIntervals) {
            if(way.getLastSegment().getSecondDot() <= interval.getFirstDot()){
                way.add(interval);
                index = mask.indexOf(interval.getAreaId());
                if(index > 0) {
                    mainArray.remove(index);
                    mask.remove(index);
                }
            }
        }

        return way;
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
        for (SegmentPack line : arrayHash.getHashPack().values()) {
            sss.addAll(line);
        }
        return sss;
    }
    public static LineList getAllIntervalsMerge(ArrayHash arrayHash) {
        List<Interval> sss = new ArrayList<>();
        for (SegmentPack line : arrayHash.getHashPack().values()) {
            sss = merge2(((List<Interval>) line.getCollection()), sss);
        }
        return new LineList(sss);
    }

    public static LineSet getAllSetIntervals(ArrayHash arrayHash){
        LineSet sss = new LineSet(-1);
        for (SegmentPack line : arrayHash.getHashPack().values()) {
            sss.addAll(line);
        }
        return sss;
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
    public static List<Interval> merge2(List<Interval> lineList, List<Interval> line) {

        ListIterator<Interval> first = lineList.listIterator();
        ListIterator<Interval> second = line.listIterator();
        List<Interval> list = new ArrayList<>();
        Interval firstInterval = null;
        Interval secondInterval = null;
        if(first.hasNext() && second.hasNext()) {
            firstInterval = first.next();
            secondInterval = second.next();
        }


        while(first.hasNext() && second.hasNext()){
            if(firstInterval.getSecondDot() < secondInterval.getSecondDot()) {
                list.add(firstInterval);
                firstInterval = first.next();
            }
            else {
                list.add(secondInterval);
                secondInterval = second.next();
            }
        }

        if(!first.hasNext()){
            while (second.hasNext()){
                list.add(second.next());
            }
        }
        else {
            while (first.hasNext()){
                list.add(first.next());
            }
        }

        return list;
    }

}
