import java.util.PriorityQueue;
import java.util.Queue;

public class BehaviorTreeForA_StarAlgorithm {
    private BehaviorNode root;
    private Queue<BehaviorNode> frontier;
    int[] goal;

    /**
     * tree constructor.
     *
     * @param rootState Array of int variables( '0' means "blank" ). ex: [1,2,3,4,5,6,0,7,8,9,0,10,11,12,13,14].
     * @param goalState Array of int variables.
     */
    public BehaviorTreeForA_StarAlgorithm(int[] rootState, int[] goalState) {
        frontier = new PriorityQueue<BehaviorNode>();
        root = new BehaviorNode(null, rootState);
        goal = goalState;
        root.CalculateCostToEndUsingHammingDistance(goal);
        frontier.add(root);
    }

    /**
     * add a new BehaviorNode to tree.
     *
     * @param parent parent node
     * @param state  new node state
     * @return "true" if node added successfully and "false" if state already exists in frontier or explored.
     */
    public BehaviorNode NewNode(BehaviorNode parent, int[] state) throws Throwable {
        /*
         * check for invalid inputs.
         */
        if (state.length != root.state.length) {
            throw new Throwable("Wrong state size");
        }
        BehaviorNode newNode = new BehaviorNode(parent, state.clone());
        newNode.CalculateCostToEndUsingHammingDistance(goal);
        frontier.add(newNode);
        return newNode;
    }

    /**
     * only get next node from frontier.
     *
     * @return node object
     */
    public BehaviorNode GetHeadOfFrontier() {
        return frontier.poll();
    }

}
