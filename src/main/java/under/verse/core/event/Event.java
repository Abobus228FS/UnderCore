package under.verse.core.event;

public abstract class Event {
    public abstract void run();
    
    @Override
    public String toString() {
        return "Event{name=" + this.getClass().getSimpleName() + "}";
    }
}
