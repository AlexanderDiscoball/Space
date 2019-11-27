package math;

import math.entity.interval.Interval;

import java.io.Serializable;

public class SerializationTest implements Serializable {
    Interval[] serializeArray;

    public SerializationTest(Interval[] ser){
        serializeArray = ser;
    }

    public Interval[] getSerializeArray() {
        return serializeArray;
    }
}
