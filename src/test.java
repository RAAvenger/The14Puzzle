import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

class test {

    public static void main(String[] args) throws Throwable {
        test This = new test();
        System.out.println(new Date().getTime());
//        This.TestNodeClass();
        This.TestTreeClass();
        System.out.println(new Date().getTime());
    }

    public void TestNodeClass() {
        short[] nodeState1 = new short[16];
        for (int i = 0; i < 16; i++) {
            nodeState1[i] = (short) new Random().nextInt(16);
        }
        short[] nodeState2 = new short[16];
        for (int i = 0; i < 16; i++) {
            nodeState2[i] = (short) new Random().nextInt(16);
        }
        BehaviorNode node1 = new BehaviorNode(null, nodeState1);
        BehaviorNode node2 = new BehaviorNode(null, nodeState2);
        BehaviorNode node3 = new BehaviorNode(null, nodeState1);
        System.out.println(node1.Print());
        System.out.println(node2.Print());
        boolean temp;
        temp = BehaviorNode.Compare(node1, node2);
        System.out.println("result1=" + temp);
        temp = BehaviorNode.Compare(node1, node3);
        System.out.println("result2=" + temp);
        /**
         * check contains function of collections.
         */
        Queue<BehaviorNode> list1 = new PriorityQueue<BehaviorNode>();
        list1.offer(node2);
        list1.offer(node3);
        temp = list1.contains(node1);
        System.out.println("result3=" + temp);
        Queue<BehaviorNode> list2 = new PriorityQueue<BehaviorNode>();
        list2.offer(node2);
        temp = list2.contains(node1);
        System.out.println("result4=" + temp);
    }

    public void TestTreeClass() throws Throwable {
        /**
         * basic test.
         */
        short[] root = newState(), lastState = null;
        BehaviorTree tree = new BehaviorTree(root);
        BehaviorNode frontNode = null;
        boolean temp;
        for (int c = 0; c < 12; c++) {
            frontNode = tree.GetHeadOfFrontier();
            for (int d = 0; d < 8; d++) {
                lastState = newState();
                temp = tree.NewNode(frontNode, lastState);
                System.out.println("Add:" + temp);
            }
            tree.AddNodeToExplored(frontNode);
            frontNode = tree.GetHeadOfFrontier();
        }
        /**
         *  check if we can add duplicate states.
         */
        temp = tree.NewNode(frontNode, root);
        System.out.println("Add root:" + temp);
        temp = tree.NewNode(frontNode, lastState);
        System.out.println("Add last:" + temp);
        System.in.read();
    }

    public short[] newState() {
        short[] state = new short[16];
        for (int i = 0; i < 16; i++) {
            state[i] = (short) new Random().nextInt(16);
        }
        return state;
    }
}
