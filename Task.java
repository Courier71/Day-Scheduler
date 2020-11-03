// --== CS400 File Header Information ==--
// Name: Samuel Erickson
// Email: swerickson2@wisc.edu
// Team: LF
// Role: Front End Developer
// TA: Divyanshu
// Lecturer: Gary Dahl
// Notes to Grader: N/A

/**
 * This creates a Task object for use in the the personal scheduler that organizes tasks into a red
 * black tree. This class uses a custom toString() method to output a task name as well as a start
 * and end time. The task object are compared alphabetically by their task name.
 * 
 * @author drewscharenbrock
 *
 */
public class Task implements Comparable<Task> {

  private String taskName;
  private int startTime;
  private int endTime;

  /**
   * Constructor for the Task class that sets the taskName, startTime, and endTime
   * 
   * @param taskName
   * @param startTime
   * @param endTime
   * @throws IllegalArgumentException if the start or end time is not valid
   */
  public Task(String taskName, int startTime, int endTime) throws IllegalArgumentException {

    if (startTime < 0000 || startTime > 2359 || startTime % 100 > 59) {
      throw new IllegalArgumentException("Start time must be a valid time between 0000 and 2359");
    }
    if (endTime < 0 || endTime > 2359 || endTime % 100 > 59 || endTime < startTime) {
      throw new IllegalArgumentException(
          "End time must be a valid time between 0000 and 2359. End time must also come after"
              + " start time.");
    }

    this.taskName = taskName;
    this.startTime = startTime;
    this.endTime = endTime;

  }

  /**
   * Accessor method for the task name
   * 
   * @return taskName
   */
  public String getTaskName() {
    return taskName;
  }

  /**
   * Accessor method for the start time
   * 
   * @return startTime
   */
  public int getStartTime() {
    return startTime;
  }

  /**
   * Accessor method for the end time
   * 
   * @return endTime
   */
  public int getEndTime() {
    return endTime;
  }
  
  public void changeName(String name) {
	  taskName=name;
  }

  /**
   * Overridden toString method. Returns a String representation of the task name as well as the
   * start and end time
   */
  @Override
  public String toString() {
    return taskName + ": " + String.format("%04d", startTime) + "-" + String.format("%04d", endTime);
  }

  /**
   * Overridden compareTo method. Returns 0 if task start time is the same. Returns -1 if the task
   * compareTo is called on comes before the provided task and returns 1 if not.
   */
  @Override
  public int compareTo(Task task) {
    return Integer.compare(this.startTime, task.startTime);
  }
}
