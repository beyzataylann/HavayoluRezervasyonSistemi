import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        // Create Flight with example tickets
        Ticket[] tickets = new Ticket[5];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket(i, "TKT" + i, false, 0);
        }
        Flight flight = new Flight(1, "2024-07-01", "Istanbul - London", tickets);

        // Create Lock for synchronization
        Lock lock = new ReentrantLock();

        // Create Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Ask user for operation type: reader or writer
        System.out.println("Enter 'reader' for reading reservation status, 'writer' for making reservation:");
        String operationType = scanner.nextLine();

        // Determine operation based on user input
        if (operationType.equalsIgnoreCase("reader")) {
            System.out.println("Reading reservation status for all tickets:");
            // Create multiple clients as readers
            for (int i = 0; i < tickets.length; i++) {
                ClientWithLock client = new ClientWithLock(flight, lock, i, "reader");
                Thread thread = new Thread(() -> client.start());
                thread.start();
            }
        } else if (operationType.equalsIgnoreCase("writer")) {
            System.out.println("Enter ticket number to reserve (0-" + (tickets.length - 1) + "):");
            int ticketNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Create a single client as writer for reservation
            ClientWithLock client = new ClientWithLock(flight, lock, ticketNumber, "writer");
            Thread thread = new Thread(() -> client.start());
            thread.start();
        } else {
            System.out.println("Invalid operation type entered. Please enter 'reader' or 'writer'.");
        }

        // Close scanner
        scanner.close();
    }
}
