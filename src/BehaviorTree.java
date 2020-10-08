import java.util.LinkedList;
import java.util.Queue;

public class BehaviorTree {
    private BehaviorNode root;
    private Queue<BehaviorNode> frontier;
    private Queue<BehaviorNode> explored;

    /**
     * tree constructor.
     *
     * @param rootState Array of int variables( "0" => "empty place" ). ex: [1,2,3,4,5,6,0,7,8,9,0,10,11,12,13,14].
     */
    public BehaviorTree(int[] rootState) {
        frontier = new LinkedList<BehaviorNode>();
        explored = new LinkedList<BehaviorNode>();
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
    public BehaviorNode NewNode(BehaviorNode parent, int[] state) throws Throwable {
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
            return newNode;
        } else {
            return null;
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
