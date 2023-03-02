package darklyz.fixmods.mixin;

import dev.mrsterner.bewitchmentplus.common.entity.YewBroomEntity;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.network.packet.TogglePressingForwardPacket;
import moriyashiine.bewitchment.common.registry.BWComponents;
import moriyashiine.bewitchment.common.registry.BWEntityTypes;
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
public class TogglePressingForwardPacketMixin {
    private static boolean NO_YEW_BROOM = false;

    private static boolean isYewBroomVehicle(ServerPlayerEntity entity){
        try { return !NO_YEW_BROOM && entity.getVehicle() instanceof YewBroomEntity; }
        catch (NoClassDefFoundError e) { NO_YEW_BROOM = true; return false; }
    }

    @Inject(at = @At("HEAD"), method = "handle", cancellable = true)
    private static void handleYewBroom(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender, CallbackInfo info) {
        try {
            boolean pressingForward = buf.readBoolean();
            if (pressingForward && BewitchmentAPI.getFamiliar(player) != BWEntityTypes.OWL && !isYewBroomVehicle(player)) {
                if (!BewitchmentAPI.drainMagic(player, 1, true)) {
                    return;
                }

                if (player.age % 60 == 0) {
                    BewitchmentAPI.drainMagic(player, 1, false);
                }
            }
            BWComponents.BROOM_USER_COMPONENT.get(player).setPressingForward(pressingForward);
        } catch (IndexOutOfBoundsException ignored) {} finally { info.cancel(); }
    }
}
