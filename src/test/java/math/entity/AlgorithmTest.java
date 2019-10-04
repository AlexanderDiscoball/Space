package math.entity;

import math.GeneratorRandom;
import math.Refactor;
import math.Algorithms;
import math.entity.SimulationSegments.Matrix;
import math.entity.SimulationSegments.MatrixList;
import math.entity.SimulationSegments.Segment;
import math.entity.SimulationSegments.StackSegments;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlgorithmTest {
    Matrix clon = GeneratorRandom.generateMatrix();

    @Test
    public void RandomTest(){
        MatrixList matrixList = GeneratorRandom.generateMatrix();
        int buf = 0;
        for (int i = 0; i < matrixList.size(); i++) {
           buf += matrixList.get(i).size();
            matrixList.get(i).setFullLength();
        }
        System.out.println(buf);
        System.out.println(matrixList.size());

    }
    @Test
    public void ref(){
        int sizeBefore = 0;
        int sizeAfter = 0;
        int sumBefore = 0;
        int sumAfter = 0;

        Matrix matrix = clon.clone();
        System.out.println(matrix.size());
        for (StackSegments stack :matrix) {
            sizeBefore += stack.size();
            for (Segment segment :stack) {
                sizeBefore+=segment.getLength();
            }
        }
        matrix = Refactor.dynamicAlgorithm(matrix);
        for (StackSegments stack :matrix) {
            sizeAfter += stack.size();
            for (Segment segment :stack) {
                sizeAfter+=segment.getLength();
            }
        }
        for (int j = 0; j < matrix.size(); j++) {
            System.out.println("Решение "+j+" "+ matrix.get(j).size()+" Сумма "+  matrix.get(j).getFullLength());
        }
        System.out.println("Общее количество решений "+ matrix.size());
        System.out.println("Ref"+matrix.get(0));
        System.out.println("Ref"+matrix.get(0).size());
        assertEquals(sizeBefore, sizeAfter);
        assertEquals(sumBefore, sumAfter);
    }

    @Test
    public void refSet(){
        int sizeBefore = 0;
        int sizeAfter = 0;
        int sumBefore = 0;
        int sumAfter = 0;
        int count = 0;
        Matrix matrix = clon.clone();
        for (StackSegments stack :matrix) {
            sizeBefore += stack.size();
            for (Segment segment :stack) {
                sizeBefore+=segment.getLength();
            }
        }
        System.out.println("Размер матрицы до "+count);
        matrix = Algorithms.dynamicAlgorithm(matrix);
        for (StackSegments stack :matrix) {
            sizeAfter += stack.size();
            for (Segment segment :stack) {
                sizeAfter+=segment.getLength();
            }
        }

        int counter = 0;
        for (StackSegments set :matrix.getCollection()) {
            System.out.println("Решение "+counter+" Элементов "+set.size()+" Сумма "+  set.getFullLength());
            if(counter==matrix.getCollection().size()-1){
                System.out.println("RefSet "+set.getCollection());
                System.out.println("RefSet "+set.getCollection().size());
            }
            counter++;
        }
        assertEquals(sizeBefore, sizeAfter);
        assertEquals(sumBefore, sumAfter);
    }

    @Test
    public void both(){

        ref();

        refSet();
    }
}
