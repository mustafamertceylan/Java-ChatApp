package ChatServer;

import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer {
    private static final int PORT = 12345; // Sunucunun dinleyeceği port numarası
    private static Map<String, PrintWriter> clientWriters = new HashMap<>(); // Kullanıcı adlarını ve yazdıklarını eşleştiren bir harita

    public static void main(String[] args) {
        try {
            // Sunucunun yerel IP adresi alınır
            String localIp = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Sunucu başlatıldı: " + localIp + ":" + PORT);

            // Sunucu belirtilen port üzerinde başlatılır
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Bağlantılar bekleniyor...");

            // Kullanıcılar sunucuya bağlandıkça kabul edilir ve her biri için bir thread başlatılır
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Kullanıcı ile iletişimi yöneten iç sınıf
    public static class ClientHandler extends Thread {
        private Socket socket; // İstemci ile bağlantıyı temsil eder
        private PrintWriter out; // Mesaj göndermek için kullanılan akış
        private BufferedReader in; // Mesaj almak için kullanılan akış
        private String userName; // Kullanıcının  mesajlaşırkenki kullanıcı adı

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Veri alışverişi için kullanılacak veri giriş çıkışlarının ayarlanması
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Kullanıcı adı alınır
                synchronized (clientWriters) {
                    while (true) {
                        out.println("Lütfen kullanıcı adınızı giriniz:");
                        userName = in.readLine(); // Kullanıcıdan giriş alınır

                        if (userName == null || userName.trim().isEmpty()) {
                            out.println("Geçersiz kullanıcı adı. Tekrar deneyin.");
                        } else if (clientWriters.containsKey(userName)) {
                            out.println("Bu kullanıcı adı zaten kullanımda. Başka bir isim seçin.");
                        } else {
                            clientWriters.put(userName, out); // Kullanıcı adı kabul edilir ve listeye eklenir
                            out.println("Kullanıcı adı kabul edildi: " + userName);
                            break;
                        }
                    }
                }

                System.out.println(userName + " bağlandı.");
                broadcastMessage("Server: " + userName + " sohbete katıldı.");

                // Kullanıcıdan gelen mesajları dinler
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("@")) {
                        // Özel mesaj gönderme
                        String[] parts = message.split(" ", 2); // Mesaj iki parçaya bölünür: hedef kullanıcı ve mesaj
                        if (parts.length < 2) {
                            out.println("Hatalı mesaj formatı. Örnek: @kullanıcı_adi mesaj");
                            continue;
                        }
                        String targetUser = parts[0].substring(1); // targetUser : "@" işaretinden sonra gelen kullanıcı adı
                        String msgText = parts[1];

                        if (clientWriters.containsKey(targetUser)) {
                            // Hedef kullanıcıya mesaj gönderilir
                            clientWriters.get(targetUser).println(userName + " (özel): " + msgText);
                        } else {
                            out.println("Kullanıcı bulunamadı: " + targetUser);
                        }
                    } else {
                        // Tüm kullanıcılara mesaj gönderilir
                        broadcastMessage(userName + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Kullanıcının sunucudan bağlantısı kesildiğinde kullanıcı sunucudan silinir
                if (userName != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(userName); // Kullanıcı listeden çıkarılır
                    }
                    broadcastMessage("Server: " + userName + " sohbetten ayrıldı.");
                    System.out.println(userName + " bağlantıyı kapattı.");
                }
                try {
                    socket.close(); // Bağlantı kapatılır
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Tüm kullanıcılara mesaj gönderen metot
        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters.values()) {
                    writer.println(message);
                }
            }
        }

        // Kullanıcı adı güncelleme işlemini yapan metot
        public static boolean updateUserName(String oldUserName, String newUserName) {
            synchronized (clientWriters) {
                if (newUserName == null || newUserName.trim().isEmpty() || clientWriters.containsKey(newUserName)) {
                    return false; // Geçersiz veya zaten kullanımda olan kullanıcı adı
                }

                PrintWriter writer = clientWriters.remove(oldUserName); // Eski kullanıcı adı silinir
                if (writer != null) {
                    clientWriters.put(newUserName, writer); // Yeni kullanıcı adı eklenir
                    broadcastGlobalMessage("Server: " + oldUserName + " kullanıcı adını " + newUserName + " olarak değiştirdi.");
                    return true; // Güncelleme başarılı
                }
                return false; // Kullanıcı adı bulunamadı
            }
        }

        // Tüm kullanıcılara sunucu mesajı gönderen yöntem
        private static void broadcastGlobalMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters.values()) {
                    writer.println(message);
                }
            }
        }
    }
}
