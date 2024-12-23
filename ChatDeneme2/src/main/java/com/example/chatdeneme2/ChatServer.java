package com.example.chatdeneme2;

//import java.io.*;
//import java.net.*;
//import java.util.*;
//import java.util.concurrent.*;
//
//public class ChatServer {
//    private static final int PORT = 12345;
//    private static Map<String, PrintWriter> clientWriters = new HashMap<>(); // Kullanıcı adı -> PrintWriter
//
//    public static void main(String[] args) {
//        System.out.println("Server started...");
//        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//            while (true) {
//                new ClientHandler(serverSocket.accept()).start(); // Yeni istemci kabul edilir
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class ClientHandler extends Thread {
//        private Socket socket;
//        private PrintWriter out;
//        private BufferedReader in;
//        private String userName;
//
//        public ClientHandler(Socket socket) {
//            this.socket = socket;
//        }
//
//        public void run() {
//            try {
//                // Input ve output stream'lerini açıyoruz
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                out = new PrintWriter(socket.getOutputStream(), true);
//
//                // İstemciden kullanıcı adı alıyoruz
//                out.println("Enter your username:");
//                userName = in.readLine();
//
//                synchronized (clientWriters) {
//                    clientWriters.put(userName, out); // Kullanıcı adı ile PrintWriter'ı eşleştiriyoruz
//                }
//                out.println("Welcome " + userName + "! You can start chatting.");
//
//                // Sunucuya gelen mesajları okur ve hedef kişiye iletir
//                String message;
//                while ((message = in.readLine()) != null) {
//                    if (message.startsWith("@")) {
//                        // Mesajın başında @ varsa, hedef kullanıcı adı var demektir
//                        String[] messageParts = message.split(" ", 2);
//                        String targetUser = messageParts[0].substring(1); // @user şeklinden user'ı alıyoruz
//                        String messageText = messageParts[1];
//
//                        // Hedef kullanıcı varsa mesajı gönderiyoruz
//                        if (clientWriters.containsKey(targetUser)) {
//                            PrintWriter targetWriter = clientWriters.get(targetUser);
//                            targetWriter.println(userName + ": " + messageText);
//                        } else {
//                            out.println("User not found: " + targetUser);
//                        }
//                    } else {
//                        // Eğer @ yoksa, tüm kullanıcılara gönder
//                        synchronized (clientWriters) {
//                            for (PrintWriter writer : clientWriters.values()) {
//                                writer.println(userName + ": " + message);
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                // Bağlantı sonlandığında, kullanıcıyı listeden çıkarıyoruz
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                synchronized (clientWriters) {
//                    clientWriters.remove(userName);
//                }
//            }
//        }
//    }
//}


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

//public class ChatServer {
//    private static final int PORT = 12345;
//    private static Map<String, PrintWriter> clientWriters = new HashMap<>(); // Kullanıcı adı -> PrintWriter
//
//    public static void main(String[] args) {
//        System.out.println("Server started...");
//        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//            while (true) {
//                new ClientHandler(serverSocket.accept()).start(); // Yeni istemci kabul edilir
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class ClientHandler extends Thread {
//        private Socket socket;
//        private PrintWriter out;
//        private BufferedReader in;
//        private String userName;
//
//        public ClientHandler(Socket socket) {
//            this.socket = socket;
//        }
//
//        public void run() {
//            try {
//                // Input ve output stream'lerini açıyoruz
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                out = new PrintWriter(socket.getOutputStream(), true);
//
//                // Kullanıcı adını bağlantı sırasında alıyoruz
//                synchronized (clientWriters) {
//                    while (true) {
//                        out.println("Mesajlaşmaya başlamak için lütfen kullanıcı adını giriniz..."); // İstemciden kullanıcı adı talep edildiğini belirtmek için bir protokol mesajı
//                        userName = in.readLine();
//
//                        if (userName == null || userName.trim().isEmpty()) {
//                            out.println("Geçersiz kullanıcı adı");
//                        } else if (clientWriters.containsKey(userName)) {
//                            out.println("Kullanıcı adı alınmış");
//                        } else {
//                            clientWriters.put(userName, out);
//                            out.println("Kullanıcı adı kabul edildi..." + userName);
//                            break;
//                        }
//                    }
//                }
//
//                System.out.println(userName + " bağlandı.");
//                broadcastMessage("Server: " + userName + " Sohbete katıldı :)");
//
//
//                String message;
//                while ((message = in.readLine()) != null) {
//                    if (message.startsWith("@")) {
//                        String[] messageParts = message.split(" ", 2);
//                        String targetUser = messageParts[0].substring(1);
//                        String messageText = messageParts[1];
//
//
//                        if (clientWriters.containsKey(targetUser)) {
//                            PrintWriter targetWriter = clientWriters.get(targetUser);
//                            targetWriter.println(userName + ": " + messageText);
//                        } else {
//                            out.println("User not found: " + targetUser);
//                        }
//                    } else {
//
//                        broadcastMessage(userName + ": " + message);
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                synchronized (clientWriters) {
//                    clientWriters.remove(userName);
//                }
//                broadcastMessage("Server: " + userName + " sohbetten ayrıldı.");
//                System.out.println(userName + " adlı kullanıcının bağlantısı koptu.");
//            }
//        }
//
//        private void broadcastMessage(String message) {
//            synchronized (clientWriters) {
//                for (PrintWriter writer : clientWriters.values()) {
//                    writer.println(message);
//                }
//            }
//        }
//    }
//}
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Map<String, PrintWriter> clientWriters = new HashMap<>(); // Kullanıcı adı -> PrintWriter

    public static void main(String[] args) {
        System.out.println("Server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start(); // Yeni istemci kabul edilir
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Input ve output stream'lerini açıyoruz
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // İstemciden kullanıcı adı alınıyor (ilk mesaj olarak gönderiliyor)
                synchronized (clientWriters) {
                    while (true) {
                        userName = in.readLine(); // İlk mesaj kullanıcı adı olarak kabul edilir
                        if (userName == null || userName.trim().isEmpty()) {
                            out.println("Geçersiz kullanıcı adı");
                        } else if (clientWriters.containsKey(userName)) {
                            out.println("Kullanıcı adı zaten kullanılıyor, farklı bir isimle bağlanmayı deneyin.");
                        } else {
                            clientWriters.put(userName, out); // Kullanıcı adı kaydediliyor
                            out.println("Kullanıcı adı kabul edildi: " + userName);
                            break;
                        }
                    }
                }

                System.out.println(userName + " bağlandı.");
                broadcastMessage("Server: " + userName + " sohbete katıldı.");

                // Kullanıcıdan gelen mesajları okuma ve işleme
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("@")) {
                        String[] messageParts = message.split(" ", 2);
                        String targetUser = messageParts[0].substring(1);
                        String messageText = messageParts[1];

                        if (clientWriters.containsKey(targetUser)) {
                            PrintWriter targetWriter = clientWriters.get(targetUser);
                            targetWriter.println(userName + ": " + messageText);
                        } else {
                            out.println("Kullanıcı bulunamadı: " + targetUser);
                        }
                    } else {
                        broadcastMessage(userName + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(userName);
                }
                broadcastMessage("Server: " + userName + " sohbetten ayrıldı.");
                System.out.println(userName + " adlı kullanıcının bağlantısı koptu.");
            }
        }

        // Tüm istemcilere mesaj gönderme
        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters.values()) {
                    writer.println(message);
                }
            }
        }
    }
}
