package com.mcwstairs.kikoz.objects.stair_types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class LoftStairs extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape E = Shapes.or(Block.box(0, 5, 0, 8, 8, 16), Block.box(8, 13, 0, 16, 16, 16));
    public static final VoxelShape S = Shapes.or(Block.box(0, 5, 0, 16, 8, 8), Block.box(0, 13, 8, 16, 16, 16));
    public static final VoxelShape W = Shapes.or(Block.box(8, 5, 0, 16, 8, 16), Block.box(0, 13, 0, 8, 16, 16));
    public static final VoxelShape N = Shapes.or(Block.box(0, 5, 8, 16, 8, 16), Block.box(0, 13, 0, 16, 16, 8));
    
    
    
	public LoftStairs(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos,
			CollisionContext selectionContext) {

		switch (state.getValue(FACING)) {

		case NORTH:
			return N;

		case SOUTH:
			return S;

		case EAST:
			return E;

		case WEST:
			return W;
		default:
			return null;

		}
		
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	public void onBroken(Level worldIn, BlockPos pos) {
		worldIn.levelEvent(1029, pos, 0);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}
}
