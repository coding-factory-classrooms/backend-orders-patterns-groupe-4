package org.example.models.items.goodies;

public class SaberFigurine extends Goodie {
    private Dimension dimensions;

    public SaberFigurine() {
        this.setName("Saber");
        this.dimensions = new Dimension(12 , 30);
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    public class Dimension {
        public float width;
        public float height;

        public Dimension(float width, float height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString() {
            return width + " x " + height;
        }
    }

    @Override
    public String toString() {
        return "SaberFigurine " + this.dimensions.toString();
    }
}
