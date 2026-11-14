package under.verse.core;

import java.util.concurrent.ConcurrentHashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {
    
    private static final Logger logger = new Logger();
    private static final ConcurrentHashMap<String, Logger> map = new ConcurrentHashMap<>();
    private final String name;
    private boolean b = false;
    
    public void info(String text) {
        System.out.print("\r\u001B[2K");
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " INFO]: " + name + text);
        System.out.print("> ");
    }
    public void warning(String text) {
        System.out.print("\r\u001B[2K");
        System.out.println("\u001B[33m[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " WARN]: " + name + text + "\u001B[0m");
        System.out.print("> ");
    }
    public void error(String text) {
        System.out.print("\r\u001B[2K");
        System.out.println("\u001B[31m[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " ERROR]: " + name + text + "\u001B[0m");
        System.out.print("> ");
    }
    public void fatal(String text) {
        System.out.print("\r\u001B[2K");
        System.out.println("\u001B[31m[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " FATAL]: " + name + text);
        if (!b) UnderCore.shutdown();
    }
    public void info(Throwable t) {
        for (String str : getTrace(t)) info(str);
    }
    public void warning(Throwable t) {
        for (String str : getTrace(t)) warning(str);
    }
    public void error(Throwable t) {
        for (String str : getTrace(t)) error(str);
    }
    public void fatal(Throwable t) {
        b = true;
        for (String str : getTrace(t)) fatal(str);
        UnderCore.shutdown();
    }
    private static String[] getTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString().split("\n");
    }
    public static Logger getLogger() {
        return logger;
    }
    public static Logger getLogger(String name) {
        Logger l = map.get(name);
        if (l == null) {
            Logger newL = new Logger(name);
            map.put(name, newL);
            return newL;
        }
        return l;
    }
    private Logger() { this.name = ""; }
    private Logger(String name) { this.name = "[" + name + "] "; }
}
