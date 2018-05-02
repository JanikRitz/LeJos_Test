public interface BTGeneric extends Runnable {
    int commLoop();

    void addObject(int x, int y, MapNavigationPilot.MapObject object);

    @Override
    void run();
}
