package under.verse.core.network.packet;

public abstract class Packet {
    private byte[] bytes;
    
    public byte[] toByteArray() {
        return bytes;
    }
    public abstract byte getId();
    
    @Override
    public String toString() {
        return "Packet{name=" + this.getClass().getSimpleName() + "}";
    }
}
