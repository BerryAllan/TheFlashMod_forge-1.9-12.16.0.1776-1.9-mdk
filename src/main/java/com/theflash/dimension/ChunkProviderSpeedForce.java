package com.theflash.dimension;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderHell;

public class ChunkProviderSpeedForce extends ChunkProviderHell
{
	public ChunkProviderSpeedForce(World worldIn, boolean p_i45636_2_, long p_i45636_4_)
	{
		super(worldIn, p_i45636_2_, p_i45636_4_);
	}

	public void setBlocksInChunk(int p_180518_1_, int p_180518_2_, ChunkPrimer cprimer)
	{
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 0; y <= 255 && y >= 0; ++y)
				{
					int code = (x * 16 + z) * 256 + y;
					if (y < 8)
					{
						cprimer.setBlockState(x, y, z, Blocks.stone.getDefaultState());
					}
					else
					{
						cprimer.setBlockState(x, y, z, Blocks.air.getDefaultState());
					}
				}
			}
		}
	}
}
