import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class RR {

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

	public static void main(String[] args) {

		ArrayList<Job> jobList = new ArrayList<>();
		ArrayList<Job> finishedJobs = new ArrayList<>();
		int[] arrivalArray = new int[100];

		final int totalJobs = 20; // Feel free to change this
		final float min = 0.1f;
		final float max = 10.0f;
		float avgWait = 0, avgTurnAround = 0,  avgResponse= 0;

		// Fill the arrivalArray
		for (int i = 0; i < 99; i++) {
			arrivalArray[i] = i + 1;
		}

		// Add jobs to the jobList
		// using arrivalArray to pick order of arrival
		// NO REPEATS!
		for (int i = 0; i < totalJobs; i++) {
			Random r = new Random();
			int jobNum = r.nextInt(100);
			if (arrivalArray[jobNum] != 0) {
				jobList.add(new Job("Job " + i, arrivalArray[jobNum], r
						.nextFloat() * (max - min) + min, 1));
				arrivalArray[jobNum] = 0;
				jobList.get(i).TurnAroundTime = jobList.get(i).CPUTime;
			} else {
				i--;
			}
		}

		// Sort by Arrival Time
		class sortByArrival implements Comparator<Job> {
			public int compare(Job j1, Job j2) {
				if ((j1.ArrivalTime - j2.ArrivalTime) >= 0) {
					return 1;
				} else
					return -1;
			}
		}
		Collections.sort(jobList, new sortByArrival());
		
		/*
		 * Printing out the jobList before we run it.
		 */
		System.out.println("JOBLIST BEFORE CPU RUN");
		System.out.println("-----------------------------");

		float totalCPUTime = 0.0f;
		for (Job j : jobList) {
			System.out.println("Process Name: " + j.getJobName()
					+ " || Arrival Time: " + j.getArrivalTime()
					+ " || CPU Time (Response): " + j.getCPUTime());
			totalCPUTime += j.CPUTime;
		}
		avgResponse = totalCPUTime / 20;

		System.out.println("-----------------------------");

		// Algorithm
		float waitTime = 0.0f;
		while (!jobList.isEmpty()) {
			Iterator<Job> jIterator = jobList.iterator();
			while (jIterator.hasNext()) {
				Job j = jIterator.next();
				// If time remaining is <= 0, remove the job from the list
				// and add it to the finished list
				if (j.CPUTime <= 0) {
					finishedJobs.add(j);
					j.TurnAroundTime += j.WaitingTime;
					jIterator.remove();
				}
				j.WaitingTime = waitTime;

				// If time remaining is less than 1, subtract the remaining and
				// move on to the next process. Do not waste time hanging for
				// the remainder
				// of the second
				if (j.CPUTime <= 1) {
					waitTime += j.CPUTime;
					j.CPUTime -= j.CPUTime;
					finishedJobs.add(j);
					j.TurnAroundTime += j.WaitingTime;
					jIterator.remove();

					// Else just subtract 1 second from the CPU process time
					// and move on to the next process.
				} else {
					j.CPUTime -= 1;
					waitTime++;
				}
			}
		}

		// Calculate Averages
		float totalWait = 0.0f;
		float totalTurnAround = 0.0f;
		float totalResponse = 0.0f;

		for (Job j : finishedJobs) {
			totalWait += j.getWaitingTime();
			totalTurnAround += j.getTurnAroundTime();
			totalResponse += j.getCPUTime();
		}

		avgWait = totalWait / totalJobs;
		avgTurnAround = totalTurnAround / totalJobs;

		// Print Results
		System.out
				.println("THIS IS LISTED IN THE ORDER THE PROCESSES FINISHED");
		System.out
				.println("Process || CPU Time (Response) || Waiting Time || Turn Around Time");
		System.out.println("-----------------------------");

		for (Job j : finishedJobs) {
			System.out.println("Process Name: " + j.getJobName()
					+ " || Arrival Time: " + j.getArrivalTime()
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