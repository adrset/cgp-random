package cgp.simulation;

public class InputParams {
    static InputParams singleton;
    int columns = 3;
    int rows = 2;

    InputParams(){};

    public static InputParams getSingleton() {
        return singleton;
    }

    public static synchronized InputParams getInstance() {
        if (singleton == null) {
            singleton = new InputParams();
        }
        return singleton;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
