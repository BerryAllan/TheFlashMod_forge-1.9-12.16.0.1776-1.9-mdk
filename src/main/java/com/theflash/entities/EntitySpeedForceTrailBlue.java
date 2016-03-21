package com.theflash.entities;

import com.theflash.items.FlashArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpeedForceTrailBlue extends EntitySpeedForceTrailBase
{
	public EntitySpeedForceTrailBlue(World world)
	{
		super(world);
		this.posX = Minecraft.getMinecraft().thePlayer.lastTickPosX;
		this.posY = Minecraft.getMinecraft().thePlayer.lastTickPosY;
		this.posZ = Minecraft.getMinecraft().thePlayer.lastTickPosZ;
		this.rotationYaw = Minecraft.getMinecraft().thePlayer.prevRotationYaw;
		this.rotationPitch = Minecraft.getMinecraft().thePlayer.prevRotationPitch;

		//this.getCollisionBoundingBox()
		//		.expand(-this.getCollisionBoundingBox().maxX, -this.getCollisionBoundingBox().maxY,
		//				-this.getCollisionBoundingBox().maxZ);
	}

	@Override protected void entityInit()
	{
		super.entityInit();
	}

	@Override protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(
				Minecraft.getMinecraft().thePlayer.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
						.getBaseValue() * (FlashArmor.flashFactor * 8));
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float partialTicks)
	{
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float partialTicks)
	{
		return 1.0F;
	}
}
