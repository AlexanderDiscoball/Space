package math;

import math.entity.interval.Interval;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerWriteMain {
    public static void main(String[] args) {
        int amount = 150_000_000;
        Interval[] test = new Interval[amount];
        for (int i = 0; i < amount; i++) {
            test[i] = new Interval(1,1,1,1,1);
        }

        System.out.println("Создание обьектов завершено");

        SerializationTest serializationTest = new SerializationTest(test);
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Serialize\\test.ser")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serializationTest);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
