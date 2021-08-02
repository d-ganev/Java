package bg.sofia.uni.fmi.mjt.wish.list;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class WishListServer {
    private static final int BUFFER_SIZE = 512;

    private HashMap<String, ArrayList<String>> students;
    private Selector selector;
    private ByteBuffer commandBuffer;
    private ServerSocketChannel ssc;
    private boolean isThereInput = true;
    private boolean isServerRunning = true;

    public WishListServer(int port) {
        try {
            students = new HashMap<>();
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            commandBuffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (isServerRunning) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        isThereInput = true;
                        read(key);
                        if (!isThereInput) {
                            continue;
                        }
                    } else if (key.isAcceptable()) {
                        accept(key);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isServerRunning = false;
    }

    private void read(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            commandBuffer.clear();
            int r;
            r = sc.read(commandBuffer);
            if (r <= 0) {
                //System.out.println("nothing to read, will close channel");
                sc.close();
                isThereInput = false;
                return;
            }
            commandBuffer.flip();
            byte[] byteArray = new byte[commandBuffer.remaining()];
            commandBuffer.get(byteArray);
            String input = new String(byteArray, StandardCharsets.UTF_8);
            input = input.replace(System.lineSeparator(), "");
            String reply = getExecutedCommand(input) + System.lineSeparator();
            commandBuffer.clear();
            commandBuffer.put(reply.getBytes());
            commandBuffer.flip();
            sc.write(commandBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
            SocketChannel accept;
            accept = sockChannel.accept();
            accept.configureBlocking(false);
            accept.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getExecutedCommand(String input) {
        String[] words = input.split(" ");
        final int commandIndex = 0;
        String command = words[commandIndex];

        return switch (command) {
            case "post-wish" -> postWish(words);
            case "get-wish" -> getWish();
            case "disconnect" -> disconnect();
            default -> "[ Unknown command ]";
        };
    }

    private String getWish() {
        if (!students.isEmpty()) {
            Random randomInt = new Random();
            String randomStudent = (String) students.keySet().toArray()[randomInt.nextInt(students.size())];
            ArrayList<String> wishesOfRandomStudent = students.remove(randomStudent);
            return "[ " + randomStudent + ": " + wishesOfRandomStudent + " ]";
        }

        return "[ There are no students present in the wish list ]";
    }

    private String postWish(String[] wordsOfInput) {
        final int indexOfStudent = 1;
        String student = wordsOfInput[indexOfStudent];
        StringBuilder wishBuilder = new StringBuilder();
        final int startIndexOfWish = 2;
        for (int i = startIndexOfWish; i < wordsOfInput.length; i++) {
            wishBuilder.append(wordsOfInput[i]);
            if (i < wordsOfInput.length - 1) {
                wishBuilder.append(" ");
            }
        }
        String currWish = new String(wishBuilder);
        ArrayList<String> wishesOfCurrStudent = students.get(student);

        if (!students.containsKey(student)) {
            ArrayList<String> newWishList = new ArrayList<>();
            newWishList.add(currWish);
            students.put(student, newWishList);
            return "[ Gift " + currWish + " for student " + student + " submitted successfully ]";
        } else if (students.containsKey(student) && !wishesOfCurrStudent.contains(currWish)) {
            students.remove(student);
            wishesOfCurrStudent.add(currWish);
            students.put(student, wishesOfCurrStudent);
            return "[ Gift " + currWish + " for student " + student + " submitted successfully ]";
        }
        return "[ The same gift for student " + student + " was already submitted ]";
    }

    private String disconnect() {
        return "[ Disconnected from server ]";
    }

}
