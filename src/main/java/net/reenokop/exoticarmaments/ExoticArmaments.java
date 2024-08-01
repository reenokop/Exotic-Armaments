package net.reenokop.exoticarmaments;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.reenokop.exoticarmaments.event.ModLootTables;
import net.reenokop.exoticarmaments.item.ModItems;
import net.reenokop.exoticarmaments.item.SaiItem;
import net.reenokop.exoticarmaments.network.DualWieldPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.reenokop.exoticarmaments.item.ModItems.*;

public class ExoticArmaments implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("exoticarmaments");

	@Override
	public void onInitialize() {

		ModItems.initializeItems();

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.WOODEN_HOE, WOODEN_MACHETE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.STONE_HOE, STONE_MACHETE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.IRON_HOE, IRON_MACHETE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.GOLDEN_HOE, GOLDEN_MACHETE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.DIAMOND_HOE, DIAMOND_MACHETE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.addAfter(Items.NETHERITE_HOE, NETHERITE_MACHETE));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> content.addBefore(Items.WOODEN_SWORD,
                WOODEN_MACHETE, STONE_MACHETE, IRON_MACHETE, GOLDEN_MACHETE, DIAMOND_MACHETE, NETHERITE_MACHETE,
                WOODEN_LONG_SWORD, STONE_LONG_SWORD, IRON_LONG_SWORD, GOLDEN_LONG_SWORD, DIAMOND_LONG_SWORD, NETHERITE_LONG_SWORD,
                WOODEN_SAI, STONE_SAI, IRON_SAI, GOLDEN_SAI, DIAMOND_SAI, NETHERITE_SAI));

		SaiItem.sais.add(WOODEN_SAI);
		SaiItem.sais.add(STONE_SAI);
		SaiItem.sais.add(IRON_SAI);
		SaiItem.sais.add(GOLDEN_SAI);
		SaiItem.sais.add(DIAMOND_SAI);
		SaiItem.sais.add(NETHERITE_SAI);

		PayloadTypeRegistry.playC2S().register(DualWieldPacket.DUAL_WIELD_ID, DualWieldPacket.DUAL_WIELD_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(DualWieldPacket.DUAL_WIELD_ID, DualWieldPacket::apply);

		ModLootTables.modifyLootTables();

		LOGGER.info("HINT: You can press Attack to deliver a strike against your enemies!");
	}

}