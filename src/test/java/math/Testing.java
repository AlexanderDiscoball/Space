package math;

import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import math.entity.LineSegments.LineList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    @Test
    public void test(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            list.add(poissonRandomNumber(5));
        }
        int sum = 0;
        for (Integer aDouble :list) {
            sum += aDouble;
        }
        System.out.println(((double) sum) / 300);
        RandomEngine engine = new DRand();
        Poisson poisson = new Poisson(5, engine);
        double kol = 1000000D;
        list = new ArrayList<>();
        for (int i = 0; i < kol; i++) {
            list.add((poisson.nextInt()));
        }
        sum = 0;
        for (Integer aDouble :list) {
            sum += aDouble;
        }
        System.out.println(((double) sum) / kol);

    }

    int poissonRandomNumber(int lambda) {
        double L = Math.exp(-lambda);
        int k = 0;
        double p = 1;
        do {
            k = k + 1;
            double u = Math.random();
            p = p * u;
        } while (p > L);
        return k - 1;
    }

    @Test
    public void testList(){
        LineList lineList = new LineList(-1);
        List<Integer> list =(List) lineList.getCollection();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        lineList.getCollection().subList(2,3+1).clear();
        System.out.println(lineList.getCollection());
    }
}
