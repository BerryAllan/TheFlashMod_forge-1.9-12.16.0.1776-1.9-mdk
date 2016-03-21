/*
 * Decompiled with CFR 0_110.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.theflash.items;

import com.theflash.handlers.CreativeTabHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemFlashRing extends Item
{
	public ItemFlashRing(String id)
	{
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabHandler.tabFlash);
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par2List, boolean par4)
	{
		par2List.add("\u00a76Right-click to eject suit.");
	}
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player, EnumHand hand)
	{
		if (player.inventory.armorItemInSlot(3) == null && player.inventory.armorItemInSlot(2) == null
				&& player.inventory.armorItemInSlot(1) == null && player.inventory.armorItemInSlot(0) == null)
		{
			//player.setCurrentItemOrArmor(4, new ItemStack(FlashItems.helmetFlash));
			//player.setCurrentItemOrArmor(3, new ItemStack(FlashItems.chestPlateFlash));
			//player.setCurrentItemOrArmor(2, new ItemStack(FlashItems.legsFlash));
			//player.setCurrentItemOrArmor(1, new ItemStack(FlashItems.bootsFlash));
		}
		else if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem()
				.equals(FlashItems.helmetFlash) && player.inventory.armorItemInSlot(2) != null && player.inventory
				.armorItemInSlot(2).getItem().equals(FlashItems.chestPlateFlash)
				&& player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem()
				.equals(FlashItems.legsFlash) && player.inventory.armorItemInSlot(0) != null && player.inventory
				.armorItemInSlot(0).getItem().equals(FlashItems.bootsFlash))
		{
			//player.setCurrentItemOrArmor(4, null);
			//player.setCurrentItemOrArmor(3, null);
			//player.setCurrentItemOrArmor(2, null);
			//player.setCurrentItemOrArmor(1, null);

			FlashArmor.flashFactor = 0;
		}
		return new ActionResult(EnumActionResult.PASS, par1ItemStack);
	}
}

