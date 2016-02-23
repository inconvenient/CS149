import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/** 
 * LRU
 * Least Recently Used paging Algorithm
 * It keeps track of what page is used when. Every time a cache-line is used, the age of all other cache-lines changes
 * @author CS 149 group 3
 *
 */
public class LRU {

	private Queue<Reference> pages=new LinkedList<Reference>();
	private int hits;
	
	public LRU(){
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
				Reference f=find(reference);
				f.resetAge(); //reset newly added page's age
				printPage();
				System.out.println();
				continue;
			}
			if(pages.size()<4){
				pages.add(reference);
				printPage();
				System.out.println();
			}else if(pages.size()==4){				
				e=evict();
				pages.add( reference);	
				printPage();
				System.out.println("  Evicted "+ e.getRefNum() + " Paged "+ reference.getRefNum());	
			}
				
			for(Reference a:pages){
				a.incAge(); //increase age after use
			}
		}	
		
	}	
	
	public Reference evict(){
		Iterator<Reference> i= pages.iterator();
		Reference highest=i.next();
		
		while(i.hasNext()){
			Reference t=i.next();
			if(t.getAge()>highest.getAge()){
				highest=t;
			}
		}
	
		pages.remove(highest);
		return highest;
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
