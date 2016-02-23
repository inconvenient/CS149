import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/** 
 * MFU
 * Most Frequently Used paging Algorithm
 * It keeps track of the number of reference for each page in the recent past and 
 * page to be replaced is the one which has the highest count. 
 * @author CS 149 group 3
 *
 */
public class MFU {
	private Queue<Reference> pages=new LinkedList<Reference>();
	private int hits;
	
	public MFU(){
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
				f.incUses();
				printPage();
				System.out.println();
				continue;
			}
			if(pages.size()<4){
				reference.incUses();
				pages.add(reference);
				printPage();
				System.out.println();
			}else if(pages.size()==4){				

				e= evict();
				reference.incUses();
				pages.add( reference);	
				printPage();
				System.out.println("  Evicted "+ e.getRefNum() + " Paged "+ reference.getRefNum());	
			}
	
		}	
		
	}
	
	public Reference evict(){
		Iterator<Reference> i= pages.iterator();
		Reference highest=i.next();
		
		//find a page with the highest use 
		while(i.hasNext()){
			Reference t=i.next();
			if(t.getUses()>highest.getUses()){
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
