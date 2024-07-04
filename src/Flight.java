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

    public Ticket getTicket(int ticketID) {
        if (ticketID >= 0 && ticketID < ticketList.length) {
            return ticketList[ticketID];
        }
        return null;
    }

    public int getFlightID() {
        return flightID;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getRoute() {
        return route;
    }
}
