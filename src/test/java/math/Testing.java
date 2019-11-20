package math;

import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import math.entity.AreaSegments.Area;
import math.entity.Array.*;
import math.entity.LineSegments.Line;
import math.entity.LineSegments.LineList;
import math.entity.LineSegments.Track;
import math.entity.SegmentPack;
import math.entity.interval.Interval;
import math.spring.Algo;
import math.spring.Sepa;
import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.stream.Stream;

import static math.Simulation.residuePoints;
import static math.Simulation.step;
import static org.junit.Assert.assertEquals;

public class Testing {

    private static Algo algorithms;
    private static Sepa separator;

    public Testing(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        algorithms = context.getBean(Algo.class);
        separator = new Separator();
    }


    @Test
    public void Test() {
        long timeStart, timeEnd, timeToSeparateStart, timeToSeparateEnd, timeToAlgoStart, timeToAlgoEnd;
        long resultTime = 0;
        long resultTimeGreedy = 0;
        long resultTimeNadir = 0;
        long timeToSeparateResult = 0;
        long timeToAlgoResult = 0;
        int end;
        int start = 0;
        int gr = 0;
        int counterPasses = 0;
        Track result = new Track(-1);
        ArrayHash mainArray = Simulation.genSimulationForTest();
        System.out.println("Создание обьектов завершено");


        List<Integer> dropPoints = Simulation.dropPoints;
        System.out.println("Количество точек сброса "+dropPoints.size());
        if(InputData.getNeedStatistics()) {
            preparingStatistics(Algorithms.getAllIntervals(mainArray), mainArray, dropPoints);
        }

        for (Integer dropPoint :dropPoints) {

            timeStart = System.currentTimeMillis();
            end = dropPoint;
            timeToSeparateStart = System.currentTimeMillis();
            //
            SeparateArray separateArray = separator.separationArrays(mainArray,start,end);
            //
            timeToSeparateEnd= System.currentTimeMillis();
            timeToSeparateResult += timeToSeparateEnd - timeToSeparateStart;

            if(separateArray.size() == 0){
                start = end;
                continue;
            }
            counterPasses++;

            timeToAlgoStart = System.currentTimeMillis();
            //
            Track endSol = Algorithms.nadirAlgorithm(separateArray,mainArray);
            //
            timeToAlgoEnd = System.currentTimeMillis();
            timeToAlgoResult += timeToAlgoEnd - timeToAlgoStart;
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
        System.out.println("Время на разбивку "+timeToSeparateResult/1000+ " секунд");
        System.out.println("Время на алгоритм "+timeToAlgoResult/1000+ " секунд");
        System.out.println("Общее время "+(resultTime) /1000+ " секунд");
        System.out.println();
        if(InputData.getCheckResults()) {
            for (int i = 0; i <  Simulation.allResults.size(); i++) {
                assertEquals(Simulation.allResults.getRangeOfIntervals().get(i).getAreaId(), result.getRangeOfIntervals().get(i).getAreaId());
            }
        }
        System.out.println("Решения совпадают");

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
        System.out.println("ШАГ "+Simulation.step);
        System.out.println("Случайные точки сброса " + dropPoints);
        System.out.println("Количество обьектов "+arrayHash.getHashPack().size());

        int intervalSize = 0;
        for (SegmentPack segments :arrayHash.getHashPack().values()) {
            intervalSize += segments.size();
        }
        System.out.println("Количество интервалов "+intervalSize);


        //allIntervals.getCollection().sort(Comparator.comparing(Interval::getPriority));
        //System.out.println("Начало и конец максимальный крен "+allIntervals.getFirstSegment().getPriority() +"::" + allIntervals.getLastSegment().getPriority());
        //allIntervals.sort();
        //System.out.println("Начало и конец максимальная длина "+allIntervals.getFirstSegment().getFirstDot() +"::" + allIntervals.getLastSegment().getSecondDot());

    }

    public static void showIntervalSizeAndFullSize(Object object){
        System.out.println("oppa");
        GraphLayout graphLayout = GraphLayout.parseInstance(object);
        for (Class<?> key : graphLayout.getClasses()) {
            if(key.getName().contains("Segment.Segment")) {
                System.out.println(graphLayout.getClassCounts().count(key));
                System.out.println(graphLayout.getClassSizes().count(key));
            }
        }
        System.out.println(graphLayout.totalSize()/1024/1024 + " Мегабайт");
    }
}
