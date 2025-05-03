package com.utad.proyectoFinal.characterSystem.images;

public abstract class ImageDecorator implements CharacterImage {
    protected final CharacterImage inner;

    protected ImageDecorator(CharacterImage inner) {
        this.inner = inner;
    }
}
