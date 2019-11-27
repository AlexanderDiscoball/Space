package math;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SerReadMAin {
    public static void main(String[] args) {
        SerializationTest serializationTest = null;
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Serialize\\test.ser")) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            serializationTest = (SerializationTest) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        assert serializationTest != null;
        System.out.println(serializationTest.getSerializeArray().length);
    }
}
