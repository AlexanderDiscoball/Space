package math.entity.interval;

public class DropInterval {
    private int firstDot;
    private int secondDot;
    private int memory;

    public DropInterval(int firstDot, int secondDot, int memory) throws IllegalArgumentException{
        if(firstDot <= secondDot) {
            this.firstDot = firstDot;
            this.secondDot = secondDot;
            this.memory = memory;
        }
        else throw new IllegalArgumentException("Первое число должно быть меньше второго");
    }

    public int getFirstDot() {
        return firstDot;
    }

    public int getSecondDot() {
        return secondDot;
    }

    public int getMemory() {
        return memory;
    }

    @Override
    public String toString() {
        return firstDot
                +"-"
                +secondDot
                +" Mem"
                +memory;
    }
}
