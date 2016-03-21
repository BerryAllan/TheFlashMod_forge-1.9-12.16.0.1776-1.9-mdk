// Sometimes, I think compiler ignores all my comments :(

package com.theflash.items;

import com.theflash.main.ClientProxy;
import com.theflash.main.MainRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class FutureFlashArmor extends FlashAbstract
{
	boolean isDown = Keyboard.getEventKeyState();
	private Minecraft gameController;
	private String[] armorTypes = new String[] { "futureFlashHelmet", "futureFlashChestPlate", "futureFlashLegs",
			"futureFlashBoots" };

	public FutureFlashArmor(ArmorMaterial armorMaterial, int renderIndex, EntityEquipmentSlot armorType)
	{
		super(armorMaterial, renderIndex, armorType);
	}

	@Override public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer)
	{
		if (stack.getItem().equals(FlashItems.helmetFutureFlash) || stack.getItem()
				.equals(FlashItems.chestPlateFutureFlash) || stack.getItem().equals(FlashItems.bootsFutureFlash))
		{
			return "theflashmod:textures/armor/future_flash_1.png";
		}
		else if (stack.getItem().equals(FlashItems.legsFutureFlash))
		{
			return "theflashmod:textures/armor/future_flash_2.png";
		}
		else
		{
			return null;
		}
	}

	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
			EntityEquipmentSlot armorSlot, ModelBiped _default)
	{
		ModelBiped armorModel = new ModelBiped();
		if (itemStack != null)
		{
			if (itemStack.getItem() instanceof FutureFlashArmor)
			{
				int type = ((ItemArmor) itemStack.getItem()).armorType.getIndex();
				armorModel = ClientProxy.getArmorModel(7);
			}
			if (armorModel != null)
			{
				armorModel.bipedHead.showModel = armorSlot.getIndex() == 0;
				armorModel.bipedHeadwear.showModel = armorSlot.getIndex() == 0;
				armorModel.bipedBody.showModel = armorSlot.getIndex() == 1 || armorSlot.getIndex() == 2;
				armorModel.bipedRightArm.showModel = armorSlot.getIndex() == 1;
				armorModel.bipedLeftArm.showModel = armorSlot.getIndex() == 1;
				armorModel.bipedRightLeg.showModel = armorSlot.getIndex() == 2 || armorSlot.getIndex() == 3;
				armorModel.bipedLeftLeg.showModel = armorSlot.getIndex() == 2 || armorSlot.getIndex() == 3;
				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				//armorModel.heldItemRight = ((EntityPlayer) entityLiving).inventory.armorItemInSlot(0) != null ? 1 : 0;
				//armorModel.heldItemRight = ((EntityPlayer) entityLiving).inventory.getCurrentItem() != null ? 1 : 0;
				//armorModel.aimedBow = false;

				if (entityLiving instanceof EntityPlayer)
				{
					/*
					EnumAction enumaction = ((EntityPlayer) entityLiving).getHeldItemMainhand().getItemUseAction();

					if (enumaction == EnumAction.BLOCK)
					{
						armorModel.rightArmPose = ModelBiped.ArmPose.BLOCK;
					}
					else if (enumaction == EnumAction.BOW)
					{
						armorModel.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
					*/
				}

				return armorModel;
			}
		}
		return null;
	}

	@Override public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem()
				.equals(FlashItems.helmetFutureFlash) && player.inventory.armorItemInSlot(2) != null && player.inventory
				.armorItemInSlot(2).getItem().equals(FlashItems.chestPlateFutureFlash)
				&& player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem()
				.equals(FlashItems.legsFutureFlash) && player.inventory.armorItemInSlot(0) != null && player.inventory
				.armorItemInSlot(0).getItem().equals(FlashItems.bootsFutureFlash))
		{
			player.addPotionEffect(new PotionEffect(MainRegistry.speedForce, 10, FlashArmor.flashFactor * 8));

			if (FlashArmor.flashFactor >= 1)
			{
				player.capabilities.setFlySpeed((float) (FlashArmor.flashFactor * 0.04));
				player.fallDistance = 0.0f;
				player.jumpMovementFactor = FlashArmor.jumpFactor;

				player.addPotionEffect(
						new PotionEffect(MainRegistry.speedDigging, 10, FlashArmor.flashFactor * 8, false, false));
				player.addPotionEffect(
						new PotionEffect(MainRegistry.speedStrength, 10, (FlashArmor.flashFactor / 2), false, false));
			}
			else
			{
				player.capabilities.isFlying = false;
			}
		}
	}
}
