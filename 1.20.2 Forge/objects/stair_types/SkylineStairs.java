package com.mcwstairs.kikoz.objects.stair_types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SkylineStairs extends LoftStairs {

	public static final VoxelShape E = Shapes.or(
		    Block.box(0.1, 0, 3, 2, 6, 5),
		    Block.box(0.1, 0, 11, 2, 6, 13),
		    Block.box(0, 6, 0, 8, 8, 16),
		    Block.box(8, 14, 0, 16, 16, 16),
		    Block.box(8, 4, 3, 10, 14, 5),
		    Block.box(2, 4, 3, 8, 6, 5),
		    Block.box(10, 12, 3, 16, 14, 5),
		    Block.box(2, 4, 11, 8, 6, 13),
		    Block.box(8, 4, 11, 10, 14, 13),
		    Block.box(10, 12, 11, 16, 14, 13)
		);

		public static final VoxelShape S = Shapes.or(
		    Block.box(11, 0, 0, 13, 6, 1.9),
		    Block.box(3, 0, 0, 5, 6, 1.9),
		    Block.box(0, 6, -0.1, 16, 8, 7.9),
		    Block.box(0, 14, 7.9, 16, 16, 15.9),
		    Block.box(11, 4, 7.9, 13, 14, 9.9),
		    Block.box(11, 4, 1.9, 13, 6, 7.9),
		    Block.box(11, 12, 9.9, 13, 14, 15.9),
		    Block.box(3, 4, 1.9, 5, 6, 7.9),
		    Block.box(3, 4, 7.9, 5, 14, 9.9),
		    Block.box(3, 12, 9.9, 5, 14, 15.9)
		);

		public static final VoxelShape W = Shapes.or(
		    Block.box(14, 0, 11, 15.9, 6, 13),
		    Block.box(14, 0, 3, 15.9, 6, 5),
		    Block.box(8, 6, 0, 16, 8, 16),
		    Block.box(0, 14, 0, 8, 16, 16),
		    Block.box(6, 4, 11, 8, 14, 13),
		    Block.box(8, 4, 11, 14, 6, 13),
		    Block.box(0, 12, 11, 6, 14, 13),
		    Block.box(8, 4, 3, 14, 6, 5),
		    Block.box(6, 4, 3, 8, 14, 5),
		    Block.box(0, 12, 3, 6, 14, 5)
		);

		public static final VoxelShape N = Shapes.or(
		    Block.box(3, 0, 14, 5, 6, 15.9),
		    Block.box(11, 0, 14, 13, 6, 15.9),
		    Block.box(0, 6, 8, 16, 8, 16),
		    Block.box(0, 14, 0, 16, 16, 8),
		    Block.box(3, 4, 6, 5, 14, 8),
		    Block.box(3, 4, 8, 5, 6, 14),
		    Block.box(3, 12, 0, 5, 14, 6),
		    Block.box(11, 4, 8, 13, 6, 14),
		    Block.box(11, 4, 6, 13, 14, 8),
		    Block.box(11, 12, 0, 13, 14, 6)
		);

       
	public SkylineStairs(Properties properties) {
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
