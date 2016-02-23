import java.util.LinkedList;
import java.util.Random;

public class RunAlgorithms {

	/*
	 * Everything works except for the process weighting. I have no idea how to
	 * do the process weighting and making sure the distribution is evenly
	 * spread... Help!
	 */

	public static final int ALGORITHM_RUNTIME = 60; // Seconds
	public static final int MAX_PROCESSES = 150;
	public static final int TOTAL_RUNS = 5; // 1 for testing (5 is for turn-in)

	static int completedProcesses = 0;
	int totalRuns = 0;

	public static void main(String[] args) {

		// ----------------- FIRST FIT ----------------- //

		LinkedList<Process> processListFF = createProcesses(MAX_PROCESSES);
		System.out
				.format("Best Fit Algorithm: Creating Processes! --  %d RUNS | %d PROCESSES\n",
						TOTAL_RUNS, MAX_PROCESSES);
		System.out.println("-------------------------------------------------");
		System.out
				.println("Process ID   |   Arrival Time   |   RunTime   |   Memory Size");
		for (Process p : processListFF) {
			System.out.println(p.processID + " | " + p.arrivalTime + " | "
					+ p.runDuration + " | " + p.memorySize);
		}
		System.out.println("-----------------------------");

		for (int i = 0; i < TOTAL_RUNS; i++) {
			System.out.format("Begin Process Run and Swapping! Run %d\n", i);
			System.out.println("-----------------------------");

			FirstFitMemory FFTest = new FirstFitMemory();
			completedProcesses = FFTest.runFirstFit(processListFF);
			System.out.println("-----------------------------");
			System.out.println("RUN 1 COMPLETE");
			System.out.format("Total Processes Completed: %d\n",
					completedProcesses);
			System.out.println("-----------------------------");
		}

		// ----------------- BEST FIT ----------------- //

		LinkedList<Process> processListBF = createProcesses(MAX_PROCESSES);
		System.out
				.format("First Fit Algorithm: Creating Processes! --  %d RUNS | %d PROCESSES\n",
						TOTAL_RUNS, MAX_PROCESSES);
		System.out.println("-------------------------------------------------");
		System.out
				.println("Process ID   |   Arrival Time   |   RunTime   |   Memory Size");
		for (Process p : processListBF) {
			System.out.println(p.processID + " | " + p.arrivalTime + " | "
					+ p.runDuration + " | " + p.memorySize);
		}
		System.out.println("-----------------------------");

		for (int i = 0; i < TOTAL_RUNS; i++) {
			System.out.format("Begin Process Run and Swapping! Run %d\n", i);
			System.out.println("-----------------------------");

			FirstFitMemory FFTest = new FirstFitMemory();
			completedProcesses = FFTest.runFirstFit(processListBF);
			System.out.println("-----------------------------");
			System.out.println("RUN 1 COMPLETE");
			System.out.format("Total Processes Completed: %d\n",
					completedProcesses);
			System.out.println("-----------------------------");
		}

		// ----------------- NEXT FIT ----------------- //

		LinkedList<Process> processListNF = createProcesses(MAX_PROCESSES);
		System.out
				.format("First Fit Algorithm: Creating Processes! --  %d RUNS | %d PROCESSES\n",
						TOTAL_RUNS, MAX_PROCESSES);
		System.out.println("-------------------------------------------------");
		System.out
				.println("Process ID   |   Arrival Time   |   RunTime   |   Memory Size");
		for (Process p : processListNF) {
			System.out.println(p.processID + " | " + p.arrivalTime + " | "
					+ p.runDuration + " | " + p.memorySize);
		}
		System.out.println("-----------------------------");

		for (int i = 0; i < TOTAL_RUNS; i++) {
			System.out.format("Begin Process Run and Swapping! Run %d\n", i);
			System.out.println("-----------------------------");

			NextFitMemory NFTest = new NextFitMemory();
			completedProcesses = NFTest.runFirstFit(processListNF);
			System.out.println("-----------------------------");
			System.out.println("RUN 1 COMPLETE");
			System.out.format("Total Processes Completed: %d\n",
					completedProcesses);
			System.out.println("-----------------------------");
		}
	}

	// --------- HELPER METHODS! --------- //
	/*
	 * @param n :: number of processes you want to run (DEFAULT IS 150) Create
	 * Processes To Run
	 */
	public static LinkedList<Process> createProcesses(int n) {
		LinkedList<Process> processQueue = new LinkedList<Process>();
		Random r = new Random();
		int arrival = 0;
		int tempProcessNum = 0;
		for (int i = 0; i < n; i++) {
			// ------Creation Variables!------ //
			char id = Process.VALID_IDs.charAt(tempProcessNum
					% Process.VALID_IDs.length());
			int size = Process.VALID_MEMSIZES[r.nextInt(4)];
			int duration = r.nextInt(Process.MAX_RUNTIME) + 1;

			// Adding the newly created process to the queue
			processQueue.addLast(new Process(id, arrival, size, duration,
					arrival));
			++tempProcessNum;
			if (tempProcessNum > 4) {
				arrival += r.nextInt(5) / 2;
			}
		}
		return processQueue;
	}
}
