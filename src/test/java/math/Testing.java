package math;

import math.entity.areasegments.AreaList;
import math.entity.array.*;
import math.entity.interval.Interval;
import math.entity.linesegments.Algorithms;
import math.entity.linesegments.LineList;
import math.entity.linesegments.Track;
import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class Testing {

    private static Separator separator;
    private static List<Track> iterResults = new ArrayList<>();

    public Testing(){
        separator = new Separator();
    }

    @Test
    public void Test() {
        long timeStart, timeEnd, timeToSeparateStart, timeToSeparateEnd, timeToAlgoStart, timeToAlgoEnd;
        long resultTime = 0;
        long timeToSeparateResult = 0;
        long timeToAlgoResult = 0;
        int end;
        int start = 0;
        int counterPasses = 0;

        Track result = new Track(-1);
        Simulation simulation = new Simulation();
        ArrayHash mainArray = simulation.genSimulationForTest();
        System.out.println("Создание обьектов завершено");

        HashMap<Integer, Track> separateArray;
        List<Integer> dropPoints = Simulation.createRandomDropPoints(simulation.amountSolutions,simulation.step);
        List<Integer> residuePoints = Simulation.setResiduePoints(simulation.firstArray,dropPoints,simulation.step);

        System.out.println("Количество точек сброса "+dropPoints.size());
        if(InputData.isNeedStatistics()) {
            System.out.println("ШАГ "+simulation.step);
            preparingStatistics(Algorithms.getAllIntervals(mainArray), mainArray, dropPoints);
        }

        timeStart = System.currentTimeMillis();
        for (Integer dropPoint :dropPoints) {

            end = dropPoint;
            timeToSeparateStart = System.currentTimeMillis();
            //
            separateArray = separator.separationArrays(mainArray,start,end);
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
            iterResults.add(endSol);
            result.addAll(endSol);

            if(mainArray.size() == 0){
                break;
            }

            start = end;

        }

        timeEnd = System.currentTimeMillis();
        resultTime += timeEnd - timeStart;
        System.out.println("Размер остатка " + mainArray.getHashPack().size());
        System.out.println("Остаток " + mainArray.getHashPack());
        System.out.println("ОБьекты на которые попала точка сброса " + residuePoints);
        List<Integer> areasId = new ArrayList<>();

        for (AreaList area :mainArray.getHashPack().values()) {
            areasId.add(area.getAreaId());
        }

        StringBuilder sb = new StringBuilder();
        for (Integer id : residuePoints) {
            if(!(areasId.contains(id))){
                sb.append(id).append(" ");
            }
        }

        System.out.println("Обьекты сьемки Которые не содержаться "+sb);
        System.out.println("Число ненулевых проходов (в которых был результат) "+ counterPasses);
        System.out.println("Результат "+result.size()+ " - число обьектов");
        System.out.println("Время на разбивку "+timeToSeparateResult/1000+ " секунд");
        System.out.println("Время на алгоритм "+timeToAlgoResult/1000+ " секунд");
        System.out.println("Общее время "+(resultTime) /1000+ " секунд");
        System.out.println();

        //System.out.println("ALL"+Algorithms.getResultsAll());
        //System.out.println("ITE"+iterResults);
//        for (int i = 1; i < Algorithms.getResultsAll().size(); i +=2) {
//            System.out.println(Algorithms.getResultsAll().get(i-1) +" "+ Algorithms.getResultsAll().get(i));
//        }
//
//        for (Track intervals :iterResults) {
//            System.out.println(intervals);
//        }

        if(InputData.isCheckResults()) {
            assertEquals(simulation.allResults.size(), result.size());
            for (int i = 0; i <  simulation.allResults.size(); i++) {
                assertEquals(simulation.allResults.getRangeOfIntervals().get(i).getAreaId(), result.getRangeOfIntervals().get(i).getAreaId());
                assertEquals(simulation.allResults.getRangeOfIntervals().get(i).getRoll(), result.getRangeOfIntervals().get(i).getRoll());
            }
        }
        System.out.println("Решения совпадают");

    }

    private static void preparingStatistics(LineList allIntervals,ArrayHash arrayHash,List<Integer> dropPoints) {
        System.out.println("Случайные точки сброса " + dropPoints);
        System.out.println("Количество обьектов "+arrayHash.getHashPack().size());

        int intervalSize = 0;
        for (AreaList segments :arrayHash.getHashPack().values()) {
            intervalSize += segments.size();
        }
        System.out.println("Количество интервалов "+intervalSize);
        if(InputData.isNeedStatisticsMaxMin()) {
            int maxLength = Collections.max(allIntervals.getCollection(), Comparator.comparing(Interval::getPriority)).getPriority();
            int minLength = Collections.min(allIntervals.getCollection(), Comparator.comparing(Interval::getPriority)).getPriority();
            System.out.println("Начало и конец максимальный крен " + minLength + "::" + maxLength);
            maxLength = Collections.max(allIntervals.getCollection(), Comparator.comparing(Interval::getSecondDot)).getSecondDot();
            minLength = Collections.min(allIntervals.getCollection(), Comparator.comparing(Interval::getFirstDot)).getFirstDot();
            System.out.println("Начало и конец максимальная длина " + minLength + "::" + maxLength);
        }

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

//    @Test
//    public void TestWithSerialize() {
//        long timeStart, timeEnd, timeToSeparateStart, timeToSeparateEnd, timeToAlgoStart, timeToAlgoEnd;
//        long resultTime = 0;
//        long resultTimeGreedy = 0;
//        long resultTimeNadir = 0;
//        long timeToSeparateResult = 0;
//        long timeToAlgoResult = 0;
//        int end;
//        int start = 0;
//        int counterPasses = 0;
//        Track result = new Track(-1);
//        ArrayHash mainArray = null;
//        Simulation simulation = new Simulation();
//
//        if(false) {
//            System.out.println("Создание новой сериализации");
//            try (FileOutputStream outputStream = new FileOutputStream("E:\\mainArray.ser")) {
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//                mainArray = simulation.genSimulationForTest();
//                System.out.println("Создание обьектов завершено");
//                objectOutputStream.writeObject(mainArray);
//                objectOutputStream.close();
//                outputStream.close();
//                System.out.println("Создание сериализации завершено");
//                System.exit(0);
//            } catch (IOException e) {
//                e.getStackTrace();
//            }
//        }
//
//        if(true) {
//            System.out.println("Использование сериализации");
//            try{
//                FileInputStream fileInputStream = new FileInputStream("E:\\mainArray.ser");
//                ObjectInputStream objectInputStreamSem = new ObjectInputStream(fileInputStream);
//                mainArray = (ArrayHash) objectInputStreamSem.readObject();
//                objectInputStreamSem.close();
//                fileInputStream.close();
//            }
//            catch (IOException e) {
//                e.getMessage();
//                e.getStackTrace();
//                System.exit(0);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Чтение обьектов завершено");
//        }
//
//        if(mainArray == null){
//            System.out.println("Создаем новый MainArray");
//            mainArray = simulation.genSimulationForTest();
//        }
//
//        List<Integer> dropPoints = mainArray.dropPoints;
//        List<Integer> residuePoints = new ArrayList<>();
//        System.out.println(dropPoints);
//        System.out.println(residuePoints);
//        HashMap<Integer, Track> separateArray;
//
//        System.out.println("Количество точек сброса "+dropPoints.size());
//        if(InputData.isNeedStatistics()) {
//            System.out.println("ШАГ "+mainArray.step);
//            preparingStatistics(Algorithms.getAllIntervals(mainArray), mainArray, dropPoints);
//        }
//
//        for (Integer dropPoint :dropPoints) {
//
//            timeStart = System.currentTimeMillis();
//            end = dropPoint;
//            timeToSeparateStart = System.currentTimeMillis();
//            //
//            separateArray = separator.separationArrays(mainArray,start,end);
//            //
//            timeToSeparateEnd= System.currentTimeMillis();
//            timeToSeparateResult += timeToSeparateEnd - timeToSeparateStart;
//
//            if(separateArray.size() == 0){
//                start = end;
//                continue;
//            }
//            counterPasses++;
//
//            timeToAlgoStart = System.currentTimeMillis();
//            //
//            Track endSol = Algorithms.nadirAlgorithm(separateArray,mainArray);
//            //
//            timeToAlgoEnd = System.currentTimeMillis();
//            timeToAlgoResult += timeToAlgoEnd - timeToAlgoStart;
//            iterResults.add(endSol);
//            result.addAll(endSol);
//
//            if(mainArray.size() == 0){
//                break;
//            }
//
//            start = end;
//            timeEnd = System.currentTimeMillis();
//            resultTime += timeEnd - timeStart;
//
//        }
//
//        resultTime = resultTime - resultTimeGreedy - resultTimeNadir;
//        System.out.println("Размер остатка " + mainArray.getHashPack().size());
//        System.out.println("Остаток " + mainArray.getHashPack());
//        System.out.println("ОБьекты на которые попала точка сброса " + residuePoints);
//        List<Integer> areasId = new ArrayList<>();
//
//        for (AreaList area :mainArray.getHashPack().values()) {
//            areasId.add(area.getAreaId());
//        }
//        StringBuilder sb = new StringBuilder();
//        for (Integer id : residuePoints) {
//            if(!(areasId.contains(id))){
//                sb.append(id).append(" ");
//            }
//        }
//        System.out.println("Обьекты сьемки Которые не содержаться "+sb);
//        System.out.println("Число ненулевых проходов (в которых был результат) "+ counterPasses);
//        System.out.println("Результат "+result.size()+ " - число обьектов");
//        System.out.println("Время на разбивку "+timeToSeparateResult/1000+ " секунд");
//        System.out.println("Время на алгоритм "+timeToAlgoResult/1000+ " секунд");
//        System.out.println("Общее время "+(resultTime) /1000+ " секунд");
//        System.out.println();
//
//        //System.out.println("ALL"+Algorithms.getResultsAll());
//        //System.out.println("ITE"+iterResults);
//        //        for (int i = 1; i < Algorithms.getResultsAll().size(); i +=2) {
//        //            System.out.println(Algorithms.getResultsAll().get(i-1) +" "+ Algorithms.getResultsAll().get(i));
//        //        }
//        //
//        //        for (Track intervals :iterResults) {
//        //            System.out.println(intervals);
//        //        }
//
//        if(InputData.isCheckResults()) {
//            assertEquals(mainArray.allResults.size(), result.size());
//            for (int i = 0; i <  mainArray.allResults.size(); i++) {
//                assertEquals(mainArray.allResults.getRangeOfIntervals().get(i).getAreaId(), result.getRangeOfIntervals().get(i).getAreaId());
//                assertEquals(mainArray.allResults.getRangeOfIntervals().get(i).getLine(), result.getRangeOfIntervals().get(i).getLine());
//            }
//        }
//        System.out.println("Решения совпадают");
//
//    }
}
