package main.java.framework;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        AppConfig config = AppConfig.fromArgs(args);

        Runner runner = new Runner(config);
        runner.run();
    }
}
