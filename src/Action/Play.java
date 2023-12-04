package Action;

import Logic.MainLogic;
import Structure.Methods.Operation;
import Structure.Component.State;
import Structure.Component.Position;

import java.util.*;

public class Play {

    Scanner scanner = new Scanner(System.in);

    Map<Integer, String> moves = new HashMap<>();

    public Play() {
        moves.put(0, "W");
        moves.put(1, "S");
        moves.put(2, "A");
        moves.put(3, "D");
    }

    public State UserPlay(State state) {
        System.out.println("W : to Move Farmer Up \nS : to Move Farmer Down\nA : to Move Farmer Left\nD : to Move Farmer Right\nQ : to Exit the Game");
        Position farmerpos = state.getFarmerpos();
        MainLogic logic = new MainLogic(state);
        String input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "Q" -> {
                return null;
            }
            case "W" -> {
                Position dirpos = new Position(farmerpos.getX() - 1, farmerpos.getY());
                if (logic.HandleMoveAction(dirpos, "UP")) {
                    System.out.println("================= New Board After Action =================");
                    Operation.printBoard(state.getBoard());

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        return null;
                    } else {
                        return new State(state);
                    }
                } else {
                    System.out.println("Can not Move , There is Rock , Or Multiple Seed in this place !!");
                    return state;
                }
            }
            case "S" -> {
                Position dirpos = new Position(farmerpos.getX() + 1, farmerpos.getY());
                if (logic.HandleMoveAction(dirpos, "DOWN")) {
                    System.out.println("================= New Board After Action =================");
                    Operation.printBoard(state.getBoard());

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        return null;
                    } else {
                        return new State(state);
                    }
                } else {
                    System.out.println("Can not Move , There is Rock , Or Multiple Seed in this place !!");
                    return state;
                }
            }
            case "D" -> {
                Position dirpos = new Position(farmerpos.getX(), farmerpos.getY() + 1);
                if (logic.HandleMoveAction(dirpos, "RIGHT")) {
                    System.out.println("================= New Board After Action =================");
                    Operation.printBoard(state.getBoard());

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        return null;
                    } else {
                        return new State(state);
                    }
                } else {
                    System.out.println("Can not Move , There is Rock , Or Multiple Seed in this place !!");
                    return state;
                }
            }
            case "A" -> {
                Position dirpos = new Position(farmerpos.getX(), farmerpos.getY() - 1);
                if (logic.HandleMoveAction(dirpos, "LEFT")) {
                    System.out.println("================= New Board After Action =================");
                    Operation.printBoard(state.getBoard());

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        return null;
                    } else {
                        return new State(state);
                    }
                } else {
                    System.out.println("Can not Move , There is Rock , Or Multiple Seed in this place !!");
                    return state;
                }
            }
            default -> {
                System.out.println("Wrong Action");
                return state;
            }
        }
    }

    public List<State> AvailableMoves(State state) {
        List<State> available_moves = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            State current2 = new State(state);
            State newState = MainLogic.AutoPlay(current2, moves.get(i));
            if (newState != null) {
                available_moves.add(newState);
            }
        }
        return available_moves;
    }

    public void BFS(State state) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.add(state);
        visited.add(state);

        while (!queue.isEmpty()) {
            State currentState = new State(queue.poll());
            visited.add(currentState);
            // ReInitialize Path to currentState Because its null
            if (currentState.getPath().isEmpty()) {
                currentState.AddToPath(currentState);
            }

            // Check if the current State is the final state
            if (new MainLogic(currentState).checkGoal()) {
                System.out.println("End BFS , The Goal State Is :\n");
                Operation.printBoard(currentState.getBoard());
                System.out.println("============== PATH ==============");
                for (State fina : currentState.getPath()) {
                    Operation.printBoard(fina.getBoard());
                }
                System.out.println("Cost= " + currentState.getCost());
                System.out.println("Total Visited= " + visited.size());
                return;
            }

            // Generate All Possible States
            List<State> result = AvailableMoves(new State(currentState));

            // Try All Possible States
            for (State curr : result) {
                if (!visited.contains(curr)) {
                    curr.setParentAndPath(currentState);
                    queue.add(curr);
                }
            }
        }
        System.out.println("There Is No Solution");
    }

    public void DFS(State state) {
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();

        stack.add(state);
        visited.add(state);

        while (!stack.isEmpty()) {
            State currentState = new State(stack.pop());
            visited.add(currentState);
            // ReInitialize Path to currentState Because its null
            if (currentState.getPath().isEmpty()) {
                currentState.AddToPath(currentState);
            }

            // Check if the current State is the final state
            if (new MainLogic(currentState).checkGoal()) {
                System.out.println("End DFS , The Goal State Is :\n");
                Operation.printBoard(currentState.getBoard());
                System.out.println("============== PATH ==============");
                for (State fina : currentState.getPath()) {
                    Operation.printBoard(fina.getBoard());
                }
                System.out.println("Cost= " + currentState.getCost());
                System.out.println("Total Visited= " + visited.size());
                return;
            }

            // Generate All Possible States
            List<State> result = AvailableMoves(new State(currentState));

            // Try All Possible States
            for (State curr : result) {
                if (!visited.contains(curr)) {
                    curr.setParentAndPath(currentState);
                    stack.add(curr);
                }
            }
        }
        System.out.println("There Is No Solution");
    }

    public void UCS(State state) {
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(State::getCost));
        Map<State, State> parentMap = new HashMap<>();
        Set<State> visited = new HashSet<>();

        priorityQueue.add(state);
        visited.add(state);

        while (!priorityQueue.isEmpty()) {
            State currentState = new State(priorityQueue.poll());
            visited.add(currentState);
            // ReInitialize Path to currentState Because its null
            if (currentState.getPath().isEmpty()) {
                currentState.AddToPath(currentState);
            }

            // Check if the current state is the final state
            if (new MainLogic(currentState).checkGoal()) {
                System.out.println("End UCS , The Goal State Is :\n");
                Operation.printBoard(currentState.getBoard());
                System.out.println("============== PATH ==============");
                Operation.printPath(currentState, parentMap);
                System.out.println("Cost= " + currentState.getCost());
                System.out.println("Total Visited= " + visited.size());
                return;
            }

            // Generate all possible states
            List<State> result = AvailableMoves(new State(currentState));

            // Try all possible states
            for (State nextState : result) {
                if (!visited.contains(nextState) || nextState.getCost() < FindElementInHashSet(visited,nextState).getCost() ) {
                    // Set the Current State As Parent For Next State
                    parentMap.put(nextState, currentState);
                    nextState.setParentAndPath(currentState);
                    priorityQueue.add(nextState);
                }
            }
        }
        System.out.println("There Is No Solution");
    }

    private State FindElementInHashSet (Set <State> set , State state) {
        State findedElement = null ;
        for (State element : set){
            if (element.equals(state)){
                findedElement =  element ;
            }
        }
        return findedElement ;
    }

    public void AStar(State state) {
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(State::getPriority));
        Map<State, State> parentMap = new HashMap<>();
        Set<State> visited = new HashSet<>();

        priorityQueue.add(state);
        visited.add(state);

        while (!priorityQueue.isEmpty()) {
            State currentState = new State(priorityQueue.poll());
            visited.add(currentState);
            // Reinitialize Path to currentState Because it's null
            if (currentState.getPath().isEmpty()) {
                currentState.AddToPath(currentState);
            }

            // Check if the current state is the final state
            if (new MainLogic(currentState).checkGoal()) {
                System.out.println("End A*, The Goal State Is :\n");
                Operation.printBoard(currentState.getBoard());
                System.out.println("============== PATH ==============");
                //Operation.printPath(currentState, parentMap);
                System.out.println("Cost= " + currentState.getCost());
                System.out.println("Total Visited= " + visited.size());
                return;
            }

            // Generate all possible states
            List<State> result = AvailableMoves(new State(currentState));

            // Try all possible states
            for (State nextState : result) {
                // Calculate the priority of the next state
                int priority = nextState.getCost() + Heuristic_1(nextState);

                if (!visited.contains(nextState) || priority < nextState.getPriority()) {
                    // Update the priority and parent of the next state
                    nextState.setPriority(priority);
                    parentMap.put(nextState, currentState);
                    nextState.setParentAndPath(currentState);
                    priorityQueue.add(nextState);
                }
            }
        }
        System.out.println("There Is No Solution");
    }

    public void HillClimbing(State state) {
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(State::getPriority));
        Map<State, State> parentMap = new HashMap<>();
        Set<State> visited = new HashSet<>();

        priorityQueue.add(state);
        visited.add(state);

        while (!priorityQueue.isEmpty()) {
            State currentState = new State(priorityQueue.poll());
            visited.add(currentState);
            // ReInitialize Path to currentState Because its null
            if (currentState.getPath().isEmpty()) {
                currentState.AddToPath(currentState);
            }

            // Check if the current state is the final state
            if (new MainLogic(currentState).checkGoal()) {
                System.out.println("End Hill Climbing , The Goal State Is :\n");
                Operation.printBoard(currentState.getBoard());
                System.out.println("============== PATH ==============");
                Operation.printPath(currentState, parentMap);
                System.out.println("Cost= " + currentState.getCost());
                System.out.println("Total Visited= " + visited.size());
                return;
            }

            // Generate all possible states
            List<State> result = AvailableMoves(new State(currentState));

            // Try all possible states
            for (State nextState : result) {
                if (!visited.contains(nextState)) {
                    // Set the Current State As Parent For Next State
                    parentMap.put(nextState, currentState);
                    // Set Heuristic Value For Next State
                    nextState.setPriority(Heuristic_1(nextState));
                    nextState.setParentAndPath(currentState);
                    priorityQueue.add(nextState);
                }
            }
        }
        System.out.println("There Is No Solution");
    }

    public void OriginalHillClimbing(State state) {

        State currentState = new State(state);
        int currentCost = Heuristic_1(currentState);

        while (true) {

            if (new MainLogic(currentState).checkGoal()) {
                Operation.printBoard(currentState.getBoard());
                return;
            }

            // Generate All Possible Neighbors
            List<State> neighbors = AvailableMoves(new State(currentState));

            State bestNeighbor = null;
            int bestNeighborCost = Integer.MAX_VALUE;

            // Find the neighbor with the lowest heuristic cost
            for (State neighbor : neighbors) {
                int neighborCost = Heuristic_1(neighbor);
                if (neighborCost < bestNeighborCost) {
                    // Update Best Neighbor With Cost
                    bestNeighbor = new State(neighbor);
                    bestNeighborCost = neighborCost;
                }
            }

            // If no better neighbor is found, return the current state
            if (bestNeighborCost >= currentCost) {
                Operation.printBoard(currentState.getBoard());
                return;
            }

            // Update the current state and cost to the best neighbor
            currentState = new State(bestNeighbor);
            currentCost = bestNeighborCost;
        }
    }

    private int Heuristic_1(State state) {
        MainLogic logic = new MainLogic(state);
        int penalty = 0 ;
        int totalDistance = 0;

        // Iterate over each seed and calculate the distance to the closest hole
        for (Position seed : state.getSeedList()) {
            // Set Big Random Value To minDistance
            int minDistance = Integer.MAX_VALUE;

            for (Position hole : state.getHoleList()) {
                // Calculate the Manhattan distance between Seed And Hole
                int distance = Math.abs(seed.getX() - hole.getX()) + Math.abs(seed.getY() - hole.getY());
                minDistance = Math.min(minDistance, distance);
            }
            totalDistance += minDistance;
        }
        // Check if the current state is a losing state
        if (logic.checkLose()) {
            penalty = 10000;
        }
        return totalDistance + penalty;
    }

    public int Heuristic_2(State state) {
        MainLogic logic = new MainLogic(state);
        int penalty = 0 ;
        int totalDistance = 0;

        // Iterate over each seed and calculate the distance to the closest hole
        for (Position seed : state.getSeedList()) {
            // Set a large random value to minDistance
            int minDistance = Integer.MAX_VALUE;

            for (Position hole : state.getHoleList()) {
                // Calculate the Manhattan distance between the seed and Farmer And Save It In Current
                Position current = new Position(Math.abs(seed.getX() - state.getFarmerpos().getX()), Math.abs(seed.getX() - state.getFarmerpos().getX()));
                // Calculate the Manhattan distance between Current And Hole
                int distance = Math.abs(current.getX() - hole.getX()) + Math.abs(current.getY() - hole.getY());

                minDistance = Math.min(minDistance, distance);
            }

            // Calculate the distance between the farmer and the seed
            int farmerDistance = Math.abs(state.getFarmerpos().getX() - seed.getX())
                    + Math.abs(state.getFarmerpos().getY() - seed.getY());

            // Adjust the total distance by considering the farmer's position
            totalDistance += minDistance  + farmerDistance ;
        }
        // Check if the current state is a losing state
        if (logic.checkLose()) {
            penalty = 10000;
        }
        return totalDistance + penalty;
    }

    public int Heuristic_3(State state) {
        MainLogic logic = new MainLogic(state);
        int penalty = 0 ;
        int totalDistance = 0;

        // Iterate over each seed and calculate the distance to the corresponding hole
        for (int i = 0; i < state.getSeedList().size(); i++) {
            Position seed = state.getSeedList().get(i);
            Position hole = state.getHoleList().get(i);

            // Calculate the Manhattan distance between the seed and hole
            int distance = Math.abs(seed.getX() - hole.getX()) + Math.abs(seed.getX() - hole.getX());

            totalDistance += distance;
        }

        // Calculate the distance between the farmer and the seeds
        for (Position seed : state.getSeedList()) {
            int distance = Math.abs(seed.getX() - state.getFarmerpos().getX())
                    + Math.abs(seed.getY() - state.getFarmerpos().getY());

            totalDistance += distance;
        }
        // Check if the current state is a losing state
        if (logic.checkLose()) {
            penalty = 10000;
        }
        return totalDistance + penalty;
    }

}