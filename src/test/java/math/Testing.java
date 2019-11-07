package math;

import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import math.entity.Array.ArrayHash;
import math.entity.Array.Selection;
import math.entity.Array.TwoDimensionalArray;
import math.entity.Array.TwoDimensionalArrayList;
import math.entity.LineSegments.Line;
import math.entity.LineSegments.LineList;
import math.entity.LineSegments.Track;
import math.entity.SegmentPack;
import math.entity.interval.Interval;
import org.junit.Test;

import java.util.*;

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
        ArrayHash mainArray = Simulation.genSimulationForTest();
        Separator separator = new Separator();
        LineList solution = new LineList(-1);
        for (SegmentPack segmentPack :mainArray.values()) {
            System.out.println(segmentPack);
            solution.addAll(segmentPack);
        }
        System.out.println("ssssssssss");
        Selection selection = separator.createSelection(solution);
        for (Track track :selection) {
            track.getRangeOfIntervals().sort(Comparator.comparing(Interval::getSecondDot));
            System.out.println(track);
        }


        int i = Algorithms.nadirAlgorithmAll(selection,selection.getTracksCount(),0,0);
        System.out.println("Надирный "+i);
        TwoDimensionalArray twoDimensionalArray = new TwoDimensionalArrayList();
        twoDimensionalArray.add(solution);
        TwoDimensionalArray twoDimensionalArray1 = Algorithms.greedyAlgorithm(twoDimensionalArray);
        System.out.println("Жадный "+twoDimensionalArray1.size());
    }

    public static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int aIndex = 0;
        int bIndex = 0;
        int i = 0;

        while (i < result.length) {
            result[i] = a[aIndex] < b[bIndex] ? a[aIndex++] : b[bIndex++];
            if (aIndex == a.length) {
                System.arraycopy(b, bIndex, result, ++i, b.length - bIndex);
                break;
            }
            if (bIndex == b.length) {
                System.arraycopy(a, aIndex, result, ++i, a.length - aIndex);
                break;
            }
            i++;
        }
        return result;
    }

    @Test
    public void asd(){
        Algorithms.fullSepaAlgo();
    }

    public static void main(String[] args) {
       Line list = GeneratorRandom.generateStackSegments(-1);
       Line list2 = GeneratorRandom.generateStackSegments(-1);
       Line list3 = GeneratorRandom.generateStackSegments(-1);
       Line list4 = GeneratorRandom.generateStackSegments(-1);

       List<Interval> result = Algorithms.merge2(((List<Interval>) list.getCollection()),(List<Interval>) list2.getCollection());
       result = Algorithms.merge2(result,(List<Interval>) list3.getCollection());
       result = Algorithms.merge2(result,(List<Interval>) list4.getCollection());
        System.out.println(result);
        Iterator<Interval> iterator = result.listIterator();
       Interval prev = iterator.next();
        while(iterator.hasNext()){
            Interval interval = iterator.next();
            if(prev.getSecondDot()> interval.getSecondDot()){
                System.out.println("Error");
                System.out.println(prev);
                System.out.println(interval);
            }
        }

    }
    @Test
    public void howManyMemory(){
        System.out.println(Runtime.getRuntime().maxMemory()/8/1024/1024 +" Магабайт");
        System.out.println(Runtime.getRuntime().totalMemory()/8/1024/1024 +" Магабайт");
    }
}
