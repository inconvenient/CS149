import java.util.Random;

public class FCFS {

	public static void main(String[] args) {

		Job[] jobList = new Job[20];
		int[] arrivalArray = new int[100];

		float min = 0.1f;
		float max = 10.0f;
		float avgWait = 0, avgTurnAround = 0, avgResponse= 0;

		// Fill the arrivalArray
		for (int i = 0; i < 99; i++) {
			arrivalArray[i] = i + 1;
		}

		// Add jobs to the jobList
		// using arrivalArray to pick order of arrival
		for (int i = 0; i < 20; i++) {
			Random r = new Random();
			int jobNum = r.nextInt(100);
			if (arrivalArray[jobNum] != 0) {
				jobList[i] = new Job("Job " + i, arrivalArray[jobNum],
						r.nextFloat() * (max - min) + min, 1);
				arrivalArray[jobNum] = 0;
			} else {
				i--;
			}
		}

		// Sort by Arrival Time (Bubble Sort)
		// First come, first serve
		for (int j = 0; j < jobList.length; j++) {
			for (int k = 0; k < jobList.length; k++) {
				if (jobList[j].getArrivalTime() < jobList[k].getArrivalTime()) {
					Job buffer = jobList[j];
					jobList[j] = jobList[k];
					jobList[k] = buffer;
				}
			}
		}

		// Algorithm
		float waitTime = 0.0f;

		for (int i = 0; i < jobList.length; i++) {
			jobList[i].setWaitingTime(waitTime);
			jobList[i].setTurnAroundTime(jobList[i].getCPUTime() + waitTime);
			waitTime = jobList[i].getTurnAroundTime();
		}

		// Calculate Averages
		float totalWait = 0.0f;
		float totalTurnAround = 0.0f;
		float totalResponse = 0.0f;

		for (int i = 0; i < 20; i++) {
			totalWait += jobList[i].getWaitingTime();
			totalTurnAround += jobList[i].getTurnAroundTime();
			totalResponse += jobList[i].getCPUTime();
		}

		avgWait = totalWait / 20;
		avgTurnAround = totalTurnAround / 20;
		avgResponse = totalResponse / 20;

		// Print Results
		System.out
				.println("Process || CPU Time (Response) || Waiting Time || Turn Around Time");
		System.out.println("-----------------------------");
		for (int i = 0; i < jobList.length; i++) {
			System.out
					.println("Process Name: " + jobList[i].getJobName()
							+ " || Arrival Time: "
							+ jobList[i].getArrivalTime()
							+ " || CPU Time (Response): "
							+ jobList[i].getCPUTime() + " || Wait Time: "
							+ jobList[i].getWaitingTime()
							+ " || Turn Around Time: "
							+ jobList[i].getTurnAroundTime());
		}

		System.out.println("-----------------------------");

		System.out.println("Total Jobs Completed: " + jobList.length);
		System.out.println("Average Wait Time: " + avgWait);
		System.out.println("Average Turn Around: " + avgTurnAround);
		System.out.println("Average Response Time: "+ avgResponse);

	}
}
