package com.tf.restocompras.service;

import com.tf.restocompras.model.ingredient.Ingredient;
import com.tf.restocompras.model.item.Item;
import com.tf.restocompras.model.unit.Unit;
import com.tf.restocompras.repository.ItemRepository;
import com.tf.restocompras.repository.RestaurantRepository;
import com.tf.restocompras.service.mapper.PriceComparisonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AnalysisServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Spy
    private UnitService unitService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Spy
    private PriceComparisonMapper priceComparisonMapper;

    private AnalysisService analysisService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        analysisService = new AnalysisService(restaurantRepository, itemRepository, unitService, priceComparisonMapper);
    }

    @Test
    public void testExample() {

        Ingredient ingredient = Ingredient.builder()
                .price(BigDecimal.valueOf(20))
                .quantity(BigDecimal.valueOf(2000))
                .unit(Unit.G)
                .build();

        Item item = Item.builder()
                .price(100.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        boolean b = analysisService.isItemCheaperThanIngredient(item, ingredient);

        assertTrue(b);

    }

    @Test
    public void givenExpensiveItemThenReturnFalse() {

        Ingredient ingredient = Ingredient.builder()
                .price(BigDecimal.valueOf(20))
                .quantity(BigDecimal.valueOf(2000))
                .unit(Unit.G)
                .build();

        Item item = Item.builder()
                .price(500.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        boolean b = analysisService.isItemCheaperThanIngredient(item, ingredient);

        assertFalse(b);


    }

    @Test
    public void givenDifferentUnitFamilyThenReturnFalse() {

        Ingredient ingredient = Ingredient.builder()
                .price(BigDecimal.valueOf(20))
                .quantity(BigDecimal.valueOf(2000))
                .unit(Unit.ML)
                .build();

        Item item = Item.builder()
                .price(500.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        boolean b = analysisService.isItemCheaperThanIngredient(item, ingredient);

        assertFalse(b);
    }


    @Test
    public void givenSamePriceItemsThenReturnFalse() {
        Item itemOne = Item.builder()
                .price(500.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        Item itemTwo = Item.builder()
                .price(500.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        boolean b = analysisService.isItemCheaperThanItem(itemOne, itemTwo);

        assertFalse(b);
    }

    @Test
    public void givenItemOneExpensiveThanItemTwoThenReturnFalse() {
        Item itemOne = Item.builder()
                .price(5000.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        Item itemTwo = Item.builder()
                .price(500.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        boolean b = analysisService.isItemCheaperThanItem(itemOne, itemTwo);

        assertFalse(b);
    }

    @Test
    public void givenOneCheaperThanItemTwoThenReturnTrue() {
        Item itemOne = Item.builder()
                .price(1.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        Item itemTwo = Item.builder()
                .price(2.00)
                .quantity(20.00)
                .unit(Unit.KG)
                .build();

        boolean b = analysisService.isItemCheaperThanItem(itemOne, itemTwo);

        assertTrue(b);
    }


    @Test
    public void givenItemAndItemCalculatePriceDifference() {
        Ingredient ingredient = Ingredient.builder()
                .price(BigDecimal.valueOf(20))
                .quantity(BigDecimal.valueOf(2000))
                .unit(Unit.ML)
                .build();

        Item item = Item.builder()
                .price(19.00)
                .quantity(2000.0)
                .unit(Unit.ML)
                .build();

        double v = analysisService.calculatePriceDifference(ingredient, item);

        assertEquals(1.0, v);
    }

}