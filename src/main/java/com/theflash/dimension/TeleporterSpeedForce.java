//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.theflash.dimension;

import com.theflash.blocks.FlashBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TeleporterSpeedForce extends Teleporter
{
	private static final int radius = 20;
	private final WorldServer worldServer;
	private final Random random;

	public TeleporterSpeedForce(WorldServer world)
	{
		super(world);
		this.worldServer = world;
		this.random = new Random(world.getSeed());
	}

	public void placeInPortal(Entity entity, float f1)
	{
		if (!this.func_180620_b(entity, f1))
		{
			this.func_85188_a(entity);
			this.func_180620_b(entity, f1);
		}

	}

	public boolean func_180620_b(Entity entity, float p_180620_2_)
	{
		int x = MathHelper.floor_double(entity.posX);
		int z = MathHelper.floor_double(entity.posZ);
		int px = -2147483648;
		int py = 0;
		int pz = 0;

		for (int xD = -20; xD <= 20; ++xD)
		{
			for (int zD = -20; zD <= 20; ++zD)
			{
				for (int y = -20; y < this.worldServer.getActualHeight() + 5; ++y)
				{
					if (this.worldServer.getBlockState(new BlockPos(x + xD, y, z + zD)).getBlock()
							== FlashBlocks.speedForceBlock)
					{
						px = x + xD;
						py = y;
						pz = z + zD;
					}
				}
			}
		}

		if (px == -2147483648)
		{
			return false;
		}
		else
		{
			while (!this.worldServer.getBlockState(new BlockPos(px, py - 1, pz)).getBlock().getDefaultState().getMaterial().isSolid())
			{
				--py;
			}

			entity.posX = (double) ((float) px + 0.5F);
			entity.posY = (double) py + 0.1D;
			entity.posZ = (double) ((float) pz + 0.5F);
			entity.rotationPitch = 0.0F;
			entity.setSneaking(false);
			if (this.worldServer.getBlockState(new BlockPos(px + 1, py, pz)).getBlock() != FlashBlocks.speedForceBlock
					&& this.worldServer.getBlockState(new BlockPos(px - 1, py, pz)).getBlock()
					!= FlashBlocks.speedForceBlock)
			{
				if (this.worldServer.getBlockState(new BlockPos(px, py, pz + 1)).getBlock()
						!= FlashBlocks.speedForceBlock
						&& this.worldServer.getBlockState(new BlockPos(px, py, pz - 1)).getBlock()
						!= FlashBlocks.speedForceBlock)
				{
					entity.rotationPitch = 90.0F;
				}
				else
				{
					entity.rotationYaw = 90.0F;
				}
			}
			else
			{
				entity.rotationYaw = 0.0F;
			}

			return true;
		}
	}

	public boolean func_85188_a(Entity p_85188_1_)
	{
		byte b0 = 16;
		double d0 = -1.0D;
		int i = MathHelper.floor_double(p_85188_1_.posX);
		int j = MathHelper.floor_double(p_85188_1_.posY);
		int k = MathHelper.floor_double(p_85188_1_.posZ);
		int l = i;
		int i1 = j;
		int j1 = k;
		int k1 = 0;
		int l1 = this.random.nextInt(4);

		int i2;
		double d1;
		int k2;
		double d2;
		int i3;
		int j3;
		int k3;
		int l3;
		int i4;
		int j4;
		int k4;
		int l4;
		int i5;
		double d3;
		double d4;
		int k5;
		for (i2 = i - b0; i2 <= i + b0; ++i2)
		{
			d1 = (double) i2 + 0.5D - p_85188_1_.posX;

			for (k2 = k - b0; k2 <= k + b0; ++k2)
			{
				d2 = (double) k2 + 0.5D - p_85188_1_.posZ;

				label291:
				for (i3 = this.worldServer.getActualHeight() - 1; i3 >= 0; --i3)
				{
					if (this.worldServer.isAirBlock(new BlockPos(i2, i3, k2)))
					{
						while (i3 > 0 && this.worldServer.isAirBlock(new BlockPos(i2, i3 - 1, k2)))
						{
							--i3;
						}

						for (j3 = l1; j3 < l1 + 4; ++j3)
						{
							k3 = j3 % 2;
							l3 = 1 - k3;
							if (j3 % 4 >= 2)
							{
								k3 = -k3;
								l3 = -l3;
							}

							for (i4 = 0; i4 < 3; ++i4)
							{
								for (j4 = 0; j4 < 4; ++j4)
								{
									for (k4 = -1; k4 < 4; ++k4)
									{
										l4 = i2 + (j4 - 1) * k3 + i4 * l3;
										i5 = i3 + k4;
										k5 = k2 + (j4 - 1) * l3 - i4 * k3;
										if (k4 < 0 && !this.worldServer.getBlockState(new BlockPos(l4, i5, k5))
												.getBlock().getDefaultState().getMaterial().isSolid() || k4 >= 0 && !this.worldServer
												.isAirBlock(new BlockPos(l4, i5, k5)))
										{
											continue label291;
										}
									}
								}
							}

							d3 = (double) i3 + 0.5D - p_85188_1_.posY;
							d4 = d1 * d1 + d3 * d3 + d2 * d2;
							if (d0 < 0.0D || d4 < d0)
							{
								d0 = d4;
								l = i2;
								i1 = i3;
								j1 = k2;
								k1 = j3 % 4;
							}
						}
					}
				}
			}
		}

		if (d0 < 0.0D)
		{
			for (i2 = i - b0; i2 <= i + b0; ++i2)
			{
				d1 = (double) i2 + 0.5D - p_85188_1_.posX;

				for (k2 = k - b0; k2 <= k + b0; ++k2)
				{
					d2 = (double) k2 + 0.5D - p_85188_1_.posZ;

					label229:
					for (i3 = this.worldServer.getActualHeight() - 1; i3 >= 0; --i3)
					{
						if (this.worldServer.isAirBlock(new BlockPos(i2, i3, k2)))
						{
							while (i3 > 0 && this.worldServer.isAirBlock(new BlockPos(i2, i3 - 1, k2)))
							{
								--i3;
							}

							for (j3 = l1; j3 < l1 + 2; ++j3)
							{
								k3 = j3 % 2;
								l3 = 1 - k3;

								for (i4 = 0; i4 < 4; ++i4)
								{
									for (j4 = -1; j4 < 4; ++j4)
									{
										k4 = i2 + (i4 - 1) * k3;
										l4 = i3 + j4;
										i5 = k2 + (i4 - 1) * l3;
										if (j4 < 0 && !this.worldServer.getBlockState(new BlockPos(k4, l4, i5))
												.getBlock().getDefaultState().getMaterial().isSolid() || j4 >= 0 && !this.worldServer
												.isAirBlock(new BlockPos(k4, l4, i5)))
										{
											continue label229;
										}
									}
								}

								d3 = (double) i3 + 0.5D - p_85188_1_.posY;
								d4 = d1 * d1 + d3 * d3 + d2 * d2;
								if (d0 < 0.0D || d4 < d0)
								{
									d0 = d4;
									l = i2;
									i1 = i3;
									j1 = k2;
									k1 = j3 % 2;
								}
							}
						}
					}
				}
			}
		}

		k5 = l;
		int j2 = i1;
		k2 = j1;
		int l5 = k1 % 2;
		int l2 = 1 - l5;
		if (k1 % 4 >= 2)
		{
			l5 = -l5;
			l2 = -l2;
		}

		if (d0 < 0.0D)
		{
			i1 = MathHelper.clamp_int(i1, 70, this.worldServer.getActualHeight() - 10);
			j2 = i1;

			for (i3 = -1; i3 <= 1; ++i3)
			{
				for (j3 = 1; j3 < 3; ++j3)
				{
					for (k3 = -1; k3 < 3; ++k3)
					{
						l3 = k5 + (j3 - 1) * l5 + i3 * l2;
						i4 = j2 + k3;
						j4 = k2 + (j3 - 1) * l2 - i3 * l5;
						boolean iblockstate = k3 < 0;
						this.worldServer.setBlockState(new BlockPos(l3, i4, j4), iblockstate ?
								FlashBlocks.speedForceBlock.getDefaultState() :
								Blocks.air.getDefaultState());
					}
				}
			}
		}

		//IBlockState var38 = FlashBlocks.speedForceBlock.getDefaultState().withProperty(BlockPortal.AXIS, l5 != 0?Axis.X:Axis.Z);

		for (j3 = 0; j3 < 4; ++j3)
		{
			for (k3 = 0; k3 < 4; ++k3)
			{
				for (l3 = -1; l3 < 4; ++l3)
				{
					i4 = k5 + (k3 - 1) * l5;
					j4 = j2 + l3;
					k4 = k2 + (k3 - 1) * l2;
					boolean flag1 = k3 == 0 || k3 == 3 || l3 == -1 || l3 == 3;
					//this.worldServer.setBlockState(new BlockPos(i4, j4, k4), flag1?FlashBlocks.speedForceBlock.getDefaultState():var38, 2);
				}
			}

			for (k3 = 0; k3 < 4; ++k3)
			{
				for (l3 = -1; l3 < 4; ++l3)
				{
					i4 = k5 + (k3 - 1) * l5;
					j4 = j2 + l3;
					k4 = k2 + (k3 - 1) * l2;
					this.worldServer.notifyNeighborsOfStateChange(new BlockPos(i4, j4, k4),
							this.worldServer.getBlockState(new BlockPos(i4, j4, k4)).getBlock());
				}
			}
		}

		return true;
	}

	public void removeStalePortalLocations(long p_85189_1_)
	{
	}
}
