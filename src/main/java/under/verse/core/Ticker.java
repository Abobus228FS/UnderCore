package under.verse.core;

public final class Ticker {
    private Ticker() {}
    static {
        new Thread(() -> {
            while (true) {
                long millis = System.currentTimeMillis();
                long nano = System.nanoTime();
                try {
                    tick();
                    int tps = UnderCore.getConfig().getInt("tick-per-second", 20);
                    int tickTimeMs = 1000 / tps;
                    long[] timings = timings(tickTimeMs - (System.currentTimeMillis() - millis), tickTimeMs * 1000000 - (System.nanoTime() - nano));
                    if (timings[0] >= 0 && timings[1] >= 0) Thread.sleep(timings[0], (int) timings[1]);
                } catch (Throwable t) {
                    Logger.getLogger().error("ОУ ШИТ БЛЯТЬ У ТЕБЯ ЯДРО КРАШНУЛОСЬ ИЛИ ПЛАГИН ВЫДАЛ ХУЙНЮ АЛО"); //это что такое XD
                    Logger.getLogger().error(t);
                }
            }
        }).start();
    }
    private static final void tick() {}
    private static final long[] timings(long millis, long nano) {
        nano -= (millis * 1000000);
        while (true) {
            if (nano >= 1000000) {
                int i = (int) nano / 1000000;
                nano = nano % 1000000;
                millis += i;
            } else {
                return new long[]{ millis, nano };
            }
        }
    }
}
