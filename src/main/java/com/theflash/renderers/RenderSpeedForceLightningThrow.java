package com.theflash.renderers;

import com.theflash.entities.EntitySpeedForceLightningThrow;
import com.theflash.entities.EntitySpeedForceTrailBase;
import com.theflash.lib.RefStrings;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderSpeedForceLightningThrow extends Render<EntitySpeedForceLightningThrow>
{
	protected ResourceLocation location = new ResourceLocation(
			RefStrings.MODID + ":textures/entity/LightningThrow.png");
	
	public RenderSpeedForceLightningThrow(RenderManager rm)
	{
		super(rm);
	}
	
	/**
	 * Actually renders the given argument.
	 */
	public void doRender(EntitySpeedForceLightningThrow entity, double x, double y, double z, float yaw, float pitch)
	{
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);

		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * yaw - 90.0F, 0.0F, 1.0F,
				0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * yaw, 0.0F, 0.0F,
				1.0F);

		GL11.glRotatef(270F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-1 * entity.rotationPitch, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
		
		float f10 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);

		GL11.glScalef(f10, f10, f10);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		
		double size = 16D * entity.length;
		
		for (int i = 0; i < 4; ++i)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f10);
			//tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			//tessellator.getWorldRenderer().pos(0.0D, -2.0D, 0.0D).tex((double) 0, (double) 0);
			//tessellator.getWorldRenderer().pos(size, -2.0D, 0.0D).tex(entity.length, (double) 0);
			//tessellator.getWorldRenderer().pos(size, 2.0D, 0.0D).tex(entity.length, (double) 1);
			//tessellator.getWorldRenderer().pos(0.0D, 2.0D, 0.0D).tex((double) 0, (double) 1);
			tessellator.draw();
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
	
	/**
	 * Returns the location of an entity's texture.
	 */
	protected ResourceLocation getEntityTexture(EntitySpeedForceLightningThrow p_110775_1_)
	{
		return location;
	}
}
