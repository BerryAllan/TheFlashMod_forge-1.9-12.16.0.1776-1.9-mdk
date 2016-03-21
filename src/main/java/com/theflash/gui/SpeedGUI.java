package com.theflash.gui;

import com.theflash.items.FlashArmor;
import com.theflash.handlers.SpeedForceHandler;
import com.theflash.items.FlashItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

public class SpeedGUI
{
	EntityPlayer player;
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static void renderToHud()
	{
		if ((mc.inGameHasFocus || (mc.currentScreen != null && (mc.currentScreen instanceof GuiChat)))
				&& !mc.gameSettings.showDebugInfo)
		{
			ScaledResolution res = new ScaledResolution(mc);
			FontRenderer fontRender = mc.fontRendererObj;
			int width = res.getScaledWidth();
			int height = res.getScaledHeight();
			
			EntityPlayer player = mc.thePlayer;
			
			if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem().equals(FlashItems.helmetFlash)
					&& player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem()
					.equals(FlashItems.chestPlateFlash) && player.inventory.armorItemInSlot(1) != null && player
					.inventory.armorItemInSlot(1).getItem().equals(FlashItems.legsFlash) && player.inventory.armorItemInSlot(0) != null
					&& player.inventory.armorItemInSlot(0).getItem().equals(FlashItems.bootsFlash))
			{
				String FlashFactor = "Flash Factor: ";
				String test = Integer.toString(FlashArmor.flashFactor);
				int x = width / 2 + 175;
				int y = height / 2 + 75;
				int color = 0xffffff;
				mc.fontRendererObj.drawStringWithShadow(test, x, y, color);
				mc.fontRendererObj.drawStringWithShadow(FlashFactor, x - 75, y, color);
				
				String SloMoFactor = "Slo-Mo Factor: ";
				String test2 = Float.toString(1.0F - SpeedForceHandler.slowMoFactor);
				int x1 = width / 2 + 175;
				int y1 = height / 2 + 75;
				int color1 = 0xffffff;
				mc.fontRendererObj.drawStringWithShadow(test2, x, y + 15, color);
				mc.fontRendererObj.drawStringWithShadow(SloMoFactor, x - 75, y + 15, color);
				
				String Speed = "Speed: ";
				String test3 = Double.toString(SpeedForceHandler.speedkmh);
				int x2 = width / 2 + 175;
				int y2 = height / 2 + 75;
				int color2 = 0xffffff;
				mc.fontRendererObj.drawStringWithShadow(test3, x, y - 15, color);
				mc.fontRendererObj.drawStringWithShadow(Speed, x - 75, y - 15, color);
			}
			else if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem()
					.equals(FlashItems.helmetFutureFlash) && player.inventory.armorItemInSlot(2) != null && player
					.inventory.armorItemInSlot(2).getItem().equals(FlashItems.chestPlateFutureFlash)
					&& player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem()
					.equals(FlashItems.legsFutureFlash) && player.inventory.armorItemInSlot(0) != null && player
					.inventory.armorItemInSlot(0).getItem().equals(FlashItems.bootsFutureFlash))
			{
				String FlashFactor = "Flash Factor: ";
				String test = Integer.toString(FlashArmor.flashFactor);
				int x = width / 2 + 175;
				int y = height / 2 + 75;
				int color = 0xffffff;
				mc.fontRendererObj.drawStringWithShadow(test, x, y, color);
				mc.fontRendererObj.drawStringWithShadow(FlashFactor, x - 75, y, color);

				String SloMoFactor = "Slo-Mo Factor: ";
				String test2 = Float.toString(1.0F - SpeedForceHandler.slowMoFactor);
				int x1 = width / 2 + 175;
				int y1 = height / 2 + 75;
				int color1 = 0xffffff;
				mc.fontRendererObj.drawStringWithShadow(test2, x, y + 15, color);
				mc.fontRendererObj.drawStringWithShadow(SloMoFactor, x - 75, y + 15, color);

				String Speed = "Speed: ";
				String test3 = Double.toString(SpeedForceHandler.speedkmh);
				int x2 = width / 2 + 175;
				int y2 = height / 2 + 75;
				int color2 = 0xffffff;
				mc.fontRendererObj.drawStringWithShadow(test3, x, y - 15, color);
				mc.fontRendererObj.drawStringWithShadow(Speed, x - 75, y - 15, color);
			}
		}
	}
}
