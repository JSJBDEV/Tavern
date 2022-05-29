package ace.actually.tavern;

import ace.actually.tavern.block.BarBlock;
import ace.actually.tavern.block.BarBlockEntity;
import ace.actually.tavern.block.TwentyOnesBlock;
import ace.actually.tavern.block.TwentyOnesBlockEntity;
import ace.actually.tavern.item.BrewItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tavern implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		registerBlocks();
		registerBlockEntities();
		registerItems();
		LOGGER.info("Hello Fabric world!");
	}

	public static final Item MUG_ITEM = new Item(new Item.Settings().group(ItemGroup.FOOD));
	public static final BrewItem BEER_BREW = new BrewItem(new Item.Settings().group(ItemGroup.FOOD));
	public static final Item BLANK_CARD = new Item(new Item.Settings().group(ItemGroup.MISC));
	private void registerItems()
	{
		Registry.register(Registry.ITEM,new Identifier("tavern","mug"),MUG_ITEM);
		Registry.register(Registry.ITEM,new Identifier("tavern","beer_mug"),BEER_BREW);
		Registry.register(Registry.ITEM,new Identifier("tavern","blank_card"),BLANK_CARD);
	}

	public static final BarBlock BAR_BLOCK = new BarBlock(AbstractBlock.Settings.of(Material.BAMBOO));
	public static final TwentyOnesBlock TWENTY_ONES_BLOCK = new TwentyOnesBlock(AbstractBlock.Settings.of(Material.BAMBOO));

	private void registerBlocks()
	{
		Registry.register(Registry.BLOCK,new Identifier("tavern","bar_block"),BAR_BLOCK);
		Registry.register(Registry.BLOCK,new Identifier("tavern","twenty_ones_block"),TWENTY_ONES_BLOCK);
	}

	public static BlockEntityType<BarBlockEntity> BAR_BLOCK_ENTITY;
	public static BlockEntityType<TwentyOnesBlockEntity> TWENTY_ONES_BLOCK_ENTITY;
	private void registerBlockEntities()
	{
		BAR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,"tavern:bar_block_entity", FabricBlockEntityTypeBuilder.create(BarBlockEntity::new,BAR_BLOCK).build(null));
		TWENTY_ONES_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,"tavern:twenty_ones_entity", FabricBlockEntityTypeBuilder.create(TwentyOnesBlockEntity::new,TWENTY_ONES_BLOCK).build(null));
	}
}
