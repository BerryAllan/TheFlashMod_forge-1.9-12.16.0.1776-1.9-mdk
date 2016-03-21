package com.theflash.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpeedForceLightningThrow extends Entity
{
	
	final int lifeSpan = 13;
	public double length = 1;
	int life = 0;
	int tileX = 0, tileY = 0, tileZ = 0;
	private EntityLivingBase owner;
	
	public EntitySpeedForceLightningThrow(World w)
	{
		super(w);
		this.posX = Minecraft.getMinecraft().thePlayer.posX;
		this.posY = Minecraft.getMinecraft().thePlayer.posY;
		this.posZ = Minecraft.getMinecraft().thePlayer.posZ;
		tileX = (int) Minecraft.getMinecraft().thePlayer.posX;
		tileY = (int) Minecraft.getMinecraft().thePlayer.posY;
		tileZ = (int) Minecraft.getMinecraft().thePlayer.posZ;
		this.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
		this.rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
		this.owner = owner;
		this.length = length;
	}
	
	public EntitySpeedForceLightningThrow(World w, EntityLivingBase owner, double x, double y, double z, float yaw,
			float pitch, double length)
	{
		super(w);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		tileX = (int) x;
		tileY = (int) y;
		tileZ = (int) z;
		this.rotationPitch = pitch;
		this.rotationYaw = yaw;
		this.owner = owner;
		this.length = length;
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if (++life > lifeSpan)
		{
			this.setDead();
		}
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound tag)
	{
		
		this.tileX = tag.getShort("xTile");
		this.tileY = tag.getShort("yTile");
		this.tileZ = tag.getShort("zTile");
		this.length = tag.getByte("len") * 0.01d;
		
		if (tag.hasKey("direction", 9))
		{
			NBTTagList nbttaglist = tag.getTagList("direction", 6);
			this.motionX = nbttaglist.getFloatAt(0);
			this.motionY = nbttaglist.getFloatAt(1);
			this.motionZ = nbttaglist.getFloatAt(2);
		}
		else
		{
			this.setDead();
		}
		
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound tag)
	{
		//net.minecraft.entity.projectile.EntityFireball
		tag.setShort("xTile", (short) this.tileX);
		tag.setShort("yTile", (short) this.tileY);
		tag.setShort("zTile", (short) this.tileZ);
		tag.setByte("len", (byte) (this.length * 100f));
		tag.setTag("direction",
				this.newFloatNBTList(new float[] { (float) this.motionX, (float) this.motionY, (float) this.motionZ }));
		
	}
	
	/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
	@SideOnly(Side.CLIENT) @Override public void setPositionAndRotation(double x, double y, double z, float yaw,
			float pitch)
	{
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}
	
}
