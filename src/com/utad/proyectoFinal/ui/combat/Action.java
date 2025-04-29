package com.utad.proyectoFinal.ui.combat;

public enum Action {
    ATACK("⚔️"),
    HEAL("❤️"),
    CONCENTRATE("➕"),
    RUN("🏃"),
    PROTECTED("🛡️"),
    BREAK("❌"),
    ;

    private String actionIcon;
    private Action (String action) {
        this.actionIcon = action;
    }
    public String getIcon() {
        return actionIcon;
    }
}
