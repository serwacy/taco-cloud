package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findById(final String id);
    Ingredient saveIngredient(final Ingredient ingredient);
}
