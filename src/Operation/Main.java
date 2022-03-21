package Operation;
import UserInfo.*;
import WelcomePage.InitialDisplay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
	//input data
	static Scanner sc=new Scanner(System.in);
	private static Scanner input;
	private static Scanner lockerInput;
	//output data 
	private static PrintWriter output;
	//model to store data.
	private static Users users;
	
	
	public static void main(String[] args) {
		execute();
	}
	public static void execute() {
		initApp();
		InitialDisplay.welcomeScreen();
		signInOptions();

	}
	public static void signInOptions() {
		System.out.println("1 . Press 1 to Register as a new user ");
		System.out.println("2 . Press 2 to Login ");
		Scanner sc=new Scanner(System.in);
		int option = sc.nextInt();
		switch(option) {
			case 1 : 
				registerUser();
				break;
			case 2 :
				loginUser();
				break;
			default :
				System.out.println("Please select 1 Or 2");
				break;
		}
		sc.close();
		input.close();
	}
	
	public static void lockerOptions(String inpUsername) {
		System.out.println("\n\n----------------PLEASE SELECT ANY OF THE OPTIONS---------------\n");
		System.out.println("\n\n1. FETCH ALL FILES IN ASCENDING ORDER FROM THE DIRECTORY");
		System.out.println("2. GET DETAILS AND ADD FILES, DELETE OR SEARCH FILES FROM YOUR DIRECTORY");
		System.out.println("3. EXIT APPLICATION\n\n ");
		int option = sc.nextInt();
		switch(option) {
			case 1 : 
				fetchFileNames(inpUsername);
				break;
			case 2 :
				System.out.println("---------------WELCOME "+inpUsername+" ---------------\n");
				System.out.println("Enter 1 to ADD A FILE TO THE DIRECTORY\n");
				System.out.println("Enter 2 to DELETE SPECIFIC FILE FROM DIRECTORY\n");
				System.out.println("Enter 3 to SEARCH A FILE\n");
				System.out.println("Enter 4 to NAVIGATE BACK\n");
				int ch=sc.nextInt();
				switch(ch) {
				case 1:
					createFile(inpUsername);
					break;
				case 2:
					deleteFile(inpUsername);
					break;
				case 3:
					searchFile(inpUsername);
					break;
				case 4:
					lockerOptions(inpUsername);
					break;
				default :
					System.out.println("Please select 1 , 2 , 3  OR 4");
					break;
				}
			case 3:
				System.out.println("Thank you for using LOCKEDME.COM\n\n Regards,\n DEVESH SINGH");
				System.exit(0);
				break;
			default :
				System.out.println("Please select 1 OR 2");
				break;
		}
		lockerInput.close();
	}
	
	public static void registerUser() {
		System.out.println("==========================================");
		System.out.println("*   WELCOME TO REGISTRATION PAGE	*");
		System.out.println("==========================================");
		
		System.out.println("Enter Username :");
		String username = sc.next();
		sc.nextLine();
		users.setUsername(username);
		System.out.println();
		//taking a copy of the username for checking it's pre-existence
		String cpy=username;
		try { 
		boolean found = false;
		//checking pre-existence of the entered username in the Users.txt file
		while(input.hasNext() && !found) {
			if(input.next().equals(cpy)) {
				System.out.println("User Already Present. Kindly Login!\n");
				found=true;
				break;
			}
		}
		if(!found) {
			System.out.println("Enter Password :");
			String password = sc.next();
			users.setPassword(password);
		output.println(users.getUsername());
		output.println(users.getPassword());
		System.out.println("User Registration Successful !\n\n Please Login\n\n");
		output.close();
		}
		execute();
		}
		catch(Exception e) {
			System.out.println("Registration Unsuccessful. Please Try Again !\n");
			signInOptions();
		}
		
	}
	public static void loginUser() {
		System.out.println("==========================================");
		System.out.println("*   WELCOME TO LOGIN PAGE	 *");
		System.out.println("==========================================");
		int flag=0;
		System.out.println("Enter Username :");
		String inpUsername = sc.next();
		sc.nextLine();
		boolean found = false;
		while(input.hasNext() && !found) {
			if(input.next().equals(inpUsername)) {
				System.out.println("Enter Password :");
				String inpPassword = sc.next();
				sc.nextLine();
				if(input.next().equals(inpPassword)) {
					System.out.println("Login Successful !");
					found = true;
					lockerOptions(inpUsername);
					flag=1;
					break;
				}
				break;
			}
		}
		if(!found) {
			System.out.println("Login Unsuccessful ! Please try again !!\n ");
			execute();
		}
		
	}

	public static void createFile(String username) {
		//Checking whether a user directory exists or not. If not, user 
		//will not be able to add files due to absence of directory and
		//will be requested to create one
		String directoryPath = "C:/DeveshLocker/"+username;
        File userfolder = new File(directoryPath);
        
        if (!userfolder.isDirectory()) {
				System.out.println("Directory of this name Not Found\n");
				System.out.println("Press 1 to Create one and 0 otherwise");
				String input=sc.next();
				if(input.equals("1"))
					createDir(username);
				else if(input.equals("0")) {
					System.out.println("Navigating back to Main Menu due to abscence of directory\n");
					lockerOptions(username);
				}
				else {
					System.out.println("Please enter only 1 or 0 as your choice\n");
					createFile(username);
				}
		}
		
		
		System.out.println("Enter File Name\n");
		String fname=sc.next();

		//creating file and adding content to it
		try {
		      File file = new File("C:/DeveshLocker/"+username+"/"+fname+".txt");
		      if (file.createNewFile()) {
		        System.out.println("File created: " + file.getName());
		      } else {
		        System.out.println("File already exists.");
		        lockerOptions(username);
		      }
		 } 
		catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      lockerOptions(username);
		    }
		System.out.println("Do you want to enter data into above file?\n Enter 1 for YES and 0 for NO\n");
		String ch=sc.next();
		sc.nextLine();
		if(ch.equals("0")||ch.equals("1")) {
			if(ch.equals("1")) {
			System.out.println("Enter data as a String\n");
			String ip=sc.nextLine();
			//sc.nextLine();
			System.out.println();
			try {
			      FileWriter myWriter = new FileWriter("C:/DeveshLocker/"+username+"/"+fname+".txt");
			      myWriter.write(ip);
			      myWriter.close();
			      System.out.println("Data stored in the file\n\n");
			      lockerOptions(username);
			    } 
			catch (Exception e) {
			      System.out.println("An error occurred in writing data to file\n");
			      e.printStackTrace();
			      lockerOptions(username);
			    }
			}
			else {
				System.out.println("File Remains Empty\n");
				lockerOptions(username);
			}
		}
		else {
			System.out.println("Invalid Input. File has remained empty. Redirecting to Main Menu...\n");
			lockerOptions(username);
		}
	}
	public static void fetchFileNames(String username) {
		try {
		 File directoryPath = new File("C:/DeveshLocker/"+username);
	      String contents[] = directoryPath.list();
	      if(contents.length==0) {
	    	  System.out.println("No Files Present in this Directory\n");
	    	  lockerOptions(username);
	      }
	      else {
	    	  System.out.println("List of files and directories in the specified directory:");
	    	  Arrays.sort(contents);
		      for(int i=0; i<contents.length; i++) {
		         System.out.println(contents[i]);
		      }
		      lockerOptions(username);
	      }
		}
		catch(Exception e) {
			System.out.println("Directory Not Found\n\n");
			createDir(username);
		}
		lockerOptions(username);
	}
	
	//function to create directory
	public static void createDir(String username) {
		try {
		Path path = Paths.get("C:/DeveshLocker/"+username);
		Files.createDirectories(path);
		System.out.println("A Directory has been created for you. You can now add, delete or search for files.\n");
		}
		catch(Exception e) {
			e.printStackTrace();
			lockerOptions(username);
		}
	}
	public static void deleteFile(String username) {
			System.out.println("Enter Filename to delete");
			String deletefile=sc.next();
			sc.nextLine();
			File dfile=new File("C:/DeveshLocker/"+username+"/"+deletefile+".txt");
			if(!dfile.exists()) {
				System.out.println("FILE NOT FOUND\n\n");
				lockerOptions(username);
			}
			else {
			try {
			Files.delete(Paths.get("C:/DeveshLocker/"+username+"/"+deletefile+".txt"));
			System.out.println("File "+deletefile+" DELETED successfully\n\n");
			lockerOptions(username);
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Deletion Not Done. Please try again!\n\n");
				lockerOptions(username);
			}
		}
	}
	public static void searchFile(String username) {
		System.out.println("Enter name of file to search\n");
		String filename=sc.next();
		//sc.nextLine();
		File dfile=new File("C:/DeveshLocker/"+username+"/"+filename+".txt");
		if(!dfile.exists()) {
			System.out.println("No such named file exists in your directory\n Please Create One\n\n");
			lockerOptions(username);
		}
		else {
			System.out.println("File Found. Press 1 to view data within file and 0 otherwise\n");
			String choice=sc.next();
			//sc.nextLine();
			if(choice.equals("0")||choice.equals("1")) {
				if(choice.equals("1")){
					try {
					FileReader fr = new FileReader("C:/DeveshLocker/"+username+"/"+filename+".txt");
					int i;
					int c=0;
					String contents="";
				    while ((i = fr.read()) != -1) {
				            // Print all the content of a file
				        	c++;
				            contents+=(char)i;
				        }
				    if(contents.length()==0) {
		        		System.out.println("File is Empty"); 
		        	}
				    else
				    	System.out.println(contents);
				    fr.close();
				    lockerOptions(username);
					}
					catch(Exception e) {
						System.out.println("File Data cannot be read. Please try again !\n");
						lockerOptions(username);
					}
				}
	
				else {
					System.out.println("Returning back to Main Menu\n\n");
					lockerOptions(username);
				}
			}
			else {
				System.out.println("Please enter either 0 or 1");
				searchFile(username);
			}
		}
	}
	public static void initApp() {

		File  dbFile = new File("C:/DeveshLocker/users.txt");
		
		try {
			//read data 
			input = new Scanner(dbFile);
			//output 
			output = new PrintWriter( new FileWriter(dbFile,true));

			users = new Users();
		} catch (IOException e) {
			System.out.println("File Not Found ");
		}
		
	}

}
