package model;

public class EventChoice {

    private final String choice;
    private final int cashChange;
    private final int moraleChange;
    private final int coffeeChange;
    private final int bugsChange;

    public EventChoice(Builder builder) {
        this.choice = builder.description;
        this.cashChange = builder.cashChange;
        this.moraleChange = builder.moraleChange;
        this.coffeeChange = builder.coffeeChange;
        this.bugsChange = builder.bugsChange;
    }

    public String getChoice() {
        return choice;
    }

    public int getCashChange() {
        return cashChange;
    }

    public int getMoraleChange() {
        return moraleChange;
    }

    public int getCoffeeChange() {
        return coffeeChange;
    }

    public int getBugsChange() {
        return bugsChange;
    }

    public static Builder builder(String description) {
        return new Builder(description);
    }

    public static class Builder {
        private final String description;
        private int cashChange;
        private int moraleChange;
        private int coffeeChange;
        private int bugsChange;

        public Builder(String description) {
            this.description = description;
        }

        public Builder cash(int cashChange) {
            this.cashChange = cashChange;
            return this;
        }

        public Builder morale(int moraleChange) {
            this.moraleChange = moraleChange;
            return this;
        }

        public Builder coffee(int coffeeChange) {
            this.coffeeChange = coffeeChange;
            return this;
        }

        public Builder bugs(int bugsChange) {
            this.bugsChange = bugsChange;
            return this;
        }

        public EventChoice build() {
            return new EventChoice(this);
        }
    }
}
