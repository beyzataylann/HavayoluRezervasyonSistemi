import java.io.Serializable;

public class Flight implements Serializable {
    private int flightID;
    private String flightDate;
    private String route;
    private Ticket[] ticketList;



    public Flight(int flightID, String flightDate, String route, Ticket[] ticketList) {
        this.flightID = flightID;
        this.flightDate = flightDate;
        this.route = route;
        this.ticketList = ticketList;
    }



    public Ticket[] getTicketList() {

        return ticketList;
    }


}
