import java.util.LinkedList;
import java.util.Queue;

public class BehaviorTreeForBFSAlgorithm {
    private BehaviorNode root;
    private Queue<BehaviorNode> frontier;
    private Queue<BehaviorNode> explored;

    /**
     * tree constructor.
     *
     * @param rootState Array of int variables( "0" => "empty place" ). ex: [1,2,3,4,5,6,0,7,8,9,0,10,11,12,13,14].
     */
    public BehaviorTreeForBFSAlgorithm(int[] rootState) {
        frontier = new LinkedList<BehaviorNode>();
        explored = new LinkedList<BehaviorNode>();
        root = new BehaviorNode(null, rootState.clone());
        frontier.offer(root);
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
        if (!frontier.contains(parent) && !explored.contains(parent)) {
            throw new Throwable("Parent doesn't exists in tree");
        }
        BehaviorNode newNode = new BehaviorNode(parent, state.clone());
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
     * @param node given node from frontier.
     * @return true if node added successfully.
     */
    public boolean AddNodeToExplored(BehaviorNode node) {
        if (frontier.contains(node) && !explored.contains(node)) {
            frontier.remove(node);
            return explored.offer(node);
        } else
            return false;
    }

    /**
     * search frontier for given node.
     *
     * @param node a Behavior Node.
     * @return if node exists in frontier return other node otherwise return null.
     */
    public BehaviorNode SearchFrontier(BehaviorNode node) {
        for (BehaviorNode item : frontier) {
            if (BehaviorNode.Compare(item, node))
                return item;
        }
        return null;
    }

    /**
     * search explored for given node.
     *
     * @param node a Behavior Node.
     * @return if node exists in explored return other node otherwise return null.
     */
    public BehaviorNode SearchExplored(BehaviorNode node) {
        for (BehaviorNode item : explored) {
            if (BehaviorNode.Compare(item, node))
                return item;
        }
        return null;
    }
}
