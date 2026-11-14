package under.verse.core.event;

import java.util.Map;
import java.util.concurrent.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.InvocationTargetException;
import under.verse.core.event.player.*;
import under.verse.core.event.block.*;

public class EventManager {

    private static final ConcurrentHashMap<Class<? extends Event>, ConcurrentHashMap<EventPriority, ConcurrentHashMap<Method, Listener>>> listeners = new ConcurrentHashMap<>();
    private static final EventManager instance = new EventManager();
    
    static {
        registerEvent(PlayerJoinEvent.class);
        registerEvent(PlayerQuitEvent.class);
        registerEvent(PlayerChatEvent.class);
        registerEvent(PlayerCommandEvent.class);
        registerEvent(PlayerMoveEvent.class);
        registerEvent(PlayerKickEvent.class);
        registerEvent(PlayerPluginMessageEvent.class);
        registerEvent(BlockBreakEvent.class);
    }
    
    public static void throwEvent(Event event) {
        Map map = listeners.get(event.getClass());
        if (map != null) {
            for (EventPriority ep : EventPriority.values()) {
                ((ConcurrentHashMap) map.get(ep)).forEach((method, listener) -> {
                    try {
                        ((Method) method).invoke(listener, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        event.run();
    }
    public static void registerListener(Listener listener) {
        Class<?> clazz = listener.getClass();
        if (!isListener(listener)) {
            throw new IllegalArgumentException();
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            EventHandler annotation = method.getAnnotation(EventHandler.class);
            if (annotation != null) {
                Map map = listeners.get(method.getParameters()[0].getType());
                if (map == null) throw new IllegalArgumentException();
                method.setAccessible(true);
                listeners.get(method.getParameters()[0].getType()).get(annotation.priority()).put(method, listener);
            }
        }
    }
    private static boolean isListener(Listener listener) {
        Class<?> clazz = listener.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            EventHandler annotation = method.getAnnotation(EventHandler.class);
            if (annotation != null) {
                Parameter[] parametrs = method.getParameters();
                if (parametrs.length != 1) throw new IllegalArgumentException();
                if (!Event.class.isAssignableFrom(parametrs[0].getType())) throw new IllegalArgumentException();
                    return true;
            }
        }
        return false;
    }
    public static void registerEvent(Event event) {
        registerEvent(event.getClass());
    }
    public static void registerEvent(Class<? extends Event> event) {
        ConcurrentHashMap<EventPriority, ConcurrentHashMap<Method, Listener>> map = new ConcurrentHashMap<>();
        for (EventPriority ep : EventPriority.values()) {
            map.put(ep, new ConcurrentHashMap<>());
        }
        listeners.put(event, map);
    }
    private EventManager() {}
    public static EventManager get() {
        return instance;
    }
}
