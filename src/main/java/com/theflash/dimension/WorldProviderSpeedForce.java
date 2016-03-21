//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.theflash.dimension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderSpeedForce extends WorldProvider
{
	public WorldProviderSpeedForce()
	{
	}

	public void registerWorldChunkManager()
	{
		super.registerWorldChunkManager();
		this.worldChunkMgr = new BiomeProviderSingle(BiomeGenSpeedForce.instance);
	}

	public String getWelcomeMessage()
	{
		return "Entering the Speed Force";
	}

	public String getDepartMessage()
	{
		return "Exiting the Speed Force";
	}

	public String getDimensionName()
	{
		return "The Speed Force";
	}

	public boolean canRespawnHere()
	{
		return false;
	}

	@SideOnly(Side.CLIENT) public float getCloudHeight()
	{
		return 300.0F;
	}

	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderSpeedForce(this.worldObj, true, this.getSeed());
	}

	public BiomeGenBase getBiomeGenForCoords(BlockPos pos)
	{
		return BiomeGenSpeedForce.instance;
	}

	public int getAverageGroundLevel()
	{
		return 80;
	}

	protected void generateLightBrightnessTable()
	{
		if (false)
		{
			for (int i = 0; i < this.lightBrightnessTable.length; ++i)
			{
				this.lightBrightnessTable[i] = 4.0F;
			}
		}
		else
		{
			super.generateLightBrightnessTable();
		}

	}

	public double getMovementFactor()
	{
		return 1.0D;
	}

	@Override public DimensionType getDimensionType()
	{
		return DimensionType.OVERWORLD;
	}

	public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
	{
		return 1.0F;
	}

	public String getInternalNameSuffix()
	{
		return "_aworld";
	}
}
