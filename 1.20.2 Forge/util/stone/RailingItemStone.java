package com.mcwstairs.kikoz.util.stone;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;

public class RailingItemStone extends BlockItem implements IForgeItem {

	public RailingItemStone(Block block, Properties prop) {
		super(block, prop);
	}
	
	   @OnlyIn(Dist.CLIENT)
	   public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
	      list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	   }
	   
		@OnlyIn(Dist.CLIENT)
		   public MutableComponent getDisplayName() {
		      return Component.translatable("mcwstairs.railing.desc");
		}
		

		
}