package airport;

import java.util.HashMap;  //to store flights quickly
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//Airport System class controls all flights in the system .
	//it uses a HashMap to store and manage Flight objects

public class AirportSystem {
	
	
	private HashMap<String ,Flight> flights;
	//we created an object we use HashMaps that stores all flights
	//here the key = flight number which is a String 
	//value= flight object 
	
	//lets us easily add, find, or update flights using their flight number.
	
	private LinkedList<FlightRequest>landingQueue;
	//landing queue: a list of FlightRequest objects 
	//which will store all landing requests in order.
	
	private LinkedList<Flight>runwayQueue;
	// runway queue : normal FIFO queue of flights waiting to use the runway
	//which will hold flights waiting to use the runway, following the normal first-come, first-served rule.
	
	
	
	public AirportSystem () { 
		
		//constructor initializes the HashMap
		
		flights= new HashMap<String,Flight>();
		
		//constructor to initialize linkedList 
		
		landingQueue= new LinkedList<FlightRequest>();
		
		//constructor to initialize runway queue
		
		runwayQueue= new LinkedList<Flight>();
	}
	
	
	
	//METHOD 1--> addFlight() : adds a new flight to the system 
	
	public void addFlight(String flightNo, String airline,String origin, String destination,int priorityLevel) {
		
		//check if this flight already exists in the system 
		if(flights.containsKey(flightNo)) {
			System.out.println("Flight already exists: "+ flightNo);
			return;
		}
		
		//create a new flight object because hashmaps can only store objects
		// and because each flight needs its own data 
		
		Flight newFlight= new Flight (flightNo, airline, origin, destination, priorityLevel);
		
		//it stores it inside the HashMap using put 
		//where the key is the flight number and the value is the Flight object.
		
		flights.put(flightNo,newFlight);
		System.out.println("Flight added to the system: "+flightNo);
	}
	
	
	
	
	
	//METHOD 2--> searchFlight(flightNo): searches for a flight by its number 
	//then returns the flight object if found, else return null
	
	public Flight searchFlight(String flightNo) {
		return flights.get(flightNo); //which is a HashMap function that tries to find the value linked to that key
		// returns object for null
	}
	
	
	
	
	
	
	//METHOD 3--> deleteFlight(flightNo): deletes a flight from the HashMap 
	//then returns true if deletion was successful,false if not found 
	
	public boolean deleteFlight(String flightNo) {
		
		if(!flights.containsKey(flightNo)) { //starts by checking if the flight number exists in the HashMap
			System.out.println(" sorry . Cannot delete. Flight not found: "+ flightNo);
			return false;
		}
		
		flights.remove(flightNo); //remove the flight  from HashMap
		
		//After removing it, the method calls cancelLandingRequest(flightNo)
		//to delete any landing request connected to this flight 
		
		cancelLandingRequest(flightNo);
		
		System.out.println("Flight deleted: "+ flightNo);
		return true;
	}
	
	
	
	
	//METHOD4--> displayAllFlights(): displays all flights currently stored in the system 
	
	public void displayAllFlights() {
		//If the HashMap is empty, it prints that no flights are available.
		if(flights.isEmpty()) {
			System.out.println("No flights available ");
			return ;
		}
		
		//Otherwise, it loops through every Flight object 
		//in the HashMap and prints its details.
	
		
		//We don’t use a normal loop because HashMap does not have indexes like an array or list.
		
		for(Flight f : flights.values()) {  //Instead of looping through keys, we use values 
			                               //which gives us all the Flight objects directly to print its information.
			
			System.out.println(f.toString());  
		}
	}
	
	
	
	
	
	
	//METHOD 5--> updateFlightStatus(): updates the status of a flight 
	//example waiting to land ,scheduled 
	// returns true if updated , false if flight not found 
	
	public boolean updateFlightStatus(String flightNo, String newStatus) {
		Flight f= flights.get(flightNo); //search for the flight in the HashMap.
		
		if (f== null) {
			System.out.println("Sorry . Flight not found : "+flightNo); //the flight is not in the system
			return false;
		}
		//If the flight is found
		f.setStatus(newStatus); //update its status to the new value.
		
		System.out.println("Status updated for"+ flightNo+ "-->"+ newStatus);
		return true;
	
	}
	
	
	
	
	
	//METHOD 6- displayFlightDetails(flightNo) : display all details for one flight 
	
	public void displayFlightDetails(String flightNo) {
		Flight f =flights.get(flightNo); //finds the flight 
		if (f == null) {
            System.out.println("Flight not found: " + flightNo);
            return;
        }

        // print the flight using toString()
        System.out.println(f.toString());
    }
	
	
	
	
	
	
	//METHOD 7 --> requestLanding(flightNo): a flight request permission to land 
	// we create FlightRequest and insert it into Landing Queues
	//in correct position , based on priority and time 
	
	public boolean requestLanding (String flightNo) {
		Flight f = flights.get(flightNo); //searching for the flight in the HashMap using the flight number.
		
		if(f==null) {
			System.out.println(" Sorry. Cannot request landing. Flight not found:  "+ flightNo);
			return false;
		}
		
		//If it is found, we collect the flight’s priority and time
		
		int priority = f.getPriorityLevel();
        long time = System.currentTimeMillis();   // current time
        
        //create a new flight request object using the flight,priority and time 
        
        FlightRequest req = new FlightRequest(f,priority,time);
        
        //insert into LandingQueue in sorted order 
        //to place this request in the correct position inside the landing queue
        //sorted by priority and then by request time.
        
        insertIntoLandingQueue(req);
        
        //update flight status  so that flight now has status "WaitingToLand" instead of "Scheduled"
        f.setStatus("WaitingToLand");
        f.setStatus(" Landing request for flight : "+ flightNo);
        return true;
	}
	
	
	
	//helper method inserts a FlightRequest into landingQueue
	//decides where in the list the request should go (front,middle,last)
    // based on  priority and then time.
	
    private void insertIntoLandingQueue(FlightRequest req) {

        // If list is empty add the request at index 0 
        if (landingQueue.isEmpty()) {
            landingQueue.add(req);  
            return;
        }

        // Find the correct index where req should be inserted 
        int i = 0;
        while (i < landingQueue.size()) {
            FlightRequest current = landingQueue.get(i);

            // first, compare priority (smaller number = higher priority)
            
            if (req.getPriorityLevel() < current.getPriorityLevel()) {
                break;  // req should go before current
            }

            // if same priority, compare requestTime (earlier time goes first)
            
            if (req.getPriorityLevel() == current.getPriorityLevel() && req.getRequestTime() < current.getRequestTime()) {
                break;
            }

            i++;
        }

        // Once  found insert the new request into the queue at position i.
        landingQueue.add(i, req);
    }
	
    
    
    
    
    //METHOD 8 --> displayLandingQueue: displays all landing requests in order 
    //highest priority and earliest requests are printed first
    
    public void displayLandingQueue() {

        if (landingQueue.isEmpty()) {
            System.out.println("No landing requests.");
            return;
        }

        System.out.println("Landing Queue (from highest priority to lowest):");

        
        
        //goes through each request in the landing queue
        //For each position it retrieves the FlightRequest object
        //then gets the Flight linked to that request.
        
        
        for (int i = 0; i < landingQueue.size(); i++) {
            FlightRequest req = landingQueue.get(i); //store the request in a variable named req
            Flight f = req.getFlight(); //extracts the flight object from the request

            System.out.println((i + 1) + ". " + f.getFlightNo() + " | priority=" + req.getPriorityLevel() + " | status=" + f.getStatus());
                              //request number in the queue
        }
    }
    
    
    
    
    
    //METHOD 9 --> promoteToEmergency(flightNo): Changes a flight's landing request to emergency.
   //find → update → remove → reinsert
    
    public boolean promoteToEmergency(String flightNo) {

        // Loop through the landing queue to look for the flight’s landing request
        for (int i = 0; i < landingQueue.size(); i++) {

            FlightRequest req = landingQueue.get(i);

            // For each request, 
            //compare the flight number with the input flight number.
            if (req.getFlight().getFlightNo().equals(flightNo)) {

                // Update priority to emergency
                req.setPriorityLevel(1);
                
               // Update the Flight object’s priority as well
                req.getFlight().setPriorityLevel(1);

                // Remove it from the queue
                landingQueue.remove(i);

                // Add it back so the queue can reorder
                landingQueue.add(req);

                System.out.println("Flight promoted to EMERGENCY: " + flightNo);
                return true;
            }
        }

        // If the loop ends and nothing was found
        System.out.println("No landing request found for flight: " + flightNo);
        return false;
    }


      
    
    
    
    //METHOD 10--> cancelLandingRequest(flightNo) : cancels a landing for a specific flight 
    //removes its FlightRequest from LandingQueue (if exists)
    
    public boolean cancelLandingRequest(String flightNo) {
    	
    	//loops through the landing queue
        for (int i = 0; i < landingQueue.size(); i++) {
        	//get request at position i
            FlightRequest req = landingQueue.get(i);
            
            //Compare the flight number inside the request with the input flight number.
            
            if (req.getFlight().getFlightNo().equals(flightNo)) {
            	
                // Remove the request from the queue
                landingQueue.remove(i);

                //  update to scheduled
                req.getFlight().setStatus("Scheduled");

                System.out.println("Landing request cancelled for: " + flightNo);
                return true;
            }
        }
        
        //if request was not found 
        System.out.println("No landing request to cancel for: " + flightNo);
        return false;
    }
    
    
    
    
    
    // METHOD 11: assignNextflightToRunway - takes the next flight to the landingQueue
    //(highest priority) and move it to the runwayQueue 
    //returns true if successful, false if there is no flight to assign 
    
    public boolean assignNextFlightToRunway() {

        // If there are no landing requests, we cannot assign anything
        if (landingQueue.isEmpty()) {
            System.out.println("No flights waiting to land.");
            return false;
        }

        // Remove the first request from the landingQueue
        FlightRequest nextRequest = landingQueue.removeFirst();

        // Get the Flight from this request
        Flight f = nextRequest.getFlight();

        // Add the flight to the runwayQueue (at the end) FIFO ORDER 
        runwayQueue.addLast(f);

        // Update the flight's status
        f.setStatus("QueuedForRunway");

        System.out.println("Flight assigned to runway queue: " + f.getFlightNo());
        return true;
    }
    
    
    
    
    
    
    //METHOD 12 : processRunway()-processes the next flight in the runwayQueue
    //simulates that the flight has landed.
    
    public void processRunway() {

        // If no flights are waiting on the runway, nothing to process
        if (runwayQueue.isEmpty()) {
            System.out.println("No flights in runway queue.");
            return;
        }

        // Remove the flight at the front of the runwayQueue
        Flight f = runwayQueue.removeFirst();

        // Update status to "Landed"
        f.setStatus("Landed");

        System.out.println("Flight has landed: " + f.getFlightNo());
    }
    
    
    
    
    
    
    //METHOD 13 - displayRunwayQueue()- displays all flights currently waiting in the runwayQueue
    public void displayRunwayQueue() {

        if (runwayQueue.isEmpty()) {
            System.out.println("Runway queue is empty.");
            return;
        }

        System.out.println("Runway Queue (first = next to land):");

        for (int i = 0; i < runwayQueue.size(); i++) {
            Flight f = runwayQueue.get(i);

            System.out.println((i + 1) + ". " +
                    f.getFlightNo() +
                    " | status=" + f.getStatus() +
                    " | priority=" + f.getPriorityLevel());
        }
    }
    
    
    
    
    
   // METHOD 14- saveSystemState()- saves all flights in the system to a text file 
    //called flight.txt and each line in the file will contain one flight's information.
    
    public void saveSystemState() {

        try {
            // Open a file called "flights.txt" for writing
            PrintWriter out = new PrintWriter(new FileWriter("flights.txt"));

            // If there are no flights, write a simple message
            if (flights.isEmpty()) {
                out.println("No flights in the system.");
            } else {
                // Write each flight on a separate line
                for (Flight f : flights.values()) {
                    out.println(f.toString());
                }
            }

            // Close the file
            out.close();

            System.out.println("System state saved to flights.txt");

        } catch (IOException e) {
            // If something goes wrong with file writing
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }
    

}
