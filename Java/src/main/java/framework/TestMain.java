package main.java.framework;

import java.lang.reflect.InvocationTargetException;

public class TestMain {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AppConfig config = AppConfig.fromArgs(args);

        new Tester(config).run();
    }
}
