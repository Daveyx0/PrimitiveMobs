package net.daveyx0.primitivemobs.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleBreakingColored extends ParticleBreaking {

    public ParticleBreakingColored(World worldIn, double posXIn, double posYIn, double posZIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, Item itemIn, int meta, float r, float g, float b)
    {
        super(worldIn, posXIn, posYIn, posZIn, xSpeedIn, ySpeedIn, zSpeedIn, itemIn, meta);
        this.setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(itemIn, meta));
        this.particleRed = r/255f;
        this.particleGreen = g/255f;
        this.particleBlue = b/255f;
        this.particleGravity = Blocks.SNOW.blockParticleGravity;
        this.particleScale /= 2.0F;
    }
}
