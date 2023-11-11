import Action.Play;
import Structure.Component.State;

import java.awt.event.KeyAdapter;
import java.util.Scanner;

public class Main extends KeyAdapter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Level From 1 -> 7 To Start Game");
        int level = scanner.nextInt();

        State state = new State().CreateInitialState(level);
        Play play = new Play();

        while (level > 7 || level < 1) {
            System.out.println("Invalid Level Number , Select 1 -> 7");
            level = scanner.nextInt();
            state = new State().CreateInitialState(level);
        }

        System.out.println("Select Number The Way To Solve:\n1- User Play\n2- BFS\n3- DFS");
        int way = scanner.nextInt();

        while (way > 3 || way < 1) {
            System.out.println("Invalid Way Number , Select 1 -> 3");
            way = scanner.nextInt();
        }

        switch (way) {
            case 1 -> {
                while (state != null) {
                    state = play.UserPlay(state);
                }
            }
            case 2 -> play.BFS(state);
            case 3 -> play.DFS(state);
        }

    }
}