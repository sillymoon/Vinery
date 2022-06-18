package daniking.vinery;

import daniking.vinery.block.GrapeBush;
import daniking.vinery.block.RockBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectRegistry {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    // Red Grapes
    public static final Block RED_GRAPE_BUSH = register("red_grape_bush", new GrapeBush(getBushSettings(), GrapeBush.Type.RED), false);
    public static final Item RED_GRAPE = register("red_grape", new Item(getSettings().food(FoodComponents.SWEET_BERRIES)));
    public static final Item RED_GRAPE_SEEDS = register("red_grape_seeds", new GrapeBushSeedItem(RED_GRAPE_BUSH, getSettings(), GrapeBush.Type.RED));

    // White Grapes
    public static final Block WHITE_GRAPE_BUSH = register("white_grape_bush", new GrapeBush(getBushSettings(), GrapeBush.Type.WHITE), false);
    public static final Item WHITE_GRAPE = register("white_grape", new Item(getSettings().food(FoodComponents.SWEET_BERRIES)));
    public static final Item WHITE_GRAPE_SEEDS = register("white_grape_seeds", new GrapeBushSeedItem(WHITE_GRAPE_BUSH, getSettings(), GrapeBush.Type.WHITE));
    // Misc
    public static final Block ROCKS = register("rocks", new RockBlock(getRockSettings()));
    public static final Block ROCKS_VARIANT_B = register("rocks_variant_b", new RockBlock(getRockSettings()), false);

    public static final Block ROCKS_VARIANT_C = register("rocks_variant_c", new RockBlock(getRockSettings()), false);

//    public static final Block WHITE_VINE = register("white_vine", new WhiteVineBlock(getVineSettings()));
//    public static final Block POT = register("pot", new Block(FabricBlockSettings.of(Material.STONE).strength(2.5F).requiresTool()));
//    public static final Block BARREL = register("barrel", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.5F).requiresTool()));
//    public static final Block GRAPEVINE = register("grapevine", new GrapeBush(getVineSettings()));

    private static <T extends Block> T register(String path, T block) {
        return register(path, block, true);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem) {
        final Identifier id = new VineryIdentifier(path);
        BLOCKS.put(id, block);
        if (registerItem) {
            ITEMS.put(id, new BlockItem(block, getSettings()));
        }
        return block;
    }

    private static <T extends Item> T register(String path, T item) {
        final Identifier id = new VineryIdentifier(path);
        ITEMS.put(id, item);
        return item;
    }


    public static void init() {
        for (Map.Entry<Identifier, Block> entry : BLOCKS.entrySet()) {
            Registry.register(Registry.BLOCK, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Identifier, Item> entry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, entry.getKey(), entry.getValue());
        }
    }

    private static Item.Settings getSettings() {
        return new Item.Settings().group(Vinery.CREATIVE_TAB);
    }

    private static Block.Settings getVineSettings() {
        return FabricBlockSettings.copyOf(Blocks.VINE);
    }

    private static Block.Settings getBushSettings() {
        return FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH);
    }

    private static AbstractBlock.Settings getRockSettings() {
        return FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque();

    }

    public static Map<Identifier, Block> getBlocks() {
        return Collections.unmodifiableMap(BLOCKS);
    }

    public static Map<Identifier, Item> getItems() {
        return Collections.unmodifiableMap(ITEMS);
    }

}