package com.theflash.main;

import com.theflash.entities.*;
import com.theflash.models.ModelFlash;
import com.theflash.renderers.RenderFutureFlashLightning;
import com.theflash.renderers.RenderSpeedForceLightningThrow;
import com.theflash.renderers.RenderSpeedForceTrailBlue;
import com.theflash.renderers.RenderSpeedForceTrailYellow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends ServerProxy
{
	private static final ModelBiped bipedBase = new ModelBiped(0.2f);
	private static final ModelFlash flash_armor = new ModelFlash(0.2f);
	
	public static ModelBiped getArmorModel(int id)
	{
		switch (id)
		{
		case 7:
		{
			return flash_armor;
		}
		}
		return bipedBase;
	}

	@Override public void init(FMLInitializationEvent e)
	{
		super.init(e);
		// client-only init code
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		// add renderers
		//RenderingRegistry.registerEntityRenderingHandler(EntitySpeedForceLightningThrow.class,
				//new RenderSpeedForceLightningThrow(rm));

	}

	@Override public void registerRenderThings()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntitySpeedForceTrailBlue.class,
				new IRenderFactory<EntitySpeedForceTrailBase>()
				{

					public Render<? super EntitySpeedForceTrailBase> createRenderFor(RenderManager manager)
					{
						return new RenderSpeedForceTrailBlue(manager);
					}
				});
		RenderingRegistry.registerEntityRenderingHandler(EntitySpeedForceTrailYellow.class,
				new IRenderFactory<EntitySpeedForceTrailBase>()
				{

					public Render<? super EntitySpeedForceTrailBase> createRenderFor(RenderManager manager)
					{
						return new RenderSpeedForceTrailYellow(manager);
					}
				});
		RenderingRegistry.registerEntityRenderingHandler(EntityFutureFlashLightning.class,
				new IRenderFactory<EntitySpeedForceTrailBase>()
				{

					public Render<? super EntitySpeedForceTrailBase> createRenderFor(RenderManager manager)
					{
						return new RenderFutureFlashLightning(manager);
					}
				});
		RenderingRegistry.registerEntityRenderingHandler(EntitySpeedForceLightningThrow.class,
				new IRenderFactory<EntitySpeedForceLightningThrow>()
				{

					public Render<? super EntitySpeedForceLightningThrow> createRenderFor(RenderManager manager)
					{
						return new RenderSpeedForceLightningThrow(manager);
					}
				});

	}

	@Override public EntityPlayer getPlayerEntity(MessageContext ctx)
	{
		return ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx);
	}
}
