package com.limeshulkerbox.youreafishnow.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class CanBreatheInWaterMixin extends LivingEntity {
	protected CanBreatheInWaterMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override()
	public boolean canBreatheInWater() {
		return true;
	}

	@Unique
	protected void tickWaterBreathingAir(int air) {
		if (this.isAlive() && !this.isSubmergedIn(FluidTags.WATER)) {
			this.setAir(air - 1);
			if (this.getAir() == -20) {
				this.setAir(0);
				this.damage(DamageSource.DROWN, 2.0F);
			}
		} else {
			this.setAir(getNextAirOnLand(air));
		}

	}

	@Override
	public void baseTick() {
		int i = this.getAir();
		super.baseTick();
		this.tickWaterBreathingAir(i);
	}
}
