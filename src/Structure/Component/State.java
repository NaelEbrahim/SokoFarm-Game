package Structure.Component;

import Structure.Methods.Operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {

    int rows, columns;

    public char[][] board;

    Position farmerpos;

    State parent = null;

    int cost = 0;

    List<State> path = new ArrayList<>();

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.getBoard());
    }

    public List<Position> SeedList = new ArrayList<>();

    public void setParentAndPath(State parent) {
        if (parent != null) {
            this.parent = parent;
            this.path.addAll(parent.getPath());
            this.path.add(this);
        }
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost + 1;
    }

    public State getParent() {
        return parent;
    }

    public List<State> getPath() {
        return path;
    }

    public Position getSeedList(int index) {
        return this.SeedList.get(index);
    }

    public List<Position> getSeedList() {
        return SeedList;
    }

    public char[][] getBoard() {
        return board;
    }

    public void DeleteFromSeedList(Position position) {
        this.SeedList.removeIf(element -> element.getX() == position.getX() && element.getY() == position.getY());
    }

    public State() {
    }

    public Position getFarmerpos() {
        return farmerpos;
    }

    public void setFarmerpos(Position farmerpos) {
        this.farmerpos = farmerpos;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public State(State state) {
        this.rows = state.getRows();
        this.columns = state.getColumns();
        this.board = new char[this.rows][this.columns];
        if (state.getParent() != null) {
            this.setParentAndPath(state.getParent());
            setCost(state.parent.getCost());
        }
        for (int i = 0; i < this.rows; i++) {
            System.arraycopy(state.board[i], 0, this.board[i], 0, this.columns);
        }
        this.farmerpos = state.getFarmerpos();
        this.SeedList = new ArrayList<>(state.getSeedList());
    }

    @Override
    public boolean equals(Object obj) {
        State obj2 = (State) obj;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        } else {
            return (
                    Arrays.deepEquals(this.getBoard(), obj2.getBoard()) &&
                            IsEqualPosition(this.getFarmerpos(), obj2.getFarmerpos())
            );
        }
    }

    public boolean IsEqualPosition(Position pos1, Position pos2) {
        return (pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY());
    }

    public State(int rows, int columns, char[][] board) {
        this.rows = rows;
        this.columns = columns;
        this.board = board;
        this.parent = null;
        this.cost = 0;
        this.path.add(this);
    }

    public State CreateInitialState(int level) {
        // This Method Will Implement Single Time

        State state;
        char[][] board;

        switch (level) {
            case 1 -> {
                // Level 1
                board = new char[][]{
                        {'R', 'R', 'R', 'E', 'E'},
                        {'R', 'H', 'R', 'E', 'E'},
                        {'R', 'E', 'R', 'R', 'R'},
                        {'R', 'E', 'S', 'E', 'R'},
                        {'R', 'E', 'E', 'H', 'R'},
                        {'R', 'E', 'S', 'E', 'R'},
                        {'R', 'R', 'F', 'R', 'R'},
                        {'E', 'R', 'R', 'R', 'E'}
                };
                // Level 1
                state = new State(8, 5, board);

                state.setFarmerpos(new Position(6, 2));

                state.SeedList.add(new Position(3, 2));
                state.SeedList.add(new Position(5, 2));
            }
            case 2 -> {
                // Level 2
                board = new char[][]{
                        {'R', 'R', 'R', 'R', 'R', 'R', 'E', 'E'},
                        {'R', 'E', 'E', 'E', 'E', 'R', 'R', 'R'},
                        {'R', 'E', 'S', 'S', 'E', 'E', 'E', 'R'},
                        {'R', 'R', 'R', 'H', 'E', 'E', 'R', 'R'},
                        {'E', 'R', 'F', 'H', 'E', 'E', 'R', 'E'},
                        {'E', 'R', 'R', 'R', 'R', 'R', 'R', 'E'},
                };
                //  Level 2
                state = new State(6, 8, board);

                state.setFarmerpos(new Position(4, 2));

                state.SeedList.add(new Position(2, 2));
                state.SeedList.add(new Position(2, 3));
            }
            case 3 -> {
                //Level 3
                board = new char[][]{
                        {'R', 'R', 'R', 'R', 'R'},
                        {'R', 'H', 'S', 'E', 'R'},
                        {'R', 'R', 'R', 'E', 'R'},
                        {'R', 'H', 'E', 'E', 'R'},
                        {'R', 'E', 'E', 'E', 'R'},
                        {'R', 'E', 'S', 'R', 'R'},
                        {'R', 'F', 'E', 'R', 'E'},
                        {'R', 'R', 'R', 'R', 'E'},
                };
                // Level 3
                state = new State(8, 5, board);

                state.setFarmerpos(new Position(6, 1));

                state.SeedList.add(new Position(1, 2));
                state.SeedList.add(new Position(5, 2));
            }
            case 4 -> {
                //  Level 4
                board = new char[][]{
                        {'E', 'E', 'R', 'R', 'R', 'R', 'E'},
                        {'E', 'E', 'R', 'E', 'E', 'R', 'R'},
                        {'R', 'R', 'R', 'S', 'E', 'E', 'R'},
                        {'R', 'E', 'H', 'S', 'E', 'E', 'R'},
                        {'R', 'E', 'R', 'E', 'E', 'E', 'R'},
                        {'R', 'E', 'R', 'H', 'E', 'E', 'R'},
                        {'R', 'F', 'R', 'R', 'R', 'R', 'R'},
                        {'R', 'R', 'R', 'E', 'E', 'E', 'E'},
                };
                // Level 4
                state = new State(8, 7, board);

                state.setFarmerpos(new Position(6, 1));

                state.SeedList.add(new Position(2, 3));
                state.SeedList.add(new Position(3, 3));
            }
            case 5 -> {
                // Level 5
                board = new char[][]{
                        {'E', 'E', 'E', 'R', 'R', 'R', 'R'},
                        {'E', 'E', 'E', 'R', 'E', 'E', 'R'},
                        {'E', 'E', 'R', 'R', 'S', 'E', 'R'},
                        {'E', 'E', 'R', 'H', 'F', 'E', 'R'},
                        {'R', 'R', 'R', 'E', 'S', 'E', 'R'},
                        {'R', 'H', 'E', 'E', 'E', 'R', 'R'},
                        {'R', 'R', 'R', 'R', 'R', 'R', 'E'},
                };
                // Level 5
                state = new State(7, 7, board);

                state.setFarmerpos(new Position(3, 4));

                state.SeedList.add(new Position(2, 4));
                state.SeedList.add(new Position(4, 4));
            }
            case 6 -> {
                // Level 6
                board = new char[][]{
                        {'R', 'R', 'R', 'R', 'R', 'E'},
                        {'R', 'E', 'E', 'H', 'R', 'E'},
                        {'R', 'E', 'S', 'E', 'R', 'R'},
                        {'R', 'E', 'S', 'F', 'H', 'R'},
                        {'R', 'R', 'R', 'R', 'R', 'R'},
                };
                //    Level 6
                state = new State(5, 6, board);

                state.setFarmerpos(new Position(3, 3));

                state.SeedList.add(new Position(2, 2));
                state.SeedList.add(new Position(3, 2));
            }
            case 7 -> {
                // Level 7
                board = new char[][]{
                        {'E', 'E', 'E', 'E', 'R', 'R', 'R'},
                        {'R', 'R', 'R', 'R', 'R', 'H', 'R'},
                        {'R', 'E', 'E', 'H', 'S', 'E', 'R'},
                        {'R', 'E', 'S', 'E', 'R', 'E', 'R'},
                        {'R', 'R', 'R', 'E', 'E', 'F', 'R'},
                        {'E', 'E', 'R', 'R', 'R', 'R', 'R'},
                };
                // Level 7
                state = new State(6, 7, board);

                state.setFarmerpos(new Position(4, 5));

                state.SeedList.add(new Position(2, 4));
                state.SeedList.add(new Position(3, 2));
            }
            default -> {
                return null;
            }
        }

        Operation.printBoard(board);

        return state;
    }

}