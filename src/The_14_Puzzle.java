import java.util.Date;
import java.util.LinkedList;


class The_14_Puzzle {
    private BehaviorTree behaviorTree;

    private int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 0};

    private BehaviorNode goal;
    private int rowLength;

    public static void main(String[] args) throws Throwable {
        The_14_Puzzle This = new The_14_Puzzle();
//hard
//        This.StartGame(new int[]{7, 2, 12, 9, 5, 3, 1, 4, 8, 0, 11, 14, 0, 6, 13, 10});
//        This.StartGame(new int[]{1, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14});
//medium
        This.StartGame(new int[]{5, 1, 4, 3, 2, 0, 10, 7, 9, 6, 0, 8, 13, 14, 12, 11});
//easy
//        This.StartGame(new int[]{1, 2, 3, 4, 5, 0, 0, 8, 9, 6, 7, 12, 13, 10, 11, 14});
//        This.StartGame(new int[]{1, 0, 2, 3, 5, 6, 7, 4, 9, 10, 11, 8, 0, 13, 14, 12});
//        This.StartGame(new int[]{1, 2, 3, 0, 5, 6, 7, 4, 9, 10, 11, 8, 13, 14, 0, 12});
//        This.StartGame(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 0});
//        This.StartGame(new int[]{0, 2, 3, 0, 1, 5, 10, 4, 9, 6, 8, 7, 13, 14, 11, 12});
        long startTime = new Date().getTime();
        LinkedList<BehaviorNode> path = This.Play();
        System.out.println((new Date().getTime() - startTime) + "ms");
        if (path == null) {
            System.out.println("No Answer!");
            return;
        }
        for (BehaviorNode step : path) {
            System.out.println(step.Print());
            System.out.println("-----------------------------------------------");
        }
    }

    /**
     * initial the game.
     *
     * @param startState start state of the game.
     */
    public void StartGame(int[] startState) {
        behaviorTree = new BehaviorTree(startState.clone());
        goal = new BehaviorNode(null, goalState.clone());
        rowLength = (int) Math.sqrt(startState.length);
    }

    /**
     * start from startState, then for each currentState find all possible moves, then change currentState to nextState.
     * if goal showed up find path to goal.
     *
     * @return path to goal.
     */
    public LinkedList<BehaviorNode> Play() throws Throwable {
        return Algorithm2();
    }

    /**
     * use bfs and start from startState to find goal.
     *
     * @return path from start to goal.
     */
    private LinkedList<BehaviorNode> Algorithm1() throws Throwable {
        BehaviorNode currentNode = behaviorTree.GetHeadOfFrontier();
        int firstBlank, secondBlank;
        LinkedList<int[]> possibleMoves;
        BehaviorNode newNode;
        while (true) {
            firstBlank = currentNode.FindFirstBlank();
            secondBlank = currentNode.FindSecondBlank();
            possibleMoves = FindAllPossibleMoves(currentNode.state.clone(), firstBlank);
            int possibleMovesLength = possibleMoves.size();
            possibleMoves.addAll(FindAllPossibleMoves(currentNode.state.clone(), secondBlank));
            for (int i = 0; i < possibleMovesLength; i++) {
                possibleMoves.addAll(FindAllPossibleMoves(possibleMoves.get(i).clone(), secondBlank));
            }
            for (int[] move : possibleMoves) {
                newNode = behaviorTree.NewNode(currentNode, move.clone());
                if (newNode != null && BehaviorNode.Compare(newNode, goal))
                    return newNode.PathToNode();
            }
            behaviorTree.AddNodeToExplored(currentNode);
            currentNode = behaviorTree.GetHeadOfFrontier();
            possibleMoves.clear();
            if (currentNode == null)
                return null;
        }
    }

    /**
     * use two bfs tree, in one of them start from startState and in the other one start from goal, if a same state
     * occurred in both trees we found a path from start state to goal.
     *
     * @return path from start to goal.
     */

    public LinkedList<BehaviorNode> Algorithm2() throws Throwable {
        /* head variables */
        BehaviorNode headCurrentNode = behaviorTree.GetHeadOfFrontier();
        int headFirstBlank, headSecondBlank;
        LinkedList<int[]> headPossibleMoves;
        boolean headNotEnded = true;
        /* tail variables */
        BehaviorTree tailBehaviorTree = new BehaviorTree(goal.state.clone());
        BehaviorNode tailCurrentNode = tailBehaviorTree.GetHeadOfFrontier();
        int tailFirstBlank, tailSecondBlank;
        LinkedList<int[]> tailPossibleMoves;
        boolean tailNotEnded = true;
        while (true) {
            /* head process */
            if (headNotEnded) {
                headFirstBlank = headCurrentNode.FindFirstBlank();
                headSecondBlank = headCurrentNode.FindSecondBlank();
                headPossibleMoves = FindAllPossibleMoves(headCurrentNode.state.clone(), headFirstBlank);
                headPossibleMoves.addAll(FindAllPossibleMoves(headCurrentNode.state.clone(), headSecondBlank));
                for (int i = 0; i < headPossibleMoves.size(); i++) {
                    BehaviorNode newNode = behaviorTree.NewNode(headCurrentNode, headPossibleMoves.get(i).clone());
                    if (newNode != null) {
                        BehaviorNode searchResult = tailBehaviorTree.SearchExplored(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(newNode, searchResult);
                        }
                        searchResult = tailBehaviorTree.SearchFrontier(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(newNode, searchResult);
                        }
                    }
                }
                behaviorTree.AddNodeToExplored(headCurrentNode);
                headCurrentNode = behaviorTree.GetHeadOfFrontier();
                headPossibleMoves.clear();
            }
            /* tail process */
            if (tailNotEnded) {
                tailFirstBlank = tailCurrentNode.FindFirstBlank();
                tailSecondBlank = tailCurrentNode.FindSecondBlank();
                tailPossibleMoves = FindAllPossibleMoves(tailCurrentNode.state.clone(), tailFirstBlank);
                tailPossibleMoves.addAll(FindAllPossibleMoves(tailCurrentNode.state.clone(), tailSecondBlank));
                for (int i = 0; i < tailPossibleMoves.size(); i++) {
                    BehaviorNode newNode = tailBehaviorTree.NewNode(tailCurrentNode, tailPossibleMoves.get(i).clone());
                    if (newNode != null) {
                        BehaviorNode searchResult = behaviorTree.SearchExplored(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(searchResult, newNode);
                        }
                        searchResult = behaviorTree.SearchFrontier(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(searchResult, newNode);
                        }
                    }
                }
                tailBehaviorTree.AddNodeToExplored(tailCurrentNode);
                tailCurrentNode = tailBehaviorTree.GetHeadOfFrontier();
                tailPossibleMoves.clear();
            }
            if (headCurrentNode == null)
                headNotEnded = false;
            if (tailCurrentNode == null)
                tailNotEnded = false;
            if (!tailNotEnded && !headNotEnded)
                return null;
        }
    }

    /**
     * get two nodes and get their paths then Concatenate path of second node to path of first node.
     *
     * @param firstNode  first BehaviorNode
     * @param secondNode second BehaviorNode
     * @return a path from first state in path of first node to last state in path of second node.
     */
    private LinkedList<BehaviorNode> ConcatenatePaths(BehaviorNode firstNode, BehaviorNode secondNode) {
        LinkedList<BehaviorNode> firstPath = firstNode.PathToNode();
        LinkedList<BehaviorNode> secondPath = secondNode.PathToNode();
        for (int i = secondPath.size() - 1; i >= 0; i--) {
            firstPath.addLast(secondPath.get(i));
        }
        return firstPath;
    }

    /**
     * find all possible moves for given blank in given state.
     *
     * @param state a state of game board.
     * @param blank place of the blank in game board.
     * @return all possible states after blank moved.
     */
    private LinkedList<int[]> FindAllPossibleMoves(int[] state, int blank) {
        LinkedList<int[]> movesList = new LinkedList<int[]>();
        /*
          down
         */
        int down = blank + rowLength;
        if (CheckDestination(state, down) && down / rowLength == blank / rowLength + 1) {
            movesList.add(SwipBlank(state.clone(), blank, down));
        }
        /*
         * right
         */
        int right = blank + 1;
        if (CheckDestination(state, right) && right / rowLength == blank / rowLength) {
            movesList.add(SwipBlank(state.clone(), blank, right));
        }
        /*
         * up
         */
        int up = blank - rowLength;
        if (CheckDestination(state, up) && up / rowLength == blank / rowLength - 1) {
            movesList.add(SwipBlank(state.clone(), blank, up));
        }
        /*
         * left
         */
        int left = blank - 1;
        if (CheckDestination(state, left) && left / rowLength == blank / rowLength) {
            movesList.add(SwipBlank(state.clone(), blank, left));
        }
        return movesList;
    }

    /**
     * check if blank can swipe with given destination.
     *
     * @param state       current state of game board.
     * @param destination the place that blank want to swipe with.
     * @return true if blank can swipe and false if blank can't swipe.
     */
    private boolean CheckDestination(int[] state, int destination) {
        return destination >= 0 && destination < state.length && state[destination] != 0;
    }

    /**
     * swipe given blank to given place.
     *
     * @param state       current game board state.
     * @param blank       current place of blank.
     * @param destination destination of blank.
     * @return game board state after swipe.
     */
    private int[] SwipBlank(int[] state, int blank, int destination) {
        int temp = state[blank];
        state[blank] = state[destination];
        state[destination] = temp;
        return state;
    }
}
