package math;

public class InputData {
    private static final int channelAmount = 10;
    private static final int segmentsAmount = 10;
    private static final int timeAmount = 1000000;
    private static final int dropPoints = 10;
    private static final int LAMBDA = 5;
    private static final int COEFSPACE = 1;
    private static final int COEFLENGTH = 1;
    private static final int COEFDROPPOINTS = 1;
    private static final boolean needStatistics = false;
    private static final int seed = 10;

    public static int getChannelAmount() {
        return channelAmount;
    }

    public static int getSegmentsAmount() {
        return segmentsAmount;
    }

    public static int getTimeAmount() {
        return timeAmount;
    }

    public static int getDropPoints() {
        return dropPoints;
    }

    public static int getLAMBDA() {
        return LAMBDA;
    }

    public static int getCoefSpace(){
        return COEFSPACE;
    }

    public static int getCoefLength(){
        return COEFLENGTH;
    }
    public static int getCoefDropPoints(){
        return COEFDROPPOINTS;
    }

    public static boolean getNeedStatistics(){
        return needStatistics;
    }

    public static int getSeed() {
        return seed;
    }
}