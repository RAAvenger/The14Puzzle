import java.util.Date;
import java.util.LinkedList;

class The_14_Puzzle {
    private int rowLength;

    public static void main(String[] args) throws Throwable {
        The_14_Puzzle This = new The_14_Puzzle();
        int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 0};
//hard
//        int[] startState = {7, 2, 12, 9, 5, 3, 1, 4, 8, 0, 11, 14, 0, 6, 13, 10};
//        int[] startState = {1, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
//medium
//        int[] startState = {5, 1, 4, 3, 2, 0, 10, 7, 9, 6, 0, 8, 13, 14, 12, 11};
//easy
//        int[] startState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 0};
//        int[] startState = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0, 0, 14};
//        int[] startState = {1, 2, 3, 4, 5, 0, 0, 8, 9, 6, 7, 12, 13, 10, 11, 14};
//        int[] startState = {1, 0, 2, 3, 5, 6, 7, 4, 9, 10, 11, 8, 0, 13, 14, 12};
//        int[] startState = {1, 2, 3, 0, 5, 6, 7, 4, 9, 10, 11, 8, 13, 14, 0, 12};
        int[] startState = {0, 2, 3, 0, 1, 5, 10, 4, 9, 6, 8, 7, 13, 14, 11, 12};
        long startTime = new Date().getTime();
        LinkedList<BehaviorNode> path = This.Play(startState, goalState);
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
     * start from startState, then for each currentState find all possible moves, then change currentState to nextState.
     * if goal showed up find path to goal.
     *
     * @return path to goal.
     */
    public LinkedList<BehaviorNode> Play(int[] startState, int[] goalState) throws Throwable {
        rowLength = (int) Math.sqrt(startState.length);
//        return Algorithm1(startState.clone(), goalState.clone());
//        return Algorithm2(startState.clone(), goalState.clone());
        return Algorithm3(startState.clone(), goalState.clone());
    }

    /**
     * use bfs and start from startState to find goal.
     *
     * @return path from start to goal.
     */
    public LinkedList<BehaviorNode> Algorithm1(int[] startState, int[] goalState) throws Throwable {
        BehaviorNode goal = new BehaviorNode(null, goalState.clone(), -1);
        BehaviorTreeForBFSAlgorithm tree = new BehaviorTreeForBFSAlgorithm(startState);
        BehaviorNode currentNode = tree.GetHeadOfFrontier();
        LinkedList<int[]> possibleMoves;
        while (true) {
            int firstBlank = currentNode.FindFirstBlank();
            int secondBlank = currentNode.FindSecondBlank();
            possibleMoves = FindAllPossibleMoves(currentNode.state.clone(), firstBlank, secondBlank)[0];
            for (int[] move : possibleMoves) {
                BehaviorNode newNode = tree.NewNode(currentNode, move.clone());
                if (newNode != null && BehaviorNode.CompareStates(newNode, goal))
                    return newNode.PathToNode();
            }
            tree.AddNodeToExplored(currentNode);
            currentNode = tree.GetHeadOfFrontier();
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
    public LinkedList<BehaviorNode> Algorithm2(int[] startState, int[] goalState) throws Throwable {
        BehaviorNode goal = new BehaviorNode(null, goalState.clone(), -1);
        /* head variables */
        BehaviorTreeForBFSAlgorithm headTree = new BehaviorTreeForBFSAlgorithm(startState);
        BehaviorNode headCurrentNode = headTree.GetHeadOfFrontier();
        int headFirstBlank, headSecondBlank;
        LinkedList<int[]> headPossibleMoves;
        boolean headNotEnded = true;
        /* tail variables */
        BehaviorTreeForBFSAlgorithm tailTree = new BehaviorTreeForBFSAlgorithm(goal.state.clone());
        BehaviorNode tailCurrentNode = tailTree.GetHeadOfFrontier();
        int tailFirstBlank, tailSecondBlank;
        LinkedList<int[]> tailPossibleMoves;
        boolean tailNotEnded = true;
        while (true) {
            /* head process */
            if (headNotEnded) {
                headFirstBlank = headCurrentNode.FindFirstBlank();
                headSecondBlank = headCurrentNode.FindSecondBlank();
                headPossibleMoves = FindAllPossibleMoves(headCurrentNode.state.clone(), headFirstBlank, headSecondBlank)[0];
                for (int i = 0; i < headPossibleMoves.size(); i++) {
                    BehaviorNode newNode = headTree.NewNode(headCurrentNode, headPossibleMoves.get(i).clone());
                    if (newNode != null) {
                        BehaviorNode searchResult = tailTree.SearchExplored(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(newNode, searchResult);
                        }
                        searchResult = tailTree.SearchFrontier(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(newNode, searchResult);
                        }
                    }
                }
                headTree.AddNodeToExplored(headCurrentNode);
                headCurrentNode = headTree.GetHeadOfFrontier();
                headPossibleMoves.clear();
            }
            /* tail process */
            if (tailNotEnded) {
                tailFirstBlank = tailCurrentNode.FindFirstBlank();
                tailSecondBlank = tailCurrentNode.FindSecondBlank();
                tailPossibleMoves = FindAllPossibleMoves(tailCurrentNode.state.clone(), tailFirstBlank, tailSecondBlank)[0];
                for (int i = 0; i < tailPossibleMoves.size(); i++) {
                    BehaviorNode newNode = tailTree.NewNode(tailCurrentNode, tailPossibleMoves.get(i).clone());
                    if (newNode != null) {
                        BehaviorNode searchResult = headTree.SearchExplored(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(searchResult, newNode);
                        }
                        searchResult = headTree.SearchFrontier(newNode);
                        if (searchResult != null) {
                            return ConcatenatePaths(searchResult, newNode);
                        }
                    }
                }
                tailTree.AddNodeToExplored(tailCurrentNode);
                tailCurrentNode = tailTree.GetHeadOfFrontier();
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
     * use A-Star algorithm to find a path from start state to final state( goal ).
     *
     * @return path from start to goal.
     */
    public LinkedList<BehaviorNode> Algorithm3(int[] startState, int[] goalState) throws Throwable {
        BehaviorNode goal = new BehaviorNode(null, goalState.clone(), -1);
        BehaviorTreeForA_StarAlgorithm tree = new BehaviorTreeForA_StarAlgorithm(startState, goalState);
        while (true) {
            BehaviorNode currentNode = tree.GetHeadOfFrontier();
            if (currentNode != null && BehaviorNode.CompareStates(currentNode, goal))
                return currentNode.PathToNode();
            if (currentNode == null)
                return null;
            int firstBlank = currentNode.FindFirstBlank();
            int secondBlank = currentNode.FindSecondBlank();
            LinkedList[] possibleMoves = FindAllPossibleMoves(currentNode.state.clone(), firstBlank, secondBlank);
            for (int i = 0; i < possibleMoves[0].size(); i++) {
                int[] move = (int[]) possibleMoves[0].get(i);
                int moveCost = (int) possibleMoves[1].get(i);
                tree.NewNode(currentNode, move, moveCost);
            }
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
        for (int i = secondPath.size() - 2; i >= 0; i--) {
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
    private LinkedList<int[]> FindBlankPossibleMoves(int[] state, int blank) {
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
     * find all possible different moves for two blanks in the state.
     *
     * @param state       current state.
     * @param firstBlank  index of first blank.
     * @param secondBlank index of second blank.
     * @return list of states.
     */
    private LinkedList[] FindAllPossibleMoves(int[] state, int firstBlank, int secondBlank) {
        /** first blank moves first. */
        LinkedList<int[]> firstListOfPossibleMoves = FindBlankPossibleMoves(state.clone(), firstBlank);
        LinkedList<Integer> firstListOfMoveCosts = new LinkedList<Integer>();
        int firstBlankMovesLength = firstListOfPossibleMoves.size();
        for (int i = 0; i < firstBlankMovesLength; i++) {
            firstListOfPossibleMoves.addAll(FindBlankPossibleMoves(firstListOfPossibleMoves.get(i).clone(), secondBlank));
        }
        for (int i = 0; i < firstListOfPossibleMoves.size(); i++)
            firstListOfMoveCosts.add(i < firstBlankMovesLength ? 1 : 2);
        /** second blank moves first. */
        LinkedList<int[]> secondListOfPossibleMoves = FindBlankPossibleMoves(state.clone(), secondBlank);
        LinkedList<Integer> secondListOfMoveCosts = new LinkedList<Integer>();
        int secondBlankMovesLength = secondListOfPossibleMoves.size();
        for (int i = 0; i < secondBlankMovesLength; i++) {
            secondListOfPossibleMoves.addAll(FindBlankPossibleMoves(secondListOfPossibleMoves.get(i).clone(), firstBlank));
        }
        for (int i = 0; i < secondListOfPossibleMoves.size(); i++)
            secondListOfMoveCosts.add(i < secondBlankMovesLength ? 1 : 2);
        /** add moves( moves that don't exist in first list ) from second list to first list. */
        for (int i = 0; i < secondListOfPossibleMoves.size(); i++) {
            int[] move1 = secondListOfPossibleMoves.get(i);
            boolean doNotExistsInFirstList = true;
            for (int j = 0; j < firstListOfPossibleMoves.size(); j++) {
                int[] move2 = firstListOfPossibleMoves.get(j);
                /** move1 == move2 */
                if (CompareStates(move1, move2)) {
                    doNotExistsInFirstList = false;
                    break;
                }
            }
            /** move1 doesn't exists in first list */
            if (doNotExistsInFirstList) {
                firstListOfPossibleMoves.add(move1);
                firstListOfMoveCosts.add(secondListOfMoveCosts.get(i));
            }
        }
        return new LinkedList[]{firstListOfPossibleMoves, firstListOfMoveCosts};
    }

    /**
     * compare two states.
     *
     * @return "true" if they are equal "false" if they are not equal.
     */
    private boolean CompareStates(int[] firstState, int[] secondState) {
        for (int i = 0; i < firstState.length; i++) {
            /** move1 != move2 */
            if (firstState[i] != secondState[i]) {
                return false;
            }
        }
        return true;
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