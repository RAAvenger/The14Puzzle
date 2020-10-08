import java.util.Stack;

public class BehaviorNode {
    public int[] state;
    public int pathCost;
    public BehaviorNode parent;

    /**
     * Node constructor.
     *
     * @param state  new state
     * @param parent parent node
     */
    public BehaviorNode(BehaviorNode parent, int[] state) {
        this.state = state;
        this.parent = parent;
        this.pathCost = (parent != null) ? parent.pathCost + 1 : 0;
    }

    /**
     * print node state to string.
     *
     * @param rowLength length of table rows.
     * @return state table as string.
     */
    public String Print(int rowLength) {
        return getString(rowLength);
    }

    /**
     * print node state to string.
     *
     * @return state table as string.
     */
    public String Print() {
        int rowLength = (int) Math.round(Math.sqrt(this.state.length));
        return getString(rowLength);
    }

    private String getString(int rowLength) {
        String result = "";
        for (int i = 0; i < this.state.length; i++) {
            result += (this.state[i] >= 10) ? this.state[i] : "0" + this.state[i];
            if (i % rowLength == rowLength - 1) {
                result += "\n";
            } else {
                result += ",";
            }
        }
        return result;
    }

    /**
     * get path from root to this node.
     *
     * @return a stack of nodes.
     */
    public Stack<BehaviorNode> PathToNode() {
        BehaviorNode currentNode = this;
        Stack<BehaviorNode> path = new Stack<BehaviorNode>();
        do {
            path.push(currentNode);
            currentNode = currentNode.parent;
        } while (currentNode != null);
        return path;
    }

    /**
     * get two nodes and compare their states.
     *
     * @param first
     * @param second
     * @return "true" if nodes have same states and "false" if they don't have same states.
     */
    public static boolean Compare(BehaviorNode first, BehaviorNode second) {
        for (int i = 0; i < first.state.length; i++) {
            if (first.state[i] != second.state[i])
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        /**
         * the object is exactly this node.
         */
        if (object == this)
            return true;
        /**
         * the object isn't a node.
         */
        if (!(object instanceof BehaviorNode))
            return false;
        /**
         * compare object and this class.
         */
        return BehaviorNode.Compare(this, (BehaviorNode) object);
    }

    /**
     * find first blank's( 0 ) index in state array.
     *
     * @return if blank exists => 0_15, if blank don't exists => -1.
     */
    public int FindFirstBlank() {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0)
                return i;
        }
        return -1;
    }

    /**
     * find second blank's( 0 ) index in state array.
     *
     * @return if blank exists => 0_15, if blank don't exists => -1.
     */
    public int FindSecondBlank() {
        for (int i = state.length - 1; i >= 0; i--) {
            if (state[i] == 0)
                return i;
        }
        return -1;
    }
//    @Override
//    public int compareTo(Object o) {
//        if (this.equals(o))
//            return 0;
//        BehaviorNode ob = (BehaviorNode) o;
//        if (this.pathCost > ob.pathCost)
//            return 1;
//        else if (this.pathCost < ob.pathCost)
//            return -1;
//        else
//            return 2;
//    }
}
