package darklyz.fixmods.mixin;

import moriyashiine.bewitchment.api.entity.BroomEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.api.VRData;
import org.vivecraft.gameplay.VRPlayer;

@Mixin(value = VRPlayer.class, remap = false)
public class VRPlayerMixin {
	private boolean NO_BROOM = false;

	private boolean isBroomVehicle(ClientPlayerEntity entity) {
		try { return !NO_BROOM && entity.getVehicle() instanceof BroomEntity; }
		catch (NoClassDefFoundError e) { NO_BROOM = true; return false; }
	}

	@Inject(at = @At("HEAD"), method = "doPermanantLookOverride", cancellable = true)
	private void doPermanantLookOverride(ClientPlayerEntity entity, VRData data, CallbackInfo info) {
		if (entity != null && isBroomVehicle(entity)) {
			entity.setYaw(data.getController(1).getYaw());
			entity.setHeadYaw(entity.getYaw());
			entity.setPitch(-data.getController(1).getPitch());

			info.cancel();
		}
	}
}
