package bg.sofia.uni.fmi.mjt.shopping;

import bg.sofia.uni.fmi.mjt.shopping.item.Apple;
import bg.sofia.uni.fmi.mjt.shopping.item.Chocolate;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapShoppingCartTest {

    private static final double DELTA = 0.01;

    @Mock
    ProductCatalog productCatalogMock;

    @InjectMocks
    MapShoppingCart listShoppingCart;

    @Test
    public void testGetUniqueItemsWithoutRepetitiveItems() {
        Item apple = new Apple("ID");
        Item chocolate = new Chocolate("ID");
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(chocolate);
        Set<Item> expectedResult = new HashSet<>();
        expectedResult.add(apple);
        expectedResult.add(chocolate);
        assertEquals("Getting unique elements should work properly.",
                expectedResult, listShoppingCart.getUniqueItems());
    }

    @Test
    public void testGetUniqueItemsWhenConsistingRepetitiveItems() {
        Item apple = new Apple("ID");
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(apple);
        Set<Item> expectedResult = new HashSet<>();
        expectedResult.add(apple);
        assertEquals("Getting unique elements should work properly.",
                expectedResult, listShoppingCart.getUniqueItems());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemNull() {
        listShoppingCart.removeItem(null);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveItemNotFound() {
        listShoppingCart.removeItem(new Apple("ID"));
    }

    @Test
    public void testRemoveItemWhenRepeating() {
        Item apple = new Apple("ID");
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(apple);
        listShoppingCart.removeItem(apple);
        Map<Item, Integer> expectedList = new HashMap<>();
        final Integer occurrences = 1;
        expectedList.put(apple, occurrences);
        assertEquals("Removing elements should work properly.", expectedList, listShoppingCart.getItems());
    }

    @Test
    public void testRemoveItemWhenNotRepeating() {
        Item apple = new Apple("ID");
        listShoppingCart.addItem(apple);
        listShoppingCart.removeItem(apple);
        Map<Item, Integer> expectedList = new HashMap<>();
        assertEquals("Removing elements should work properly.", expectedList, listShoppingCart.getItems());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemNull() {
        Apple apple = null;
        listShoppingCart.addItem(apple);
    }

    @Test
    public void testAddItemWhenItemsRepeating() {
        listShoppingCart.addItem(new Apple("ID"));
        listShoppingCart.addItem(new Apple("ID"));
        listShoppingCart.addItem(new Chocolate("ID"));
        listShoppingCart.addItem(new Chocolate("ID"));
        Map<Item, Integer> items = new HashMap<>();
        final Integer occurrences = 2;
        items.put(new Apple("ID"), occurrences);
        items.put(new Chocolate("ID"), occurrences);
        assertEquals("getTotal should return 0, when there is no items.", items, listShoppingCart.getItems());
    }

    @Test
    public void testAddItemWithoutItemsRepeating() {
        listShoppingCart.addItem(new Apple("ID"));
        listShoppingCart.addItem(new Chocolate("ID"));
        Map<Item, Integer> items = new HashMap<>();
        items.put(new Apple("ID"), 1);
        items.put(new Chocolate("ID"), 1);
        assertEquals("getTotal should return 0, when there is no items.", items, listShoppingCart.getItems());
    }

    @Test
    public void testGetTotalZeroCase() {
        assertEquals("getTotal should return 0, when there is no items.", 0.0, listShoppingCart.getTotal(), DELTA);
    }

    @Test
    public void testGetTotalNonzeroCase() {
        listShoppingCart.addItem(new Apple("ID1"));
        listShoppingCart.addItem(new Chocolate("ID2"));

        final double price1 = 10.0;
        ProductInfo productInfo1 = new ProductInfo("name", "description", price1);
        when(productCatalogMock.getProductInfo("ID1"))
                .thenReturn(productInfo1);

        final double price2 = 15.0;
        ProductInfo productInfo2 = new ProductInfo("name", "description", price2);
        when(productCatalogMock.getProductInfo("ID2"))
                .thenReturn(productInfo2);

        final double expectedResult = 25.0;
        assertEquals("Getting Total sum should work properly.", expectedResult, listShoppingCart.getTotal(), DELTA);
    }

    private boolean haveTheSameOrderedElements(List<Item> collection1, Collection<Item> collection2) {
        if (collection1.size() != collection2.size()) {
            return false;
        }
        int index = 0;
        for (Item item : collection2) {
            if (!collection1.get(index).equals(item)) {
                return false;
            }
            index++;
        }
        return true;
    }

    @Test
    public void testGetSortedItems() {
        Apple apple = new Apple("ID");
        Chocolate chocolate = new Chocolate("ID");
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(apple);
        listShoppingCart.addItem(chocolate);
        listShoppingCart.addItem(chocolate);
        listShoppingCart.addItem(chocolate);

        List<Item> expectedResult = new ArrayList<>();
        expectedResult.add(chocolate);
        expectedResult.add(apple);
        assertTrue("Getting items sorted, should work properly.",
                haveTheSameOrderedElements(expectedResult, listShoppingCart.getSortedItems()));
    }
}
