/* WARNING: The code that follows will make you cry;
 * 		a safety pig is provided below for your benefit.
 * 
 *                          _
 _._ _..._ .-',     _.._(`))
'-. `     '  /-._.-'    ',/
   )         \            '.
  / _    _    |             \
 |  a    a   /              |
 \   .-.                     ;  
  '-('' ).-'       ,'       ;
     '-;           |      .'
        \           \    /
        | 7  .__  _.-\   \
        | |  |  ``/  /`  /
       /,_|  |   /,_/   /
          /,_/      '`-'
 * 
 * Feel free to use the safety pig whenever it suits you best.
 */

package com.theflash.handlers;

import com.theflash.entities.EntityFutureFlashLightning;
import com.theflash.entities.EntitySpeedForceLightningThrow;
import com.theflash.entities.EntitySpeedForceTrailBlue;
import com.theflash.entities.EntitySpeedForceTrailYellow;
import com.theflash.items.FlashAbstract;
import com.theflash.items.FlashArmor;
import com.theflash.items.FlashItems;
import com.theflash.main.MainRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/*
 * features list so far:
 *
 * running speed faster
 * running on water
 * running on air (traditional and new way)
 * jumping higher
 * stronger
 * stronger as speed increases
 * running up walls
 * fall damage not taken
 * phasing
 * slow time
 * step helper
 * tornadoes
 * vortexes
 * rapid regeneration
 * invisibility
 * lightning trail
 * the flash ring(s)
 * future flash lightning emanations
 * steal speed
 * glowing flash armor
 * 
 * need:
 * speed digging
 * lightning throw (just needs to render)
 * speed force dimension
 * becoming the flash through barry's lab
 * cosmic treadmill to upgrade speed in survival
 *
 */

public class SpeedForceHandler
{
	public static final double rangeMax = 32;
	public static final double radius = 2;
	public static final float damage = 8;
	public static int upperSpeedLimit = 64;
	public static int lowerSpeedLimit = 0;
	public static float upperJumpMoveLimit = 0.64F;
	public static float lowerJumpMoveLimit = 0.04F;
	public static float gameSpeed = SlowTimeHandler.getGameSpeed();
	public static float slowMoFactor = 0.0F;
	public static float slowMoUpperLimit = 0.9F;
	public static float sloMoLowerLimit = 0.0F;
	public static boolean waterRunningUnlocked = true;
	public static boolean wallRunningUnlocked = true;
	public static boolean tornadoesAndVortexesUnlocked = true;
	public static boolean phasingUnlocked = true;
	public static boolean flyingUnlocked = true;
	public static boolean betterFlyingUnlocked = true;
	public static boolean lightningThrowUnlocked = true;
	public static boolean invisibilityUnlocked = true;
	public static boolean isFlying = false;
	public static boolean vibrating = false;
	public static boolean vibratingInvisible = false;
	public static boolean jump;
	public static double speedkmh;
	public static Minecraft mc = Minecraft.getMinecraft();
	private final double piOver2 = Math.PI / 2;

	@SubscribeEvent public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			if (KeysHandler.SPACEBAR.isKeyDown()/*mc.gameSettings.keyBindJump.isKeyDown()*/ && player.onGround
					&& !player.isInWater())
			{
				int jump = 4;
				player.motionY = 0.42F;
				if (FlashArmor.flashFactor >= 1)
				{
					player.motionY += (double) ((float) (jump + 1) * 0.1F);
				}
				if (player.isSprinting())
				{
					float f = player.rotationYaw * 0.017453292F;
					player.motionX -= (double) (MathHelper.sin(f) * 0.2F);
					player.motionZ += (double) (MathHelper.cos(f) * 0.2F);
				}
				player.isAirBorne = true;
				ForgeHooks.onLivingJump(player);
			}
			if (KeysHandler.SPACEBAR.isKeyDown() && player.isInWater() && FlashArmor.flashFactor < 1)
			{
				player.motionY += 0.025;
			}

			if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(2) != null
					&& player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(0) != null
					&& player.inventory.armorItemInSlot(3).getItem() instanceof FlashAbstract && player.inventory
					.armorItemInSlot(2).getItem() instanceof FlashAbstract && player.inventory.armorItemInSlot(1)
					.getItem() instanceof FlashAbstract && player.inventory.armorItemInSlot(0)
					.getItem() instanceof FlashAbstract)
			{
				//unlocked?
				waterRunningUnlocked = true;
				wallRunningUnlocked = true;
				tornadoesAndVortexesUnlocked = true;
				phasingUnlocked = true;
				flyingUnlocked = true;
				betterFlyingUnlocked = true;
				lightningThrowUnlocked = true;
				invisibilityUnlocked = true;

				// speed
				speedkmh = player.motionY;/*MathHelper.sqrt_double(
						(player.posX - player.lastTickPosX) * (player.posX - player.lastTickPosX)
								+ (player.posZ - player.lastTickPosZ) * (player.posZ - player.lastTickPosZ)) / (
						player.ticksExisted - (player.ticksExisted - 1));*/

				if (player.moveForward > 0 && FlashArmor.flashFactor >= 1 && !player.isSneaking())
				{
					player.setSprinting(true);
				}

				World world1 = player.worldObj;
				int x1 = MathHelper.floor_double(player.posX);
				int y1 = MathHelper.floor_double(player.posY + player.eyeHeight);
				int z1 = MathHelper.floor_double(player.posZ);

				// water running
				if (FlashArmor.flashFactor >= 1 && SpeedForceHandler.waterRunningUnlocked)
				{
					if (world1.getBlockState(new BlockPos(x1, (int) (player.getEntityBoundingBox().minY - 1), z1))
							.getBlock().getDefaultState().getMaterial() == Material.water || player.isInWater())
					{
						player.motionY = 0.0D;
						player.capabilities.isFlying = true;
						player.isInWater();
						player.playSound(new SoundEvent(new ResourceLocation("liquid.water")), 1.0F, 1.7F);

						if (player.isSneaking() && !Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()
								&& Minecraft.getMinecraft().currentScreen == null)
						{
							player.motionY = -0.42;
						}
						if (KeysHandler.SPACEBAR.isKeyDown())
						{
							player.motionY = 0.42;

							if (FlashArmor.flashFactor >= 1)
							{
								player.motionY += (double) ((float) (4 + 1) * 0.1F);
							}
							if (player.isSprinting())
							{
								float f = player.rotationYaw * 0.017453292F;
								player.motionX -= (double) (MathHelper.sin(f) * 0.2F);
								player.motionZ += (double) (MathHelper.cos(f) * 0.2F);
							}

							player.isAirBorne = true;
							ForgeHooks.onLivingJump(player);
						}

					}
					else
					{
						player.capabilities.isFlying = false;
					}
				}
				// speed falling while in slo-mo
				if (slowMoFactor > 0 && player.isSneaking())
				{
					player.motionY -= slowMoFactor * 1.1111111111111111111111111111111111111111111;
				}
				// rapid regeneration
				if (!player.isDead)
				{
					if (player.getHealth() < player.getMaxHealth())
					{
						if (player.inventory.armorItemInSlot(2).getItem().equals(FlashItems.chestPlateFlash))
						{
							player.heal((float) ((FlashArmor.flashFactor / 2) * 0.0125));
							// player.setHealth(player.getHealth() + (float) ((FlashArmor.flashFactor / 1.5) * 0.0125));
						}
						else if (player.inventory.armorItemInSlot(2).getItem().equals(FlashItems.chestPlateFutureFlash))
						{
							player.heal((float) ((FlashArmor.flashFactor / 1.5) * 0.0125));
							// player.setHealth(player.getHealth() + (float) ((FlashArmor.flashFactor / 1.5) * 0.0125));
						}
						player.hurtTime = 0;
						player.maxHurtTime = 0;
					}
				}
				else if (player.isDead)
				{
					player.setDead();
				}
				// step helper
				if (FlashArmor.flashFactor >= 1)
				{
					player.stepHeight = player.isCollidedHorizontally && player.onGround ? 1.5f : 0.5f;
				}

				// increase speed
				if (KeysHandler.UP_KEY.isKeyDown())
				{
					if (FlashArmor.flashFactor < upperSpeedLimit)
					{
						FlashArmor.flashFactor += 1;
					}

					if (FlashArmor.jumpFactor < upperJumpMoveLimit)
					{
						FlashArmor.jumpFactor += 0.02F;
					}

					if (FlashArmor.jumpFactor >= upperJumpMoveLimit)
					{
						FlashArmor.jumpFactor = upperJumpMoveLimit;
					}
				}
				// decrease speed
				if (KeysHandler.DOWN_KEY.isKeyDown())
				{
					if (FlashArmor.flashFactor >= lowerSpeedLimit)
					{
						FlashArmor.flashFactor -= 1;
					}
					if (FlashArmor.flashFactor <= lowerSpeedLimit || FlashArmor.flashFactor < 1
							|| FlashArmor.flashFactor == -1)
					{
						FlashArmor.flashFactor = lowerSpeedLimit;
					}

					if (FlashArmor.jumpFactor > lowerJumpMoveLimit)
					{
						FlashArmor.jumpFactor -= 0.02F;
					}

					if (FlashArmor.jumpFactor <= lowerJumpMoveLimit)
					{
						FlashArmor.jumpFactor = lowerJumpMoveLimit;
					}
				}
				// speed max
				if (KeysHandler.PGUP_KEY.isKeyDown())
				{
					FlashArmor.flashFactor = upperSpeedLimit;
					FlashArmor.jumpFactor = upperJumpMoveLimit;
				}
				// speed min
				if (KeysHandler.PGDOWN_KEY.isKeyDown())
				{
					FlashArmor.flashFactor = lowerSpeedLimit + 1;
					FlashArmor.jumpFactor = lowerJumpMoveLimit + 0.02F;
				}
				// wall running
				if (FlashArmor.flashFactor >= 1 && player.isCollidedHorizontally && KeysHandler.R_KEY.isKeyDown()
						&& wallRunningUnlocked && !Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()
						&& Minecraft.getMinecraft().currentScreen == null)
				{
					player.motionY += 0.25;
				}

				//extinguish all fires by vibration
				if (FlashArmor.flashFactor >= 1 && player.isBurning() && vibrating)
				{
					player.extinguish();
				}

				// slow time perspective
				float speed = Float.valueOf(Float.toString(this.gameSpeed - this.slowMoFactor).substring(0, 3))
						.floatValue();
				if (KeysHandler.C_KEY.isKeyDown() && !Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()
						&& Minecraft.getMinecraft().currentScreen == null)
				{
					slowMoFactor += 0.04F;
					if (slowMoFactor >= slowMoUpperLimit)
					{
						slowMoFactor = slowMoUpperLimit;
					}
					SlowTimeHandler.setGameSpeed(speed);
					Minecraft.getMinecraft().currentScreen = null;
					player.arrowHitTimer = (int) (SlowTimeHandler.getGameSpeed() * 500.0F);
				}
				if (KeysHandler.X_KEY.isKeyDown() && !Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()
						&& Minecraft.getMinecraft().currentScreen == null)
				{
					slowMoFactor -= 0.04F;
					if (slowMoFactor <= sloMoLowerLimit)
					{
						slowMoFactor = sloMoLowerLimit;
					}

					SlowTimeHandler.setGameSpeed(speed);
					Minecraft.getMinecraft().currentScreen = null;
				}
				// phase
				if (KeysHandler.F_KEY.isKeyDown() && phasingUnlocked && FlashArmor.flashFactor >= 1)
				{
					player.noClip = true;
					vibrating = true;
					if (invisibilityUnlocked)
					{
						vibratingInvisible = true;
					}
					else
					{
						vibratingInvisible = false;
					}

					mc.renderChunksMany = false;
					player.capabilities.isFlying = true;
					player.onGround = false;
					player.capabilities.disableDamage = true;

					if (KeysHandler.SPACEBAR.isKeyDown())
					{
						player.motionY += 0.42F;
					}
					else if (player.isSneaking())
					{
						player.motionY += (player.capabilities.getFlySpeed() * 3.0F) - 0.42F;
					}

					if (!isFlying)
					{
						if (world1.getBlockState(new BlockPos(x1, player.getEntityBoundingBox().minY - 1, z1))
								.getBlock().getDefaultState().getMaterial() == Material.air)
						{
							player.motionY -= 0.42;
						}
					}
						/*
						if (world1.getBlockState(new BlockPos(x1, player.getEntityBoundingBox().minY - 1, z1))
								.getBlock().getMaterial() != Material.air &&
								world1.getBlockState(new BlockPos(x1, player.getEntityBoundingBox().minY - 1, z1))
										.getBlock().getMaterial() != Material.water && player.isInWater())
						{
							player.onGround = true;
						}
						else
						{
							player.onGround = false;
						}
						*/
				}
				else
				{
					vibrating = false;
					vibratingInvisible = false;
				}

				// flying
				if (KeysHandler.R_KEY.isKeyDown() && FlashArmor.flashFactor >= 1 && flyingUnlocked && !Minecraft
						.getMinecraft().ingameGUI.getChatGUI().getChatOpen()
						&& Minecraft.getMinecraft().currentScreen == null && !player.isCollidedHorizontally)
				{
					if (betterFlyingUnlocked)
					{
						player.capabilities.isFlying = true;
						isFlying = true;

						if (KeysHandler.SPACEBAR.isKeyDown())
						{
							player.motionY += 0.42F;
						}
						else if (player.isSneaking())
						{
							player.motionY += (player.capabilities.getFlySpeed() * 3.0F) - 0.42F;
						}
					}

					else if (!player.isCollidedHorizontally && KeysHandler.SPACEBAR.isKeyDown())
					{
						player.motionY += 0.175D;
						isFlying = true;
					}

					else
					{
						isFlying = false;
					}
				}

				// steal speed
				List<Entity> entities3 = player.worldObj.loadedEntityList;

				if (entities3.size() > 0)
				{
					for (int i = 0; i < entities3.size(); i++)
					{
						Entity entity = entities3.get(i);

						if (KeysHandler.Z_KEY.isKeyDown() && FlashArmor.flashFactor >= 1)
						{
							if (entity instanceof EntityPlayer)
							{

							}
							else
							{
								if (mc.objectMouseOver.entityHit == entity)
								{
									entity.performHurtAnimation();

									entity.motionX = 0;
									entity.motionY = 0;
									entity.motionZ = 0;

									entity.setPosition(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);

									entity.rotationYaw = entity.prevRotationYaw;
									entity.rotationPitch = entity.prevRotationPitch;
								}
							}
						}
					}
				}

				// catching arrows
				if (KeysHandler.G_KEY.isKeyDown())
				{
					World world = player.worldObj;
					int x = 3;
					int y = 3;
					int z = 3;

					List<Entity> entityList = world
							.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().expandXyz(16));

					if (entityList.size() > 0)
					{
						for (int i = 0; i < entityList.size(); i++)
						{
							Entity entity = entityList.get(i);

							if (entity instanceof EntityPlayer)
							{

							}
							else if (entity instanceof EntityArrow)
							{
								entity.motionX = 0;
								entity.motionY = 0;
								entity.motionZ = 0;

								entity.setPosition(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);

								entity.rotationYaw = entity.prevRotationYaw;
								entity.rotationPitch = entity.prevRotationPitch;

								entity.setDead();
								player.inventory.addItemStackToInventory(new ItemStack(Items.arrow));
							}
							else
							{

							}
						}
					}
				}

				// tornado blowback and vortexes
				if (KeysHandler.V_KEY.isKeyDown() && FlashArmor.flashFactor >= 1 && tornadoesAndVortexesUnlocked)
				{
					World world = player.worldObj;
					int x = 16;
					int y = 16;
					int z = 16;

					List<Entity> entityList = world
							.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().expandXyz(16));

					if (player.isSneaking())
					{
						if (entityList.size() > 0)
						{
							for (int i = 0; i < entityList.size(); i++)
							{
								Entity entity = entityList.get(i);

								int j = 1;
								if (entity instanceof EntityPlayer)
								{

								}
								else
								{
									float f = player.rotationYaw * 0.017453292f;
									entity.motionX += (double) (MathHelper.sin(f)) / 2;
									entity.motionZ -= (double) (MathHelper.cos(f)) / 2;
									entity.motionY -= j / 2;
								}
							}
						}
					}
					else
					{
						if (entityList.size() > 0)
						{
							for (int i = 0; i < entityList.size(); i++)
							{
								Entity entity = entityList.get(i);

								double j = 1;
								if (entity instanceof EntityPlayer)
								{

								}
								else
								{
									float f = player.rotationYaw * 0.017453292f;
									entity.motionX -= (double) (MathHelper.sin(f)) / 2;
									entity.motionZ += (double) (MathHelper.cos(f)) / 2;
									entity.motionY += j / 2;
								}
							}
						}
					}
				}
				// speed force lightning throw
				if (KeysHandler.T_KEY.isKeyDown() && FlashArmor.flashFactor >= 1)
				{
					// zap all entities in a 16 block long, 4 block wide cylinder...
					// first, calculate range limit (which will be shorter if we hit a solid block)
					double vecX = (double) (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper
							.cos(player.rotationPitch / 180.0F * (float) Math.PI));
					double vecY = (double) (-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI));
					double vecZ = (double) (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI) * MathHelper
							.cos(player.rotationPitch / 180.0F * (float) Math.PI));

					double range = 0;
					double nx = player.posX, ny = player.posY + 1, nz = player.posZ;
					while (range < rangeMax)
					{
						if (world1.getBlockState(new BlockPos((int) nx, (int) ny, (int) nz)).getBlock()
								.getDefaultState().isOpaqueCube())
						{
							break;
						}
						nx += vecX;
						ny += vecY;
						nz += vecZ;
						range += 1;
					}
					if (range < 2)
						range = 2;
					final double rangeSqr = range * range;
					vecX *= range;
					vecY *= range;
					vecZ *= range;

					final EntityLightningBolt fakeBolt = new EntityLightningBolt(world1, nx, ny, nz, true);

					// 	System.out.println("Lightning range = "+range);

					//	AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(player.posX-range,player.posY-range,player.posZ-range,
					//			player.posX+range,player.posY+range,player.posZ+range);

					List entities = world1.getEntitiesWithinAABB(EntityLivingBase.class,
							player.getEntityBoundingBox().expandXyz(16)); // ArrayList<EntityLivingBase>
					for (int i = 0; i < entities.size(); i++)
					{
						if (!(entities.get(i) instanceof EntityLivingBase))
							continue;
						EntityLivingBase e = (EntityLivingBase) entities.get(i);
						if (player == e)
						{
							continue; // don't zap yourself
						}
						double dx = (e.posX - player.posX);
						double dy = (e.posY - player.posY);
						double dz = (e.posZ - player.posZ);
						double distSqr = dx * dx + dy * dy + dz * dz;
						if (distSqr < rangeSqr)
						{
							// target in range, but is it in AoE?
							double dist = Math.sqrt(distSqr);
							// A.B = A*B cos@
							double angle = Math
									.acos(FlashArmor.dotProduct(dx, dy, dz, vecX, vecY, vecZ) / (dist * range));
							if (angle < piOver2)
							{ // in front of player
								double perpendicular = dist * Math.sin(angle);
								if (perpendicular < radius)
								{
									// in AoE
									e.attackEntityFrom(DamageSource.magic, damage);
									//	e.setFire(burnTime);
									e.onStruckByLightning(fakeBolt);
								}
							}
						}
					}

					double deltaX = (double) (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI));
					double deltaZ = (double) (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI));

					world1.playSound(player.posX, player.posY, player.posZ,
							new SoundEvent(new ResourceLocation("random.explode")), SoundCategory.HOSTILE, 4F, 1.7F,
							false);
					world1.playSound(player.posX, player.posY, player.posZ,
							new SoundEvent(new ResourceLocation("ambient.weather.thunder")), SoundCategory.WEATHER, 1F,
							1.7F, false);

					world1.spawnEntityInWorld(
							new EntitySpeedForceLightningThrow(world1, player, player.posX, player.posY + 1,
									player.posZ, player.rotationYaw, player.rotationPitch, range / rangeMax));

				}

				// speed trail blue
				if (FlashArmor.flashFactor >= 1 && player.moveForward > 0 && player.inventory.armorItemInSlot(2)
						.getItem().equals(FlashItems.chestPlateFutureFlash) && !vibratingInvisible && !(
						Minecraft.getMinecraft().gameSettings.thirdPersonView == 0))
				{
					for (int i = 0; i < 1; ++i)
					{
						Entity entity = EntityList.createEntityByName("speed_force_trail_blue", world1);

						if (entity instanceof EntityLivingBase)
						{
							EntityLiving entityliving = (EntityLiving) entity;
							entity.setLocationAndAngles(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ,
									player.rotationYaw, player.rotationPitch);
							entityliving.rotationYawHead = entityliving.rotationYaw;
							entityliving.renderYawOffset = entityliving.rotationYaw;
							entityliving.onInitialSpawn(world1.getDifficultyForLocation(new BlockPos(entityliving)),
									(IEntityLivingData) null);
							world1.spawnEntityInWorld(entity);
						}
					}
				}

				List<Entity> entities = world1.getLoadedEntityList();

				if (entities.size() > 0)
				{
					for (int i = 0; i < entities.size(); i++)
					{
						Entity entity = entities.get(i);
						double speedTrailTime = 0;
						if (FlashArmor.flashFactor > 0 && FlashArmor.flashFactor <= 16)
						{
							speedTrailTime = 2.5;
						}
						else if (FlashArmor.flashFactor > 16 && FlashArmor.flashFactor <= 32)
						{
							speedTrailTime = 5;
						}
						else if (FlashArmor.flashFactor > 32 && FlashArmor.flashFactor <= 48)
						{
							speedTrailTime = 7.5;
						}
						else if (FlashArmor.flashFactor > 48 && FlashArmor.flashFactor <= 64)
						{
							speedTrailTime = 10;
						}

						if (entity instanceof EntitySpeedForceTrailBlue && entity.ticksExisted >= speedTrailTime)
						{
							entity.setDead();
						}
					}
				}

				// speed trail yellow
				if (FlashArmor.flashFactor >= 1 && player.moveForward > 0 && player.inventory.armorItemInSlot(2)
						.getItem().equals(FlashItems.chestPlateFlash) && !vibratingInvisible && !(
						Minecraft.getMinecraft().gameSettings.thirdPersonView == 0))
				{
					for (int i = 0; i < 1; ++i)
					{
						Entity entity = EntityList.createEntityByName("speed_force_trail_yellow", world1);

						if (entity instanceof EntityLivingBase)
						{
							EntityLiving entityliving = (EntityLiving) entity;
							entity.setLocationAndAngles(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ,
									player.rotationYaw, player.rotationPitch);
							entityliving.rotationYawHead = entityliving.rotationYaw;
							entityliving.renderYawOffset = entityliving.rotationYaw;
							entityliving.onInitialSpawn(world1.getDifficultyForLocation(new BlockPos(entityliving)),
									(IEntityLivingData) null);
							world1.spawnEntityInWorld(entity);
						}
					}
				}

				List<Entity> entities2 = world1.getLoadedEntityList();

				if (entities2.size() > 0)
				{
					for (int i = 0; i < entities2.size(); i++)
					{
						Entity entity = entities2.get(i);
						double speedTrailTime = 0;
						if (FlashArmor.flashFactor > 0 && FlashArmor.flashFactor <= 16)
						{
							speedTrailTime = 2.0;
						}
						else if (FlashArmor.flashFactor > 16 && FlashArmor.flashFactor <= 32)
						{
							speedTrailTime = 4.0;
						}
						else if (FlashArmor.flashFactor > 32 && FlashArmor.flashFactor <= 48)
						{
							speedTrailTime = 6.0;
						}
						else if (FlashArmor.flashFactor > 48 && FlashArmor.flashFactor <= 64)
						{
							speedTrailTime = 8.0;
						}

						if (entity instanceof EntitySpeedForceTrailYellow && entity.ticksExisted >= speedTrailTime)
						{
							entity.setDead();
						}
					}
				}

				//future flash lightning
				if (player.moveForward == 0)
				{
					if (FlashArmor.flashFactor >= 1 && player.inventory.armorItemInSlot(2).getItem()
							.equals(FlashItems.chestPlateFutureFlash) && !vibratingInvisible && !(
							Minecraft.getMinecraft().gameSettings.thirdPersonView == 0))
					{
						for (int i = 0; i < 1; ++i)
						{
							Entity entity = EntityList.createEntityByName("future_flash_lightning", world1);

							if (entity instanceof EntityLivingBase)
							{
								EntityLiving entityliving = (EntityLiving) entity;
								entity.setLocationAndAngles(player.lastTickPosX, player.lastTickPosY,
										player.lastTickPosZ, player.rotationYaw, player.rotationPitch);
								entityliving.rotationYawHead = entityliving.rotationYaw;
								entityliving.renderYawOffset = entityliving.rotationYaw;
								entityliving.onInitialSpawn(world1.getDifficultyForLocation(new BlockPos(entityliving)),
										(IEntityLivingData) null);
								// world1.spawnEntityInWorld(entity);
							}
						}
					}
				}
				if (entities2.size() > 0)
				{
					for (int i = 0; i < entities2.size(); i++)
					{
						Entity entity = entities2.get(i);
						double speedTrailTime = 1;

						if (entity instanceof EntityFutureFlashLightning && entity.ticksExisted >= speedTrailTime)
						{
							entity.setDead();
						}
						else if (entity instanceof EntityFutureFlashLightning && player.moveForward > 0)
						{
							entity.setDead();
							entity.preventEntitySpawning = true;
						}
						else
						{
							entity.preventEntitySpawning = false;
						}
					}
				}

				// travel to speed force
				if (player.moveForward > 0 && FlashArmor.flashFactor >= 64)
				{
					player.changeDimension(MainRegistry.dimensionID);
				}
			}
			else
			{
				FlashArmor.flashFactor = 0;
				SlowTimeHandler.setGameSpeed(1.0F);
				slowMoFactor = 0.0F;
				waterRunningUnlocked = false;
				wallRunningUnlocked = false;
				tornadoesAndVortexesUnlocked = false;
				phasingUnlocked = false;
				flyingUnlocked = false;
				betterFlyingUnlocked = false;
				lightningThrowUnlocked = false;
				invisibilityUnlocked = false;
				vibrating = false;
				vibratingInvisible = false;
			}
		}
	}

	@SubscribeEvent public void pre(RenderPlayerEvent.Pre event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			if (vibratingInvisible)
			{
				event.setCanceled(true);
				player.setInvisible(true);
				player.setSilent(true);
			}
			else
			{
				event.setCanceled(false);
				player.setInvisible(false);
			}
		}
	}
}
