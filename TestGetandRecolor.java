import static org.junit.jupiter.api.Assertions.fail;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGetandRecolor {
  
  TimeScheduler sced = new TimeScheduler();
/*
 * Inserts several tasks into a schedule that will require the tree to use multiple rotations and
 * recolor.
 */
  @BeforeEach
  void setUp() throws Exception {
    sced.insertTask("Workout", 600, 700);
    sced.insertTask("Breakfast", 700, 730);
    sced.insertTask("English", 900, 1050);
    sced.insertTask("Biology", 1200, 1330);
    sced.insertTask("Dinner", 1900, 2000);
    
  }
/*
 * This test case tests the get method that returns a task and also tests the recoloring of the
 * RedBlackTree to ensure the TimeScheduler is setting up a proper RB Tree
 */
  @Test
  void test() {
    //Test the get method on a task in the schedule
    if(!sced.get(900).getTaskName().equals("English")) {
      fail("The get method did not return the correct task.");
    }
    //Test the get method on a start time that does not exist
    try {
      sced.get(500);
      fail("The get method wrongly returned a task with a start time that does not exist");
    }catch(NoSuchElementException e) {
      
    }
    //Test the root of the schedule's children to ensure they were rotated and recolored correctly
    if(!sced.getData(sced.tree.root.leftChild).getTaskName().equals("Workout")) {
      fail("The schedule did not correctly rotate the task nodes.");
    }
    //Test root's right child to evaluate schedule's use of right-left rotation
    if(!sced.getData(sced.tree.root.rightChild).getTaskName().equals("Biology")) {
      fail("The tree did not perform a right-left rotation correctly.");
    }
    //Test important colors in tree to ensure RB properties are kept
    if(sced.getColor(sced.tree.root.leftChild).equals("Red")) {
      fail("The tree root's left child was not changed to black to keep the black property.");
    }
    if(sced.getColor(sced.tree.root.rightChild).equals("Red")) {
      fail("The tree root's right child was not changed to black so the red property is violated.");
    }
    if(sced.getColor(sced.tree.root.rightChild.leftChild).equals("Black")) {
      fail("The subtree's child is not red so the black property is violated.");
    }
    if(sced.getColor(sced.tree.root.rightChild.rightChild).equals("Black")) {
      fail("The subtree's child is not red so the black property is violated.");
    }
    sced.generateTheSchedule();
    
  }

}
