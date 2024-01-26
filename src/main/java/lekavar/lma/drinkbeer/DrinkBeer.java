package lekavar.lma.drinkbeer;

import lekavar.lma.drinkbeer.block.*;
import lekavar.lma.drinkbeer.block.entity.BartendingTableEntity;
import lekavar.lma.drinkbeer.block.entity.BeerBarrelEntity;
import lekavar.lma.drinkbeer.block.entity.MixedBeerEntity;
import lekavar.lma.drinkbeer.block.entity.TradeboxEntity;
import lekavar.lma.drinkbeer.event.WakeUpEvent;
import lekavar.lma.drinkbeer.item.BeerMugBlockItem;
import lekavar.lma.drinkbeer.item.MixedBeerBlockItem;
import lekavar.lma.drinkbeer.item.RecipeBoardBlockItem;
import lekavar.lma.drinkbeer.item.SpiceBlockItem;
import lekavar.lma.drinkbeer.networking.NetWorking;
import lekavar.lma.drinkbeer.screen.BartendingTableScreenHandler;
import lekavar.lma.drinkbeer.screen.BeerBarrelScreenHandler;
import lekavar.lma.drinkbeer.screen.TradeBoxScreenHandler;
import lekavar.lma.drinkbeer.statuseffects.DrunkFrostWalkerStatusEffect;
import lekavar.lma.drinkbeer.statuseffects.DrunkStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class DrinkBeer implements ModInitializer {
    public static DrinkBeer INSTANCE;

    public DrinkBeer() {
        INSTANCE = this;
    }

    public static final ItemGroup DRINK_BEER = FabricItemGroup.builder()
            .icon(() -> new ItemStack(DrinkBeer.BEER_MUG))
            .displayName(Text.translatable("itemGroup.drinkbeer.beer"))
            .build();

    public static final ItemGroup DRINK_BEER_GENERAL = FabricItemGroup.builder()
            .icon(() -> new ItemStack(DrinkBeer.BEER_BARREL))
            .displayName(Text.translatable("itemGroup.drinkbeer.general"))
            .build();


    /*Beer group*/
    /*------------------------------------------------------------------------------------------------------------------*/
    //Beers
    public static final Block BEER_MUG = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_BLAZE_STOUT = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_BLAZE_MILK_STOUT = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_APPLE_LAMBIC = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_SWEET_BERRY_KRIEK = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_HAARS_ICEY_PALE_LAGER = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_PUMPKIN_KVASS = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_NIGHT_HOWL_KVASS = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block BEER_MUG_FROTHY_PINK_EGGNOG = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    //Mixed beer
    public static BlockEntityType<MixedBeerEntity> MIXED_BEER_ENTITY;
    public static final Block MIXED_BEER = new MixedBeerBlock(FabricBlockSettings.create().hardness(1.0f));

    /*General group*/
    /*------------------------------------------------------------------------------------------------------------------*/
    //Empty beer mug
    public static final Block EMPTY_BEER_MUG = new BeerMugBlock(FabricBlockSettings.create().hardness(1.0f));
    //Keg
    public static BlockEntityType<BeerBarrelEntity> BEER_BARREL_ENTITY;
    public static final Block BEER_BARREL = new BeerBarrelBlock(FabricBlockSettings.create().hardness(2.0f));
    public static final Identifier BEER_BARREL_ID = new Identifier("drinkbeer", "beer_barrel");
    public static final ScreenHandlerType<BeerBarrelScreenHandler> BEER_BARREL_SCREEN_HANDLER = new ScreenHandlerType<>(BeerBarrelScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES);

    static {
        Registry.register(Registries.SCREEN_HANDLER, BEER_BARREL_ID, BEER_BARREL_SCREEN_HANDLER);
    }

    //Bartending table
    public static BlockEntityType<BartendingTableEntity> BARTENDING_TABLE_ENTITY;
    public static final Block BARTENDING_TABLE_NORMAL = new BartendingTableBlock(FabricBlockSettings.create().hardness(2.0f));
    public static final Identifier BARTENDING_TABLE_ID = new Identifier("drinkbeer", "bartending_table");
    public static final ScreenHandlerType<BartendingTableScreenHandler> BARTENDING_TABLE_SCREEN_HANDLER = new ScreenHandlerType<>(BartendingTableScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES);

    static {
        Registry.register(Registries.SCREEN_HANDLER, BARTENDING_TABLE_ID, BARTENDING_TABLE_SCREEN_HANDLER);
    }

    //Trade box
    public static BlockEntityType<TradeboxEntity> TRADE_BOX_ENTITY;
    public static final TradeboxBlock TRADE_BOX_NORMAL = new TradeboxBlock((FabricBlockSettings.create().hardness(2.0f)));
    public static final Identifier TRADE_BOX_ID = new Identifier("drinkbeer", "trade_box");
    public static final ScreenHandlerType<TradeBoxScreenHandler> TRADE_BOX_SCREEN_HANDLER = new ScreenHandlerType<>(TradeBoxScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES);

    static {
        Registry.register(Registries.SCREEN_HANDLER, TRADE_BOX_ID, TRADE_BOX_SCREEN_HANDLER);
    }

    //Call bells
    public static final Block IRON_CALL_BELL = new CallBellBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block GOLDEN_CALL_BELL = new CallBellBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block LEKAS_CALL_BELL = new CallBellBlock(FabricBlockSettings.create().hardness(1.0f));
    //Recipe boards
    public static final Block RECIPE_BOARD_PACKAGE = new RecipeBoardPackageBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_BLAZE_STOUT = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_BLAZE_MILK_STOUT = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_APPLE_LAMBIC = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_SWEET_BERRY_KRIEK = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_HAARS_ICEY_PALE_LAGER = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_PUMPKIN_KVASS = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_NIGHT_HOWL_KVASS = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block RECIPE_BOARD_BEER_MUG_FROTHY_PINK_EGGNOG = new BeerRecipeBoardBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block[] RECIPE_BOARD_PACKAGE_CONTENT = {
            RECIPE_BOARD_BEER_MUG,
            RECIPE_BOARD_BEER_MUG_BLAZE_STOUT,
            RECIPE_BOARD_BEER_MUG_BLAZE_MILK_STOUT,
            RECIPE_BOARD_BEER_MUG_APPLE_LAMBIC,
            RECIPE_BOARD_BEER_MUG_SWEET_BERRY_KRIEK,
            RECIPE_BOARD_BEER_MUG_HAARS_ICEY_PALE_LAGER,
            RECIPE_BOARD_BEER_MUG_PUMPKIN_KVASS,
            RECIPE_BOARD_BEER_MUG_NIGHT_HOWL_KVASS,
            RECIPE_BOARD_BEER_MUG_FROTHY_PINK_EGGNOG
    };
    //Spices
    public static final Block SPICE_BLAZE_PAPRIKA = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_DRIED_EGLIA_BUD = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_SMOKED_EGLIA_BUD = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_AMETHYST_NIGELLA_SEEDS = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_CITRINE_NIGELLA_SEEDS = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_ICE_MINT = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_ICE_PATCHOULI = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_STORM_SHARDS = new SpiceBlock(FabricBlockSettings.create().hardness(0.5f));
    public static final Block SPICE_ROASTED_RED_PINE_NUTS = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_GLACE_GOJI_BERRIES = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_FROZEN_PERSIMMON = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_ROASTED_PECANS = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_SILVER_NEEDLE_WHITE_TEA = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_GOLDEN_CINNAMON_POWDER = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));
    public static final Block SPICE_DRIED_SELAGINELLA = new SpiceBlock(FabricBlockSettings.create().hardness(1.0f));

    /*Other*/
    /*------------------------------------------------------------------------------------------------------------------*/
    //Sounds
    public static final Identifier DRINKING_BEER = new Identifier("drinkbeer:drinking_beer");
    public static SoundEvent DRINKING_BEER_EVENT = SoundEvent.of(DRINKING_BEER);
    public static final Identifier POURING = new Identifier("drinkbeer:pouring");
    public static SoundEvent POURING_EVENT = SoundEvent.of(POURING);
    public static final Identifier POURING_CHRISTMAS = new Identifier("drinkbeer:pouring_christmas");
    public static SoundEvent POURING_CHRISTMAS_EVENT = SoundEvent.of(POURING_CHRISTMAS);
    public static final Identifier IRON_CALL_BELL_TINKLE = new Identifier("drinkbeer:iron_call_bell_tinkle");
    public static SoundEvent IRON_CALL_BELL_TINKLE_EVENT = SoundEvent.of(IRON_CALL_BELL_TINKLE);
    public static final Identifier GOLDEN_CALL_BELL_TINKLE = new Identifier("drinkbeer:golden_call_bell_tinkle");
    public static SoundEvent GOLDEN_CALL_BELL_TINKLE_EVENT = SoundEvent.of(GOLDEN_CALL_BELL_TINKLE);
    public static final Identifier LEKAS_CALL_BELL_TINKLE = new Identifier("drinkbeer:lekas_call_bell_tinkle");
    public static SoundEvent LEKAS_CALL_BELL_TINKLE_EVENT = SoundEvent.of(LEKAS_CALL_BELL_TINKLE);
    public static final Identifier UNPACKING = new Identifier("drinkbeer:unpacking");
    public static SoundEvent UNPACKING_EVENT = SoundEvent.of(UNPACKING);
    public static final Identifier NIGHT_HOWL0 = new Identifier("drinkbeer:night_howl0");
    public static SoundEvent NIGHT_HOWL0_EVENT = SoundEvent.of(NIGHT_HOWL0);
    public static final Identifier NIGHT_HOWL1 = new Identifier("drinkbeer:night_howl1");
    public static SoundEvent NIGHT_HOWL1_EVENT = SoundEvent.of(NIGHT_HOWL1);
    public static final Identifier NIGHT_HOWL2 = new Identifier("drinkbeer:night_howl2");
    public static SoundEvent NIGHT_HOWL2_EVENT = SoundEvent.of(NIGHT_HOWL2);
    public static final Identifier NIGHT_HOWL3 = new Identifier("drinkbeer:night_howl3");
    public static SoundEvent NIGHT_HOWL3_EVENT = SoundEvent.of(NIGHT_HOWL3);
    public static SoundEvent[] NIGHT_HOWL_EVENT = {NIGHT_HOWL0_EVENT, NIGHT_HOWL1_EVENT, NIGHT_HOWL2_EVENT, NIGHT_HOWL3_EVENT};
    public static final Identifier BARTENDING_TABLE_OPEN = new Identifier("drinkbeer:bartending_table_open");
    public static SoundEvent BARTENDING_TABLE_OPEN_EVENT = SoundEvent.of(BARTENDING_TABLE_OPEN);
    public static final Identifier BARTENDING_TABLE_CLOSE = new Identifier("drinkbeer:bartending_table_close");
    public static SoundEvent BARTENDING_TABLE_CLOSE_EVENT = SoundEvent.of(BARTENDING_TABLE_CLOSE);
    public static final Identifier TRADEBOX_OPEN = new Identifier("drinkbeer:tradebox_open");
    public static SoundEvent TRADEBOX_OPEN_EVENT = SoundEvent.of(TRADEBOX_OPEN);
    public static final Identifier TRADEBOX_CLOSE = new Identifier("drinkbeer:tradebox_close");
    public static SoundEvent TRADEBOX_CLOSE_EVENT = SoundEvent.of(TRADEBOX_CLOSE);
    //Status effects
    public static final StatusEffect DRUNK_FROST_WALKER = new DrunkFrostWalkerStatusEffect();
    public static final StatusEffect DRUNK = new DrunkStatusEffect();
    //Particles
    public static final DefaultParticleType MIXED_BEER_DEFAULT = FabricParticleTypes.simple();
    public static final DefaultParticleType CALL_BELL_TINKLE_PAW = FabricParticleTypes.simple();

    @Override
    public void onInitialize() {
        /*Group registry*/
        Registry.register(Registries.ITEM_GROUP, new Identifier("drinkbeer", "beer"), DRINK_BEER);
        Registry.register(Registries.ITEM_GROUP, new Identifier("drinkbeer", "general"), DRINK_BEER_GENERAL);

        /*Beer group*/
        /*------------------------------------------------------------------------------------------------------------------*/
        //Beers
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug"), BEER_MUG);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug"), new BeerMugBlockItem(BEER_MUG, new StatusEffectInstance(StatusEffects.HASTE, 1200), 2));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_blaze_stout"), BEER_MUG_BLAZE_STOUT);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_blaze_stout"), new BeerMugBlockItem(BEER_MUG_BLAZE_STOUT, new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1800), 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_blaze_milk_stout"), BEER_MUG_BLAZE_MILK_STOUT);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_blaze_milk_stout"), new BeerMugBlockItem(BEER_MUG_BLAZE_MILK_STOUT, new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 2400), 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_apple_lambic"), BEER_MUG_APPLE_LAMBIC);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_apple_lambic"), new BeerMugBlockItem(BEER_MUG_APPLE_LAMBIC, new StatusEffectInstance(StatusEffects.REGENERATION, 300), 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_sweet_berry_kriek"), BEER_MUG_SWEET_BERRY_KRIEK);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_sweet_berry_kriek"), new BeerMugBlockItem(BEER_MUG_SWEET_BERRY_KRIEK, new StatusEffectInstance(StatusEffects.REGENERATION, 400), 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_haars_icey_pale_lager"), BEER_MUG_HAARS_ICEY_PALE_LAGER);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_haars_icey_pale_lager"), new BeerMugBlockItem(BEER_MUG_HAARS_ICEY_PALE_LAGER, new StatusEffectInstance(DrinkBeer.DRUNK_FROST_WALKER, 1200), 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_pumpkin_kvass"), BEER_MUG_PUMPKIN_KVASS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_pumpkin_kvass"), new BeerMugBlockItem(BEER_MUG_PUMPKIN_KVASS, null, 9));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_night_howl_kvass"), BEER_MUG_NIGHT_HOWL_KVASS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_night_howl_kvass"), new BeerMugBlockItem(BEER_MUG_NIGHT_HOWL_KVASS, null, 4));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_mug_frothy_pink_eggnog"), BEER_MUG_FROTHY_PINK_EGGNOG);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_mug_frothy_pink_eggnog"), new BeerMugBlockItem(BEER_MUG_FROTHY_PINK_EGGNOG, new StatusEffectInstance(StatusEffects.ABSORPTION, 2400), 2));
        //Mixed beer
        MIXED_BEER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "drinkbeer:mixed_beer_entity", FabricBlockEntityTypeBuilder.create(MixedBeerEntity::new, MIXED_BEER).build(null));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "mixed_beer"), MIXED_BEER);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "mixed_beer"), new MixedBeerBlockItem());
        ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("drinkbeer", "beer"))).register(content -> {
                content.add(BEER_MUG);
                content.add(BEER_MUG_BLAZE_STOUT);
                content.add(BEER_MUG_BLAZE_MILK_STOUT);
                content.add(BEER_MUG_APPLE_LAMBIC);
                content.add(BEER_MUG_SWEET_BERRY_KRIEK);
                content.add(BEER_MUG_HAARS_ICEY_PALE_LAGER);
                content.add(BEER_MUG_PUMPKIN_KVASS);
                content.add(BEER_MUG_NIGHT_HOWL_KVASS);
                content.add(BEER_MUG_FROTHY_PINK_EGGNOG);
                content.add(MIXED_BEER);

        });
        /*General group*/
        /*------------------------------------------------------------------------------------------------------------------*/
        //Empty beer mug
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "empty_beer_mug"), EMPTY_BEER_MUG);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "empty_beer_mug"), new BlockItem(EMPTY_BEER_MUG, new Item.Settings().maxCount(16)));

        //Keg
        BEER_BARREL_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "drinkbeer:beer_barrel_entity", FabricBlockEntityTypeBuilder.create(BeerBarrelEntity::new, BEER_BARREL).build(null));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "beer_barrel"), BEER_BARREL);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "beer_barrel"), new BlockItem(BEER_BARREL, new Item.Settings()));
        //Bartending table
        BARTENDING_TABLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "drinkbeer:bartending_table_entity", FabricBlockEntityTypeBuilder.create(BartendingTableEntity::new, BARTENDING_TABLE_NORMAL).build(null));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "bartending_table_normal"), BARTENDING_TABLE_NORMAL);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "bartending_table_normal"), new BlockItem(BARTENDING_TABLE_NORMAL, new Item.Settings()));
        //Trade box
        TRADE_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "drinkbeer:trade_box_entity", FabricBlockEntityTypeBuilder.create(TradeboxEntity::new, TRADE_BOX_NORMAL).build(null));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "trade_box_normal"), TRADE_BOX_NORMAL);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "trade_box_normal"), new BlockItem(TRADE_BOX_NORMAL, new Item.Settings()));
        //Call bells
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "iron_call_bell"), IRON_CALL_BELL);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "iron_call_bell"), new BlockItem(IRON_CALL_BELL, new Item.Settings().maxCount(64)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "golden_call_bell"), GOLDEN_CALL_BELL);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "golden_call_bell"), new BlockItem(GOLDEN_CALL_BELL, new Item.Settings().maxCount(64)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "lekas_call_bell"), LEKAS_CALL_BELL);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "lekas_call_bell"), new BlockItem(LEKAS_CALL_BELL, new Item.Settings().maxCount(64)));
        //Recipe boards
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_package"), RECIPE_BOARD_PACKAGE);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_package"), new BlockItem(RECIPE_BOARD_PACKAGE, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug"), RECIPE_BOARD_BEER_MUG);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_blaze_stout"), RECIPE_BOARD_BEER_MUG_BLAZE_STOUT);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_blaze_stout"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_BLAZE_STOUT, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_blaze_milk_stout"), RECIPE_BOARD_BEER_MUG_BLAZE_MILK_STOUT);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_blaze_milk_stout"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_BLAZE_MILK_STOUT, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_apple_lambic"), RECIPE_BOARD_BEER_MUG_APPLE_LAMBIC);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_apple_lambic"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_APPLE_LAMBIC, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_sweet_berry_kriek"), RECIPE_BOARD_BEER_MUG_SWEET_BERRY_KRIEK);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_sweet_berry_kriek"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_SWEET_BERRY_KRIEK, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_haars_icey_pale_lager"), RECIPE_BOARD_BEER_MUG_HAARS_ICEY_PALE_LAGER);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_haars_icey_pale_lager"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_HAARS_ICEY_PALE_LAGER, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_pumpkin_kvass"), RECIPE_BOARD_BEER_MUG_PUMPKIN_KVASS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_pumpkin_kvass"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_PUMPKIN_KVASS, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_night_howl_kvass"), RECIPE_BOARD_BEER_MUG_NIGHT_HOWL_KVASS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_night_howl_kvass"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_NIGHT_HOWL_KVASS, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "recipe_board_beer_mug_frothy_pink_eggnog"), RECIPE_BOARD_BEER_MUG_FROTHY_PINK_EGGNOG);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "recipe_board_beer_mug_frothy_pink_eggnog"), new RecipeBoardBlockItem(RECIPE_BOARD_BEER_MUG_FROTHY_PINK_EGGNOG, new Item.Settings().maxCount(1)));

        //Spices
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_blaze_paprika"), new SpiceBlockItem(SPICE_BLAZE_PAPRIKA, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_blaze_paprika"), SPICE_BLAZE_PAPRIKA);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_dried_eglia_bud"), new SpiceBlockItem(SPICE_DRIED_EGLIA_BUD, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_dried_eglia_bud"), SPICE_DRIED_EGLIA_BUD);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_smoked_eglia_bud"), new SpiceBlockItem(SPICE_SMOKED_EGLIA_BUD, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_smoked_eglia_bud"), SPICE_SMOKED_EGLIA_BUD);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_amethyst_nigella_seeds"), new SpiceBlockItem(SPICE_AMETHYST_NIGELLA_SEEDS, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_amethyst_nigella_seeds"), SPICE_AMETHYST_NIGELLA_SEEDS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_citrine_nigella_seeds"), new SpiceBlockItem(SPICE_CITRINE_NIGELLA_SEEDS, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_citrine_nigella_seeds"), SPICE_CITRINE_NIGELLA_SEEDS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_ice_mint"), new SpiceBlockItem(SPICE_ICE_MINT, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_ice_mint"), SPICE_ICE_MINT);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_ice_patchouli"), new SpiceBlockItem(SPICE_ICE_PATCHOULI, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_ice_patchouli"), SPICE_ICE_PATCHOULI);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_storm_shards"), new SpiceBlockItem(SPICE_STORM_SHARDS, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_storm_shards"), SPICE_STORM_SHARDS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_roasted_red_pine_nuts"), new SpiceBlockItem(SPICE_ROASTED_RED_PINE_NUTS, null, 2));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_roasted_red_pine_nuts"), SPICE_ROASTED_RED_PINE_NUTS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_glace_goji_berries"), new SpiceBlockItem(SPICE_GLACE_GOJI_BERRIES, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_glace_goji_berries"), SPICE_GLACE_GOJI_BERRIES);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_frozen_persimmon"), new SpiceBlockItem(SPICE_FROZEN_PERSIMMON, null, 1));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_frozen_persimmon"), SPICE_FROZEN_PERSIMMON);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_roasted_pecans"), new SpiceBlockItem(SPICE_ROASTED_PECANS, null, 2));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_roasted_pecans"), SPICE_ROASTED_PECANS);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_silver_needle_white_tea"), new SpiceBlockItem(SPICE_SILVER_NEEDLE_WHITE_TEA, null, 2));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_silver_needle_white_tea"), SPICE_SILVER_NEEDLE_WHITE_TEA);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_golden_cinnamon_powder"), new SpiceBlockItem(SPICE_GOLDEN_CINNAMON_POWDER, null, 2));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_golden_cinnamon_powder"), SPICE_GOLDEN_CINNAMON_POWDER);
        Registry.register(Registries.ITEM, new Identifier("drinkbeer", "spice_dried_selaginella"), new SpiceBlockItem(SPICE_DRIED_SELAGINELLA, null, 2));
        Registry.register(Registries.BLOCK, new Identifier("drinkbeer", "spice_dried_selaginella"), SPICE_DRIED_SELAGINELLA);
        ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("drinkbeer", "general"))).register(content ->{
            content.add(EMPTY_BEER_MUG);
            content.add(BEER_BARREL);
            content.add(BARTENDING_TABLE_NORMAL);
            content.add(TRADE_BOX_NORMAL);
            content.add(IRON_CALL_BELL);
            content.add(GOLDEN_CALL_BELL);
            content.add(LEKAS_CALL_BELL);
            content.add(RECIPE_BOARD_PACKAGE);
            content.add(RECIPE_BOARD_BEER_MUG);
            content.add(RECIPE_BOARD_BEER_MUG_BLAZE_MILK_STOUT);
            content.add(RECIPE_BOARD_BEER_MUG_BLAZE_STOUT);
            content.add(RECIPE_BOARD_BEER_MUG_APPLE_LAMBIC);
            content.add(RECIPE_BOARD_BEER_MUG_SWEET_BERRY_KRIEK);
            content.add(RECIPE_BOARD_BEER_MUG_HAARS_ICEY_PALE_LAGER);
            content.add(RECIPE_BOARD_BEER_MUG_PUMPKIN_KVASS);
            content.add(RECIPE_BOARD_BEER_MUG_NIGHT_HOWL_KVASS);
            content.add(RECIPE_BOARD_BEER_MUG_FROTHY_PINK_EGGNOG);
            content.add(SPICE_BLAZE_PAPRIKA);
            content.add(SPICE_DRIED_EGLIA_BUD);
            content.add(SPICE_SMOKED_EGLIA_BUD);
            content.add(SPICE_AMETHYST_NIGELLA_SEEDS);
            content.add(SPICE_CITRINE_NIGELLA_SEEDS);
            content.add(SPICE_ICE_MINT);
            content.add(SPICE_ICE_PATCHOULI);
            content.add(SPICE_STORM_SHARDS);
            content.add(SPICE_ROASTED_RED_PINE_NUTS);
            content.add(SPICE_GLACE_GOJI_BERRIES);
            content.add(SPICE_FROZEN_PERSIMMON);
            content.add(SPICE_ROASTED_PECANS);
            content.add(SPICE_SILVER_NEEDLE_WHITE_TEA);
            content.add(SPICE_GOLDEN_CINNAMON_POWDER);
            content.add(SPICE_DRIED_SELAGINELLA);
        });
        /*Other*/
        /*------------------------------------------------------------------------------------------------------------------*/
        //Sounds
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.DRINKING_BEER, DRINKING_BEER_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.POURING, POURING_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.POURING_CHRISTMAS, POURING_CHRISTMAS_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.IRON_CALL_BELL_TINKLE, IRON_CALL_BELL_TINKLE_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.GOLDEN_CALL_BELL_TINKLE, GOLDEN_CALL_BELL_TINKLE_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.LEKAS_CALL_BELL_TINKLE, LEKAS_CALL_BELL_TINKLE_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.NIGHT_HOWL0, NIGHT_HOWL0_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.NIGHT_HOWL1, NIGHT_HOWL1_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.NIGHT_HOWL2, NIGHT_HOWL2_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.NIGHT_HOWL3, NIGHT_HOWL3_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.UNPACKING, UNPACKING_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.BARTENDING_TABLE_OPEN, BARTENDING_TABLE_OPEN_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.BARTENDING_TABLE_CLOSE, BARTENDING_TABLE_CLOSE_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.TRADEBOX_OPEN, TRADEBOX_OPEN_EVENT);
        Registry.register(Registries.SOUND_EVENT, DrinkBeer.TRADEBOX_CLOSE, TRADEBOX_CLOSE_EVENT);
        //Status effects
        Registry.register(Registries.STATUS_EFFECT, new Identifier("drinkbeer", "drunk_frost_walker"), DRUNK_FROST_WALKER);
        Registry.register(Registries.STATUS_EFFECT, new Identifier("drinkbeer", "drunk"), DRUNK);
        //Events
        EntitySleepEvents.STOP_SLEEPING.register(WakeUpEvent::onStopSleeping);
        //Particles
        Registry.register(Registries.PARTICLE_TYPE, new Identifier("drinkbeer", "mixed_beer_default"), MIXED_BEER_DEFAULT);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier("drinkbeer", "call_bell_tinkle_paw"), CALL_BELL_TINKLE_PAW);

        //Net working
        NetWorking.init();

        //Print good list
        //TradeBoxTestManager.PrintGoodList();
    }
}
