package menu;

import java.util.Scanner;
import java.util.function.Consumer;

record MenuOption(String optionName, Consumer<Scanner> action) {
}
