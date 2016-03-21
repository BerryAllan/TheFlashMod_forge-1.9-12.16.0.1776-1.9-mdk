package com.theflash.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FlashBlocks
{
	public static Block speedForceBlock;

	public static void mainRegistry()
	{
		initBlocks();
		registerBlocks();
	}

	public static void initBlocks()
	{
		speedForceBlock = new SpeedForceOre(Material.rock).setHardness(50.0F).setUnlocalizedName("speed_force_ore");
	}
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(speedForceBlock, "SpeedForceOre");
	}
}
