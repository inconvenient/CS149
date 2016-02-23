/** 
 * PageTest
 * driver for paging algorithms (FIFO, LRU, LFU, MFU and Random Pick)
 * Runs each algorithm 5 times, 100 page references each time, and computes an average hit ratio of pages already in memory
 * @author CS 149 group 3
 *
 */
public class PageTester {

	public static void main(String[] args) {
		
		double FIFOhits = 0;
		double LRUhits = 0;
		double LFUhits = 0;
		double MFUhits = 0;
		double RANDhits = 0;

		//run each algorithm 5 times
		for(int i = 0; i < 5; i++ ){
			System.out.println("          [RUN "+ (i+1) + "]");	
			System.out.println("-------------------------------------");	
			System.out.println("Ref:page number(s)");
			
			//FIFO Algorithm
			System.out.println("************FIFO***********");
			FIFO fifo=new FIFO();
			fifo.run();
			FIFOhits= FIFOhits+fifo.getHitRatio();
			
			//LRU Algorithm
			System.out.println("************LRU************");
			LRU lru=new LRU ();
			lru.run();
			LRUhits=LRUhits+lru.getHitRatio();
			
			//LFU Algorithm
			System.out.println("************LFU************");
			LFU lfu=new LFU ();
			lfu.run();
			LFUhits= LFUhits+lfu.getHitRatio();
			
			//MFU Algorithm
			System.out.println("************MFU************");
			MFU mfu=new MFU ();
			mfu.run();
			MFUhits= MFUhits+mfu.getHitRatio();
			
			//Random pick algorithm
			System.out.println("************RANDOM************");
			RAND rand=new RAND ();
			rand.run();
			RANDhits= RANDhits+rand.getHitRatio();
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------------");
		System.out.println("Paging Compeleted");
		System.out.println("Total run: 5");
		
		//prints average hit ratio for each algorithm
		System.out.println("Avg HitRatio for FIFO:"+FIFOhits/5);
		System.out.println("Avg HitRatio for LRU:"+LRUhits/5);
		System.out.println("Avg HitRatio for LFU:"+LFUhits/5);	
		System.out.println("Avg HitRatio for MFU:"+MFUhits/5);		
		System.out.println("Avg HitRatio for RAND:"+RANDhits/5);	
		
	}


	
}
