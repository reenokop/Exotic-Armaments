package net.reenokop.exoticarmaments.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.reenokop.exoticarmaments.item.SaiItem;

import java.util.UUID;

public record DualWieldPacket(UUID uuid, boolean leftHandRule) implements CustomPayload {

    public static final CustomPayload.Id<DualWieldPacket> DUAL_WIELD_ID =
            new CustomPayload.Id<>(Identifier.of("exoticarmaments", "left_hand_rule"));

    public static final PacketCodec<RegistryByteBuf, DualWieldPacket> DUAL_WIELD_CODEC =
            PacketCodec.of(DualWieldPacket::write, DualWieldPacket::read);

    public static DualWieldPacket read(RegistryByteBuf registryByteBuf) {
        return new DualWieldPacket(registryByteBuf.readUuid(), registryByteBuf.readBoolean());
    }

    public void write(RegistryByteBuf registryByteBuf) {
        registryByteBuf.writeUuid(uuid);
        registryByteBuf.writeBoolean(leftHandRule);
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return DUAL_WIELD_ID;
    }

    public static void apply(DualWieldPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            SaiItem.leftHandRule.put(packet.uuid, packet.leftHandRule);
        });
    }

}
