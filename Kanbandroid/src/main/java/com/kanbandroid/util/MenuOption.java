package com.kanbandroid.util;

import java.util.HashMap;
import java.util.Map;

public enum MenuOption {
    LOGOUT("Déconnexion");

    public String getMenu() {
        return menu;
    }

    private String menu;
    private static Map<String, MenuOption> mapValues = new HashMap<String, MenuOption>();

    static {
        for (MenuOption option : MenuOption.values()) {
            mapValues.put(option.menu, option);
        }
    }

    public static MenuOption getMenuOption(String menu) {
        return mapValues.get(menu);
    }

    MenuOption(String menu) {
        this.menu = menu;
    }
}
