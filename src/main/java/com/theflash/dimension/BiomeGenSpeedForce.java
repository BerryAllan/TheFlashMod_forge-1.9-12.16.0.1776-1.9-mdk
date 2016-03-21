//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.theflash.dimension;

import com.theflash.blocks.FlashBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class BiomeGenSpeedForce extends BiomeGenBase
{
	public static BiomeGenSpeedForce instance;

	public BiomeGenSpeedForce(BiomeGenBase.BiomeProperties properties)
	{
		super(properties);
		//this.setColor(2900485);
		//this.setBiomeName("The Speed Force");
		//this.setDisableRain();
		//this.temp(0.0F, 0.0F);
		//this.setHeight(new Height(0.0F, 0.0F));
		this.topBlock = FlashBlocks.speedForceBlock.getDefaultState();
		this.fillerBlock = FlashBlocks.speedForceBlock.getDefaultState();
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.bigMushroomsPerChunk = -999;
		this.theBiomeDecorator.clayPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = -999;
		this.theBiomeDecorator.mushroomsPerChunk = -999;
		this.theBiomeDecorator.reedsPerChunk = -999;
		this.theBiomeDecorator.sandPerChunk = -999;
		this.theBiomeDecorator.sandPerChunk2 = -999;
		this.theBiomeDecorator.waterlilyPerChunk = -999;
	}

	public void decorate(World par1World, Random par2Random, BlockPos pos)
	{
		super.decorate(par1World, par2Random, pos);
		int var5 = par2Random.nextInt(2);
		WorldGenMinable gen = new WorldGenMinable(Blocks.emerald_ore.getDefaultState(), 3);

		int var6;
		int var7;
		int var8;
		int var9;
		for (var6 = 0; var6 < var5; ++var6)
		{
			var7 = pos.getX() + par2Random.nextInt(16);
			var8 = par2Random.nextInt(28) + 4;
			var9 = pos.getY() + par2Random.nextInt(16);
			gen.generate(par1World, par2Random, new BlockPos(var7, var8, var9));
		}

		var5 = par2Random.nextInt(16);

		if (true)
		{
			gen = new WorldGenMinable(FlashBlocks.speedForceBlock.getDefaultState(), 3);
			var5 = par2Random.nextInt(2);

			for (var6 = 0; var6 < var5; ++var6)
			{
				var7 = pos.getX() + par2Random.nextInt(16);
				var8 = 8 + par2Random.nextInt(5) + par2Random.nextInt(5);
				var9 = pos.getY() + par2Random.nextInt(16);
				gen.generate(par1World, par2Random, new BlockPos(var7, var8, var9));
			}
		}

	}
}
