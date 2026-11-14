package under.verse.core.packet;

public abstract class Packet {
    private byte[] bytes;
    
    public byte[] toByteArray() {
        return bytes;
    }
    public abstract byte getId();
}
