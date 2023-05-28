package menu;

import java.util.Scanner;
import java.util.function.Consumer;
class MenuOption {
    private final String optionName;
    private final Consumer<Scanner> action;

    public MenuOption(String optionName, Consumer<Scanner> action) {
        this.optionName = optionName;
        this.action = action;
    }

    public String getOptionName() {
        return optionName;
    }

    public Consumer<Scanner> getAction() {
        return action;
    }
}
