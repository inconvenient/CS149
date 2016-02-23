import java.util.LinkedList;
import java.util.Queue;

public class NextFitMemory {

	public static final int MEMORY_SIZE = 100; // RAM in Megabytes

	/*
	 * MAIN ALGORITHM: NEXT FIT
	 * 
	 * @param :: mainMemory - The LinkedList of Memory
	 * 
	 * @param :: memSize - The Process' memory size that you're trying to add
	 * 
	 * Run Description: Set position to 0. This is the index in the LinkedList
	 * of every added process [....][AAAA][.......] => position = 0, 1, 2 etc.
	 * for every process For each process in mainMemory, if the free space is
	 * empty and it's size is greater than the added process' memory
	 * requirement, set spotFound to true and return. Else, add 1 to position
	 * and repeat for the next process in mainMemory
	 */
	// Gets first available spot in mainMemory
	// lastAllocatedAt is the last place in memory that a process was allocated.
	// You should start searching at this position + 1.
	public int getFreeIndex(LinkedList<Process> mainMemory, int memSize,
			int lastAllocatedAt) {
		int previouslySearchedIndex = 0;
		int mainMemPosition = 0;

		for (Process p : mainMemory) {
			if (p.processID == '.' && p.memorySize >= memSize) {
				/*
				 * If previouslySearched is less than lastAllocated, it means
				 * you are still searching at an index that is earlier than
				 * the position that the last process was allocated. Continue
				 * until you reach a spot after the lastAllocated index.
				 */
				if (previouslySearchedIndex < lastAllocatedAt) {
					previouslySearchedIndex = mainMemPosition;
				} else {
					return mainMemPosition;
				}
			}
			mainMemPosition++;
		}
		return previouslySearchedIndex;
	}

	public int runFirstFit(Queue<Process> processQ) {
		// Instantiation of Queues
		// ***IMPORTANT*** COPY THE QUEUE. DO NOT OVERWRITE THE ORIGINAL
		Queue<Process> processQueue = duplicateQueue(processQ);
		Queue<Process> waitingQueue = new LinkedList<>();
		LinkedList<Process> mainMemory = new LinkedList<>();
		// Instantiation of used variables
		int memoryIndex = -1; // If -1, it means no memory is available
		int completedProcesses = 0;
		int currentTime = 0;

		// Add 1 'free memory process' to simulate free memory
		mainMemory.add(new Process('.', 0, MEMORY_SIZE, 0, 0));
		Process p = null;
		Process currentlyRunning = null;

		// Make sure both queues have processes in them. Otherwise, you're done!
		while (!processQueue.isEmpty() || !waitingQueue.isEmpty()) {
			// using --currentlyRunning.runDuration "runs" process for 1sec and
			// then checks.
			// if the currentlyRunning process hits 0 runTime, remove it.
			if (currentlyRunning != null && --currentlyRunning.runDuration <= 0) {
				removeProcess(mainMemory, currentTime);
				mainMemory = makeContiguous(mainMemory);
				currentlyRunning = null;
			}
			// if nothing is running, pull a process from waiting.
			// run this new process for 1sec
			if (currentlyRunning == null && waitingQueue.peek() != null) {
				currentlyRunning = waitingQueue.remove();
				--currentlyRunning.runDuration;
			}

			/*
			 * While there are processes left in processQueue and the next
			 * process' arrival time is less than the current time AND there is
			 * an available spot for the new process in memory
			 */
			while (processQueue.peek() != null
					&& processQueue.peek().arrivalTime <= currentTime
					&& getFreeIndex(mainMemory, processQueue.peek().memorySize,
							memoryIndex) != -1) {
				// Remove the process from the processQueue and get it ready.
				p = processQueue.remove();
				p.startTime = currentTime;

				// If there is nothing currently running
				if (currentlyRunning == null) {
					// If WaitingQueue has processes waiting
					if (waitingQueue.peek() != null) {
						// Add the head of the waitingQueue to run.
						currentlyRunning = waitingQueue.remove();
					} else
						// If waitingQueue is empty, the head of the
						// processQueue is now running.
						currentlyRunning = p;
				} else {
					// If there is something already running, add it to the
					// waitList
					waitingQueue.add(p);
				}

				// Add the process (p) to available memory!
				// CurrentMemory - newProcessMemory = new MemoryAvailable
				memoryIndex = getFreeIndex(mainMemory, p.memorySize,
						memoryIndex);
				Process freeMemory = mainMemory.remove(memoryIndex);
				mainMemory.add(memoryIndex, p);
				freeMemory.memorySize -= p.memorySize;
				if (freeMemory.memorySize > 0) {
					// add remaining free memory back to the mainMemory.
					// memoryIndex + 1 is to compensate for the added process.
					mainMemory.add(memoryIndex + 1, freeMemory);
				}
				completedProcesses++;

				printResults(mainMemory, currentTime);
			}

			// If total running duration has passed 60sec, exit while loop.
			if (++currentTime > 60) {
				break;
			}
		}
		return completedProcesses;
	}

	// --------- HELPER METHODS! --------- //
	public static Queue<Process> duplicateQueue(Queue<Process> queue) {
		LinkedList<Process> duplicate = new LinkedList<>();
		for (Process p : queue) {
			duplicate.add(p);
		}
		return duplicate;
	}

	public static void printResults(Queue<Process> memory, int currentTime) {
		Queue<Process> duplicate = duplicateQueue(memory);
		StringBuilder builder = new StringBuilder().append(String.format(
				"%-2ds -- ", currentTime));
		for (Process p2 : duplicate) {
			for (int i = 0; i < p2.memorySize; i++) {
				builder.append(p2.processID);
			}
		}
		System.out.println(builder);
	}

	public static void removeProcess(LinkedList<Process> mainMemory,
			int runDuration) {
		int i = 0;
		for (Process p : mainMemory) {
			if (p.processID != '.' && p.runDuration <= 0) {
				mainMemory.remove(i);
				mainMemory.add(i, new Process('.', 0, p.memorySize, 0, 0));
				printResults(mainMemory,
						Math.max(p.arrivalTime + p.runDuration, runDuration));
				break;
			}
			i++;
		}
	}

	public static LinkedList<Process> makeContiguous(Queue<Process> mainMem) {
		LinkedList<Process> compacted = new LinkedList<>();
		while (!mainMem.isEmpty()) {
			Process p = mainMem.remove();
			while (p.processID == '.' && mainMem.peek() != null
					&& mainMem.peek().processID == '.') {
				p.memorySize += mainMem.remove().memorySize;
			}
			compacted.add(p);
		}
		return compacted;
	}
}
