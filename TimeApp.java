// --== CS400 File Header Information ==--
// Name: Samuel Erickson
// Email: swerickson2@wisc.edu
// Team: LF
// Role: Front End Developer
// TA: Divyanshu
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class allows the user to add times to a schedule
 */
public class TimeApp {
	private TimeScheduler schedule = new TimeScheduler();
	private Scanner scan = new Scanner(System.in);
	String temp = "";
	
	/**
	 * Stops the console and waits until the user hits the enter key to continue
	 */
	private void pressAnyKeyToContinue()
	 { 
	        System.out.println("Press Enter key to continue...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //Clears the screen
	        if(temp.equalsIgnoreCase("A")) scan.nextLine();
	        scan.nextLine();
	 }
	
	/**
	 * This method gets the input for which command is to be used in the app.
	 * @return The command to be used
	 */
	public String getInput() {
	    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
	    	+ "|         Add an event. \"A\"          |\n"
	        + "|         Check an event \"C\"         |\n" 
	    	+ "|         Change an event name \"CEN\" |\n"
	        + "|         See all events \"P\"         |\n"
	    	+ "|         Quit the program \"Q\"       |\n"
	        + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	    System.out.println("Please enter a command: ");
	    this.temp = this.scan.nextLine();
	    while (!this.temp.equalsIgnoreCase("A") && !this.temp.equalsIgnoreCase("C") //All valid commands
	        && !this.temp.equalsIgnoreCase("P") && !this.temp.equalsIgnoreCase("CET")
	        && !this.temp.equalsIgnoreCase("CEN") && !this.temp.equalsIgnoreCase("Q")) {
	      System.out.println("Invalid command.\nPlease enter a command: "); //If invalid command is entered
	      this.temp = scan.nextLine();
	    }
	    return temp.toUpperCase(); //Returns the valid command
	  }
	
	/**
	 * This method enacts the commands based on the param. 
	 * 
	 * @param temp valid command
	 */
	public void inputReader(String temp) { 
		String name; //Holder variables
		int start,end;
		boolean insert;
		Task hold;
		
		switch(temp) {
		
		case "A" : //Adding an event
			System.out.println("Please enter the event name: "); //Getting event details
			name = scan.next();
			
			System.out.println("Please enter the event start time in 0000 format \n(Example: 0700 is 7:00 AM and 1534 is 3:34 PM.");
			while(!scan.hasNextInt()) scan.next(); //Makes sure a valid int is entered
			start = scan.nextInt();
			
			System.out.println("Please enter the event end time in 0000 format \n(Example: 0930 is 9:30 AM and 1856 is 6:56 PM.");
			while(!scan.hasNextInt()) scan.next();
			end = scan.nextInt();
			
			try { //Checks if it's a valid task
				insert = schedule.insertTask(name, start, end);
			}
			catch (IllegalArgumentException e1) { //If it's not a valid task based on entered values
				System.out.println(e1.getMessage());
				this.pressAnyKeyToContinue();
				break;
			}
			if(insert) { //If inserted successfully
				System.out.println("Congrats! The event has been added!");
				this.pressAnyKeyToContinue();
			}
			else { //If there is already a task during that duration
				System.out.println("Sorry, that event conflicts with another.");
				this.pressAnyKeyToContinue();
			}
			break;
			
		case "C" : //Checking the details of an event
			System.out.println("Enter the start time of the event you want to check in 0000 format\n(Example: 0700 is 7:00 AM and 1534 is 3:34 PM.");
			while(!scan.hasNextInt()) scan.next(); //Makes sure a valid int is entered
			start=scan.nextInt();
			
			try { //Checks if that time is in the scheduler
				hold = schedule.get(start);
			}
			catch(NoSuchElementException e1) { //Case where that start time is not in the scheduler
				System.out.println(e1.getMessage());
				break;
			}
			System.out.println("Here is your event information!"); //Successful event retrieval
			System.out.println(hold.toString());
			this.pressAnyKeyToContinue();
			break;
			
		case "CEN" : //Changing an event name
			System.out.println("Enter the start time of the event you want to check in 0000 format\n(Example: 0700 is 7:00 AM and 1534 is 3:34 PM.");
			while(!scan.hasNextInt()) scan.next();
			start=scan.nextInt();
			scan.nextLine();
			
			try { //Checks if the start time is in the scheduler
				hold = schedule.get(start);
			}
			catch(NoSuchElementException e1) { //Case where it is not in the scheduler
				System.out.println(e1.getMessage());
				break;
			}
			
			System.out.println("Enter new name of the event: ");
			name=scan.nextLine();
			hold.changeName(name); //Changes name of the event
			System.out.println("Congrats! New name of the event is: "+name);
			System.out.println(hold);
			this.pressAnyKeyToContinue();
			break;
		case "P": //Prints out the current schedule
			schedule.generateTheSchedule();
			this.pressAnyKeyToContinue();
			break;
		}
	}
	
	

	/**
	 * This main method runs the application
	 */
	public static void main(String[] args) {
		TimeApp user = new TimeApp();
		String temp = user.getInput();
        while (!temp.equals("Q")) { //As long as the user does not want to quit
            user.inputReader(temp);
            temp = user.getInput();
        }
	}

}
