package math;

public class InputData {
    private static final int channelAmount = 100;
    private static final int segmentsAmount = 100;
    private static final int dropPoints = 69;
    private static final boolean needStatistics = true;
    private static final boolean needCheckResults = false;

    private static final int LAMBDA = 5;
    private static final int COEFSPACE = 1;
    private static final int COEFLENGTH = 1;
    private static final float COEFDROPPOINTS = 1f;

    private static final int seed = 10;
    private static final int timeAmount = 1000000;
    private static final int voluntaristCriteria = 40;

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

    public static float getCoefDropPoints(){
        return COEFDROPPOINTS;
    }

    public static boolean getNeedStatistics(){
        return needStatistics;
    }

    public static boolean getCheckResults(){
        return needCheckResults;
    }

    public static int getSeed() {
        return seed;
    }

    public static int getVoluntaristCriteria() {
        return voluntaristCriteria;
    }

}