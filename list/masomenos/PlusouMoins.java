package list.masomenos;

public class PlusouMoins {
    private static final int NOMBRE_MIN = 1;
    private static final int NOMBRE_MAX = 100;
    public static int genererNombreSecret() {
        return (int) (Math.random() * (NOMBRE_MAX - NOMBRE_MIN + 1)) + NOMBRE_MIN;
    }
}
