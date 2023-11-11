package Logic;

import Structure.Methods.Operation;
import Structure.Component.Position;
import Structure.Component.State;

public class MainLogic {

    State state;

    Operation boardOp;

    public MainLogic(State state) {
        this.state = state;
        this.boardOp = new Operation(state);
    }

    public boolean HandleMoveAction(Position dirpos, String direct) {
        char dirposElement = this.boardOp.getElement(dirpos);
        // Check if the Farmer there is no rock in Direct Position to go to
        if (dirposElement != 'R') {
            // Check if there is a seed Or SeedWithHole in Direct Position to go to
            if (dirposElement == 'S' || dirposElement == 'K') {
                return this.MoveSeedAndFarmer(dirpos, direct);
            } else {
                this.MoveFarmer(this.state.getFarmerpos(), direct);
                return true;
            }
        } else {
            return false;
        }
    }

    public void MoveFarmer(Position position, String direction) {
        switch (direction) {
            case "UP" -> {
                Position dirpos = new Position(position.getX() - 1, position.getY());
                HandleMoveFarmer(dirpos, position);
            }

            case "DOWN" -> {
                Position dirpos = new Position(position.getX() + 1, position.getY());
                HandleMoveFarmer(dirpos, position);
            }
            case "RIGHT" -> {
                Position dirpos = new Position(position.getX(), position.getY() + 1);
                HandleMoveFarmer(dirpos, position);
            }
            case "LEFT" -> {
                Position dirpos = new Position(position.getX(), position.getY() - 1);
                HandleMoveFarmer(dirpos, position);
            }
        }
    }

    public void HandleMoveFarmer(Position dirpos, Position position) {
        char dirposElement = this.boardOp.getElement(dirpos);
        char oldElement = this.boardOp.getElement(position);

        // Farmer go to Hole
        if (dirposElement == 'H') {
            this.boardOp.AddFarmerWithHole(dirpos);
        }//Farmer go to Empty
        else {
            this.boardOp.AddFarmer(dirpos);
        }

        // Farmer Leave Hole
        if (oldElement == 'M') {
            this.boardOp.AddHole(position);
        }// Farmer Leave Empty
        else {
            this.boardOp.AddEmpty(position);
        }
    }

    public boolean MoveSeedAndFarmer(Position position, String direction) {
        switch (direction) {
            case "UP" -> {
                Position dirpos = new Position(position.getX() - 1, position.getY());
                if (checkValidNeighbor(this.boardOp.getElement(dirpos))) {
                    HandleMoveSeedAndFarmer(position, dirpos, "UP");
                    return true;
                } else {
                    return false;
                }
            }
            case "DOWN" -> {
                Position dirpos = new Position(position.getX() + 1, position.getY());
                if (checkValidNeighbor(this.boardOp.getElement(dirpos))) {
                    HandleMoveSeedAndFarmer(position, dirpos, "DOWN");
                    return true;
                } else {
                    return false;
                }
            }
            case "RIGHT" -> {
                Position dirpos = new Position(position.getX(), position.getY() + 1);
                if (checkValidNeighbor(this.boardOp.getElement(dirpos))) {
                    HandleMoveSeedAndFarmer(position, dirpos, "RIGHT");
                    return true;
                } else {
                    return false;
                }
            }
            case "LEFT" -> {
                Position dirpos = new Position(position.getX(), position.getY() - 1);
                if (checkValidNeighbor(this.boardOp.getElement(dirpos))) {
                    HandleMoveSeedAndFarmer(position, dirpos, "LEFT");
                    return true;
                } else {
                    return false;
                }
            }
            default -> {
                return false;
            }
        }
    }

    public void HandleMoveSeedAndFarmer(Position position, Position dirpos, String direction) {
        char dirposElement = this.boardOp.getElement(dirpos);
        char oldElement = this.boardOp.getElement(position);
        if (checkValidElement(dirposElement)) {
            // Seed go to Hole
            if (dirposElement == 'H') {
                this.boardOp.AddSeedWithHole(dirpos);
                this.boardOp.AddEmpty(position);
                this.MoveFarmer(this.state.getFarmerpos(), direction);
            }//Seed go to Empty
            if (dirposElement == 'E') {
                this.boardOp.AddSeed(dirpos);
                this.boardOp.AddEmpty(position);
                this.MoveFarmer(this.state.getFarmerpos(), direction);
            }
            // Seed Leave Hole
            if (oldElement == 'K') {
                this.boardOp.AddFarmerWithHole(position);
            }
            this.state.DeleteFromSeedList(position);
        } else {
            System.out.println("Can not move Up , There is Rock in this place , Or Board Limit");
        }
    }

    public boolean checkGoal() {
        return (this.state.SeedList.isEmpty());
    }

    public boolean checkLose() {
        int isLose = 0;
        for (int i = 0; i < this.state.SeedList.size(); i++) {
            isLose = 0;
            Position currSeedpos = this.state.getSeedList(i);
            char up = this.boardOp.getElement(new Position(currSeedpos.getX() - 1, currSeedpos.getY()));
            char down = this.boardOp.getElement(new Position(currSeedpos.getX() + 1, currSeedpos.getY()));
            char left = this.boardOp.getElement(new Position(currSeedpos.getX(), currSeedpos.getY() - 1));
            char right = this.boardOp.getElement(new Position(currSeedpos.getX(), currSeedpos.getY() + 1));
            if (!checkValidElement(up)) {
                isLose++;
            }
            if (!checkValidElement(down)) {
                isLose++;
            }
            if (!checkValidElement(left)) {
                isLose++;
            }
            if (!checkValidElement(right)) {
                isLose++;
            }
            if (isLose == 2) {
                if ((!checkValidElement(up) && !checkValidElement(left)) || (!checkValidElement(up) && !checkValidElement(right))) {
                    return true;
                }
            }
            if (isLose == 2) {
                if ((!checkValidElement(down) && !checkValidElement(left)) || (!checkValidElement(down) && !checkValidElement(right))) {
                    return true;
                }
            }
        }
        return (isLose > 2);
    }

    public boolean checkFinalState() {
        if (checkGoal()) {
            System.out.println("\u001B[32m You Win");
            return true;
        } else if (checkLose()) {
            System.out.println("\u001B[31m Game Over");
            return true;
        }
        return false;
    }

    public static State AutoPlay(State tryingstate, String input) {
        MainLogic logic = new MainLogic(tryingstate);
        Position farmerpos = tryingstate.getFarmerpos();
        switch (input) {
            case "W" -> {
                Position dirpos = new Position(farmerpos.getX() - 1, farmerpos.getY());
                if (logic.HandleMoveAction(dirpos, "UP")) {
                    return new State(tryingstate);
                } else {
                    return null;
                }
            }
            case "S" -> {
                Position dirpos = new Position(farmerpos.getX() + 1, farmerpos.getY());
                if (logic.HandleMoveAction(dirpos, "DOWN")) {
                    return new State(tryingstate);
                } else {
                    return null;
                }
            }
            case "D" -> {
                Position dirpos = new Position(farmerpos.getX(), farmerpos.getY() + 1);
                if (logic.HandleMoveAction(dirpos, "RIGHT")) {
                    return new State(tryingstate);
                } else {
                    return null;
                }
            }
            case "A" -> {
                Position dirpos = new Position(farmerpos.getX(), farmerpos.getY() - 1);
                if (logic.HandleMoveAction(dirpos, "LEFT")) {
                    return new State(tryingstate);
                } else {
                    return null;
                }
            }
            default -> {
                return tryingstate;
            }
        }
    }

    public boolean checkValidElement(char element) {
        return (element != 'R' && element != 'K');
    }

    public boolean checkValidNeighbor(char element) {
        return (element == 'E' || element == 'H');
    }

}