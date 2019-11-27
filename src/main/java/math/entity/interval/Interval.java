package math.entity.interval;

public class Interval implements Comparable<Interval>{

    private int firstDot;
    private int secondDot;
    private int roll;
    private int length;
    private int priority;
    private int areaId;

    public Interval(int firstDot, int secondDot, int roll, int areaId) throws IllegalArgumentException{
        if(firstDot <= secondDot) {
            this.firstDot = firstDot;
            this.secondDot = secondDot;
            this.roll = roll;
            this.areaId = areaId;
            this.length = secondDot - firstDot;
            this.priority = setPriority(roll);
        }
        else throw new IllegalArgumentException("Первое число должно быть меньше второго");
    }

    public Interval(int firstDot, int secondDot, int length, int roll, int areaId) throws IllegalArgumentException{
        if(firstDot <= secondDot) {
            this.firstDot = firstDot;
            this.secondDot = secondDot;
            this.roll = roll;
            this.areaId = areaId;
            this.length = length;
            this.priority = setPriority(roll);
        }
        else throw new IllegalArgumentException("Первое число должно быть меньше второго");
    }

    private int setPriority(int line) {
        if(line<0){
            return line - (2*line);
        }
        else return line;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public Integer getLength(){
        return length;
    }

    public int getFirstDot() {
        return firstDot;
    }

    public int getSecondDot() {
        return secondDot;
    }

    public int getRoll() {
        return roll;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public String toString(){
        return
                +firstDot
                +"-"
                +secondDot
                +" R"
                + roll
                +" Id"
                +areaId;
    }


    @Override
    public int compareTo(Interval o) {
        Integer x1 = this.secondDot;
        Integer x2 = o.secondDot;
        int sComp = x1.compareTo(x2);

        if (sComp != 0) {
            return sComp;
        } else {
            x1 = this.firstDot;
            x2 = o.firstDot;
            sComp = x1.compareTo(x2);
            if (sComp != 0) {
                return sComp;
            } else {
                x1 = this.roll;
                x2 = o.roll;
                sComp = x1.compareTo(x2);
            }
        }
        return sComp;
    }

    public boolean isSegmentIntersections(Interval interval){
        return (this.getFirstDot() >= interval.getSecondDot() || this.getSecondDot() <= interval.getFirstDot());
    }
}
