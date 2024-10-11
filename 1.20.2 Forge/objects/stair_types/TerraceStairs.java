package com.mcwstairs.kikoz.objects.stair_types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TerraceStairs extends LoftStairs {

    public static final VoxelShape E = Shapes.or(
    		Block.box(1, 6, 0, 9, 8, 16),
    		Block.box(8, 14, 0, 16, 16, 16),
    		Block.box(0, 0, 5, 2, 2, 11),
    		Block.box(2, 2, 5, 4, 4, 11),
    		Block.box(4, 4, 5, 6, 6, 11),
    		Block.box(8, 8, 5, 10, 10, 11),
    		Block.box(10, 10, 5, 12, 12, 11),
    		Block.box(12, 12, 5, 14, 14, 11));
    public static final VoxelShape S = Shapes.or(Block.box(0, 6, 1, 16, 8, 9),
    		Block.box(0, 14, 8, 16, 16, 16),
    		Block.box(5, 0, 0, 11, 2, 2),
    		Block.box(5, 2, 2, 11, 4, 4),
    		Block.box(5, 4, 4, 11, 6, 6),
    		Block.box(5, 8, 8, 11, 10, 10),
    		Block.box(5, 10, 10, 11, 12, 12),
    		Block.box(5, 12, 12, 11, 14, 14));
    public static final VoxelShape W = Shapes.or(Block.box(7, 6, 0, 15, 8, 16),
    		Block.box(0, 14, 0, 8, 16, 16),
    		Block.box(14, 0, 5, 16, 2, 11),
    		Block.box(12, 2, 5, 14, 4, 11),
    		Block.box(10, 4, 5, 12, 6, 11),
    		Block.box(6, 8, 5, 8, 10, 11),
    		Block.box(4, 10, 5, 6, 12, 11),
    		Block.box(2, 12, 5, 4, 14, 11)
);
    public static final VoxelShape N = Shapes.or(Block.box(0, 6, 7, 16, 8, 15),
    		Block.box(0, 14, 0, 16, 16, 8),
    		Block.box(5, 0, 14, 11, 2, 16),
    		Block.box(5, 2, 12, 11, 4, 14),
    		Block.box(5, 4, 10, 11, 6, 12),
    		Block.box(5, 8, 6, 11, 10, 8),
    		Block.box(5, 10, 4, 11, 12, 6),
    		Block.box(5, 12, 2, 11, 14, 4));
       
	public TerraceStairs(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
}
