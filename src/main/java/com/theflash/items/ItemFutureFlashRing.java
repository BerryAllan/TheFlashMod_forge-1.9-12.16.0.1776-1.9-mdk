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

public class ItemFutureFlashRing extends Item
{
	public ItemFutureFlashRing(String id)
	{
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabHandler.tabFlash);
	}

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par2List, boolean par4)
	{
		par2List.add("\u00a76Right-click to eject suit.");
	}

	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player,
			EnumHand hand)
	{

		if (player.inventory.armorItemInSlot(3) == null && player.inventory.armorItemInSlot(2) == null
				&& player.inventory.armorItemInSlot(1) == null && player.inventory.armorItemInSlot(0) == null)
		{
			player.inventory.armorItemInSlot(3).setItem(FlashItems.helmetFutureFlash);
			player.inventory.armorItemInSlot(2).setItem(FlashItems.chestPlateFutureFlash);
			player.inventory.armorItemInSlot(1).setItem(FlashItems.legsFutureFlash);
			player.inventory.armorItemInSlot(0).setItem(FlashItems.bootsFutureFlash);
		}
		else if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem()
				.equals(FlashItems.helmetFutureFlash) && player.inventory.armorItemInSlot(2) != null && player.inventory
				.armorItemInSlot(2).getItem().equals(FlashItems.chestPlateFutureFlash)
				&& player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem()
				.equals(FlashItems.legsFutureFlash) && player.inventory.armorItemInSlot(0) != null && player.inventory
				.armorItemInSlot(0).getItem().equals(FlashItems.bootsFutureFlash))
		{
			player.inventory.armorItemInSlot(3).setItem(null);
			player.inventory.armorItemInSlot(2).setItem(null);
			player.inventory.armorItemInSlot(1).setItem(null);
			player.inventory.armorItemInSlot(0).setItem(null);

			FlashArmor.flashFactor = 0;
		}
		return new ActionResult(EnumActionResult.SUCCESS, par1ItemStack);

	}
}

