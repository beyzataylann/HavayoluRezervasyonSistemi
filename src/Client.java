import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

public class Client implements Runnable {
    private Flight flight;
    private Lock lock;
    private int ticketID;
    private String operationType;

    public Client(Flight flight, Lock lock, int ticketID, String operationType) {
        this.flight = flight;
        this.lock = lock;
        this.ticketID = ticketID;
        this.operationType = operationType;
    }

    @Override
    public void run() {
        if (operationType.equalsIgnoreCase("reader")) {
            readReservationList();
        } else if (operationType.equalsIgnoreCase("writer")) {
            makeReservation();
        } else if (operationType.equalsIgnoreCase("cancel")) {
            cancelReservation();
        }
    }

    private void readReservationList() {
        lock.lock();
        try {
            Ticket[] tickets = flight.getTicketList();
            for (Ticket ticket : tickets) {
                String status = ticket.isTicketState() ? "Revize" : "Boş";
                PrintStream out = System.out;
                out.println("Ticket " + ticket.getTicketNumber() + ": " + status);
            }
        } finally {
            lock.unlock();
        }
    }

    private void makeReservation() {
        lock.lock();
        try {
            Ticket ticket = flight.getTicket(ticketID);
            if (ticket.isTicketState()) {
                System.out.println(ticket.getTicketNumber() + " rezerve edilmiştir. Lütfen farklı koltuk numarası seçiniz.");
            } else {
                ticket.setTicketState(true);
                ticket.setTicketHolder(1);
                System.out.println("Rezervasyon başarılı ile gerçekleşti: koltuk numaranız " + ticket.getTicketNumber());
            }
        } finally {
            lock.unlock();
        }
    }

    private void cancelReservation() {
        lock.lock();
        try {
            Ticket ticket = flight.getTicket(ticketID);
            if (!ticket.isTicketState()) {
                System.out.println(ticket.getTicketNumber() + " zaten boş. Lütfen farklı koltuk numarası seçiniz.");
            } else {
                ticket.setTicketState(false);
                ticket.setTicketHolder(0);
                System.out.println("Rezervasyon iptali başarılı: koltuk numaranız " + ticket.getTicketNumber());
            }
        } finally {
            lock.unlock();
        }
    }
}
