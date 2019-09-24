package math.entity;

import math.InputData;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BetterGeneratorRandom {
    public static Segment generateRandomSegment(int min,int max, int lineNumber){
        int firstDot = getRandomNumber(min,max);
        int secondDot  = getRandomNumber(firstDot+1,max);
        return new Segment(firstDot, secondDot, lineNumber);
    }

    private static int getRandomNumber(int start, int end) {
        return (start + (int) (Math.random() * end));
    }

    private static ArrayList<StackSegments> generateStacksNumberOfChannels() {
       ArrayList<StackSegments> matrixList = new ArrayList<>();
        for (int channel = 0; channel < InputData.getChannelAmount(); channel++) {
            matrixList.add(generateRandomStack(channel));
        }
        return matrixList;
    }

    private static StackSegments generateRandomStack(int channel){
        StackSegments stackSegments = new StackSegments(channel);
        int min = 0;
        int max = InputData.getSegmentsAmount();
        while (stackSegments.getSize() != InputData.getSegmentsAmount()){
            stackSegments.add(generateRandomSegment(min, max,channel));
            min = stackSegments.get(stackSegments.getSize()-1).getSecondDot();
        }
        return stackSegments;
    }

    public static Segment[][] generateSegmentMatrix(){
        Segment[][] matrix = new Segment[InputData.getChannelAmount()][InputData.getSegmentsAmount()];
        ArrayList<StackSegments> matrixList = generateStacksNumberOfChannels();
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                matrix[i][j] = matrixList.get(i).get(j);
            }
        }
        return matrix;
    }
}
