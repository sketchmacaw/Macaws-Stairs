package com.mcwstairs.kikoz.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class StairPlatform extends Block {

    public static final VoxelShape PLATFORM = Shapes.or(Block.box(0, 13, 0, 16, 16, 16));

    
	public StairPlatform(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos,
			CollisionContext selectionContext) {

			return PLATFORM;

	}

	public void onBroken(Level worldIn, BlockPos pos) {
		worldIn.levelEvent(1029, pos, 0);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

}
