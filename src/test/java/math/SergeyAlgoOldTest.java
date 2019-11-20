package math;

import math.entity.Array.ArrayHash;
import math.entity.Array.Selection;
import math.entity.LineSegments.LineList;
import math.entity.LineSegments.LineSet;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArraySet;
import math.entity.LineSegments.Track;
import math.entity.interval.Interval;
import math.entity.SegmentPack;
import org.junit.Test;

import java.util.*;

public class SergeyAlgoOldTest {
    private static int buf = 0;
    private static Integer sizeInputData = 0;
    private static Integer lengthInputData = 0;
    private Algorithms algorithms = new Algorithms();

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
//        ArrayHash mainArray = Simulation.genSimulationForTest();
//        Separator separator = new Separator();
//        List<Integer> dropPoints = Simulation.dropPoints;
//        LineList  solution = separator.separationArrays(mainArray,0,dropPoints.get(1));
//        TwoDimensionalArray twoDimensionalArray = separator.createLineArray(solution);
//        Selection nadir = separator.createSelection(solution);
//        List<LinkedList<SergAlg>> tracks = convertOld(nadir);
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

    private List<LinkedList<SergAlg>> convertOld(Selection nadir) {
        List<LinkedList<SergAlg>> tracks = new ArrayList<>();
        for (Track track :nadir) {
            LinkedList<SergAlg> lineSet = new LinkedList<>();
            for (Interval interval : track.getRangeOfIntervals()) {
                lineSet.add(new SergAlg(interval.getFirstDot(), interval.getSecondDot(), interval.getLine()));
            }
            tracks.add(lineSet);
        }
    return tracks;
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

    public void dinamic(List<LinkedList<SergAlg>> tracks){
        TwoDimensionalArray twoDimensionalArray = clone(tracks);
        twoDimensionalArray = algorithms.dynamicAlgorithm(twoDimensionalArray);
        System.out.println("Количество решений динамическим алгоритмом (по длине) " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }
    public void dinamicSize(List<LinkedList<SergAlg>> tracks){
        TwoDimensionalArray twoDimensionalArray = clone(tracks);
        twoDimensionalArray = algorithms.dynamicAlgorithmSize(twoDimensionalArray);
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
        twoDimensionalArray = algorithms.greedyAlgorithm(twoDimensionalArray);
        System.out.println("Количество решений жадным алгоритмом " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }
    public void greedy(TwoDimensionalArray twoDimensionalArray){
        twoDimensionalArray = algorithms.greedyAlgorithm(twoDimensionalArray);
        System.out.println("Количество решений жадным алгоритмом " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }


    public void fasterDinamic(List<LinkedList<SergAlg>> tracks){
        TwoDimensionalArray twoDimensionalArray = clone(tracks);
        twoDimensionalArray = algorithms.dynamicAlgorithm(twoDimensionalArray);
        System.out.println("Количество решений быстрым динамическим " + twoDimensionalArray.getCollection().size());
        verification(twoDimensionalArray);
    }

    private TwoDimensionalArray clone(List<LinkedList<SergAlg>> tracks) {
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArraySet();
        for (LinkedList<SergAlg> SergAlgs : tracks) {
            LineSet lineSet = new LineSet(SergAlgs.getFirst().getRoll());
            for (SergAlg SergAlg : SergAlgs) {
               lineSet.add(new Interval(SergAlg.getBegin(), SergAlg.getEnd(), SergAlg.getRoll(), Simulation.generateAreaId()));
            }
            lineSet.setFullLength();
            twoDimensionalArray.add(lineSet);
        }
        return twoDimensionalArray;
    }


    public static void verification(TwoDimensionalArray twoDimensionalArray){
        Integer realSize = 0;
        Integer realLength = 0;
        for (SegmentPack segmentPack :twoDimensionalArray) {
             realSize += segmentPack.size();
            for (Interval interval :segmentPack) {
                realLength += interval.getLength();
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