package math;

import math.entity.Array.TwoDimensionalArrayList;
import math.entity.LineSegments.LineList;
import math.entity.LineSegments.LineSet;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArraySet;
import math.entity.Segment.Segment;
import math.entity.SegmentPack;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SergeyAlgoTest {
    private static int buf = 0;
    private static Integer sizeInputData = 0;
    private static Integer lengthInputData = 0;


    @Test
    public void allAlgorithms(){
        List<LinkedList<SergAlg>> tracks = GeneratorRandom.genareteSergEntity();
        setTempToCheck(tracks);

        long start = System.currentTimeMillis();

        greedy(tracks);

        long end = System.currentTimeMillis();
        System.out.println("Время на жадный алгоритм "+(end - start)+ " миллисекунд");
        System.out.println();

        start = System.currentTimeMillis();

        dinamic(tracks);

        end = System.currentTimeMillis();
        System.out.println("Время на динамическим алгоритмом (по длине) "+(end - start)+ " миллисекунд");
        System.out.println();

        start = System.currentTimeMillis();

        dinamicSize(tracks);

        end = System.currentTimeMillis();
        System.out.println("Время на динамическим алгоритмом (по количеству) "+(end - start)+ " миллисекунд");
        System.out.println();

        start = System.currentTimeMillis();

        fasterDinamic(tracks);

        end = System.currentTimeMillis();
        System.out.println("Время на динамическим алгоритмом быстрый "+(end - start)+ " миллисекунд");
        System.out.println();

        start = System.currentTimeMillis();

        nadir(tracks);

        end = System.currentTimeMillis();
        System.out.println("Время на надирный алгоритм "+(end - start)+ " миллисекунд");
        System.out.println();
    }

    @Test
    public void greedAndNadir(){
        List<LinkedList<SergAlg>> tracks = GeneratorRandom.genareteSergEntity();
        setTempToCheck(tracks);
        long start = System.currentTimeMillis();

        greedy(tracks);

        long end = System.currentTimeMillis();
        System.out.println("Время на жадный алгоритм "+(end - start)+ " миллисекунд");
        System.out.println();

//        start = System.currentTimeMillis();
//
//       fasterDinamic(tracks);
//
//        end = System.currentTimeMillis();
//        System.out.println("Время на быстрый динамический "+(end - start)+ " миллисекунд");
//        System.out.println();


        start = System.currentTimeMillis();

        nadir(tracks);

        end = System.currentTimeMillis();
        System.out.println("Время на надирный алгоритм "+(end - start)+ " миллисекунд");
        System.out.println();
    }
    @Test
    public void onlyNadir(){
        List<LinkedList<SergAlg>> tracks = GeneratorRandom.genareteSergEntity();


        long start = System.currentTimeMillis();

        nadir(tracks);

        long end = System.currentTimeMillis();
        System.out.println("Время на надирный алгоритм "+(end - start)+ " миллисекунд");
        System.out.println();
    }


    public void setTempToCheck(List<LinkedList<SergAlg>> tracks){
        for (LinkedList<SergAlg> SergAlgs :tracks) {
            sizeInputData += SergAlgs.size();
            for (SergAlg sergAlg : SergAlgs) {
                lengthInputData += sergAlg.getLen();
            }
        }
    }

    public List<LinkedList<SergAlg>> convertToSergEntity(TwoDimensionalArray twoDimensionalArraySet){
        List<LinkedList<SergAlg>> tracks=new ArrayList<>();
        System.out.println(twoDimensionalArraySet.size());
        for (SegmentPack segmentPack :twoDimensionalArraySet) {
            LinkedList<SergAlg> temporary=new LinkedList<>();
            for (Segment segment :segmentPack) {
                temporary.add(new SergAlg(segment.getFirstDot(),segment.getSecondDot(),segment.getLine()));
            }
            tracks.add(temporary);
        }
        return tracks;
    }



    public void dinamic(List<LinkedList<SergAlg>> tracks){
        TwoDimensionalArray twoDimensionalArray = clone(tracks);
        twoDimensionalArray = Algorithms.dynamicAlgorithm(twoDimensionalArray);
        System.out.println("Количество решений динамическим алгоритмом (по длине) " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }
    public void dinamicSize(List<LinkedList<SergAlg>> tracks){
        TwoDimensionalArray twoDimensionalArray = clone(tracks);
        twoDimensionalArray = Algorithms.dynamicAlgorithmSize(twoDimensionalArray);
        System.out.println("Количество решений динамическим алгоритмом (по количеству) " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }

    public void nadir(List<LinkedList<SergAlg>> tracks){
        int[] tracks_characteristics = SergAlg.get_tracks_info(tracks);
        //SergAlg.output_tracks(tracks,"tracks: ");
        ArrayList<int[]> result = SergAlg.bypass_tracks(tracks,tracks_characteristics);
    }

    public void greedy(List<LinkedList<SergAlg>> tracks){
        TwoDimensionalArray twoDimensionalArray = clone(tracks);
        twoDimensionalArray = Algorithms.greedyAlgorithm(twoDimensionalArray);
        System.out.println("Количество решений жадным алгоритмом " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }
    @Test
    public void seive(){
        List<LinkedList<SergAlg>> tracks = GeneratorRandom.genareteSergEntity();
        TwoDimensionalArray mainArray = clone2(tracks);
        List<Integer> indexes = mainArray.setAreasId();
        TwoDimensionalArray buf;
        long resultTime = 0;
        long timeStart= 0;
        long timeEnd= 0;
        int start = 0;
        int end;
        LineList result = new LineList(-1);
        List<Integer> dropPoints = GeneratorRandom.createRandomDropPoints();
        System.out.println("Точки сброса " + dropPoints);
        timeStart = System.currentTimeMillis();
        LineList allIntervals = Algorithms.getAllIntervals(mainArray);
        allIntervals.sort();
        System.out.println("Все интервалы "+allIntervals);
        timeEnd = System.currentTimeMillis();
        resultTime += (timeEnd - timeStart);
        for (Integer dropPoint : dropPoints) {
            if(mainArray.size() == 0){
                break;
            }
            end = dropPoint;
            timeStart = System.currentTimeMillis();
            buf = Algorithms.separation(allIntervals,start,end);
            timeEnd = System.currentTimeMillis();
            resultTime += (timeEnd - timeStart);

            buf = Algorithms.greedyAlgorithm(buf);
            if(buf.size() == 0){
                start = end;
                continue;
            }
            result.addAll(buf.get(0));
            Algorithms.removeUsedSegmentsFromMainMatrix(mainArray ,indexes ,buf);
            start = end;
        }
        System.out.println("Конец ");
        if(mainArray.getCollection().size() <= 400) {
            System.out.println("Остаток " + mainArray.getCollection());
        }
        else {
            System.out.println("Остаток слишком велик для печати ");
        }
        System.out.println("Результат "+result);
        System.out.println("Размер остатка " + mainArray.getCollection().size());

        System.out.println("Время на разбивку "+(resultTime)+ " миллисекунд");
        System.out.println();
    }

    public void fasterDinamic(List<LinkedList<SergAlg>> tracks){
        TwoDimensionalArray twoDimensionalArray = clone(tracks);
        twoDimensionalArray = Refactor.dynamicAlgorithm(twoDimensionalArray);
        System.out.println("Количество решений быстрым динамическим " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }

    private TwoDimensionalArray clone(List<LinkedList<SergAlg>> tracks) {
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArraySet();
        for (LinkedList<SergAlg> SergAlgs : tracks) {
            LineSet lineSet = new LineSet(SergAlgs.getFirst().getRoll());
            for (SergAlg SergAlg : SergAlgs) {
               lineSet.add(new Segment(SergAlg.getBegin(), SergAlg.getEnd(), SergAlg.getRoll()));
            }
            lineSet.setFullLength();
            twoDimensionalArray.add(lineSet);
        }
        return twoDimensionalArray;
    }

    private TwoDimensionalArray clone2(List<LinkedList<SergAlg>> tracks) {
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArrayList();
        for (LinkedList<SergAlg> SergAlgs : tracks) {
            LineList lineList = new LineList(SergAlgs.getFirst().getRoll());
            for (SergAlg SergAlg : SergAlgs) {
               lineList.add(new Segment(SergAlg.getBegin(), SergAlg.getEnd(), SergAlg.getRoll()));
            }
            lineList.setFullLength();
            twoDimensionalArray.add(lineList);
        }
        return twoDimensionalArray;
    }

    public static void verification(TwoDimensionalArray twoDimensionalArray){
        Integer realSize = 0;
        Integer realLength = 0;
        for (SegmentPack segmentPack :twoDimensionalArray) {
             realSize += segmentPack.size();
            for (Segment segment :segmentPack) {
                realLength += segment.getLength();
            }
        }
        if (sizeInputData.equals(realSize) && lengthInputData.equals(realLength)){
            System.out.println("Проверка пройдена успешно");
        }
        else {
            System.out.println("Проверка не пройдена");
        }
    }



}