package airport;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AirportSystem Asystem = new AirportSystem();
        Scanner input = new Scanner(System.in);
        
        //add 10 flights to the system

        Asystem.addFlight("ME201", "MEA", "Beirut", "Dubai", 4);
        Asystem.addFlight("QR102", "Qatar Airways", "Doha", "Paris", 3);
        Asystem.addFlight("TK707", "Turkish Airlines", "Istanbul", "Berlin", 5);
        Asystem.addFlight("EK450", "Emirates", "Dubai", "Sydney", 2);
        Asystem.addFlight("LH330", "Lufthansa", "Frankfurt", "New York", 4);
        Asystem.addFlight("AF250", "Air France", "Paris", "Tokyo", 3);
        Asystem.addFlight("BA911", "British Airways", "London", "Toronto", 5);
        Asystem.addFlight("AA100", "American Airlines", "Chicago", "Miami", 2);
        Asystem.addFlight("RJ404", "Royal Jordanian", "Amman", "Madrid", 4);
        Asystem.addFlight("SV888", "Saudia Airlines", "Riyadh", "Kuala Lumpur", 3);

        int choice;

        do {
            System.out.println("\n===============================");
            System.out.println(" AIRPORT LANDING & RUNWAY SYSTEM");
            System.out.println("===============================");
            System.out.println("1. Add a new flight to the system");
            System.out.println("2. Display all flights");
            System.out.println("3. Search for a flight");
            System.out.println("4. Request landing for a flight");
            System.out.println("5. Display landing queue");
            System.out.println("6. Promote a flight to emergency");
            System.out.println("7. Cancel a landing request");
            System.out.println("8. Assign next flight to runway");
            System.out.println("9. Display runway queue");
            System.out.println("10. Process runway (land a flight)");
            System.out.println("11. Save system state");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
            
            case 1:
                System.out.print("Please enter flight number: ");
                String numb = input.nextLine();

                System.out.print("Please enter airline name: ");
                String airline = input.nextLine();

                System.out.print("Please enter origin: ");
                String origin = input.nextLine();

                System.out.print(" Please enter destination: ");
                String destination = input.nextLine();

                System.out.print("Please enter initial priority (1–5): ");
                int priority = Integer.parseInt(input.nextLine());

                Asystem.addFlight(numb, airline, origin, destination, priority);
                System.out.println("Flight added successfully.");
                break;


                case 2:
                    Asystem.displayAllFlights();
                    break;

                case 3:
                    System.out.print("Enter flight number: ");
                    String searchNo = input.nextLine();
                    Asystem.displayFlightDetails(searchNo);
                    break;

                case 4:
                    System.out.print("Enter flight number: ");
                    String flightNo = input.nextLine();
                    Flight flight = Asystem.searchFlight(flightNo);

                    if (flight == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    System.out.println("\nChoose landing type:");
                    System.out.println("1. Emergency");
                    System.out.println("2. Low Fuel");
                    System.out.println("3. VIP");
                    System.out.println("4. Normal");
                    System.out.println("5. Cargo");
                    System.out.print("Enter priority (1–5): ");

                    int p = Integer.parseInt(input.nextLine());
                    flight.setPriorityLevel(p);
                    Asystem.requestLanding(flightNo);
                    break;

                case 5:
                    Asystem.displayLandingQueue();
                    break;

                case 6:
                    System.out.print("Enter flight number to promote: ");
                    String promoNo = input.nextLine();
                    Asystem.promoteToEmergency(promoNo);
                    break;

                case 7:
                    System.out.print("Enter flight number to cancel request: ");
                    String cancelNo = input.nextLine();
                    Asystem.cancelLandingRequest(cancelNo);
                    break;

                case 8:
                    Asystem.assignNextFlightToRunway();
                    break;

                case 9:
                    Asystem.displayRunwayQueue();
                    break;

                case 10:
                    Asystem.processRunway();
                    break;

                case 11:
                    Asystem.saveSystemState();
                    break;

                case 0:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice.Please try again!");
            }

        } while (choice != 0);

        input.close();
    }
}
