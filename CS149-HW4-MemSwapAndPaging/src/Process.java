public class Process {

	public char processID;
	public int processNum;
	public int arrivalTime;
	public int memorySize;
	public int runDuration;
	public int startTime;

	// ---------PROCESSES VALID VALUES---------//
	public static String VALID_IDs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
	public static final int[] VALID_MEMSIZES = { 5, 11, 17, 31 }; // Megabyte
	public static final int MAX_RUNTIME = 5; // 1-5 Seconds

	public Process(char id, int arrival, int size, int duration, int start) {
		this.processID = id;
		this.arrivalTime = arrival;
		this.memorySize = size;
		this.runDuration = duration;
		this.startTime = start;
		this.processNum = 0;
	}
}
