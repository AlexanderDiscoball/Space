package math;

import math.entity.SimulationSegments.MatrixList;
import math.entity.SimulationSegments.Segment;
import math.entity.SimulationSegments.StackSegments;
import math.entity.SimulationSegments.StackSegmentsList;
import math.entity.StackSegmentsSlice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorRandom {

    private static final int MAXSTEP = 10;
    private static final int DROPPOINTS = InputData.getDropPoints();
    private static final int STEPDROPPOINTS = InputData.getTimeAmount()/DROPPOINTS;
    private static final LocalDateTime TIMENOW = LocalDateTime.now();
    private static int buf = 0;
    private static int seed = 15;
    private static Random gen = new Random(seed);

    public static MatrixList generateMatrix(){
        MatrixList matrixList = new MatrixList();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            StackSegments stackSegments = generateStackSegments(line);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }

    private static StackSegments generateStackSegments(int line){
        StackSegmentsList stackSegmentsList = new StackSegmentsList(line);

        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
          stackSegmentsList.add(generateSegment(line));
        }
        buf = getRandomStep();
        return stackSegmentsList;
    }

    private static Segment generateSegment(int line){
        int first = buf;
        int second = buf + 1 + getRandomStep();
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

    public static List<Integer> createRandomDropPoints(){
        int point = 0;
        List<Integer> dropPoints = new ArrayList<>();
        for (int i = 0; i < DROPPOINTS; i++) {
            point += STEPDROPPOINTS;
            dropPoints.add(point);
        }
        return dropPoints;
    }












    //DELIMITER
    public static Segment generateRandomSegment(int min,int max, int lineNumber){
        int firstDot = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(min==0) min = 1;
        int secondDot  = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(firstDot>secondDot){
            firstDot =  firstDot + secondDot;
            secondDot = firstDot - secondDot;
            firstDot = firstDot - secondDot;
        }

        return new Segment(firstDot, secondDot, lineNumber);
    }

    private static int getRandomNumber(int from, int to){
        return (from + (int) (Math.random() * to));
    }

    public static ArrayList<StackSegmentsList> generateManyStacks() {
        ArrayList<StackSegmentsList> matrixList = new ArrayList<>();
        int intervalMax;
        int intervalMin;

        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        int segmentCounter = 1;
        for (int channel = 0; channel < InputData.getChannelAmount(); channel++) {
            StackSegmentsList stackSegmentsList = new StackSegmentsList(channel);
            HashSet<Integer> howManyZeroElements = getRandomCountZeroElements();

            while (stackSegmentsList.size() != InputData.getSegmentsAmount()){
                if (howManyZeroElements.contains(segmentCounter)) {
                   stackSegmentsList.addZeroSegments();
                }
                else {
                    intervalMax = (int) ((amountOfTime / segmentsAmount) * segmentCounter);
                    intervalMin = getMinInterval(segmentCounter);
                    stackSegmentsList.add(generateRandomSegment(intervalMin, intervalMax, channel));
                }
                segmentCounter++;
            }

            segmentCounter=1;
            matrixList.add(stackSegmentsList);
        }
        return matrixList;
    }

    public static HashSet<Integer> getRandomCountZeroElements(){
        int howManyElements = ThreadLocalRandom.current().nextInt(1, InputData.getSegmentsAmount()+1);

        HashSet<Integer> repoRandomNumber = new HashSet<>();
        for (int i = 1; i < howManyElements; i++) {
            repoRandomNumber.add(ThreadLocalRandom.current().nextInt(1, InputData.getSegmentsAmount()+1));
        }
        return repoRandomNumber;
    }

    private static void addZeroSegments(StackSegmentsList stackSegmentsList){
        while (stackSegmentsList.size() != InputData.getSegmentsAmount()){
            stackSegmentsList.addZeroSegments();
        }
    }

    public static Segment[][] generateSegmentMatrix(){
        Segment[][] matrix = new Segment[InputData.getChannelAmount()][InputData.getSegmentsAmount()];
        ArrayList<StackSegmentsList> matrixList = generateManyStacks();
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                    matrix[i][j] = matrixList.get(i).get(j);
            }
        }
        return matrix;
    }

    private static int getMinInterval(int segmentCounter){
        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        if (segmentCounter == 1) {
            return  0;
        } else {
            return  (int) ((amountOfTime / segmentsAmount) * (segmentCounter - 1));
        }
    }
}
