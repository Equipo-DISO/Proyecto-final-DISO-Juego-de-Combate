package com.utad.proyectoFinal.ui.combat;

public enum Action {
    ATACK("⚔️", 0),
    HEAL("❤️", 0),
    CONCENTRATE("➕", 0),
    RUN("🏃", -1),
    PROTECTED("🛡️", 0),
    BREAK("❌", 0),
    ;

    private String actionIcon;
    private Action (String action, int trim) {
        if (trim < 0) this.actionIcon = action;
        else this.actionIcon = "" + action.charAt(trim);
    }
    public String getIcon() {
        return actionIcon;
    }
}
