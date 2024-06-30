import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        // Create Flight with example tickets
        Ticket[] tickets = new Ticket[5];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket(i, "Ticket" + i, false, 0);
        }
        Flight flight = new Flight(1, "2024-07-04", "Samsun - Ankara", tickets);

        // Create Lock for synchronization
        Lock lock = new ReentrantLock();

        // Create Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Infinite loop for user operations
        while (true) {
            // Ask user for operation type: reader or writer
            System.out.println("Koltuk numarlarını listelemek için reader ,bilet alımı için writer seçeneğini yazınız:");
            String operationType = scanner.nextLine();

            if (operationType.equalsIgnoreCase("exit")) {
                break; // Exit the loop if user types 'exit'
            }

            // Determine operation based on user input
            if (operationType.equalsIgnoreCase("reader")) {
                System.out.println("Koltuklar:");
                // Create multiple clients as readers
                for (int i = 0; i < tickets.length; i++) {
                    ClientWithLock client = new ClientWithLock(flight, lock, i, "reader");
                    Thread thread = new Thread(() -> client.start());
                    thread.start();
                    try {
                        thread.join(); // Ensure the thread finishes before the next iteration
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (operationType.equalsIgnoreCase("writer")) {
                System.out.println("Kaç numaralı koltuğu almak istiyorunuz (0-" + (tickets.length - 1) + "):");
                int ticketNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                // Create a single client as writer for reservation
                ClientWithLock client = new ClientWithLock(flight, lock, ticketNumber, "writer");
                Thread thread = new Thread(() -> client.start());
                thread.start();
                try {
                    thread.join(); // Ensure the thread finishes before the next iteration
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid operation type entered. Please enter 'reader', 'writer' or 'exit'.");
            }
        }

        // Close scanner
        scanner.close();
    }
}
