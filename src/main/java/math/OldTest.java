package math;

import math.entity.AreaSegments.Area;
import math.entity.Array.ArrayHash;
import math.entity.Array.Selection;
import math.entity.Array.TwoDimensionalArray;
import math.entity.LineSegments.LineList;
import math.entity.LineSegments.Track;
import math.entity.SegmentPack;
import math.entity.interval.Interval;
import math.spring.Algo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;


import static math.Simulation.residuePoints;
public class OldTest {

    private static Algo algorithms;

    public OldTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        algorithms = context.getBean(Algo.class);
    }

    public static Track allResults;
    public static void main(String[] args) {
        new OldTest();
        long resultTime = 0, timeStart, timeEnd, resultTimeGreedy = 0,resultTimeNadir = 0;
        int end;
        int start=0;
        int gr = 0;
        int counterPasses = 0;
        System.out.println("Число Интервалов " + InputData.getChannelAmount()*InputData.getSegmentsAmount()*InputData.getDropPoints());
        ArrayHash mainArray = Simulation.genSimulationForTest();
//                for (SegmentPack segmentPack :mainArray.values()) {
//                    System.out.println(segmentPack);
//                }

        System.out.println("Создание обьектов завершено");
        System.out.println("ШАГ " + Simulation.step);

        Separator separator = new Separator();
        Selection selection;

        Track result = new Track(-1);
        LineList solution;
        Track subRes;

        List<Integer> dropPoints = Simulation.dropPoints;

        if (InputData.getNeedStatistics()) {
            preparingStatistics(Algorithms.getAllIntervals(mainArray), mainArray, dropPoints);
        }
        for (Integer dropPoint : dropPoints) {
                timeStart = System.currentTimeMillis();
                end = dropPoint;
                solution = separator.separationArrays222(mainArray,start,end);

                if (solution.size() == 0) {
                    start = end;
                    continue;
                }
                counterPasses++;

                selection = separator.createSelection(solution);

                if (gr == 0) {
                    resultTimeGreedy = nadirTest(solution,separator);
                    gr = 1;
                }

                subRes = Algorithms.nadirAlgorithmWhenSort(selection, mainArray);
                result.addAll(subRes);

                if (mainArray.size() == 0) {
                    break;
                }

                start = end;
                timeEnd = System.currentTimeMillis();
                resultTime += timeEnd - timeStart;
            }

        resultTime = resultTime - resultTimeGreedy - resultTimeNadir;
        System.out.println("Размер остатка " + mainArray.getHashPack().size());
        System.out.println("Остаток " + mainArray.getHashPack());
        System.out.println("ОБьекты на которые попала точка сброса" + residuePoints);
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
        System.out.println("Результат "+result.size()+ " - число обьектов");
        System.out.println("Общее время "+(resultTime) /1000+ " секунд");
        System.out.println();
        if(InputData.getCheckResults()) {
            for (int i = 0; i < allResults.size(); i++) {
                if ((allResults.getRangeOfIntervals().get(i).getAreaId() == result.getRangeOfIntervals().get(i).getAreaId())) {
                    System.out.println(allResults.getRangeOfIntervals().get(i) + "::" + result.getRangeOfIntervals().get(i));
                } else {
                    System.out.println("Ошибкааааа ");
                    break;
                }
            }
        }
    }

        private static void preparingStatistics (LineList allIntervals, ArrayHash arrayHash, List < Integer > dropPoints)
        {
            System.out.println("Точки сброса " + dropPoints);
            System.out.println("Количество обьектов " + arrayHash.getHashPack().size());

            int intervalSize = 0;
            for (SegmentPack segments : arrayHash.getHashPack().values()) {
                intervalSize += segments.size();
            }
            System.out.println("Количество интервалов " + intervalSize);


            allIntervals.getCollection().sort(Comparator.comparing(Interval::getPriority));
            System.out.println("Начало и конец максимальный крен " + allIntervals.getFirstSegment().getPriority() + "::" + allIntervals.getLastSegment().getPriority());
            allIntervals.sort();
            System.out.println("Начало и конец максимальная длина " + allIntervals.getFirstSegment().getFirstDot() + "::" + allIntervals.getLastSegment().getSecondDot());

        }
    private static long nadirTest(LineList solution,Separator separator) {
        Selection nadir = separator.createSelection(solution);
        long timeStart = System.currentTimeMillis();
        for (Track track :nadir) {
            track.getRangeOfIntervals().sort(Comparator.comparing(Interval::getSecondDot));
        }
        ArrayList list = algorithms.nadirAlgorithmAll(nadir,nadir.getTracksCount(),0,0);
        int resultBunch  =(Integer) list.get(0);
        allResults = (Track)list.get(1);
        System.out.println(resultBunch + " Количество проходов надирным алгоритмом");
        long timeEnd = System.currentTimeMillis();
        return timeEnd - timeStart;
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
    }

