/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.lang.Thread;

/**
 *
 * @author Tejas
 */
public class Sockets_Server{
    

   public static void main (String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(7315);
      while (true) {
        Socket clientSocket = serverSocket.accept();
        EchoHandler handler = new EchoHandler(clientSocket);
        handler.start();
      }
    }
    catch (Exception e) {
      System.err.println("Exception caught:" + e);
    }
  }
}

class EchoHandler extends Thread {
  Socket client;
  EchoHandler (Socket client) {
    this.client = client;
  }

  public void run () {
    try {
      //creates the input and output streams
	PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
	   
	   
        //the menu number sent over from the client
        int userChoice;
	   
        //process and runtime 
        Process someProcess = null;
        Runtime someRuntime = null;
        BufferedReader stdInp = null;
        String cmd = "";
        String output = "";
        String line;
        
        do
	   {	
			//reads what the client sent and parses it
			 userChoice = Integer.parseInt(in.readLine());
	   
			//calls different methods depending on what the user chose
			switch(userChoice)
			{
			
				//Host current date and time
				case 1:
				
					System.out.println("Forking thread: date and time\n");
					
					//creates a process for date and time
					someRuntime = Runtime.getRuntime();
					cmd = "date";
				
					//runs the process, gets the ouptut,and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					output = stdInp.readLine();
					out.println(output);
					
					break;
					
				//Host uptime	
				case 2:
				
					System.out.println("Forking thread: uptime\n");
					
					//creates a process for uptime
					someRuntime = Runtime.getRuntime();
					cmd = "uptime";
				
					//runs the process, gets the ouptut,and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					output = stdInp.readLine();
					out.println(output);
					
					break;
					
				//Host memory use	
				case 3:
					System.out.println("Forking thread: memory use\n");
					
					//creates a process for memory use
					someRuntime = Runtime.getRuntime();
					cmd = "free";
				
					//runs the process, gets the ouptut,and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					
					//stores the output into a string
					while ((line = stdInp.readLine()) != null) 
						output = output + line;
					
					//sends the output to the client
					out.println(output);
					
					break;
		
				//Host Netstat	
				case 4:
				
					System.out.println("Forking thread: netstat\n");
					
					//creates a process for netstat
					someRuntime = Runtime.getRuntime();
					cmd = "netstat";
				
					//runs the process, gets the ouptut,and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					
					//stores the output into a string
					while ((line = stdInp.readLine()) != null) 
						output = output + line;
					
					//sends the output to the client
					out.println(output);
					
					break;
					
				//Host current users	
				case 5:
					System.out.println("Forking thread: server current users\n");
					
					//creates a process for current users
					someRuntime = Runtime.getRuntime();
					cmd = "w";
				
					//runs the process, gets the ouptut,and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					
					//stores the output into a string
					while ((line = stdInp.readLine()) != null) 
						output = output + line;
					
					//sends the output to the client
					out.println(output);
					
					break;
					
				//Host running processes	
				case 6:
				
					System.out.println("Forking thread: server running processes\n");
					
					//creates a process for running processes
					someRuntime = Runtime.getRuntime();
					cmd = "ps -aux";
				
					//runs the process, gets the ouptut,and prints it on the client side
					someProcess = someRuntime.exec(cmd);
					stdInp = new BufferedReader(new InputStreamReader(someProcess.getInputStream()));
					
					//stores the output into a string
					while ((line = stdInp.readLine()) != null) 
						output = output + line;
					
					//sends the output to the client
					out.println(output);	
					
					break;
				}//switch
				
		}while(userChoice != 7);
        
        //prints to the server side that the connection is closed
	   System.out.println("Closing connection to client\n");
	   
	   //closes the streams and the sockets
        out.close();
        in.close();
        client.close();
    }
    catch (Exception e) {
      System.err.println("Exception caught: client disconnected.");
    }
  }
}

