import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestTask {

  @BeforeEach
  void setUp() throws Exception {
  }
//Tests the functionality of the Task class which we will be using as our data.
  @Test
  void test() {
    try {
      Task testTask = new Task("Test", 1300, 1800);
    }catch(IllegalArgumentException e) {
      
    fail("Not yet implemented");
    
  }
    
    try {
      Task testTask = new Task("Test2", 1500, 1200);
      fail("Task was created when start time was greater than end time.");
    }catch(IllegalArgumentException e2) {
      
    }
    
    Task task = new Task("Work", 600, 1200);
    if(task.getStartTime() != 600) {
      fail("Start time was incorrectly set.");
    }
    
    if(task.getEndTime() != 1200) {
      fail("End time was incorrectly set.");
    }
  }

}
