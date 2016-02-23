

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class HPFNP
{

   /*
    * Similar to first come first serve. Except now you have to serve each job
    * for one second: If the job finishes, move it to the finished
    * arraylist/queue If it doesn't finish, subtract 1 second from the
    * remaining time and move onto the next job. Repeat.
    */

   /*
    * Should probably use ArrayList to store jobList because you can easily
    * remove jobs
    */

   public static void main(String[] args)
   {

      ArrayList<Job> jobList = new ArrayList<>();
      ArrayList<Job> finishedJobs = new ArrayList<>();
      int[] arrivalArray = new int[100];
      ArrayList<ArrayList<Job>> priorityList = new ArrayList<ArrayList<Job>>(4);

      final int totalJobs = 20; // Feel free to change this
      final float min = 0.1f;
      final float max = 10.0f;
      float avgWait = 0, avgTurnAround = 0, avgResponse= 0;

      // Fill the arrivalArray
      for (int i = 0; i < 99; i++)
      {
         arrivalArray[i] = i + 1;
      }

      // Add jobs to the jobList
      // using arrivalArray to pick order of arrival
      // NO REPEATS!
      for (int i = 0; i < totalJobs; i++)
      {
         Random r = new Random();
         Random p = new Random();
         int jobNum = r.nextInt(100);
         int priorityNum = p.nextInt(4) + 1;
         if (arrivalArray[jobNum] != 0)
         {
            jobList.add(new Job("Job " + i, arrivalArray[jobNum], r
            .nextFloat() * (max - min) + min, priorityNum));
            arrivalArray[jobNum] = 0;
            jobList.get(i).TurnAroundTime = jobList.get(i).CPUTime;
         } else
         {
            i--;
         }
      }

      // Sort by Priority and Arrival Time
      class sortByArrival implements Comparator<Job>
      {
         @Override
         public int compare(Job j1, Job j2)
         {
            if ((j1.ArrivalTime - j2.ArrivalTime) >= 0)
            {
               return 1;
            } else
               return -1;
         }
      }
      Collections.sort(jobList, new sortByArrival());

      // Initialize priority lists.
      ArrayList<Job> jobList1 = new ArrayList<>();
      ArrayList<Job> jobList2 = new ArrayList<>();
      ArrayList<Job> jobList3 = new ArrayList<>();
      ArrayList<Job> jobList4 = new ArrayList<>();
      priorityList.add(0, jobList1);
      priorityList.add(1, jobList2);
      priorityList.add(2, jobList3);
      priorityList.add(3, jobList4);

      /* Sorts by priority */
      for (int i = 0; i < jobList.size(); i++)
      {
         Job temp = jobList.get(i);
         if (temp.getPriority() == 1)
         {
            jobList1.add(temp);
         }
         else if (temp.getPriority() == 2)
         {
            jobList2.add(temp);
         }
         else if (temp.getPriority() == 3)
         {
            jobList3.add(temp);
         }
         else if (temp.getPriority() == 4)
         {
            jobList4.add(temp);
         }
      }

      // For each Job in Priority
      float waitTime = 0.0f;

      for (int i = 0; i < 4; i++)
      {
         ArrayList<Job> temp2 = priorityList.get(i);
         for (Job j : temp2)
         {
            j.setWaitingTime(waitTime);
            j.setTurnAroundTime(j.getCPUTime() + waitTime);
            waitTime = j.getTurnAroundTime();
            finishedJobs.add(j);
         }
      }

      // Calculate Averages
      float totalWait = 0.0f;
      float totalTurnAround = 0.0f;
	  float totalResponse = 0.0f;

      for (Job j : finishedJobs)
      {
         totalWait += j.getWaitingTime();
         totalTurnAround += j.getTurnAroundTime();
         totalResponse += j.getCPUTime();
      }

      avgWait = totalWait / totalJobs;
      avgTurnAround = totalTurnAround / totalJobs;
		avgResponse = totalResponse / totalJobs;

      // Print Results
      System.out
      .println("THIS IS LISTED IN THE ORDER THE PROCESSES FINISHED");
      System.out
      .println("Process || Priority || CPU Time (Response) || Waiting Time || Turn Around Time");
      System.out.println("-----------------------------");

      for (Job j : finishedJobs)
      {
         System.out.println("Process Name: " + j.getJobName()
         + " || Arrival Time: " + j.getArrivalTime()
         + " || Priority:" + j.getPriority()
         + " || CPU Time Remaining (Response): " + j.getCPUTime()
         + " || Wait Time: " + j.getWaitingTime()
         + " || Turn Around Time: " + j.getTurnAroundTime());
      }

      System.out.println("-----------------------------");

      System.out.println("Total Jobs Completed: " + finishedJobs.size());
      System.out.println("Average Wait Time: " + avgWait);
      System.out.println("Average Turn Around: " + avgTurnAround);
		System.out.println("Average Response Time: "+ avgResponse);
   }
}
