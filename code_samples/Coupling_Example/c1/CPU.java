//	CPU.java
//	COMP2240
// 	Assigment3
//	Jamey Blackman
// 	c3183495
//	CPU is the do-er, runs timed simulation, executes process made up of instructions accessed via page requests
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.IOException;
import java.util.Collections;

public class CPU{
	private int GLOBAL_TIME;		//The simulation time

	private int TIME_QUANTUM;		//TIME_QUANTUM, NO_OF_FRAMES, blockingTime do not change throughout program lifetime
	private int NO_OF_FRAMES;
	private int blockingTime = 6;

	private ArrayList<Process> processList;
	private int noOfProcesses;
	private String policyType;

	private ArrayDeque<Process> readyQueue;		//Actual FIFO that 
	private ArrayList<Process> blockedQueue;

	private ArrayList<Process> finishedList;
	private Process activeProcess;


	public CPU(ArrayList<Process> processes, int quantum, int frames, String policy){
		GLOBAL_TIME = 0;
		TIME_QUANTUM = quantum;
		NO_OF_FRAMES = frames;
		policyType = policy;
		processList = processes;

		noOfProcesses = processes.size();

		finishedList = new ArrayList<Process>();

		readyQueue  = new ArrayDeque<Process>();
		blockedQueue = new ArrayList<Process>();		//processes enter when there is a page fault and a required page swap
		//faultedQueue = new ArrayDeque<Process>();

		activeProcess = null;
	}


	public void run(){
		int activityQuantum = 0;		//When activityQuantum is equal to TIME_QUANTUM, switch process, zero activityQuantum again.

		//allocates each process with the same number of frames
		for(int i = 0; i < processList.size(); i++){

			processList.get(i).setProcessFrames(NO_OF_FRAMES/noOfProcesses);
			readyQueue.add(processList.get(i));

		}

		//tries to run processes but faults them
		//assumes that no pages are in the main memory at beginning of run simulation
		while(!readyQueue.isEmpty())
		{
			Process proc = readyQueue.pop();

			proc.addFault(GLOBAL_TIME);
			blockedQueue.add(proc);


		}

		GLOBAL_TIME += blockingTime;


		int counter = 0;

		while(finishedList.size()!= this.noOfProcesses){
			
			//1st Step: Check blockedQueue, remove && up blocking time by 1 for remainder
			////////////////////////////

			while(!blockedQueue.isEmpty())
			{
				if(blockedQueue.get(0).getBlockedQuantum() == 0){
					Page request = blockedQueue.get(0).currentPageRequest();

					blockedQueue.get(0).getMMU().loadToMain(request);
					readyQueue.add(blockedQueue.remove(0)); //removes head always
				}
				else{
					break;
				}
			}

			


			//2nd Step: Check activeProcess
			///////////////////////////
			

			if(activeProcess == null){			//There is no active process
				if(!readyQueue.isEmpty()){
					activeProcess = readyQueue.pop();
					activeProcess.updatePageRequests(GLOBAL_TIME);
					activityQuantum = 1;
					
				}
				else{
					
				}
			}
			else{								//There is an active process

				//Option that active process is Complete
				if(activeProcess.isComplete()){
					activeProcess.updateTurnaroundTime(GLOBAL_TIME);
					
					finishedList.add(activeProcess);
					activeProcess = null;

					if(!readyQueue.isEmpty()){
						activeProcess = readyQueue.pop();
						activeProcess.updatePageRequests(GLOBAL_TIME);
						activityQuantum = 1;
						
					}
					else{
						//Cant do anything
					}
				}
				else{

					if(activityQuantum == TIME_QUANTUM){	//If Active Process must step down due to RR Scheduling

						if(!readyQueue.isEmpty()){
							readyQueue.add(activeProcess);
							activeProcess = readyQueue.remove();
							activeProcess.updatePageRequests(GLOBAL_TIME);
							activityQuantum = 1;

						}
						else{	//Ready queue is Empty, then activeProcess continues past time quantum

							//If it can surpass time quantum, then check if there will be a page fault first before iterating
							//global time


							if(isInMainMemory(activeProcess)){
							activeProcess.updatePageRequests(GLOBAL_TIME);
							activityQuantum++;
								
							}
							else{
								pageFaultEvent(activeProcess, GLOBAL_TIME);
								if(!readyQueue.isEmpty()){
									activeProcess = readyQueue.pop();
									activeProcess.updatePageRequests(GLOBAL_TIME);
									activityQuantum = 0;
									
								}
								else{
									activeProcess = null;
									activityQuantum = 0;
								
								}
							}
						} 
					}
					else if(activityQuantum < TIME_QUANTUM){	//If Active Process can stay active according to RR Scheduling

						if(isInMainMemory(activeProcess)){
							activeProcess.updatePageRequests(GLOBAL_TIME);
							activityQuantum++;
							
						}
						else{
							
							pageFaultEvent(activeProcess, GLOBAL_TIME);

							if(!readyQueue.isEmpty()){
								activeProcess = readyQueue.pop();
								activeProcess.updatePageRequests(GLOBAL_TIME);
								activityQuantum = 1;
								
							}
							else{
								activeProcess = null;
								activityQuantum = 0;
								
							}
						}
					}
				}

			}	//There is an active process - ELSE -

		
			for(int i=0; i < blockedQueue.size(); i++){
				blockedQueue.get(i).decrementBlockedQuantum();
				
				//System.out.println("P"+blockedQueue.get(i).getID());
			}

			
			GLOBAL_TIME++;



		} //while(finishedList.size()!= this.noOfProcesses) - LOOP -


	}

	public boolean isInMainMemory(Process proc){
		for(int i=0; i < proc.getMMU().getAllocatedMainMemory().size(); i++){
			if(proc.getMMU().getAllocatedMainMemory().get(i).getID() == proc.currentPageRequest().getID()){
				proc.getMMU().getAllocatedMainMemory().get(i).setBit(true);
				return true;
			}
		}
		return false;
	}

	public void pageFaultEvent(Process process, int faultTime){
		process.addFault(faultTime);			
		process.setBlockedQuantum(blockingTime);
		blockedQueue.add(process);

	}

	public String print(){
		String printString;

		ArrayList<Process> finishedSorted = new ArrayList<Process>();

			while(!finishedList.isEmpty()){
				Process proc = finishedList.get(0);

				for(int i=0; i < finishedList.size(); i++){
					if(proc.compareTo(finishedList.get(i)) == 1){
						proc = finishedList.get(i);
					}
				}

				for(int i=0; i < finishedList.size(); i++){
					if(proc.getID()==finishedList.get(i).getID()){
						finishedSorted.add(finishedList.remove(i));
					}
				}
			}
		

		


		if(policyType == "lru"){
			printString = "LRU - Fixed:\nPID   Process Name      Turnaround Time     #Faults   Fault Times\n";

	        for(int i = 0; i < finishedSorted.size() ;i++)
	        {
	            printString += i+1 + "     process"+ finishedSorted.get(i).getID()+".txt      "+finishedSorted.get(i).getTurnaroundTime()+"                  "+finishedSorted.get(i).getFaultCounter()+"         "+finishedSorted.get(i).getFaults()+"\n";
	        }
		}
		else{
			printString = "Clock - Fixed:\nPID   Process Name      Turnaround Time     #Faults   Fault Times\n";

	        for(int i = 0; i < finishedSorted.size() ;i++)
	        {
	            printString += i+1 + "     process"+ finishedSorted.get(i).getID()+".txt      "+finishedSorted.get(i).getTurnaroundTime()+"                  "+finishedSorted.get(i).getFaultCounter()+"         "+finishedSorted.get(i).getFaults()+"\n";
	        } 
		}
		return printString;

	}


}