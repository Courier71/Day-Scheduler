// --== CS400 File Header Information ==--
// Name: Samuel Erickson
// Email: swerickson2@wisc.edu
// Team: LF
// Role: Front End Developer
// TA: Divyanshu
// Lecturer: Gary Dahl
// Notes to Grader: N/A
// Email: <byao8@wisc.edu>
// Team: <LF>
// TA: <Divyanshu Saxena>
// Role: <Back End Developer>
// Lecturer: <Gary Dahl>
// Notes to Grader: <N/A>\

/**
 * This class is able to insert events to the time schedule and avoid the
 * time conflict. It can generate the entire time schedule as well
 */

import java.util.NoSuchElementException;
import java.util.Stack;

public class TimeScheduler {
    RedBlackTree<Task> tree;
    //constructor
    TimeScheduler() {
         tree = new RedBlackTree<Task>();
    }

    /**
     * Attempt to insert a task to the red black tree
     * @param taskName
     * @param startTime
     * @param endTime
     * @return True if the task has been successfully inserted to the red black tree
     */
    public boolean insertTask(String taskName, int startTime, int endTime)
    {
        Task task = new Task(taskName, startTime, endTime);
        //check for overlap
        if (checkForOverlap(task)) {
            tree.insert(task);
            return true;
        }
            return false;
    }
    
    /**
     * This method gets the Task object from the RBT by a given start time int
     * @param starttime of the Task object to be found
     * @throws NoSuchElementException if the start time does not match a Task object
     * @return the Task with the passed start time
     */
    public Task get(int starttime) {
		RedBlackTree.Node<Task> next = tree.root; //Start at root
		while(next!=null) { //As long as there is another node
			if(next.data.getStartTime()==starttime) {
				return next.data;
			}
			if(next.data.getStartTime()>starttime) {
				next=next.leftChild;
			}
			else {
				next=next.rightChild;
			}
		}
		throw new NoSuchElementException("Error: That start time is not in the schedule."); //If the Task object is never found
	}

    /**
     * Check for overlap
     * @param task
     * @return True if the task has no conflict with others
     */
    public boolean checkForOverlap(Task task)
    {
        if (tree.root == null)
        {
            return true;
        }
        Stack<RedBlackTree.Node<Task>> stack = new Stack<>();
        stack.push(tree.root);
        RedBlackTree.Node<Task>temp = stack.pop();
        while (temp != null || !stack.isEmpty())
        {
            while (temp != null)
            {
                stack.push(temp);
                temp = temp.leftChild;
            }

            temp = stack.pop();

            //check for overlap
            if (temp.data.getEndTime() > task.getStartTime())
            {
                if (temp.data.getStartTime() >= task.getEndTime())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            temp = temp.rightChild;
        }
        return true;
    }

    public Task getData(RedBlackTree.Node<Task> node)
    {
        return node.data;
    }

    public String getColor(RedBlackTree.Node<Task> node)
    {
        if (node.isBlack)
        {
            return "Black";
        }
        return "Red";
    }

    public RedBlackTree.Node<Task> getLeftChild(RedBlackTree.Node<Task> node)
    {
        return node.leftChild;
    }

    public RedBlackTree.Node<Task> getRightChild(RedBlackTree.Node<Task> node)
    {
        return node.rightChild;
    }

    /**
     * Generate the time schedule
     * @return true if the root exist
     */
    public boolean generateTheSchedule()
    {
        if (tree.root == null)
        {
            return false;
        }
        //inorder Tree Traversal
        Stack<RedBlackTree.Node<Task>> stack = new Stack<>();
        stack.push(tree.root);
        RedBlackTree.Node<Task>temp = stack.pop();
        while (temp != null || !stack.isEmpty())
        {
            while (temp != null)
            {
                stack.push(temp);
                temp = temp.leftChild;
            }

            temp = stack.pop();
            //generate the time schedule
            System.out.println(temp.data.toString());
            temp = temp.rightChild;
        }
        return true;
    }

}

