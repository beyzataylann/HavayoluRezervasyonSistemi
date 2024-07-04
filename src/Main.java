import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {

        Ticket[] tickets = new Ticket[7];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket(i, "Ticket" + i, false, 0);
        }
        Flight flight = new Flight(1, "2024-07-04", "Samsun - Ankara", tickets);

        Lock lock = new ReentrantLock();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Koltuk numarlarını listelemek için reader, bilet alımı için writer, rezervasyon iptali için cancel seçeneğini yazınız:");
            String operationType = scanner.nextLine();

            if (operationType.equalsIgnoreCase("exit")) {
                break;
            }

            if (operationType.equalsIgnoreCase("reader")) {
                System.out.println("Koltuklar:");

                for (int i = 0; i < tickets.length; i++) {
                    Client client = new Client(flight, lock, i, "reader");
                    Thread thread = new Thread(() -> client.start());
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (operationType.equalsIgnoreCase("writer")) {
                System.out.println("Kaç numaralı koltuğu almak istiyorunuz (0-" + (tickets.length - 1) + "):");
                int ticketNumber = scanner.nextInt();
                scanner.nextLine();

                Client client = new Client(flight, lock, ticketNumber, "writer");
                Thread thread = new Thread(() -> client.start());
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (operationType.equalsIgnoreCase("cancel")) {
                System.out.println("Kaç numaralı koltuğu iptal etmek istiyorunuz (0-" + (tickets.length - 1) + "):");
                int ticketNumber = scanner.nextInt();
                scanner.nextLine();

                Client client = new Client(flight, lock, ticketNumber, "cancel");
                Thread thread = new Thread(() -> client.start());
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Geçersiz işlem lütfen tekrar deneyiniz!");
            }
        }


        scanner.close();
    }
}
