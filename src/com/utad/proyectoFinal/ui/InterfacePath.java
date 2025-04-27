package com.utad.proyectoFinal.ui;

public enum InterfacePath {
    
    COLOR("Color", 100, 50),
    PLAYER("Guy", 82, 100);
    
    public static final InterfacePath.ColorEnum[] colorsList = {InterfacePath.ColorEnum.GREEN, InterfacePath.ColorEnum.ORANGE, InterfacePath.ColorEnum.PINK, InterfacePath.ColorEnum.BLACK,
        InterfacePath.ColorEnum.YELLOW, InterfacePath.ColorEnum.RED, InterfacePath.ColorEnum.BLUE, InterfacePath.ColorEnum.WHITE};
    private String identifier;
    private Integer width;
    private Integer height;

    private InterfacePath(String identifier, Integer width, Integer height) {
        this.identifier = identifier;
        this.width = width;
        this.height = height;
    }

    public enum ColorEnum {
        GREEN("Green"),
        RED("Red"),
        BLUE("Blue"),
        ORANGE("Orange"),
        YELLOW("Yellow"),
        PINK("Pink"),
        BLACK("Black"),
        WHITE("White");

        protected String label;
        private ColorEnum(String color){
            this.label = color;
        };
    }

    public String getPath(ColorEnum color) {
        return "Files/img/" + color.label + this.identifier + ".png";
    }

    public Integer getDefWidth() {
        return width;
    }
    public Integer getDefHeight() {
        return height;
    }
}
