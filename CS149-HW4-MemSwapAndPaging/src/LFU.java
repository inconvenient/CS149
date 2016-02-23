import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/** 
 * LFU
 * Least Frequent Used paging Algorithm
 * Each time a reference is made to that block the counter is increased by one. 
 * When the cache reaches capacity and has a new block waiting to be inserted 
 * the system will search for the page with the lowest counter and remove it from the cache.
 * @author CS 149 group 3
 *
 */
public class LFU {
	private Queue<Reference> pages=new LinkedList<Reference>();
	private int hits;
	
	public LFU(){
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
				hits++; //increase hit if the reference already contains the page
				Reference f=find(reference);
				f.incUses(); //increase counter by 1
				printPage();
				System.out.println();
				continue;
			}
			if(pages.size()<4){
				reference.incUses(); //increase counter by 1
				pages.add(reference);
				printPage();
				System.out.println();
			}else if(pages.size()==4){								
				e=evict();
				reference.incUses(); //increase counter by 1
				pages.add( reference);	
				printPage();
				System.out.println("  Evicted "+ e.getRefNum() + " Paged "+ reference.getRefNum());	
			}
			
		}	
		
	}
	
	public Reference evict(){
		Iterator<Reference> i= pages.iterator();
		Reference smallest=i.next();
		
		while(i.hasNext()){
			Reference t=i.next();
			if(t.getUses()<smallest.getUses()){
				smallest=t;
			}
		}
	
		pages.remove(smallest);
		return smallest;
	}
	
	public Reference find(Reference f){
		for(Reference p:pages){
			if(p.equals(f)){
				return p;
			}
		}
		return null;
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