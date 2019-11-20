package math.entity;

import math.GeneratorRandom;
import math.Algorithms;
import math.annotation.GetMethodTime;
import math.entity.Array.TwoDimensionalArray;
import math.entity.interval.Interval;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class AllAlgoOldTest {
    @Test
    public void inspect(){

        Method[] methods = Algorithms.class.getMethods();
        TwoDimensionalArray twoDimensionalArray = GeneratorRandom.generateMatrix();
        System.out.println("Тест для определения количетсва прохождений различными алгоритмами");
        System.out.println("Количество сегментов "+twoDimensionalArray.size());

        int sizeBefore = 0;
        for (SegmentPack stack : twoDimensionalArray) {
            sizeBefore += stack.size();
            for (Interval interval :stack) {
                sizeBefore+= interval.getLength();
            }
        }
        for (Method method : methods) {
            GetMethodTime meth = method.getAnnotation(GetMethodTime.class);
            if (meth != null) {
                try {

                    int sizeAfter = 0;
                    int sumBefore = 0;
                    int sumAfter = 0;

                    long start = System.currentTimeMillis();
                    TwoDimensionalArray tw = (TwoDimensionalArray)  method.invoke(Algorithms.class, twoDimensionalArray.clone());
                    long end = System.currentTimeMillis();
                    for (SegmentPack stack : tw) {
                        sizeAfter += stack.size();
                        for (Interval interval :stack) {
                            sizeAfter+= interval.getLength();
                        }
                    }

                    System.out.println("Время на алгоритм "+ method.getName()+" " + (end - start) +" миллисекунд");
                    System.out.println("Количество решений "+ method.getName()+" "+ tw.size());
                    System.out.println();
                    assertEquals(sizeBefore, sizeAfter);
                    assertEquals(sumBefore, sumAfter);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
