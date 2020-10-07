import java.util.Stack;

public class BehaviorNode {
    public short[] state;
    public int pathCost;
    public BehaviorNode parent;
    private int rowLength;

    public BehaviorNode(short[] state, int parentPathCost, BehaviorNode parent) {
        this.state = state;
        this.pathCost = parentPathCost + 1;
        this.parent = parent;
    }

    /**
     * print node state to string.
     *
     * @param rowLength length of table rows.
     * @return state table as string.
     */
    public String Print(int rowLength) {
        String result = "";
        for (int i = 0; i < this.state.length; i++) {
            result += this.state[i] >= 10 ? this.state[i] : "0" + this.state[i];
            if (i % rowLength == rowLength - 1) {
                result += "\n";
            } else {
                result += ",";
            }
        }
        return result;
    }

    /**
     * print node state to string.
     *
     * @return state table as string.
     */
    public String Print() {
        int rowLength = (int) Math.round(Math.sqrt(this.state.length));
        String result = "";
        for (int i = 0; i < this.state.length; i++) {
            result += this.state[i] >= 10 ? this.state[i] : "0" + this.state[i];
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


}
