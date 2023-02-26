package darklyz.fixmods.mixin;

import moriyashiine.bewitchment.api.entity.BroomEntity;
import net.minecraft.client.network.ClientPlayerEntity;
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
		if (entity != null && entity.getVehicle() instanceof BroomEntity) {
			entity.setYaw(data.getController(1).getYaw());
			entity.setHeadYaw(entity.getYaw());
			entity.setPitch(-data.getController(1).getPitch());

			info.cancel();
		}
	}
}
