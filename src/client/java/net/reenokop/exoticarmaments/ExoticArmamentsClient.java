package net.reenokop.exoticarmaments;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.Item;
import net.reenokop.exoticarmaments.network.CooldownPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ExoticArmamentsClient implements ClientModInitializer {

	public static AbstractClientPlayerEntity clientPlayer;
	public static Map<UUID, Set<Item>> cooldownFromSai = new HashMap<>();

	@Override
	public void onInitializeClient() {

		ClientPlayNetworking.registerGlobalReceiver(CooldownPacket.COOLDOWN_ID, (payload, context) -> {
			context.client().execute(() -> {
				cooldownFromSai = payload.cooldowns();
			});
		});
	}

}