import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BehaviorTreeForA_StarAlgorithm {
    private BehaviorNode root;
    private Queue<BehaviorNode> frontier;
    private LinkedList<BehaviorNode> dump;
    int[] goal;

    /**
     * tree constructor.
     *
     * @param rootState Array of int variables( '0' means "blank" ). ex: [1,2,3,4,5,6,0,7,8,9,0,10,11,12,13,14].
     * @param goalState Array of int variables.
     */
    public BehaviorTreeForA_StarAlgorithm(int[] rootState, int[] goalState) {
        frontier = new PriorityQueue<BehaviorNode>();
        dump = new LinkedList<>();
        root = new BehaviorNode(null, rootState, 0);
        goal = goalState;
        root.CalculateHammingDistance(goal);
        frontier.add(root);
    }

    /**
     * add a new BehaviorNode to tree.
     *
     * @param parent parent node
     * @param state  new node state
     */
    public void NewNode(BehaviorNode parent, int[] state, int moveCost) throws Throwable {
        /** check for invalid inputs. */
        if (state.length != root.state.length) {
            throw new Throwable("Wrong state size");
        }
        BehaviorNode newNode = new BehaviorNode(parent, state.clone(), moveCost);
        newNode.CalculateHammingDistance(goal);
        frontier.add(newNode);
    }

    /**
     * only get next node from frontier.
     *
     * @return node object
     */
    public BehaviorNode GetHeadOfFrontier() {
        BehaviorNode node = frontier.poll();
        dump.add(node);
        return node;

    }

}
