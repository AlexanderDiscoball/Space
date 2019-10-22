package math.entity;

import math.GeneratorRandom;
import math.Algorithms;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Segment.Segment;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AllAlgoTest {
    static final TwoDimensionalArray clon;
    static
    {clon= GeneratorRandom.generateMatrix();}


    @Test
    public void dinamicAlgorithmList(){
        int sizeBefore = 0;
        int sizeAfter = 0;
        int sumBefore = 0;
        int sumAfter = 0;
        TwoDimensionalArray twoDimensionalArray = clon.clone();
        twoDimensionalArray.getCollection().forEach(System.out::println);
        for (SegmentPack stack : twoDimensionalArray) {
            sizeBefore += stack.size();
            for (Segment segment :stack) {
                sumBefore+=segment.getLength();
            }
        }
        twoDimensionalArray = Algorithms.dynamicAlgorithm(twoDimensionalArray);
        for (SegmentPack stack : twoDimensionalArray) {
            sizeAfter += stack.size();
            for (Segment segment :stack) {
                sumAfter+=segment.getLength();
            }
        }
        for (int j = 0; j < twoDimensionalArray.size(); j++) {
            System.out.println("Решение "+j+" "+ twoDimensionalArray.get(j).size()+" Сумма "+  twoDimensionalArray.get(j).getFullLength());
        }
        System.out.println("Общее количество решений "+ twoDimensionalArray.size());
        System.out.println("Ref"+ twoDimensionalArray.get(0));
        System.out.println("Ref"+ twoDimensionalArray.get(0).size());
        assertEquals(sizeBefore, sizeAfter);
        assertEquals(sumBefore, sumAfter);
    }


    public TwoDimensionalArray dinamicAlgorithmSet(){
        int count = 0;
        TwoDimensionalArray twoDimensionalArray = clon.clone();
//
        System.out.println("Размер матрицы до "+count);
        twoDimensionalArray = Algorithms.dynamicAlgorithm(twoDimensionalArray);
        return twoDimensionalArray;
//
    }

    @Test
    public void both(){
        ArrayList<Integer> listList = new ArrayList<>();
        int counter = 0;
        TwoDimensionalArray twoDimensionalArray1 = listTest();
        for (SegmentPack set : twoDimensionalArray1.getCollection()) {
            System.out.println("Решение "+counter+" Элементов "+set.size()+" Сумма "+  set.getFullLength());
            listList.add(set.getFullLength());
            counter++;
        }
        counter = 0;
        TwoDimensionalArray twoDimensionalArray2 = dinamicAlgorithmSet();
        ArrayList<Integer> listSet = new ArrayList<>();
        for (SegmentPack set : twoDimensionalArray2.getCollection()) {
            System.out.println("Решение "+counter+" Элементов "+set.size()+" Сумма "+  set.getFullLength());
            listSet.add(set.getFullLength());
            if(counter== twoDimensionalArray2.getCollection().size()-1){
                System.out.println("Количество решений List "+ twoDimensionalArray1.size());
                System.out.println("Количество решений Set "+ twoDimensionalArray2.size());
                System.out.println("List последнее решение "+twoDimensionalArray1.get(0));
                System.out.println("Set последнее решение "+set.getCollection());
            }
            counter++;
        }
        int size = Math.min(listList.size(),listSet.size());
        for (int i = 0; i < size; i++) {
            assertEquals(listSet, listList);
        }
    }


    public TwoDimensionalArray listTest(){
        TwoDimensionalArray twoDimensionalArray = clon.clone();
        for (SegmentPack stack : twoDimensionalArray) {
            System.out.println(stack);
        }
        twoDimensionalArray = Algorithms.dynamicAlgorithm(twoDimensionalArray);
        return twoDimensionalArray;
    }
    @Test
    public void refactorList(){
        int sizeBefore = 0;
        int sizeAfter = 0;
        int sumBefore = 0;
        int sumAfter = 0;
        int counter = 0;
        TwoDimensionalArray twoDimensionalArray = clon.clone();
        for (SegmentPack stack : twoDimensionalArray) {
            sizeBefore += stack.size();
            //System.out.println(stack);
            for (Segment segment :stack) {
                sizeBefore+=segment.getLength();
            }
        }
        long start = System.currentTimeMillis();
        twoDimensionalArray = Algorithms.dynamicAlgorithm(twoDimensionalArray);
        long end = System.currentTimeMillis();
                for (SegmentPack stack : twoDimensionalArray) {
                    sizeAfter += stack.size();
                    for (Segment segment :stack) {
                        sizeAfter+=segment.getLength();
                    }
                }

        System.out.println("Время на алгоритм List " + (end - start)+" миллисекунд");
        System.out.println("Общее количество решений List "+ twoDimensionalArray.size());
                for (SegmentPack set : twoDimensionalArray.getCollection()) {
                    if(counter== 0||counter==1){
                        System.out.println("Первое решение List "+set.getCollection());
                    }
                    counter++;
                }

                assertEquals(sizeBefore, sizeAfter);
                assertEquals(sumBefore, sumAfter);

    }
    @Test
    public void refactorSet(){
        int counter = 0;
        TwoDimensionalArray twoDimensionalArray = clon.clone();
        long start = System.currentTimeMillis();
        twoDimensionalArray = Algorithms.dynamicAlgorithm(twoDimensionalArray);
        long end = System.currentTimeMillis();

        for (SegmentPack set : twoDimensionalArray.getCollection()) {
            if(counter== (twoDimensionalArray.getCollection().size()-1)){
                System.out.println("Первое решение Set "+set.getCollection());
            }
            counter++;
        }
        System.out.println("Общее количество решений Set "+ twoDimensionalArray.size());
        System.out.println("Время на алгоритм Set " + (end - start)+" миллисекунд");
    }

    @Test
    public void greedyAlg(){
        int counter = 0;
        int sizeBefore = 0;
        int sizeAfter = 0;
        int sumBefore = 0;
        int sumAfter = 0;
        TwoDimensionalArray twoDimensionalArray = clon.clone();
        for (SegmentPack segmentPack :twoDimensionalArray) {
            segmentPack.setFullLength();
        }
        Collections.sort((List)twoDimensionalArray.getCollection());

        for (SegmentPack stack : twoDimensionalArray) {
            sizeBefore += stack.size();
            //System.out.println(stack);
            for (Segment segment :stack) {
                sizeBefore+=segment.getLength();
            }
        }
        long start = System.currentTimeMillis();
        twoDimensionalArray = Algorithms.greedyAlgorithm(twoDimensionalArray);
        long end = System.currentTimeMillis();
        for (SegmentPack stack : twoDimensionalArray) {
            sizeAfter += stack.size();
            for (Segment segment :stack) {
                sizeAfter+=segment.getLength();
            }
        }

        System.out.println("Первое решение Greedy "+twoDimensionalArray.get(0));
        System.out.println("Второе решение Greedy "+twoDimensionalArray.get(1));

        System.out.println("Общее количество решений Greedy "+ twoDimensionalArray.size());
        System.out.println("Время на алгоритм Greedy " + (end - start)+" миллисекунд");
    }

    @Test
    public void greed(){
        greedyAlg();
        refactorSet();
    }

}
