package main.java;

public final class SolverFactory {

    private SolverFactory() {
    }

    public static Solver find(int year, int day) {
        String className = String.format(
                "main.java.y%d.Day%d",
                year,
                day
        );

        try {
            Class<?> clazz = Class.forName(className);

            return (Solver) clazz
                    .getDeclaredConstructor()
                    .newInstance();

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    "No solver found for " + year + " day " + day, e
            );
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(
                    "Could not create solver: " + className,
                    e
            );
        }
    }
}
