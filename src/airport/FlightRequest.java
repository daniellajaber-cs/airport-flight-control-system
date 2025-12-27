package airport;
//flightRequest class represents one landing request in the airport
// it stores the flight,its priority level, and the time of the request

public class FlightRequest {
	private Flight flight;  //the flight that made the request
	private int priorityLevel;  //priority like 1= emergency
	private long requestTime;  // time used to break ties between flights
	
	
	public FlightRequest(Flight flight , int priorityLevel,long requestTime) {
		this.flight=flight;
		this.priorityLevel=priorityLevel;
		this.requestTime=requestTime;
	}
	
	// Returns the flight associated with this request
    public Flight getFlight() {
        return flight;
    }


    public int getPriorityLevel() {
        return priorityLevel;
    }

    // Changes the priority level (useful when making a flight emergency)
    public void setPriorityLevel(int level) {
        this.priorityLevel = level;
    }

   
    public long getRequestTime() {
        return requestTime;
    }

    
    public String toString() {
        return "FlightRequest[flight=" + flight.getFlightNo() +
               ", priorityLevel=" + priorityLevel +
               ", requestTime=" + requestTime + "]";
    }

}
