package fc.potluck.model;

public final class FoodItem {

    public enum FoodType {
        FOOD,
        BEVERAGE,
        ADULT_BEVERAGE
    }

    private final String name;
    private final double cost;
    private final FoodType type;

    public FoodItem(String name, double cost, FoodType type) {
        this.name = name;
        this.cost = cost;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public FoodType getType() {
        return type;
    }

}
