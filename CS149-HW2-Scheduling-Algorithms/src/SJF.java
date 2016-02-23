import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
class SJF
{
	public static int[] arrivalArray = new int[100];
	public static ArrayList<Job> jobList = new ArrayList<Job>();
	
	public static void main(String args[])
	{
		// Fill the arrivalArray
		for (int i = 0; i < 99; i++) {
			arrivalArray[i] = i+1;
		}
		float min = 0.1f;
		float max = 10.0f;
		// Add jobs to the jobList
		for (int i = 0; i < 20; i++) {
			Random r = new Random();
			int jobNum = r.nextInt(100);
			if (arrivalArray[jobNum] != 0) {
				jobList.add(new Job("Job " + (i+1), arrivalArray[jobNum], r.nextFloat() * (max - min) + min,i));
				arrivalArray[jobNum] = 0;
			}		
			else{
				i--;
			}
		}
		//sorting process queue by burst time
		class mycomp implements Comparator<Job>{
  	      public int compare(Job j1, Job j2) // comparator for sorting
  	      {
  	    	//  System.out.println((j1.CPUTime - j2.CPUTime));
  	    	  if ((j1.CPUTime - j2.CPUTime)>=0){
  	    		  return 1;
  	    	  }
 	    	  else return -1; 	       
  	      }
		}
	    Collections.sort(jobList, new mycomp()); // sort tasks by burst times
			
		// Algorithm
		float waitTime = 0.0f;

		for (int i = 0; i < jobList.size(); i++) {
			jobList.get(i).setWaitingTime(waitTime);
			jobList.get(i).setTurnAroundTime(jobList.get(i).getCPUTime() + waitTime);
			waitTime = jobList.get(i).getTurnAroundTime();
		}
		
		// Calculate Averages
		float totalWait = 0.0f;
		float totalTurnAround = 0.0f;
		float totalResponse = 0.0f;
		float avgWait = 0, avgTurnAround = 0, avgResponse= 0;

		for (int i = 0; i < 20; i++) {
			totalWait += jobList.get(i).getWaitingTime();
			totalTurnAround += jobList.get(i).getTurnAroundTime();
			totalResponse += jobList.get(i).getCPUTime();
		}

		avgWait = totalWait / 20;
		avgTurnAround = totalTurnAround / 20;
		avgResponse = totalResponse / 20;

		// Print Results
		System.out
				.println("Process || Arrival Time || CPU Time (Response) || Waiting Time || Turn Around Time");
		System.out.println("-----------------------------");
		for (int i = 0; i < jobList.size(); i++) {
			System.out
					.println("Process Name: " + jobList.get(i).getJobName()
							+ " || Arrival Time: "
							+ jobList.get(i).getArrivalTime() + " || CPU Time (Response): "
							+ jobList.get(i).getCPUTime() + " || Wait Time: "
							+ jobList.get(i).getWaitingTime()
							+ " || Turn Around Time: "
							+ jobList.get(i).getTurnAroundTime());
		}

		System.out.println("-----------------------------");
		System.out.println("Total Jobs Completed: " + jobList.size());
		System.out.println("Average Wait Time: " + avgWait);
		System.out.println("Average Turn Around: " + avgTurnAround);
		System.out.println("Average Response Time: "+ avgResponse);
	}
}