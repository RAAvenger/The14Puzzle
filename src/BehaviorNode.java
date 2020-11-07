import java.util.LinkedList;

public class BehaviorNode implements Comparable {
    public int[] state;
    public int costFromStart;
    public int costToEnd;
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
        this.costFromStart = (parent != null) ? parent.costFromStart + 1 : 0;
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

    /**
     * get path from root to this node.
     *
     * @return a stack of nodes.
     */
    public LinkedList<BehaviorNode> PathToNode() {
        BehaviorNode currentNode = this;
        LinkedList<BehaviorNode> path = new LinkedList<BehaviorNode>();
        do {
            path.addFirst(currentNode);
            currentNode = currentNode.parent;
        } while (currentNode != null);
        return path;
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

    /**
     * get two nodes and compare their states.
     *
     * @param first  first node.
     * @param second second node.
     * @return "true" if nodes have same states and "false" if they don't have same states.
     */
    public static boolean Compare(BehaviorNode first, BehaviorNode second) {
        for (int i = 0; i < first.state.length; i++) {
            if (first.state[i] != second.state[i])
                return false;
        }
        return true;
    }

    /**
     * get goal state and calculate hamming distance from this state to goal than store value in costToEnd.
     *
     * @param finallState the goal.
     */
    public void CalculateCostToEndUsingHammingDistance(int[] finallState) {
        int cost = 0;
        for (int i = 0; i < this.state.length; i++) {
            if (state[i] != finallState[i])
                cost++;
        }
        this.costToEnd = cost;
    }

    @Override
    public boolean equals(Object object) {
        /*
         * the object is exactly this node.
         */
        if (object == this)
            return true;
        /*
         * the object isn't a node.
         */
        if (!(object instanceof BehaviorNode))
            return false;
        /*
         * compare object and this class.
         */
        return BehaviorNode.Compare(this, (BehaviorNode) object);
    }

    @Override
    public int compareTo(Object object) {
        BehaviorNode secondNode = (BehaviorNode) object;
        int costFromStartCompare = costFromStart - secondNode.costFromStart;
        int costToEndCompare = costToEnd - secondNode.costToEnd;
        if (costFromStartCompare != 0)
            return costFromStartCompare;
        else
            return costToEndCompare;
    }

    /**
     * get State as a well formatted string.
     *
     * @param rowLength length of each row.
     * @return state as well shaped string.
     */
    private String getString(int rowLength) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.state.length; i++) {
            result.append((this.state[i] >= 10) ? this.state[i] : "0" + this.state[i]);
            if (i % rowLength == rowLength - 1) {
                result.append("\n");
            } else {
                result.append(",");
            }
        }
        return result.toString();
    }


}
