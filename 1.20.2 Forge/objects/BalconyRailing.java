package com.mcwstairs.kikoz.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BalconyRailing extends Block {

	public static final EnumProperty<RailingStyle> STYLE = EnumProperty.create("style", RailingStyle.class);
	
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");

    private static final VoxelShape VEAST = Block.box(16, 0, 0, 18, 15, 16);
    private static final VoxelShape VSOUTH = Block.box(0, 0, 16, 16, 15, 18);
    private static final VoxelShape VWEST = Block.box(-2, 0, 0, 0, 15, 16);
    private static final VoxelShape VNORTH = Block.box(0, 0, -2, 16, 15, 0);
    
    public static final VoxelShape E_COLLISION = Block.box(14, 0, 0, 16, 26, 16);
    public static final VoxelShape S_COLLISION = Block.box(0, 0, 14, 16, 26, 16);
    public static final VoxelShape W_COLLISION = Block.box(0, 0, 0, 1, 26, 16);
    public static final VoxelShape N_COLLISION = Block.box(0, 0, 0, 16, 26, 1);

    
    
    private static final VoxelShape VBASE = Block.box(0, 0, 0, 16, 0.01, 16);
    
    public BalconyRailing(Properties prop) {
        super(prop);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(EAST, false)
            .setValue(WEST, false)
            .setValue(NORTH, false)
            .setValue(SOUTH, false)
            .setValue(STYLE, RailingStyle.CLASSIC));
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter blockReader, BlockPos pos) {
            return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
    	
		BlockPos pos = context.getClickedPos().below();
		LevelAccessor world = context.getLevel();
		BlockState stateBelow = world.getBlockState(pos);

		if (stateBelow.getBlock() instanceof BalconyRailing) {
			return null;
		}
    	
        Direction direction = context.getHorizontalDirection();
        return this.defaultBlockState()
            .setValue(EAST, direction == Direction.EAST)
            .setValue(WEST, direction == Direction.WEST)
            .setValue(NORTH, direction == Direction.NORTH)
            .setValue(SOUTH, direction == Direction.SOUTH);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        VoxelShape shape = VBASE;

        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, VEAST);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, VWEST);
        }
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, VNORTH);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, VSOUTH);
        }

        return shape; 
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext selectionContext) {
        VoxelShape shape = VBASE;

        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, E_COLLISION);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, W_COLLISION);
        }
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, N_COLLISION);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, S_COLLISION);
        }

        return shape; 
    }

    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(EAST, WEST, NORTH, SOUTH, STYLE);
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        Item item = heldItem.getItem();

        if (heldItem.getItem() instanceof BlockItem && ((BlockItem) heldItem.getItem()).getBlock() instanceof BalconyRailing) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS; // Only handle state change on the server side
            }

            Direction direction = player.getDirection();
            BooleanProperty directionProperty = getDirectionProperty(direction);

            // Check if the current direction property is false
            boolean currentDirectionValue = state.getValue(directionProperty);
            
            // Only update and consume the item if the property is currently false
            if (!currentDirectionValue) {
                // Set the new state where the direction property is true
                BlockState newState = state.setValue(directionProperty, true);
                level.setBlock(pos, newState, 3); // Update the block state

                if (!player.getAbilities().instabuild) { // Check if player is in creative mode
                    heldItem.shrink(1); // Decrement by 1
                }

                return InteractionResult.SUCCESS;
            }

            // If no change was made, do not consume the item
            return InteractionResult.PASS;
        } else if (item == Items.SHEARS) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS; // Only handle state change on the server side
            }

            // Cycle through styles
            RailingStyle currentStyle = state.getValue(STYLE);
            RailingStyle[] styles = RailingStyle.values();
            int newIndex = (currentStyle.ordinal() + 1) % styles.length; // Cycle to next style
            RailingStyle newStyle = styles[newIndex];

            BlockState newState = state.setValue(STYLE, newStyle);
            level.setBlock(pos, newState, 3); // Update the block state

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }



    private BooleanProperty getDirectionProperty(Direction direction) {
        switch (direction) {
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction);
        }
    }
    
    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (!player.isCreative()) {
                int dropCount = 0;

                if (state.getValue(EAST)) dropCount++;
                if (state.getValue(WEST)) dropCount++;
                if (state.getValue(NORTH)) dropCount++;
                if (state.getValue(SOUTH)) dropCount++;

                ItemStack stack = new ItemStack(this);

                for (int i = 0; i < dropCount; i++) {
                    popResource(level, pos, stack.copy());
                }
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }



  
	public enum RailingStyle implements StringRepresentable {
		CLASSIC("classic"),
		HARP("harp"),
		SMOOTH("smooth");

		private final String name;

		RailingStyle(final String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getSerializedName() {
			return this.name;
		}
	}
}
