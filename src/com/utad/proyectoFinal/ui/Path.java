package com.utad.proyectoFinal.ui;

public enum Path {
    
    COLOR("Color", 100, 50),
    PLAYER("Guy", 82, 100);
    
    public static final Path.ColorEnum[] colorsList = {Path.ColorEnum.GREEN, Path.ColorEnum.ORANGE, Path.ColorEnum.PINK, Path.ColorEnum.BLACK,
        Path.ColorEnum.YELLOW, Path.ColorEnum.RED, Path.ColorEnum.BLUE, Path.ColorEnum.WHITE};
    private String identifier;
    private Integer width;
    private Integer height;

    private Path(String identifier, Integer width, Integer height) {
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
