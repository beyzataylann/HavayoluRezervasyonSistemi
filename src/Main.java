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

        Thread readerThread1 = new Thread(new Client(flight, lock, 3, "reader"));
        readerThread1.start();

        Thread writerThread = new Thread(new Client(flight, lock, 5, "writer"));
        writerThread.start();

        Thread readerThread2 = new Thread(new Client(flight, lock, 1, "reader"));
        readerThread2.start();

        Thread cancelThread = new Thread(new Client(flight, lock, 3, "cancel"));
        cancelThread.start();

        try {
            readerThread1.join();
            writerThread.join();
            readerThread2.join();
            cancelThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
