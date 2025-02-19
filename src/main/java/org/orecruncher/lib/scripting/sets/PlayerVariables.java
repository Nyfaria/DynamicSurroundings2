/*
 * Dynamic Surroundings: Sound Control
 * Copyright (C) 2019  OreCruncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package org.orecruncher.lib.scripting.sets;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.orecruncher.lib.GameUtils;
import org.orecruncher.lib.WorldUtils;
import org.orecruncher.lib.scripting.VariableSet;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class PlayerVariables extends VariableSet<IPlayerVariables> implements IPlayerVariables {

    private final LazyVariable<Boolean> isSuffocating = new LazyVariable<>(() -> {
        if (GameUtils.isInGame()) {
            final Player player = GameUtils.getPlayer();
            return !player.isCreative() && player.getAirSupply() < 0;
        }
        return false;
    });
    private final LazyVariable<Boolean> canSeeSky = new LazyVariable<>(() -> {
        if (GameUtils.isInGame()) {
            final Level world = GameUtils.getWorld();
            final Player player = GameUtils.getPlayer();
            return world.canSeeSkyFromBelowWater(player.blockPosition().offset(0, 2, 0));
        }
        return false;
    });
    private final LazyVariable<Boolean> canRainOn = new LazyVariable<>(() -> {
        if (GameUtils.isInGame()) {
            final Level world = GameUtils.getWorld();
            final Player player = GameUtils.getPlayer();
            if (world.canSeeSkyFromBelowWater(player.blockPosition().offset(0, 2, 0)))
                return WorldUtils.getTopSolidOrLiquidBlock(world, player.blockPosition()).getY() <= player.blockPosition().getY();
        }
        return false;
    });
    private boolean isCreative;
    private boolean isBurning;
    private boolean isFlying;
    private boolean isSprintnig;
    private boolean isInLava;
    private boolean isInvisible;
    private boolean isBlind;
    private boolean isInWater;
    private boolean isWet;
    private boolean isRiding;
    private boolean isOnGround;
    private boolean isMoving;
    private float health;
    private float maxHealth;
    private float foodLevel;
    private float foodSaturationLevel;
    private double x;
    private double y;
    private double z;

    public PlayerVariables() {
        super("player");
    }

    @Override
    public void update() {

        if (GameUtils.isInGame()) {
            final Player player = GameUtils.getPlayer();
            assert player != null;

            this.isCreative = player.isCreative();
            this.isBurning = player.isOnFire();
            this.isFlying = player.hasImpulse;
            this.isSprintnig = player.isSprinting();
            this.isInLava = player.isInLava();
            this.isInvisible = player.isInvisible();
            this.isBlind = player.hasEffect(MobEffects.BLINDNESS);
            this.isInWater = player.isInWater();
            this.isWet = player.isInWaterOrRain();
            this.isRiding = player.hasOnePlayerPassenger();
            this.isOnGround = player.isOnGround();
            this.isMoving = player.walkDist != player.walkDistO;
            this.health = player.getHealth();
            this.maxHealth = player.getMaxHealth();
            this.foodLevel = player.getFoodData().getFoodLevel();
            this.foodSaturationLevel = player.getFoodData().getSaturationLevel();
            this.x = player.getX();
            this.y = player.getY();
            this.z = player.getZ();

        } else {

            this.isCreative = false;
            this.isBurning = false;
            this.isFlying = false;
            this.isSprintnig = false;
            this.isInLava = false;
            this.isInvisible = false;
            this.isBlind = false;
            this.isInWater = false;
            this.isWet = false;
            this.isRiding = false;
            this.isOnGround = false;
            this.health = 20F;
            this.maxHealth = 20F;
            this.foodLevel = 20F;
            this.foodSaturationLevel = 20F;
            this.x = 0;
            this.y = 0;
            this.z = 0;

        }

        this.isSuffocating.reset();
        this.canRainOn.reset();
        this.canSeeSky.reset();

    }

    @Nonnull
    @Override
    public IPlayerVariables getInterface() {
        return this;
    }

    @Override
    public boolean isCreative() {
        return this.isCreative;
    }

    @Override
    public boolean isBurning() {
        return this.isBurning;
    }

    @Override
    public boolean isSuffocating() {
        return this.isSuffocating.get();
    }

    @Override
    public boolean isFlying() {
        return this.isFlying;
    }

    @Override
    public boolean isSprintnig() {
        return this.isSprintnig;
    }

    @Override
    public boolean isInLava() {
        return this.isInLava;
    }

    @Override
    public boolean isInvisible() {
        return this.isInvisible;
    }

    @Override
    public boolean isBlind() {
        return this.isBlind;
    }

    @Override
    public boolean isInWater() {
        return this.isInWater;
    }

    @Override
    public boolean isMoving() {
        return this.isMoving;
    }

    @Override
    public boolean isWet() {
        return this.isWet;
    }

    @Override
    public boolean isRiding() {
        return this.isRiding;
    }

    @Override
    public boolean isOnGround() {
        return this.isOnGround;
    }

    @Override
    public boolean canRainOn() {
        return this.canRainOn.get();
    }

    @Override
    public boolean canSeeSky() {
        return this.canSeeSky.get();
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public float getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public float getFoodLevel() {
        return this.foodLevel;
    }

    @Override
    public float getFoodSaturationLevel() {
        return this.foodSaturationLevel;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }
}
