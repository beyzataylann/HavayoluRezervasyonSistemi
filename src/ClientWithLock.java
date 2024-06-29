import java.util.concurrent.locks.Lock;

public class ClientWithLock {

    private Flight flight;
    private Lock lock;
    private int ticketID;
    private String operationType;

    public ClientWithLock(Flight flight, Lock lock, int ticketID, String operationType) {
        this.flight = flight;
        this.lock = lock;
        this.ticketID = ticketID;
        this.operationType = operationType;
    }

    public void start() {
        if (operationType.equalsIgnoreCase("reader")) {
            readReservationStatus();
        } else if (operationType.equalsIgnoreCase("writer")) {
            makeReservation();
        }
    }

    private void readReservationStatus() {
        lock.lock();
        try {
            Ticket ticket = flight.getTicketList()[ticketID];
            System.out.println("Ticket " + ticket.getTicketNumber() + " - State: " + (ticket.isTicketState() ? "Occupied" : "Available"));
        } finally {
            lock.unlock();
        }
    }

    private void makeReservation() {
        lock.lock();
        try {
            Ticket ticket = flight.getTicketList()[ticketID];
            if (ticket.isTicketState()) {
                System.out.println("Ticket " + ticket.getTicketNumber() + " is already reserved.");
            } else {
                ticket.setTicketState(true);
                ticket.setTicketHolder(1); // Assuming client ID 1
                System.out.println("Reservation successful for ticket " + ticket.getTicketNumber());
            }
        } finally {
            lock.unlock();
        }
    }
}
