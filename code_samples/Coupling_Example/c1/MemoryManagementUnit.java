//	MemoryManagementUnit.java
//	COMP2240
// 	Assigment3
//	Jamey Blackman
// 	c3183495
//	Each process has its own MMU and own set of allocated frames represented by an ArrayList of Pages called allocatedMainMemory
//	Contains lru and clock policy methods
import java.util.ArrayList; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.IOException;


public class MemoryManagementUnit
{

	private ArrayList<Page> virtualMemory;

	private ArrayList<Page> allocatedMainMemory;

	 
	private int allocatedFrames;

	private String policy;

	public MemoryManagementUnit(){
		allocatedFrames = 0;
		allocatedMainMemory = new ArrayList<Page>();
		policy = "";
		virtualMemory = new ArrayList<Page>();
	}

	public MemoryManagementUnit(int frames, String swapPolicy){
		allocatedFrames = frames;
		allocatedMainMemory = new ArrayList<Page>();
		policy = swapPolicy;
		virtualMemory = new ArrayList<Page>();
	}

	public void setFrames(int frames){
		allocatedFrames = frames;
	}	

	public void loadToMain(Page pageRequest){
		if(allocatedMainMemory.size() == allocatedFrames){
			if(policy == "lru"){
				Page lruPage = allocatedMainMemory.get(0);

				for(int i=1; i < allocatedMainMemory.size(); i++){
					if(lruPage.compareTo(allocatedMainMemory.get(i)) == 1){
						lruPage = allocatedMainMemory.get(i);
					}
				}

				for(int i=0; i < allocatedMainMemory.size(); i++){
					if(lruPage.getID() == allocatedMainMemory.get(i).getID()){
						virtualMemory.add(allocatedMainMemory.remove(i));
					}
				}

				allocatedMainMemory.add(pageRequest);
			}
			else if(policy == "clock"){

				ArrayList<Page> aMMCopy = allocatedMainMemory;
				boolean finished = false;

				while(finished == false)
				{
					Page clockPage = aMMCopy.get(0);

					for(int i=1; i < aMMCopy.size(); i++){	
						if(clockPage.compareTo(aMMCopy.get(i)) == 1){
							clockPage = aMMCopy.get(i);
						}
					}

					if(clockPage.getReferenceBit() == true){
						clockPage.setBit(false);

						for(int i=0; i < aMMCopy.size(); i++){
							if(clockPage.getID() == aMMCopy.get(i).getID()){
								aMMCopy.remove(i);
							}
						}
				

					}else{
						for(int i=0; i < allocatedMainMemory.size(); i++){
							if(clockPage.getID() == allocatedMainMemory.get(i).getID()){
								virtualMemory.add(allocatedMainMemory.remove(i));
								System.out.println("hello");
								finished = true;
							}
						}
					}

				}

			}
			else{
				System.out.println("Policy not found");
			}
			//do a page swap according to LRU or CLOCK
		}
		else{
			allocatedMainMemory.add(pageRequest);
			
		}
	}

	public ArrayList<Page> getAllocatedMainMemory(){
		return allocatedMainMemory;
	}

	
}
