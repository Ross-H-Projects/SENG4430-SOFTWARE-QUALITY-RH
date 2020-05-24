//  A3.java
//  COMP2240
//  Assigment3
//  Jamey Blackman
//  c3183495
//  Parses txt files and creates CPU simulation structures, and facilitates print methods
import java.util.ArrayList; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class A3
{
    public static void main(String[] args) throws IOException
    {
    	ArrayList<Process> lruProcessList = new ArrayList<Process>();
    	ArrayList<Process> clockProcessList = new ArrayList<Process>();

    	int FRAMES = Integer.parseInt(args[0]);
    	int QUANTUM = Integer.parseInt(args[1]);


    	int processCounter = 1;
    	for(int i=2; i<args.length; i++)
    	{
    		String inputProcess = args[i];
    		lruProcessList.add(processConsumer(processCounter, inputProcess, false));
    		clockProcessList.add(processConsumer(processCounter, inputProcess, true));

    		processCounter++;
    	}

    	//two seperate CPU's for two seperate page replacement policies
    	CPU LRUPolicyCPU = new CPU(lruProcessList, QUANTUM, FRAMES, "lru");
    	CPU clockPolicyCPU = new CPU(clockProcessList, QUANTUM, FRAMES, "clock");

        LRUPolicyCPU.run();
        clockPolicyCPU.run();

    	//print out results
    	System.out.println(LRUPolicyCPU.print());
    	System.out.println(clockPolicyCPU.print());

    }

    public static Process processConsumer(int id, String inputProcess, boolean isClock)
    {
    	ArrayList<Page> pagesList = new ArrayList<Page>();
    	File newProcess = new File(inputProcess);
    	Scanner inputScanner;
        String policy;

    	try {
			inputScanner = new Scanner(newProcess);


		int j = 0;

		while(inputScanner.hasNextLine())
		{
			String inputLine = inputScanner.nextLine();

			if(inputLine.charAt(0) == 'b'){
				//beginning of process text file
			}
			else if(inputLine.charAt(0) == 'e'){
				//end of process text file
			}
			else
			{	
				if(isClock == true){
					Page newPage = new Page(Integer.parseInt(inputLine));
					pagesList.add(newPage);
					j++;
				}
				else{
					Page newPage = new Page(Integer.parseInt(inputLine));
					pagesList.add(newPage);
					j++;
				}
			}
		}

		} catch (Exception e){
			System.out.println("FILE NOT FOUND");
            System.out.println(e.toString());
		}

        if(isClock == false){       //these policy values will be fed through CPU->Process->MemoryManagementUnit->Page
		      policy = "lru";     
        }
        else
        {
            policy = "clock";
        }
        Process process = new Process(id, pagesList, policy);
        process.listToQueue(pagesList);
		return process;



    }

}