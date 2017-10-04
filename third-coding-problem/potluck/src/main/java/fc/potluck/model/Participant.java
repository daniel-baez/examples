package fc.potluck.model;

import java.util.List;

public final class Participant {

    private final String name;
    private final int age;
    private final List<FoodItem> foodItems;

    public Participant(String name, int age, List<FoodItem> foodItems) {
        this.name = name;
        this.age = age;
        this.foodItems = foodItems;
    }


    public String getName() {
        return name;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public int getAge() {
        return age;
    }

    public double getContribution(final boolean byob) {
        return foodItems.stream()
                .filter(it -> {
                    final boolean b = it.getType() == FoodItem.FoodType.ADULT_BEVERAGE && byob;
                    return !b;
                })
                .mapToDouble(FoodItem::getCost)
                .sum();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Participant that = (Participant) o;

        if (age != that.age) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}
