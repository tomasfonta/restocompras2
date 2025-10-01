package com.tf.restocompras.service;

import com.tf.restocompras.model.analysis.PriceComparison;
import com.tf.restocompras.model.analysis.PriceComparisonResponseDto;
import com.tf.restocompras.model.analysis.PriceComparisonResult;
import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.restaurant.Restaurant;
import com.tf.restocompras.model.unit.Unit;
import com.tf.restocompras.repository.ItemRepository;
import com.tf.restocompras.repository.RestaurantRepository;
import com.tf.restocompras.service.mapper.PriceComparisonMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisService {

    private final ItemRepository itemRepository;
    private final UnitService unitService;
    private final RestaurantRepository restaurantRepository;
    private final PriceComparisonMapper priceComparisonMapper;

    public AnalysisService(RestaurantRepository restaurantRepository, ItemRepository itemRepository, UnitService unitService, PriceComparisonMapper priceComparisonMapper) {
        this.restaurantRepository = restaurantRepository;
        this.itemRepository = itemRepository;
        this.unitService = unitService;
        this.priceComparisonMapper = priceComparisonMapper;
    }


    public PriceComparisonResponseDto performIngredientsPriceComparison(Long restaurantId) {
        Map<Ingredient, Item> alternatives = analyzeIngredientCosts(restaurantId);

        List<PriceComparisonResult> results = new ArrayList<>();

        for (Map.Entry<Ingredient, Item> entry : alternatives.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Item item = entry.getValue();

            if (item != null) {

                double priceDifferencePerServing = calculatePriceDifference(ingredient, item);

                double variable = ingredient.getPrice().doubleValue() - priceDifferencePerServing;

                double percentageSaving = variable * 100 / ingredient.getPrice().doubleValue();

                double monthlySavings = ingredient.getRecipe().getMonthlyServings() * priceDifferencePerServing;

                PriceComparisonResult priceComparisonResult = PriceComparisonResult.builder()
                        .monthlySavings(monthlySavings)
                        .priceDifferenceInPercentage(percentageSaving)
                        .item(item)
                        .ingredient(ingredient)
                        .build();

                results.add(priceComparisonResult);
            }
        }

        PriceComparison priceComparison = PriceComparison.builder()
                .results(results)
                .totalMonthlySavings(results.stream().map(PriceComparisonResult::monthlySavings)
                        .reduce(0.0, Double::sum))
                .build();

        return priceComparisonMapper.mapEntityToDto(priceComparison);
    }

    public double calculatePriceDifference(Ingredient ingredient, Item item) {

        Unit itemUnit = item.getUnit();
        Unit ingredientUnit = ingredient.getUnit();

        double conversionFactor;
        try {
            conversionFactor = unitService.getConversionFactor(itemUnit, ingredientUnit);
        } catch (IllegalArgumentException e) {
            System.out.println("Incompatible units: " + itemUnit + " and " + ingredientUnit);
            return 0;
        }

        double normalizedQuantity = item.getQuantity() * conversionFactor;

        double pricePerUnit = item.getPrice() / normalizedQuantity;

        double pricePerIngredientQuantity = pricePerUnit * ingredient.getQuantity().doubleValue();

        return ingredient.getPrice().doubleValue() - pricePerIngredientQuantity;
    }


    public Map<Ingredient, Item> analyzeIngredientCosts(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));


        List<Ingredient> restaurantIngredients = restaurant.getRecipes().stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .toList();

        Map<Ingredient, Item> alternatives = new HashMap<>();

        for (Ingredient ingredient : restaurantIngredients) {

            Item alternativeItem = null;

            List<Item> items = itemRepository.findByProduct(ingredient.getProduct());
            Unit ingredientUnit = ingredient.getUnit();
            List<Unit> family = unitService.getFamilyOfUnits(ingredientUnit);
            List<Item> filteredItems = items.stream()
                    .filter(item -> family.contains(item.getUnit()))
                    .toList();

            for (Item item : filteredItems) {

                if (alternativeItem == null && isItemCheaperThanIngredient(item, ingredient)) {
                    alternativeItem = item;
                }
                if (alternativeItem != null && isItemCheaperThanItem(item, alternativeItem)) {
                    alternativeItem = item;
                }
            }

            alternatives.put(ingredient, alternativeItem);
        }

        return alternatives;

    }


    public boolean isItemCheaperThanIngredient(Item item, Ingredient ingredient) {

        Unit itemUnit = item.getUnit();
        Unit ingredientUnit = ingredient.getUnit();

        double conversionFactor;
        try {
            conversionFactor = unitService.getConversionFactor(itemUnit, ingredientUnit);
        } catch (IllegalArgumentException e) {
            System.out.println("Incompatible units: " + itemUnit + " and " + ingredientUnit);
            return false;
        }

        double normalizedQuantity = item.getQuantity() * conversionFactor;

        double pricePerUnit = item.getPrice() / normalizedQuantity;

        double pricePerIngredientQuantity = pricePerUnit * ingredient.getQuantity().doubleValue();

        if (pricePerIngredientQuantity < ingredient.getPrice().doubleValue()) {
            System.out.println("The item is cheaper than the ingredient.");
            return true;
        } else {
            System.out.println("The item is more expensive than the ingredient.");
            return false;
        }
    }


    /**
     * Compara dos items y devuelve true si el itemOne es mas barato que el itemTwo
     *
     * @param itemOne
     * @param itemTwo
     * @return
     */
    public boolean isItemCheaperThanItem(Item itemOne, Item itemTwo) {


        double conversionFactor;
        try {
            conversionFactor = unitService.getConversionFactor(itemOne.getUnit(), itemTwo.getUnit());
        } catch (IllegalArgumentException e) {
            System.out.println("Incompatible units: " + itemOne.getUnit() + " and " + itemTwo.getUnit());
            return false;
        }

        double normalizedQuantity = itemOne.getQuantity() * conversionFactor;

        double pricePerUnit = itemOne.getPrice() / normalizedQuantity;

        double priceItemOne = pricePerUnit * itemTwo.getQuantity();

        if (priceItemOne < itemTwo.getPrice()) {
            System.out.println("The itemOne is cheaper than the itemTwo.");
            return true;
        } else {
            System.out.println("The itemOne more expensive than the itemTwo.");
            return false;
        }
    }


}
