package darklyz.fixmods.mixin;

import moriyashiine.bewitchment.common.network.packet.TogglePressingForwardPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TogglePressingForwardPacket.class, remap = false)
abstract class TogglePressingForwardPacketMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;readBoolean()Z", ordinal = 0), method = "handle", cancellable = true)
	private static void handleYewBroom(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender, CallbackInfo ci) {
		if (!buf.isReadable()) ci.cancel();
	}
}