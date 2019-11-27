package math;

import math.entity.areasegments.Area;
import math.entity.areasegments.AreaList;
import math.entity.array.ArrayHash;
import math.entity.array.TwoDimensionalArray;
import math.entity.array.TwoDimensionalArrayList;
import math.entity.linesegments.Algorithms;
import math.entity.linesegments.LineList;
import math.entity.linesegments.LineSet;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import org.junit.Test;

import java.util.*;

public class SieveOldTest {

    @Test
    public void sieve(){
        Separator separator = new Separator();
        TwoDimensionalArray buf;

        LineList result = new LineList(-1);
        LineList solution;

        long resultTime = 0, timeStart, timeEnd, resultTimeGreedy = 0;
        int end;
        int start = 0;
        int gr = 0;
        int counterPasses = 0;

        Simulation simulation = new Simulation();
        ArrayHash mainArray = simulation.genSimulationForTest();


/*        for (SegmentPack segments :mainArray.getHashPack().values()) {
            System.out.println(segments.size());
        }
        System.out.println(mainArray.size());*/


        System.out.println("Создание обьектов завершено");
        LineList allIntervals;

        //LineSet allSetIntervals = Algorithms.getAllSetIntervals(mainArray);
        //LineList allIntervals = Algorithms.getAllIntervalsMerge(mainArray);

        allIntervals = Algorithms.getAllIntervals(mainArray);


        int step = simulation.step;
        int loopStart = 0;
        int loopEnd = InputData.getDropPoints() * step;

        List<Integer> dropPoints = new ArrayList<>();
        while (!(loopStart > loopEnd)){
            loopStart = loopStart + step;
            dropPoints.add(loopStart);
        }
        preparingStatistics(allIntervals, mainArray,dropPoints);


        for (Integer dropPoint :dropPoints) {

            timeStart = System.currentTimeMillis();
            allIntervals = Algorithms.getAllIntervals(mainArray);
            allIntervals.sort();
            end = dropPoint;
           // System.out.println("tchkDrop " +end);
            solution = separator.separation(allIntervals,start,end);

            if(solution.size() == 0){
                start = end;
                continue;
            }
            counterPasses++;

            buf = separator.createLineArray(solution);

            if(gr == 0) {
                timeStart = System.currentTimeMillis();
                TwoDimensionalArray greedy = separator.createLineArray(solution);
                //greedy = algorithms.greedyAlgorithm(greedy);
                System.out.println(greedy.size() + " Количество проходов жадным алгоритмом");
                gr = 1;
                timeEnd = System.currentTimeMillis();
                resultTimeGreedy = timeEnd - timeStart;
            }

            solution = Algorithms.greedyAlgorithmForHashSimulation(buf,mainArray);
            result.addAll(solution);
            if(mainArray.size() == 0){
                break;
            }
            start = end;
            timeEnd = System.currentTimeMillis();
            resultTime += timeEnd - timeStart;
        }


        resultTime = resultTime - resultTimeGreedy;
        System.out.println("Размер остатка " + mainArray.getHashPack().size());

        System.out.println("Число ненулевых проходов (в которых был результат) "+ counterPasses);
        //System.out.println("Остаток " + mainArray.getHashPack().values()+ " - число обьектов");
        List<Integer> list = new ArrayList<>();
        for (Interval interval :result) {
            if(list.contains(interval.getAreaId())){
                System.out.println(interval);
            }
            list.add(interval.getAreaId());
        }
        //System.out.println("Результат "+result);
        System.out.println("Результат "+result.size()+ " - число обьектов");
        System.out.println("Общее время "+(resultTime)+ " миллисекунд");
        System.out.println();
    }

    @Test
    public void sieveWithMask(){
        Separator separator = new Separator();
        TwoDimensionalArray buf;

        LineList result = new LineList(-1);
        LineList solution;

        long resultTime = 0, timeStart, timeEnd, resultTimeGreedy = 0;
        int end;
        int start = 0;
        int gr = 0;
        int counterPasses = 0;
        Simulation simulation = new Simulation();
        ArrayHash mainArray = simulation.genSimulationForTest();
        Map<Integer,Integer> indexMask = new HashMap<>();

        initIndexMask(indexMask,mainArray);

        System.out.println("Создание обьектов завершено");

        int step = simulation.step;
        int loopStart = step;
        int loopEnd = InputData.getDropPoints() * step;

        List<Integer> dropPoints = new ArrayList<>();
        while (!(loopStart > loopEnd)){
            dropPoints.add(loopStart);
            loopStart = loopStart + (step);
        }
        preparingStatistics(Algorithms.getAllIntervals(mainArray), mainArray, dropPoints);
        for (Integer dropPoint :dropPoints) {
            timeStart = System.currentTimeMillis();
            end = dropPoint;
            //System.out.println("tchkDrop " +end);
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
        }


        resultTime = resultTime - resultTimeGreedy;
        System.out.println("Размер остатка " + mainArray.getHashPack().size());
        System.out.println("Остаток " + mainArray.getHashPack());

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

    private long greedyTest(TwoDimensionalArray greedy) {
        long timeStart = System.currentTimeMillis();
        //greedy = Algorithms.greedyAlgorithm(greedy);
        System.out.println(greedy.size() + " Количество проходов жадным алгоритмом");
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
    }

    private void initIndexMask(Map<Integer, Integer> indexMask, ArrayHash mainArray) {
        for (AreaList segments :mainArray.values()) {
            indexMask.put(segments.getFirstSegment().getAreaId(),0);
        }
    }

    private LineList copySet(LineSet allSetIntervals) {
        LineList list = new LineList(-1);
        for (Interval interval :allSetIntervals) {
            list.add(interval);
        }
        return list;
    }

    private void preparingStatistics(LineList allIntervals,ArrayHash arrayHash,List<Integer> dropPoints) {
        System.out.println("Точки сброса " + dropPoints);
        System.out.println("Количество обьектов "+arrayHash.getHashPack().size());

        int intervalSize = 0;
        for (AreaList segments :arrayHash.getHashPack().values()) {
            intervalSize += segments.size();
        }
        System.out.println("Количество интервалов "+intervalSize);


        allIntervals.getCollection().sort(Comparator.comparing(Interval::getPriority));
        System.out.println("Начало и конец максимальный крен "+allIntervals.getFirstSegment().getPriority() +"::" + allIntervals.getLastSegment().getPriority());
        allIntervals.sort();
        System.out.println("Начало и конец максимальная длина "+allIntervals.getFirstSegment().getFirstDot() +"::" + allIntervals.getLastSegment().getSecondDot());

    }
}
