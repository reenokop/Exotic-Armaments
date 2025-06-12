package net.reenokop.exoticarmaments.network;

import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;

public record CooldownPacket(Map<UUID, Set<Item>> cooldowns) implements CustomPayload {

    public static final CustomPayload.Id<CooldownPacket> COOLDOWN_ID =
            new CustomPayload.Id<>(Identifier.of("exoticarmaments", "cooldown"));

    public static final PacketCodec<RegistryByteBuf, CooldownPacket> COOLDOWN_CODEC =
            PacketCodec.of(CooldownPacket::write, CooldownPacket::read);

    public static CooldownPacket read(RegistryByteBuf buf) {
        int mapSize = buf.readVarInt();
        Map<UUID, Set<Item>> map = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            UUID uuid = buf.readUuid();
            int setSize = buf.readVarInt();
            Set<Item> itemSet = new HashSet<>();
            for (int j = 0; j < setSize; j++) {
                Item item = Registries.ITEM.get(buf.readIdentifier());
                itemSet.add(item);
            }
            map.put(uuid, itemSet);
        }
        return new CooldownPacket(map);
    }

    public void write(RegistryByteBuf buf) {
        buf.writeVarInt(cooldowns.size());
        for (Map.Entry<UUID, Set<Item>> entry : cooldowns.entrySet()) {
            buf.writeUuid(entry.getKey());
            buf.writeVarInt(entry.getValue().size());
            for (Item item : entry.getValue()) {
                buf.writeIdentifier(Registries.ITEM.getId(item));
            }
        }
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return COOLDOWN_ID;
    }
}

