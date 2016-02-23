import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/** 
 * FIFO
 * First In First Out paging algorithm
 * keeps track of all the pages in memory in a queue, with the most recent arrival at the back, and the oldest arrival in front. 
 * When a page needs to be replaced, the page at the front of the queue (the oldest page) is selected
 * @author CS 149 group 3
 *
 */

public class FIFO {	
	private Queue<Reference> pages=new LinkedList<Reference>();
	private int hits;
	
	public FIFO(){
		hits=0;
	}
	
	public void run(){
		Random x=new Random();
		int r=x.nextInt(10);
		
		for(int i=0;i<100;i++){		
			int ref=RandRef.nextRef(r);	
			Reference reference=new Reference(ref);
			System.out.print(reference.getRefNum()+" : ");
			
			if(pages.contains(reference)){
				hits++;
				printPage();
				System.out.println();
				continue;
			}
			if(pages.size()<4){
				pages.add( reference);
				printPage();
				System.out.println();
			}else if(pages.size()==4){
				Reference e=evict();
				pages.add( reference);
				printPage();
				System.out.println("  Evicted "+ e.getRefNum() + " Paged "+ reference.getRefNum());	
			}
		
		}	
			
	}	
	
	public Reference evict(){
		return pages.remove();
	}
	
	public void printPage(){
		for(Reference p:pages){
			System.out.print(p.getRefNum()+" ");	
		}
	}
	
	public double getHitRatio() {
		return (double) hits/100;
	}	
	
}
