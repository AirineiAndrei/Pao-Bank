import menu.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        Scanner scanner = new Scanner(System.in);
        menu.displayMenu(scanner);
    }
}
