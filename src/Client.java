import java.io.PrintStream;
import java.util.concurrent.locks.Lock;

public class Client {
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

    public void start() {
        if (this.operationType.equalsIgnoreCase("reader")) {
            this.readReservation();
        } else if (this.operationType.equalsIgnoreCase("writer")) {
            this.makeReservation();
        } else if (this.operationType.equalsIgnoreCase("cancel")) {
            this.cancelReservation();
        }
    }

    private void readReservation() {
        this.lock.lock();

        try {
            Ticket ticket = this.flight.getTicketList()[this.ticketID];
            PrintStream var10000 = System.out;
            String var10001 = ticket.getTicketNumber();
            var10000.println("Ticket " + var10001 + (ticket.isTicketState() ? " Revize" : " Boş"));
        } finally {
            this.lock.unlock();
        }
    }

    private void makeReservation() {
        this.lock.lock();

        try {
            Ticket ticket = this.flight.getTicketList()[this.ticketID];
            if (ticket.isTicketState()) {
                System.out.println(ticket.getTicketNumber() + " rezerve edilmiştir. Lütfen farklı koltuk numarası seçiniz.");
            } else {
                ticket.setTicketState(true);
                ticket.setTicketHolder(1);
                System.out.println("Rezervasyon başarılı ile gerçekleşti: koltuk numaranız " + ticket.getTicketNumber());
            }
        } finally {
            this.lock.unlock();
        }
    }

    private void cancelReservation() {
        this.lock.lock();

        try {
            Ticket ticket = this.flight.getTicketList()[this.ticketID];
            if (!ticket.isTicketState()) {
                System.out.println(ticket.getTicketNumber() + " zaten boş. Lütfen farklı koltuk numarası seçiniz.");
            } else {
                ticket.setTicketState(false);
                ticket.setTicketHolder(0);
                System.out.println("Rezervasyon iptali başarılı: koltuk numaranız " + ticket.getTicketNumber());
            }
        } finally {
            this.lock.unlock();
        }
    }
}
