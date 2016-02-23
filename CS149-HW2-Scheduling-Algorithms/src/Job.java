public class Job {

	// Necessary Job Variables
	public String JobName;
	public int ArrivalTime; // Time or Position it arrived
	public float CPUTime; // Time it takes to complete
	public int Priority;
	public boolean Completed;

	// Extra Variables for Calculation
	public float WaitingTime;
	public float TurnAroundTime;

	public Job(String name, int arrival, float cpuTime, int priorityVal) {
		this.JobName = name;
		this.ArrivalTime = arrival;
		this.CPUTime = cpuTime;
		this.Priority = priorityVal;
	}

	public String getJobName() {
		return JobName;
	}

	public int getArrivalTime() {
		return ArrivalTime;
	}

	public float getCPUTime() {
		return CPUTime;
	}

	public int getPriority() {
		return Priority;
	}

	public float getWaitingTime() {
		return WaitingTime;
	}

	public float getTurnAroundTime() {
		return TurnAroundTime;
	}

	public void setJobName(String jobName) {
		JobName = jobName;
	}

	public void setArrivalTime(int arrivalTime) {
		ArrivalTime = arrivalTime;
	}

	public void setCPUTime(float cPUTime) {
		CPUTime = cPUTime;
	}

	public void setPriority(int priority) {
		Priority = priority;
	}

	public void setWaitingTime(float waitingTime) {
		WaitingTime = waitingTime;
	}

	public void setTurnAroundTime(float turnAroundTime) {
		TurnAroundTime = turnAroundTime;
	}

}
