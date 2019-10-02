package math.entity;

import math.GeneratorRandom;
import math.InputData;
import math.Refactor;
import math.TestTreeSet;
import org.junit.Test;

import java.util.Random;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class RefTest {
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

        Matrix matrix = clon.clone();
        System.out.println(matrix.size());
        for (int i = 0; i < matrix.size(); i++) {
            sizeBefore += matrix.get(i).size();
        }
        matrix = Refactor.dynamicAlgorithm(matrix);
        for (int i = 0; i < matrix.size(); i++) {
            sizeAfter += matrix.get(i).size();
        }
        for (int j = 0; j < matrix.size(); j++) {
            System.out.println("Решение "+j+" "+ matrix.get(j).size()+" Сумма "+  matrix.get(j).getFullLength());
        }
        System.out.println("Общее количество решений "+ matrix.size());
        System.out.println("Ref"+matrix.get(0));
        System.out.println("Ref"+matrix.get(0).size());
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void refSet(){
        int count = 0;
        Matrix matrix = clon.clone();
        for (int i = 0; i < matrix.size(); i++) {
            count += matrix.get(i).getCollection().size();
        }
        System.out.println("Размер матрицы до "+count);
        matrix = TestTreeSet.dynamicAlgorithm(matrix);
        int counter = 0;

        for (StackSegments set :matrix.getCollection()) {
            System.out.println("Решение "+counter+" Элементов "+set.size()+" Сумма "+  set.getFullLength());
            if(counter==matrix.getCollection().size()-1){
                System.out.println("RefSet "+set.getCollection());
                System.out.println("RefSet "+set.getCollection().size());
            }
            counter++;
        }
    }

    @Test
    public void both(){

        ref();

        refSet();
    }

    private static final int MAXSTEP = 10;
    private static int buf = 0;
    private static int seed = 15;
    private static Random gen = new Random(seed);

    public static MatrixSet generateMatrix(){
        MatrixSet matrix = new MatrixSet();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            StackSegmentsSet stackSegments = generateStackSegments(line);
            matrix.add(stackSegments);
        }
        return matrix;
    }

    private static StackSegmentsSet generateStackSegments(int line){
        StackSegmentsSet stackSegments = new StackSegmentsSet(line);

        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
            stackSegments.add(generateSegment(line));
        }
        buf = getRandomStep();
        return stackSegments;
    }

    private static Segment generateSegment(int line){
        int first = buf;
        int second = buf+ 1 + getRandomStep();
        buf = second + getRandomStep();

        return new Segment(first,second,line);
    }

    private static int getRandomStep(){

        return gen.nextInt(MAXSTEP);
    }

    private static int getRandomNumber(){
        return ((int) (Math.random() * InputData.getTimeAmount()));
    }

    private static int getRandomAmount(){
        gen.setSeed(seed);
        seed+=4;
        return gen.nextInt(MAXSTEP);
    }


}
