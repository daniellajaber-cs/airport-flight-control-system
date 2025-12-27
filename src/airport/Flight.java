package airport;
//flight class represents one flight in the airport system .
//it stores data about a flight

public class Flight {
	
	private String flightNo;
	private String airline; //airline name 
	private String origin ; // city from 
	private String destination ; 
	private String status; // current status eg. waiting to land scheduled
	private int priorityLevel; // type of level (emergency , VIP...)
	
	public Flight(String flightNo, String airline, String origin,String destination, int priorityLevel) {
		this.flightNo=flightNo;
		this.airline=airline;
		this.origin=origin;
		this.destination=destination;
		this.priorityLevel=priorityLevel;
		this.status="Scheduled"; //assume it is just scheduled
	}
	
	//getter&setters
	public String getFlightNo() {
        return flightNo;
    }
	
	public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }


    public int getPriorityLevel() {
        return priorityLevel;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public void setPriorityLevel(int level) {
        this.priorityLevel = level;
    }

    //Returns a string that represents this flight.
    public String toString() {
        return "Flight[flightNo=" + flightNo +
               ", airline=" + airline +
               ", origin=" + origin +
               ", destination=" + destination +
               ", status=" + status +
               ", priorityLevel=" + priorityLevel + "]";

}
}
