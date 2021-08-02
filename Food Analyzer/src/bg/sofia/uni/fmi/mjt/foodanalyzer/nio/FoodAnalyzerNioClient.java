package bg.sofia.uni.fmi.mjt.foodanalyzer.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class FoodAnalyzerNioClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 7777;
    private static final ByteBuffer buffer = ByteBuffer.allocateDirect(512);

    public static void main(String[] args) {
        new FoodAnalyzerNioClient().start();
    }

    private void start() {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println("Connected to the server.");
            new Thread(new ClientRunnable(socketChannel)).start();

            while (socketChannel.isOpen()) {
                String input = scanner.nextLine();
                writeToServer(input, socketChannel);
                if ("disconnect".equals(input)) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred in the client I/O: " + e.getMessage());
        }
    }

    private void writeToServer(String message, SocketChannel socketChannel) {
        try {
            buffer.clear();
            buffer.put(message.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
        } catch (IOException e) {
            System.err.println("An error occurred in the client I/O: " + e.getMessage());
        }
    }
}