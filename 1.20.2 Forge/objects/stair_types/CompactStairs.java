package com.mcwstairs.kikoz.objects.stair_types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CompactStairs extends LoftStairs {

    public static final VoxelShape E = Shapes.or(
    		Block.box(5.3, 0.6, 0.1, 10.7, 10.6, 16.12),
    		Block.box(0.01, 0, 0.1, 5.32, 5.3, 16.12),
    		Block.box(10.7, 6, 0.1, 16, 16, 16.12));
    public static final VoxelShape S = Shapes.or(Block.box(0, 0.6, 5.4, 16, 10.6, 10.8),
    		Block.box(0.0, 0, 0.11, 16.0175, 5.3, 5.422),
    		Block.box(0.0, 6, 10.8, 16.0175, 16, 16.1025));
    public static final VoxelShape W = Shapes.or(Block.box(5.31, 0.6, 0.1, 10.715, 10.6, 16.12),
    		Block.box(10.695, 0, 0.1, 16, 5.3, 16.12),
    		Block.box(0.01, 6, 0.1, 5.31, 16, 16.12));
    public static final VoxelShape N = Shapes.or(Block.box(0.0, 0.6, 5.4175, 16.01, 10.6, 10.81),
    		Block.box(0.0, 0, 10.79, 16.01, 5.3, 16.1),
    		Block.box(0.0, 6, 0.11, 16.01, 16, 5.4175));
       
	public CompactStairs(Properties properties) {
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
