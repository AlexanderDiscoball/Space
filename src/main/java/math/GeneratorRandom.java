package math;


import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import math.entity.array.TwoDimensionalArrayList;
import math.entity.dropintervaltrack.TrackDropInterval;
import math.entity.interval.DropInterval;
import math.entity.linesegments.Line;
import math.entity.linesegments.LineList;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static math.Simulation.*;
import static math.Simulation.step;

public class GeneratorRandom {

    public static final int MAXSTEP = 10;
    public static final int DROPPOINTS = InputData.getDropPoints();
    public static final int STEPDROPPOINTS = InputData.getTimeAmount()/DROPPOINTS;


    public static Random gen = new Random(InputData.getSeed());
    public static int areaCunt = 0;
    public static RandomEngine engine = new DRand();
    public static Poisson poisson = new Poisson(InputData.getLAMBDA(), engine);
    public static int buf = getRandomStep();
    public static int channel = 0;
    public static boolean reverse;

    private static int areaIdBuf = 0;


    public static TwoDimensionalArrayList generateMatrix(){
        TwoDimensionalArrayList matrixList = new TwoDimensionalArrayList();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            Line stackSegments = generateStackSegments(line);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }
    public static TwoDimensionalArrayList generateMatrixForTest(){
        TwoDimensionalArrayList matrixList = new TwoDimensionalArrayList();

        for (int line = 0; line < InputData.getChannelAmount(); line++) {
            Line stackSegments = generateStackSegments(line);
            matrixList.add(stackSegments);
        }
        return matrixList;
    }

    public static Line generateStackSegments(int line){
        LineList stackSegmentsList = new LineList(line);

        for (int i = 0; i < InputData.getSegmentsAmount(); i++) {
            stackSegmentsList.add(generateSegment(line));
        }
        buf = stackSegmentsList.getFirstSegment().getSecondDot() + InputData.getCoefSpace() * getRandomStep();
        return stackSegmentsList;
    }


    public static math.entity.interval.Interval generateSegment(int line){
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * getRandomStep();
        buf = second + (InputData.getCoefSpace() * getRandomStep());
        return new math.entity.interval.Interval(first,second,line,areaIdBuf++);
    }

    public static int getModuleLine(int line) {
        if(line + 1 < InputData.getChannelAmount()){
            return line + 1;
        }
        else return 0;
    }

    public static math.entity.interval.Interval generateSegment(int step, int areaId, int firstDot, int secondDot){
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * getRandomStep();
        buf = second + (InputData.getCoefSpace() * getRandomStep());

        return new math.entity.interval.Interval(first,second,getRandomChannel(),areaId);
    }

    public static int getReverseChannel(){
        if(channel > InputData.getChannelAmount()){
            reverse = true;
        }
        if(channel < 0){
            reverse = false;
        }
        if(reverse){
            return channel--;
        }
        else {
            return channel++;
        }
    }

    public static math.entity.interval.Interval generateSegment(int line, int start, int end){
        int first = buf;
        int second = buf + 1 + InputData.getCoefLength() * poisson.nextInt();
        while(second >= end){
            first = start + InputData.getCoefLength() * poisson.nextInt();
            second = first + 1 + InputData.getCoefLength() * poisson.nextInt();
        }
        buf = second + (InputData.getCoefSpace() * poisson.nextInt());

        return new math.entity.interval.Interval(first,second,line,areaCunt);
    }


    public static int getRandomStep(){
        return 1 +  gen.nextInt(MAXSTEP);
    }

    public static int getRandomChannel(){
        return (gen.nextInt(InputData.getChannelAmount()));
    }



    public static TrackDropInterval createRandomDropIntervals(int howManyIntervals, int fullLength){
        TrackDropInterval trackDropInterval = new TrackDropInterval();

        int step = fullLength / howManyIntervals;
        int buffer = InputData.getCoefLength() * gen.nextInt(step);
        for (int i = 0; i < howManyIntervals; i++) {
            int first = buffer;
            int second = buffer + 1 + InputData.getCoefLength() * gen.nextInt(step);
            buffer = second + (InputData.getCoefSpace() * gen.nextInt(step));
            trackDropInterval.add( new DropInterval(first,second,gen.nextInt(step)));
        }
        return trackDropInterval;
    }

    public static TrackDropInterval createDropIntervals(int howManyIntervals, int fullLength){
        TrackDropInterval trackDropInterval = new TrackDropInterval();

        int step = fullLength / howManyIntervals;
        int buffer = 0;
        for (int i = 0; i < howManyIntervals; i++) {
            int first = buffer;
            int second = buffer + 1 + InputData.getCoefLength() * step;
            buffer = second;
            trackDropInterval.add( new DropInterval(first,second,step));
        }
        return trackDropInterval;
    }


    //DELIMITER
    public static math.entity.interval.Interval generateRandomSegment(int min, int max, int lineNumber){
        int firstDot = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(min==0) min = 1;
        int secondDot  = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(firstDot>secondDot){
            firstDot =  firstDot + secondDot;
            secondDot = firstDot - secondDot;
            firstDot = firstDot - secondDot;
        }
        return new math.entity.interval.Interval(firstDot, secondDot, lineNumber, generateAreaId());
    }

    public static int getRandomNumber(int from, int to){
        return (from + (int) (Math.random() * to));
    }

    public static ArrayList<LineList> generateManyStacks() {
        ArrayList<LineList> matrixList = new ArrayList<>();
        int intervalMax;
        int intervalMin;

        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        int segmentCounter = 1;
        for (int channel = 0; channel < InputData.getChannelAmount(); channel++) {
            LineList stackSegmentsList = new LineList(channel);
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

    public static void addZeroSegments(LineList stackSegmentsList){
        while (stackSegmentsList.size() != InputData.getSegmentsAmount()){
            stackSegmentsList.addZeroSegments();
        }
    }

    public static math.entity.interval.Interval[][] generateSegmentMatrix(){
        math.entity.interval.Interval[][] matrix = new math.entity.interval.Interval[InputData.getChannelAmount()][InputData.getSegmentsAmount()];
        ArrayList<LineList> matrixList = generateManyStacks();
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                matrix[i][j] = matrixList.get(i).get(j);
            }
        }
        return matrix;
    }

    public static int getMinInterval(int segmentCounter){
        double amountOfTime = InputData.getTimeAmount();
        double segmentsAmount = InputData.getSegmentsAmount();
        if (segmentCounter == 1) {
            return  0;
        } else {
            return  (int) ((amountOfTime / segmentsAmount) * (segmentCounter - 1));
        }
    }
}

