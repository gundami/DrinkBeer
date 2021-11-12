package lekavar.lma.drinkbeer.util;

import lekavar.lma.drinkbeer.DrinkBeer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum Beers {
    BEER_MUG(1, DrinkBeer.BEER_MUG.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(24000)
            .addMaterial(3, Items.WHEAT).addMaterial(Items.WATER_BUCKET)),
    BEER_MUG_BLAZE_STOUT(2, DrinkBeer.BEER_MUG_BLAZE_STOUT.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(12000)
            .addMaterial(2, Items.WHEAT).addMaterial(Items.WATER_BUCKET).addMaterial(Items.BLAZE_POWDER)),
    BEER_MUG_BLAZE_MILK_STOUT(3, DrinkBeer.BEER_MUG_BLAZE_MILK_STOUT.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(18000)
            .addMaterial(Items.WHEAT).addMaterial(Items.BLAZE_POWDER).addMaterial(Items.SUGAR).addMaterial(Items.WATER_BUCKET)),
    BEER_MUG_APPLE_LAMBIC(4, DrinkBeer.BEER_MUG_APPLE_LAMBIC.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(24000)
            .addMaterial(2, Items.WHEAT).addMaterial(Items.APPLE).addMaterial(Items.WATER_BUCKET)),
    BEER_MUG_SWEET_BERRY_KRIEK(5, DrinkBeer.BEER_MUG_SWEET_BERRY_KRIEK.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(24000)
            .addMaterial(2, Items.WHEAT).addMaterial(Items.SWEET_BERRIES).addMaterial(Items.WATER_BUCKET)),
    BEER_MUG_HAARS_ICEY_PALE_LAGER(6, DrinkBeer.BEER_MUG_HAARS_ICEY_PALE_LAGER.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(24000)
            .addMaterial(3, Items.WHEAT).addMaterial(Items.ICE, Items.BLUE_ICE, Items.PACKED_ICE)),
    BEER_MUG_PUMPKIN_KVASS(7, DrinkBeer.BEER_MUG_PUMPKIN_KVASS.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(12000)
            .addMaterial(2, Items.BREAD).addMaterial(Items.PUMPKIN).addMaterial(Items.WATER_BUCKET)),
    BEER_MUG_FROTHY_PINK_EGGNOG(8, DrinkBeer.BEER_MUG_FROTHY_PINK_EGGNOG.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(12000)
            .addMaterial(Items.WHEAT).addMaterial(Items.BEETROOT).addMaterial(Items.SUGAR_CANE).addMaterial(Items.MILK_BUCKET)),
    BEER_MUG_NIGHT_HOWL_KVASS(9, DrinkBeer.BEER_MUG_NIGHT_HOWL_KVASS.asItem(), new BeerRecipe()
            .setBeerResultNum(4).setBrewingTime(18000)
            .addMaterial(2, Items.BREAD).addMaterial(Items.BONE).addMaterial(Items.WATER_BUCKET));

    private static final Beers DEFAULT_BEER = Beers.BEER_MUG;
    public static final int EMPTY_ID = 0;

    private final int id;
    private final Item beerItem;
    private final BeerRecipe beerRecipe;

    Beers(int id, Item beerItem, BeerRecipe beerRecipe) {
        this.id = id;
        this.beerItem = beerItem;
        this.beerRecipe = beerRecipe;
        this.beerRecipe.setBeerResult(this.beerItem);
    }

    public int getId() {
        return this.id;
    }

    public Item getBeerItem() {
        return this.beerItem;
    }

    public BeerRecipe getBeerRecipe() {
        return this.beerRecipe;
    }

    public static Beers byId(int id) {
        Beers[] beers = values();
        for (Beers beer : beers) {
            if (beer.id == id) {
                return beer;
            }
        }
        return DEFAULT_BEER;
    }

    public static Beers byItem(Item beerItem) {
        Beers[] beers = values();
        for (Beers beer : beers) {
            if (beer.beerItem.equals(beerItem)) {
                return beer;
            }
        }
        return DEFAULT_BEER;
    }
}
