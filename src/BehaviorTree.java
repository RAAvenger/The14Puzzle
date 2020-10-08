import java.util.PriorityQueue;
import java.util.Queue;

public class BehaviorTree {
    private BehaviorNode root;
    private Queue<BehaviorNode> frontier;
    private Queue<BehaviorNode> explored;

    /**
     * tree constructor.
     *
     * @param rootState Array of short variables( "0" => "empty place" ). ex: [1,2,3,4,5,6,0,7,8,9,0,10,11,12,13,14].
     */
    public BehaviorTree(short[] rootState) {
        frontier = new PriorityQueue<BehaviorNode>();
        explored = new PriorityQueue<BehaviorNode>();
        root = new BehaviorNode(null, rootState);
        frontier.offer(root);
    }

    /**
     * add a new BehaviorNode to tree.
     *
     * @param parent parent node
     * @param state  new node state
     * @return "true" if node added successfully and "false" if state already exists in frontier or explored.
     * @throws Throwable
     */
    public boolean NewNode(BehaviorNode parent, short[] state) throws Throwable {
        /**
         * check for invalid inputs.
         */
        if (state.length != root.state.length) {
            throw new Throwable("Wrong state size");
        }
        if (!frontier.contains(parent) && !explored.contains(parent)) {
            throw new Throwable("Parent doesn't exists in tree");
        }

        BehaviorNode newNode = new BehaviorNode(parent, state);
        if (!frontier.contains(newNode) && !explored.contains(newNode)) {
            frontier.add(newNode);
            return true;
        } else {
            return false;
        }
    }

    /**
     * only get next node from frontier.
     *
     * @return node object
     */
    public BehaviorNode GetHeadOfFrontier() {
        return frontier.peek();
    }

    /**
     * remove node from frontier and add node to explored.
     *
     * @param node
     * @return true if node added successfully.
     */
    public boolean AddNodeToExplored(BehaviorNode node) {
        if (frontier.contains(node) && !explored.contains(node)) {
            frontier.remove(node);
            return explored.offer(node);
        } else
            return false;
    }
}
