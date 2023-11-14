package Structure.Methods;

import Structure.Component.Position;
import Structure.Component.State;

import java.util.Map;

public class Operation {

    State state;

    public Operation(State state) {
        this.state = state;
    }

    public void AddSeed(Position position) {
        this.state.board[position.getX()][position.getY()] = 'S';
        this.state.SeedList.add(new Position(position.getX(), position.getY()));
    }

    public void AddHole(Position position) {
        this.state.board[position.getX()][position.getY()] = 'H';
    }

    public void AddFarmer(Position position) {
        this.state.board[position.getX()][position.getY()] = 'F';
        this.state.setFarmerpos(new Position(position.getX(), position.getY()));
    }

    public void AddEmpty(Position position) {
        this.state.board[position.getX()][position.getY()] = 'E';
    }

    public void AddFarmerWithHole(Position position) {
        this.state.board[position.getX()][position.getY()] = 'M';
        this.state.setFarmerpos(new Position(position.getX(), position.getY()));
    }

    public void AddSeedWithHole(Position position) {
        this.state.board[position.getX()][position.getY()] = 'K';
    }

    public char getElement(Position position) {
        return this.state.board[position.getX()][position.getY()];
    }

    public static void printBoard(char[][] arr) {
        for (char[] chars : arr) {
            for (int j = 0; j < arr[0].length; j++) {
                char current = chars[j];
                switch (current) {
                    case 'E' -> System.out.print("\u001B[37m" + current + "    ");
                    case 'R' -> System.out.print("\u001B[30m" + current + "    " + "\u001B[37m");
                    case 'H' -> System.out.print("\u001B[33m" + current + "    ");
                    case 'S' -> System.out.print("\u001B[32m" + current + "    ");
                    case 'F' -> System.out.print("\u001B[31m" + current + "    ");
                    case 'M' -> System.out.print("\u001B[35m" + current + "    ");
                    case 'K' -> System.out.print("\u001B[34m" + current + "    ");
                }
            }
            System.out.println('\n');
        }
        System.out.println('\n');
    }

    public static void printPath(State state, Map<State, State> parentMap) {
        if (state == null) {
            return;
        }
        // Print Path
        printPath(parentMap.get(state), parentMap);
        // Print Goal
        Operation.printBoard(state.getBoard());
        System.out.println("\n");
    }

}