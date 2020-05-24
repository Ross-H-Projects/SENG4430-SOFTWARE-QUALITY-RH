//	Page.java
//	COMP2240
// 	Assigment3
//	Jamey Blackman
// 	c3183495
// 	Page object
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Page implements Comparable<Page>
{	
	private int id;						//
	private int lastTimeUsed;			//
	private boolean referenceBit;		//for clock policy

	public Page(int pageId){
		id = pageId;
		lastTimeUsed = 0;
		referenceBit = false;
	}

	public int getID(){return id;}
	public int getLastTimeUsed(){return lastTimeUsed;}
	public boolean getReferenceBit(){return referenceBit;}

	public void setBit(boolean isClock){
		referenceBit = isClock;
	}

	public void updateLastTimeUsed(int time){
		lastTimeUsed = time;
	}	

	public void incrementProcTime(){
		lastTimeUsed++;
	}

	@Override
	public int compareTo(Page p2){
		if (this.getLastTimeUsed() < p2.getLastTimeUsed())
			return -1;
		else if (this.getLastTimeUsed() > p2.getLastTimeUsed())
			return 1;
		else
			return 0;
	}



}