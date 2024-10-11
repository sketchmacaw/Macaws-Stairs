package com.mcwstairs.kikoz.objects;

import com.mcwstairs.kikoz.objects.BalconyRailing.RailingStyle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StairRailing extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty TOGGLE = BooleanProperty.create("toggle");
	public static final EnumProperty<RailingStyle> STYLE = EnumProperty.create("style", RailingStyle.class);
    
    public static final VoxelShape N = Block.box(0, 0, 0, 3, 20, 16);
    public static final VoxelShape E = Block.box(0, 0, 13, 16, 20, 16);
    public static final VoxelShape S = Block.box(13, 0, 0, 16, 20, 16);
    public static final VoxelShape W = Block.box(0, 0, 0, 16, 20, 3);
    
    public static final VoxelShape N_COLLISION = Block.box(0, 0, 0, 3, 38, 16);
    public static final VoxelShape E_COLLISION = Block.box(0, 0, 13, 16, 38, 16);
    public static final VoxelShape S_COLLISION = Block.box(13, 0, 0, 16, 38, 16);
    public static final VoxelShape W_COLLISION = Block.box(0, 0, 0, 16, 38, 3);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        boolean toggle = state.getValue(TOGGLE);
        switch (state.getValue(FACING)) {
            case NORTH:
                return toggle ? N : S;
            case SOUTH:
                return toggle ? S : N;
            case EAST:
                return toggle ? W : E;
            case WEST:
                return toggle ? E : W;
            default:
                return N;
        }
    }
    
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        boolean toggle = state.getValue(TOGGLE);
        switch (state.getValue(FACING)) {
            case NORTH:
                return toggle ? N_COLLISION : S_COLLISION;
            case SOUTH:
                return toggle ? S_COLLISION : N_COLLISION;
            case EAST:
                return toggle ? W_COLLISION : E_COLLISION;
            case WEST:
                return toggle ? E_COLLISION : W_COLLISION;
            default:
                return N_COLLISION;
        }
     }

    
	@Override
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos) {
		return Shapes.empty();
	}
    
	public StairRailing(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(TOGGLE, false).setValue(FACING, Direction.NORTH).setValue(STYLE, RailingStyle.CLASSIC));
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
	    builder.add(FACING, TOGGLE, STYLE);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {

	    Direction direction = context.getClickedFace();
	    BlockPos blockpos = context.getClickedPos();
	    Level level = context.getLevel();
	    BlockState neighborState = level.getBlockState(blockpos.relative(direction.getOpposite()));
	    Direction playerFacingDirection = context.getHorizontalDirection().getOpposite();

	    double clickX = context.getClickLocation().x - blockpos.getX();
	    double clickZ = context.getClickLocation().z - blockpos.getZ();

	    boolean toggleValue = true; 

	    if (neighborState.hasProperty(FACING)) {
	        Direction neighborFacingDirection = neighborState.getValue(FACING);

	        if (neighborFacingDirection == Direction.NORTH) {
	            toggleValue = clickX <= 0.5; 
	        } else if (neighborFacingDirection == Direction.SOUTH) {
	            toggleValue = clickX > 0.5;  
	        } else if (neighborFacingDirection == Direction.WEST) {
	            toggleValue = clickZ > 0.5;  
	        } else if (neighborFacingDirection == Direction.EAST) {
	            toggleValue = clickZ <= 0.5; 
	        }

	        return this.defaultBlockState()
	                   .setValue(FACING, neighborFacingDirection)
	                   .setValue(TOGGLE, toggleValue);
	    } else {
	        return this.defaultBlockState()
	                   .setValue(FACING, playerFacingDirection)
	                   .setValue(TOGGLE, true);
	    }
	}


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        Item item = heldItem.getItem();

        if (item == Items.SHEARS) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS; 
            }

            RailingStyle currentStyle = state.getValue(STYLE);
            RailingStyle[] styles = RailingStyle.values();
            int newIndex = (currentStyle.ordinal() + 1) % styles.length; 
            RailingStyle newStyle = styles[newIndex];

            BlockState newState = state.setValue(STYLE, newStyle);
            level.setBlock(pos, newState, 3); 

            return InteractionResult.SUCCESS; 
        }

        return InteractionResult.PASS;
    }


	public void onBroken(Level worldIn, BlockPos pos) {
		worldIn.levelEvent(1029, pos, 0);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
}
