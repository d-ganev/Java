package bg.sofia.uni.fmi.mjt.wish.list;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WishListClient {
    private static final int BUFFER_SIZE = 512;
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_HOST = "localhost";
    private static  ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

    public static void main() {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

            while (true) {
                System.out.print("=> ");
                String message = scanner.nextLine();
                if ("disconnect".equals(message)) {
                    break;
                }

                buffer.clear();
                buffer.put(message.getBytes());
                buffer.flip();
                socketChannel.write(buffer);

                buffer.clear();
                socketChannel.read(buffer);
                buffer.flip();

                byte[] byteArray = new byte[buffer.remaining()];
                buffer.get(byteArray);
                String reply = new String(byteArray, StandardCharsets.UTF_8);

                System.out.println(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}