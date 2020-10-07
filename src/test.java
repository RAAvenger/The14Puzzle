import java.util.Date;
import java.util.Random;

class test {

    public static void main(String[] args) throws InterruptedException {
        test This = new test();
        System.out.println(new Date().getTime());
        This.TestNodeClass();
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
        BehaviorNode node1 = new BehaviorNode(nodeState1, 0, null);
        BehaviorNode node2 = new BehaviorNode(nodeState2, 0, null);
        BehaviorNode node3 = new BehaviorNode(nodeState1, 0, null);
        System.out.println(node1.Print());
        System.out.println(node2.Print());
        long t = 0;
        boolean temp;
        long start = new Date().getTime();
        temp = BehaviorNode.Compare(node1, node2);
        t = start - new Date().getTime();
        System.out.println("result=" + temp + " time=" + t);
        start = new Date().getTime();
        temp = BehaviorNode.Compare(node1, node3);
        t = start - new Date().getTime();
        System.out.println("result=" + temp + " time=" + t);
    }
}
