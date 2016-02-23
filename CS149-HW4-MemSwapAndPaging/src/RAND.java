import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/** 
 * RAND
 * Randomly Picked paging Algorithm
 * Use Randomly created number to pick which page to replace
 * @author CS 149 group 3
 *
 */
public class RAND {
	private Queue<Reference> pages=new LinkedList<Reference>();
	private int hits;
	
	public RAND(){
		hits=0;
	}
	
	public void run(){
		Reference e=null;
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
				e=evict();
				pages.add( reference);
				printPage();
				System.out.println("  Evicted "+ e.getRefNum() + " Paged "+ reference.getRefNum());	
			}
				
		}	
		
	}
	
	//randomly picked number is used to decide which one to remove
	public Reference evict(){
		Random rr = new Random();	
		int num =rr.nextInt(4)+1;
		Reference x=null;
		Iterator<Reference> i= pages.iterator();
		
		for(int h=0;h<num;h++){
			x=i.next();
		}
		pages.remove(x);
		return x;
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
