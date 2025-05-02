package com.utad.proyectoFinal.characterSystem.images;

public abstract class EquipmentDecorator implements CharacterImage {
    protected CharacterImage decoratedImage;

    protected EquipmentDecorator(CharacterImage decoratedImage) {
        
        this.decoratedImage = decoratedImage;
    }
}
