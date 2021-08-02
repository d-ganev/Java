package bg.sofia.uni.fmi.mjt.shopping;


import bg.sofia.uni.fmi.mjt.shopping.item.Item;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class MapShoppingCart implements ShoppingCart {

    private final Map<Item, Integer> items;
    private final ProductCatalog catalog;

    public MapShoppingCart(ProductCatalog catalog) {
        items = new HashMap<>();
        this.catalog = catalog;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    @Override
    public Collection<Item> getUniqueItems() {
        Collection<Item> uniqueItems = new HashSet<>();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            uniqueItems.add(entry.getKey());
        }
        return uniqueItems;
    }

    @Override
    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The input is null!");
        }
        if (items.containsKey(item)) {
            Integer occurrences = items.get(item);
            items.put(item, occurrences + 1);
        } else {
            final int initialValue = 1;
            items.put(item, initialValue);
        }
    }

    @Override
    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The input is null!");
        }

        if (!items.containsKey(item)) {
            throw new ItemNotFoundException(String.format("Item with ID: %s is not found", item.getId()));
        }

        Integer occurrences = items.get(item);
        items.remove(item);
        if (occurrences != 1) {
            items.put(item, occurrences - 1);
        }
    }

    @Override
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            ProductInfo info = catalog.getProductInfo(entry.getKey().getId());
            total += info.price() * entry.getValue();
        }
        return total;
    }


    @Override
    public Collection<Item> getSortedItems() {
        Map<Item, Integer> sortedItems = new TreeMap<>(new Comparator<Item>() {
            public int compare(Item item1, Item item2) {
                return items.get(item2).compareTo(items.get(item1));  //descending order
            }
        });
        sortedItems.putAll(items);
        return sortedItems.keySet();
    }

}