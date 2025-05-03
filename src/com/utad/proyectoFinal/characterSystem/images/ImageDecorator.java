package com.utad.proyectoFinal.characterSystem.images;

import java.awt.Image;

public abstract class ImageDecorator implements CharacterImage {
    protected final CharacterImage inner;

    protected ImageDecorator(CharacterImage inner) {
        this.inner = inner;
    }
    
    @Override
    public Image getCompleteImage() {
        // By default, get the inner component's complete image
        return inner.getCompleteImage();
    }
}
