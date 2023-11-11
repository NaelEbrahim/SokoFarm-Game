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
        // Save current State For Set As Parent To New State
        State parent = new State(state);
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
                    state.setParent(parent);

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        Operation.printBoard(state.getParent().getBoard());
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
                    state.setParent(parent);

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        Operation.printBoard(state.getParent().getBoard());
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
                    state.setParent(parent);

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        Operation.printBoard(state.getParent().getBoard());
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
                    state.setParent(parent);

                    // Check Final State
                    if (logic.checkFinalState()) {
                        new MainLogic(state);
                        Operation.printBoard(state.getParent().getBoard());
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
            State parent = new State(currentState);

            // Check if the current State is the final state
            if (currentState.getSeedList().isEmpty()) {
                System.out.println("End BFS");
                Operation.printBoard(currentState.getBoard());
                System.out.println("===============  PATH ==============");
                for (State fina : currentState.getPath()) {
                    Operation.printBoard(fina.getBoard());
                    System.out.println("\n");
                }
                return;
            }

            // Generate All Possible States
            List<State> result = AvailableMoves(new State(currentState));

            // Try All Possible States
            for (State curr : result) {
                if (!visited.contains(curr)) {
                    curr.setParent(parent);
                    queue.add(curr);
                }
            }
        }
        System.out.println("there is no solution");
    }

    public void DFS(State state) {
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();

        stack.add(state);
        visited.add(state);

        while (!stack.isEmpty()) {
            State currentState = new State(stack.pop());
            visited.add(currentState);
            State parent = new State(currentState);

            // Check if the current State is the final state
            if (currentState.getSeedList().isEmpty()) {
                System.out.println(stack.size());
                System.out.println("End DFS");
                Operation.printBoard(currentState.getBoard());
                System.out.println("===============  PATH ==============");
                for (State fina : currentState.getPath()) {
                    Operation.printBoard(fina.getBoard());
                    System.out.println("\n");
                }
                return;
            }

            // Generate All Possible States
            List<State> result = AvailableMoves(new State(currentState));

            // Try All Possible States
            for (State curr : result) {
                if (!visited.contains(curr)) {
                    curr.setParent(parent);
                    stack.add(curr);
                }
            }
        }
        System.out.println("there is no solution");
    }
}