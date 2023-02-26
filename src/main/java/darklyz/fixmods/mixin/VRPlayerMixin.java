package darklyz.fixmods.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.api.VRData;
import org.vivecraft.gameplay.VRPlayer;

@Mixin(VRPlayer.class)
public class VRPlayerMixin {
	@Inject(at = @At("HEAD"), method = "doPermanantLookOverride", remap = false, cancellable = true)
	private void doPermanantLookOverride(ClientPlayerEntity entity, VRData data, CallbackInfo info) {
		if (entity != null) {
			Entity vehicle = entity.getVehicle();
			if (vehicle != null) {
				entity.sendChatMessage(vehicle.getType().toString(), null);
			}
		}
	}
}
