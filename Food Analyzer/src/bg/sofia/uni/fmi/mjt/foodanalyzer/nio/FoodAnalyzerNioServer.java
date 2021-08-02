package bg.sofia.uni.fmi.mjt.foodanalyzer.nio;

import bg.sofia.uni.fmi.mjt.foodanalyzer.FoodAnalyzer;
import bg.sofia.uni.fmi.mjt.foodanalyzer.FoodRequestValidator;
import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.FoodAnalyzerException;
import bg.sofia.uni.fmi.mjt.foodanalyzer.exceptions.FoodNotFoundException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class FoodAnalyzerNioServer {
    private static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 100000;
    private final ByteBuffer buffer;

    private final FoodAnalyzer analyzer;

    public FoodAnalyzerNioServer() {
        this.buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        analyzer = new FoodAnalyzer();
    }

    public static void main(String[] args) {
        new FoodAnalyzerNioServer().start();
    }

    private void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        communicateWithClient(socketChannel);
                    } else if (key.isAcceptable()) {
                        acceptSocketConnection(key, selector);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            System.out.println("There is a server socket error");
            e.printStackTrace();
        }
    }

    private void acceptSocketConnection(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }

    private void communicateWithClient(SocketChannel socketChannel) throws IOException {
        buffer.clear();
        int r = socketChannel.read(buffer); // request is loaded in buffer
        if (r < 0) {
            System.out.println("Client has closed the connection");
            socketChannel.close();
            return;
        }
        String request = getRequestFromBuffer();
        processRequest(request, socketChannel);

    }

    private String getRequestFromBuffer() {
        buffer.flip();
        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);
        String request = new String(byteArray, StandardCharsets.UTF_8);
        buffer.clear();
        return request;
    }

    private void processRequest(String request, SocketChannel socketChannel) throws IOException {
        String reply;
        if (request.equals("disconnect")) {
            socketChannel.close();
        } else if (!FoodRequestValidator.isRequestValid(request)) {
            reply = "Invalid food request!";
            sendReply(reply, socketChannel);
        } else {
            try {
                reply = analyzer.getFoodInformation(request).toString();
            } catch (FoodNotFoundException e) {
                reply = "Food is not found!";
                sendReply(reply, socketChannel);
                return;
            } catch (FoodAnalyzerException e) {
                reply = "Bad input parameters!";
                sendReply(reply, socketChannel);
                return;
            }
            sendReply(reply, socketChannel);
        }
    }

    private void sendReply(String reply, SocketChannel socketChannel) throws IOException {
        buffer.put(reply.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

}
