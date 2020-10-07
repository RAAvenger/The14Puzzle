import java.util.Queue;

public class BehaviorTree {
    public int stateLength;
    public BehaviorNode root;
    public Queue<BehaviorNode> frontier;
    public Queue<BehaviorNode> explored;
/**
 * class constructor.
 * get state length( for a 3*3 board size is 9 ) and root( ex: "1,2,3,4,5,6,_,7,8,9,_,10,11,12,13,14" ).
 */
    public BehaviorTree(int sizeOfState, String root) {

    }
}
