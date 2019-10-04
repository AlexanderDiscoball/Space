package math.entity;


import planner.spacecraft.AbstractKA;
import planner.spacecraft.AbstractMPZ;

import java.time.LocalDateTime;

public class MyMPZ extends AbstractMPZ {

    public LocalDateTime tstart; //время начала работы
    public double duration; //длительность в секундах

    public MyMPZ(LocalDateTime tstart,long duration){
        this.tstart = tstart;
        this.duration = duration;
    }
    @Override
    public double getGraphicsDuration(AbstractKA abstractKA) {
        return 0;
    }

    @Override
    public LocalDateTime getGraphicsStartTime(AbstractKA abstractKA) {
        return null;
    }

    @Override
    public double getCorrectionRoll(AbstractKA abstractKA) {
        return 0;
    }

    @Override
    public double getCorrectionPitch(AbstractKA abstractKA) {
        return 0;
    }

    @Override
    public double getCorrectionYaw(AbstractKA abstractKA) {
        return 0;
    }

    @Override
    public Double getMinimumSunAngle(AbstractKA abstractKA) {
        return null;
    }

    @Override
    public double getHorizontalFovAngleHalf(AbstractKA abstractKA) {
        return 0;
    }

    @Override
    public double getVerticalFovAngleHalf(AbstractKA abstractKA) {
        return 0;
    }

    @Override
    public Double getLeftRightStep(AbstractKA abstractKA) {
        return null;
    }

    @Override
    public Double getTopBottomStep(AbstractKA abstractKA) {
        return null;
    }
}
