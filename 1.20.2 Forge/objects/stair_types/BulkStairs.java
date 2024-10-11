package com.mcwstairs.kikoz.objects.stair_types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BulkStairs extends LoftStairs {

	public static final VoxelShape E = Shapes.or(
		    Block.box(2, 2, 2, 5, 6, 16),
		    Block.box(5, 4, 2, 6, 6, 16),
		    Block.box(10, 10, 2, 12, 13, 16),
		    Block.box(12, 12, 2, 13, 13, 16),
		    Block.box(0, 2, 0, 6, 4, 16),
		    Block.box(0, 4, 0, 8, 6, 16),
		    Block.box(0, 0, 0, 4, 2, 16),
		    Block.box(8, 10, 0, 14, 12, 16),
		    Block.box(0, 6, 0, 10, 8, 16),
		    Block.box(8, 8, 0, 12, 10, 16),
		    Block.box(8, 12, 0, 16, 16, 16),
		    Block.box(7, 13, 0, 8, 16, 16),
		    Block.box(-1, 5, 0, 0, 8, 16)
		);

		public static final VoxelShape S = Shapes.or(
		    Block.box(0, 2, 2, 14, 6, 5),
		    Block.box(0, 4, 5, 14, 6, 6),
		    Block.box(0, 10, 10, 14, 13, 12),
		    Block.box(0, 12, 12, 14, 13, 13),
		    Block.box(0, 2, 0, 16, 4, 6),
		    Block.box(0, 4, 0, 16, 6, 8),
		    Block.box(0, 0, 0, 16, 2, 4),
		    Block.box(0, 10, 8, 16, 12, 14),
		    Block.box(0, 6, 0, 16, 8, 10),
		    Block.box(0, 8, 8, 16, 10, 12),
		    Block.box(0, 12, 8, 16, 16, 16),
		    Block.box(0, 13, 7, 16, 16, 8),
		    Block.box(0, 5, -1, 16, 8, 0)
		);

		public static final VoxelShape W = Shapes.or(
		    Block.box(11, 2, 0, 14, 6, 14),
		    Block.box(10, 4, 0, 11, 6, 14),
		    Block.box(4, 10, 0, 6, 13, 14),
		    Block.box(3, 12, 0, 4, 13, 14),
		    Block.box(10, 2, 0, 16, 4, 16),
		    Block.box(8, 4, 0, 16, 6, 16),
		    Block.box(12, 0, 0, 16, 2, 16),
		    Block.box(2, 10, 0, 8, 12, 16),
		    Block.box(6, 6, 0, 16, 8, 16),
		    Block.box(4, 8, 0, 8, 10, 16),
		    Block.box(0, 12, 0, 8, 16, 16),
		    Block.box(8, 13, 0, 9, 16, 16),
		    Block.box(16, 5, 0, 17, 8, 16)
		);

		public static final VoxelShape N = Shapes.or(
		    Block.box(2, 2, 11, 16, 6, 14),
		    Block.box(2, 4, 10, 16, 6, 11),
		    Block.box(2, 10, 4, 16, 13, 6),
		    Block.box(2, 12, 3, 16, 13, 4),
		    Block.box(0, 2, 10, 16, 4, 16),
		    Block.box(0, 4, 8, 16, 6, 16),
		    Block.box(0, 0, 12, 16, 2, 16),
		    Block.box(0, 10, 2, 16, 12, 8),
		    Block.box(0, 6, 6, 16, 8, 16),
		    Block.box(0, 8, 4, 16, 10, 8),
		    Block.box(0, 12, 0, 16, 16, 8),
		    Block.box(0, 13, 8, 16, 16, 9),
		    Block.box(0, 5, 16, 16, 8, 17)
		);

       
	public BulkStairs(Properties properties) {
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
