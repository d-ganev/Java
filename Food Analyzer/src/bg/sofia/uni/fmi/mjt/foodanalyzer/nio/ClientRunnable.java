package bg.sofia.uni.fmi.mjt.foodanalyzer.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ClientRunnable implements Runnable {

    private final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    private final SocketChannel channel;

    public ClientRunnable(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.clear();
                if (channel.read(buffer) > 0) {
                    buffer.flip();
                    byte[] byteArray = new byte[buffer.remaining()];
                    buffer.get(byteArray);
                    String reply = new String(byteArray, StandardCharsets.UTF_8);
                    if (reply.equals("disconnect")) {
                        break;
                    }
                    System.out.println(reply);
                }
            } catch (IOException e) {
                System.err.println("[ Disconnected ]");
                break;
            }
        }
    }
}