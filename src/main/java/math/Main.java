package math;

import math.entity.*;
import math.entity.AreaSegments.Area;
import math.entity.Array.ArrayHash;
import math.entity.Array.Selection;
import math.entity.Array.TwoDimensionalArray;
import math.entity.LineSegments.LineList;
import math.entity.LineSegments.Track;
import math.entity.interval.Interval;

import java.util.*;
import java.util.function.Predicate;

import static math.Simulation.residuePoints;

public class Main {
    public static void main(String[] args) {
        ArrayHash mainArray = Simulation.genSimulationForTest();
        for (SegmentPack segmentPack :mainArray.values()) {
            System.out.println(segmentPack);
        }

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
            Selection selection;

            Track result = new Track(-1);
            LineList solution;

            long resultTime = 0, timeStart, timeEnd, resultTimeGreedy = 0,resultTimeNadir = 0;
            int end;
            int start = 0;
            int gr = 0;
            int counterPasses = 0;


            Map<Integer,Integer> indexMask = new HashMap<>();

            initIndexMask(indexMask,mainArray);


            List<Integer> dropPoints = Simulation.dropPoints;
            if(InputData.getNeedStatistics()) {
                preparingStatistics(Algorithms.getAllIntervals(mainArray), mainArray, dropPoints);
            }
            for (Integer dropPoint :dropPoints) {
                timeStart = System.currentTimeMillis();
                end = dropPoint;
                //solution = separator.separationArrays222(mainArray,indexMask,start,end);
                solution = separator.separationArrays(mainArray,start,end);

                if(solution.size() == 0){
                    start = end;
                    continue;
                }
                counterPasses++;

                if(gr == 0) {
                    System.out.println("NadirStart");
                    Selection nadir = separator.createSelection(solution);
                    resultTimeNadir = nadirTest(nadir);
                    System.out.println("Время чтобы перебрать все Надирным "+resultTimeNadir/1000+ " секунд");

//                    TwoDimensionalArray greedy = separator.createLineArray(solution);
//                    resultTimeGreedy = greedyTest(greedy);

                    gr = 1;
                }

                selection = separator.createSelection(solution);

                Track endSol = Algorithms.nadirAlgorithm(selection,mainArray);
                result.addAll(endSol);

                if(mainArray.size() == 0){
                    break;
                }

                start = end;
                timeEnd = System.currentTimeMillis();
                resultTime += timeEnd - timeStart;

            }


            resultTime = resultTime - resultTimeGreedy - resultTimeNadir;
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

            System.out.println("Общее время "+(resultTime) /1000+ " секунд");
            System.out.println();
    }
    private static long nadirTest(Selection nadir) {
        long timeStart = System.currentTimeMillis();
        int resultBunch = Algorithms.nadirAlgorithmAll(nadir,nadir.getTracksCount(),0,0);
        System.out.println(resultBunch + " Количество проходов надирным алгоритмом");
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
    }

    private static long greedyTest(TwoDimensionalArray greedy) {
        long timeStart = System.currentTimeMillis();
        greedy = Algorithms.greedyAlgorithm(greedy);
        System.out.println(greedy.size() + " Количество проходов жадным алгоритмом");
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
    }

    private static void initIndexMask(Map<Integer, Integer> indexMask, ArrayHash mainArray) {
        for (SegmentPack segments :mainArray.values()) {
            indexMask.put(segments.getFirstSegment().getAreaId(),0);
        }
    }


    private static void preparingStatistics(LineList allIntervals,ArrayHash arrayHash,List<Integer> dropPoints) {
        System.out.println("Точки сброса " + dropPoints);
        System.out.println("Количество обьектов "+arrayHash.getHashPack().size());

        int intervalSize = 0;
        for (SegmentPack segments :arrayHash.getHashPack().values()) {
            intervalSize += segments.size();
        }
        System.out.println("Количество интервалов "+intervalSize);


        allIntervals.getCollection().sort(Comparator.comparing(Interval::getPriority));
        System.out.println("Начало и конец максимальный крен "+allIntervals.getFirstSegment().getPriority() +"::" + allIntervals.getLastSegment().getPriority());
        allIntervals.sort();
        System.out.println("Начало и конец максимальная длина "+allIntervals.getFirstSegment().getFirstDot() +"::" + allIntervals.getLastSegment().getSecondDot());

    }


}
