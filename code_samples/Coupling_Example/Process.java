//	Process.java
//	COMP2240
// 	Assigment3
//	Jamey Blackman
// 	c3183495
//	Contains lists of pages which denote the static sequence of page requests for process, the requests that have been made and those that are still pending
//	also contiains a Main Memory unit that is interfaced with when page are requested form Main Memory. Also holds page fault information
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Process implements Comparable<Process>{
	private int pID;
	
	private ArrayList<Page> pages;					//static representation of page sequence for full rocess
	private ArrayDeque<Page> pageRequestQueue;		//queue of pages as main memory requests, depletes over process lifetime
	private ArrayDeque<Page> completedPages;		//queue of pages or page requests that have been done for process, fulfills over process lifetime 


	private int turnaroundTime;
	private ArrayList<Integer> faultTimes;
	private int faultCounter;
	private int blockedQuantum;

	private String processName;
	private int frames;
	private MemoryManagementUnit mmu;
	private Page currentPageRequest;

	private String policy;	


	public Process(){
		pID = -1;

		pages = new ArrayList<Page>();;
		
		turnaroundTime = 0;
		processName = "";
		frames = 0;

		pageRequestQueue = new ArrayDeque<Page>();
		completedPages = new ArrayDeque<Page>();
		faultTimes = new ArrayList<Integer>();

		blockedQuantum = 0;
		
		policy = "";

		mmu = new MemoryManagementUnit();
	}

	public Process(int id, ArrayList<Page> pageList, String swapPolicy){
		pID = id;

		pages = pageList;
		
		turnaroundTime = 0;
		processName = "";
		frames = 0;

		pageRequestQueue = new ArrayDeque<Page>();
		
		completedPages = new ArrayDeque<Page>();
		faultTimes = new ArrayList<Integer>();
		

		mmu = new MemoryManagementUnit(frames, swapPolicy);

		blockedQuantum = 0;

		policy = swapPolicy;

	}

	public void listToQueue(ArrayList<Page> list){
		for(int i=0; i < list.size(); i++){
			pageRequestQueue.add(list.get(i));
		}

	}

	public MemoryManagementUnit getMMU(){return mmu;}

	public void updatePageRequests(int accessedTime){
		Page accessedPage = pageRequestQueue.pop();
		accessedPage.updateLastTimeUsed(accessedTime);
		completedPages.push(accessedPage);
	}

	public Page currentPageRequest(){
		return pageRequestQueue.peek();
	}

	public int getID(){return pID;}
	public void setID(int id){pID = id;}

	public void updateTurnaroundTime(int time){turnaroundTime += time;}
	public int getTurnaroundTime(){return turnaroundTime;}

	public int getFaultCounter(){return faultCounter;}
	
	public void setProcessFrames(int pFrames){
		frames = pFrames;
		mmu.setFrames(pFrames);
	}	

	public ArrayList<Page> getPageList(){return pages;}
	public void setPages(ArrayList<Page> pageList){pages = pageList;}
	

	public void addFault(int time){
		faultTimes.add(time);
		faultCounter++;
	}

	public boolean isComplete(){
		if(completedPages.size() == pages.size()){
			return true;
		}
		else
			return false;
	}
	public int getBlockedQuantum(){return blockedQuantum;}
	public void setBlockedQuantum(int bQuantum){blockedQuantum = bQuantum;}
	public void decrementBlockedQuantum(){blockedQuantum--;}



	@Override
	public int compareTo(Process process2){
		if (this.getID() < process2.getID())
			return -1;
		else if (this.getID() > process2.getID())
			return 1;
		else
			return 0;
	}

	public String getFaults(){
		String faultString = "{";

		for(int i=0; i < faultTimes.size()-1; i++){
			faultString += faultTimes.get(i) + ", ";
		}
		faultString += faultTimes.get(faultTimes.size()-1) + "}";
		return faultString;
	}


	public void printPageList(){
		for(int i = 0; i < pages.size(); i++)
		{
			System.out.println("Page List Slot: "+i+" Page ID: "+pages.get(i).getID());
		}
	}

}