package math.entity;

import math.InputData;

import java.util.ArrayList;

public class BetterGeneratorRandom {
    public static Segment generateRandomSegment(int min,int max, int lineNumber){
        int firstDot = getRandomNumber(min,max);
        int secondDot  = getRandomNumber(firstDot+1,max);
        return new Segment(firstDot, secondDot, lineNumber);
    }

    private static int getRandomNumber(int start, int end) {
        return (start + (int) (Math.random() * end));
    }

    private static ArrayList<StackSegmentsList> generateStacksNumberOfChannels() {
       ArrayList<StackSegmentsList> matrixList = new ArrayList<>();
        for (int channel = 0; channel < InputData.getChannelAmount(); channel++) {
            matrixList.add(generateRandomStack(channel));
        }
        return matrixList;
    }

    private static StackSegmentsList generateRandomStack(int channel){
        StackSegmentsList stackSegmentsList = new StackSegmentsList(channel);
        int min = 0;
        int max = InputData.getSegmentsAmount();
        while (stackSegmentsList.size() != InputData.getSegmentsAmount()){
            stackSegmentsList.add(generateRandomSegment(min, max,channel));
            min = stackSegmentsList.get(stackSegmentsList.size()-1).getSecondDot();
        }
        return stackSegmentsList;
    }

    public static Segment[][] generateSegmentMatrix(){
        Segment[][] matrix = new Segment[InputData.getChannelAmount()][InputData.getSegmentsAmount()];
        ArrayList<StackSegmentsList> matrixList = generateStacksNumberOfChannels();
        for (int i = 0; i < InputData.getChannelAmount(); i++) {
            for (int j = 0; j < InputData.getSegmentsAmount(); j++) {
                matrix[i][j] = matrixList.get(i).get(j);
            }
        }
        return matrix;
    }
}
