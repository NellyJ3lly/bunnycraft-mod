package net.bunnycraft.block.entity.custom;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.entity.ImplementedInventory;
import net.bunnycraft.block.entity.ModBlockEntities;
import net.bunnycraft.entity.custom.AlloyLiquidEntity;
import net.bunnycraft.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class CauldronAlloyerEntity extends BlockEntity implements ImplementedInventory {






    //TODO add hopper support (check end of this class)

    // ------------------------------------------------------------------- CUSTOMIZABLE VARIABLES, use these to tweak stats, looks ect
    static final Map<String, Integer> getAlloyColor = Map.ofEntries(
            Map.entry("steel", 0x95FFFF), // the hex code of each alloy, leave 0x
            Map.entry("rose_gold", 0xFFAFA4),
            Map.entry("netherite", 0x4F3D44)
    );
    static final Map<String, Item> getAlloyBucket = Map.ofEntries(
            Map.entry("empty", Items.BUCKET), // this is a precaution, if the code glitches then it should give the bucket back
            Map.entry("steel", ModItems.MOLTEN_STEEL), // what alloy returns what bucket item
            Map.entry("rose_gold", ModItems.MOLTEN_ROSE_GOLD),
            Map.entry("netherite", ModItems.MOLTEN_NETHERITE)
    );
    static final Map<String, Integer> getAlloyConversionRate = Map.ofEntries(
            Map.entry("steel", 4), // how much alloy is required before you can scoop out a single bucket
            Map.entry("rose_gold", 2),
            Map.entry("netherite", 4)
    );

    public void tryAlloy() { // ----------------------------------- add new alloys here

        //make sure the name is consistent, the final is how many ticks it takes to complete the alloy
        trySpecificAlloy("rose_gold", 1, Items.COPPER_INGOT, Items.GOLD_INGOT, 80);
        trySpecificAlloy("steel", 4, Items.DIAMOND, Items.IRON_INGOT, 160);
        trySpecificAlloy("netherite", 4, Items.GOLD_INGOT, Items.NETHERITE_SCRAP, 140);

    }

    private int checkForHeatFromBlocks(BlockPos pos) { // -----------  add new blocks that should provide heat here
        int heat = 0;

        heat += checkForHeat(pos, Fluids.LAVA, 1);

        return heat;
    }

    private static final float fluidFullLevel = 0.9375f; // dont change this, it is vanilla value pulled from lavaCauldron
    private static final float fluidEmptyLevel = .242f;

    private static final int maxStackPerItem = 8; // adjust how high a stack in the cauldron can go as well as how high the fluid stack can go

    public void clientTick() { // particle stuff, adjust only multipliers!!!

        assert this.getWorld() != null;
        if (alloying && this.getWorld().random.nextDouble() < .5) { // this number is the chance that a particle spawns in a certain tick

            assert this.getWorld() != null;
            Random rand = this.getWorld().getRandom();

            //spawn some silly particles so the player knows its doing its thing
            double spawnX = this.getPos().getX() + ((rand.nextDouble() - .5) * .8) + .5;
            double spawnY = this.getPos().getY() + .3;
            double spawnZ = this.getPos().getZ() + ((rand.nextDouble() - .5) * .8) + .5;

            double velX = (rand.nextDouble() - .5) * .03;
            double velY = rand.nextDouble() * .06;
            double velZ = (rand.nextDouble() - .5) * .03;

            this.getWorld().addParticle(
                    ParticleTypes.SMALL_FLAME, // i didnt look too hard for a good particle but this ones great
                    spawnX, spawnY, spawnZ,
                    velX, velY, velZ
            );
        }
    }



    //-------------------------------------------------------------------- end of customizable variables




    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private final Map<UUID, DelayedTask> scheduledTasks = new LinkedHashMap<>();

    // Inner class to represent a task with its delay
    private static class DelayedTask {
        public final Runnable runnable;
        public int ticksRemaining;

        public DelayedTask(Runnable runnable, int ticks) {
            this.runnable = runnable;
            this.ticksRemaining = ticks;
        }
    }

    public UUID scheduleTask(Runnable runnable, int delayTicks) {
        if (delayTicks < 0) {
            Bunnycraft.LOGGER.warn("Attempted to schedule a task with negative delayTicks: " + delayTicks + ". Executing immediately.");
            runnable.run(); // Execute immediately if delay is negative
            return UUID.randomUUID(); // Return a dummy UUID
        }

        UUID taskId = UUID.randomUUID();
        this.scheduledTasks.put(taskId, new DelayedTask(runnable, delayTicks));
        return taskId;
    }


    private String currentAlloy = "empty";
    private int alloyAmount = 0;

    private boolean needsDisplayRefresh = false;

    private boolean alloying;

    DisplayEntity.ItemDisplayEntity itemDisplay;
    DisplayEntity.ItemDisplayEntity itemDisplay2;
    AlloyLiquidEntity liquidDisplay;

    public CauldronAlloyerEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CAULDRON_ALLOYER_ENTITY, pos, state);

        alloying = false;

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }


    //makes client and server properly sync
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


    //reads and saves inventory

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putBoolean("alloying", this.alloying);
        nbt.putString("current_alloy", this.currentAlloy);
        nbt.putInt("alloy_amount", this.alloyAmount);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        if (nbt.contains("alloying")) {
            this.alloying = nbt.getBoolean("alloying");
        } else {
            this.alloying = false;
        }
        if (nbt.contains("current_alloy")) {
            this.currentAlloy = nbt.getString("current_alloy");
        } else {
            this.currentAlloy = "empty";
        }
        if (nbt.contains("alloy_amount")) {
            this.alloyAmount = nbt.getInt("alloy_amount");
        } else {
            this.alloyAmount = 0;
        }

        this.needsDisplayRefresh = true;
    }

    public void onServerChunkLoad() {

        this.needsDisplayRefresh = true;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound nbt = createNbt(registryLookup);
        nbt.putBoolean("alloying", this.alloying);
        return nbt;

    }

    public void clearItemDisplays() {

        this.scheduledTasks.clear();

        setAlloying(false);

        double x = this.getPos().getX();
        double y = this.getPos().getY();
        double z = this.getPos().getZ();

        //gets all entities used for displays in the cauldron and kills them, helps in case a crash occurred and there are leftover entites with no reference

        Box searchBox = new Box(x, y, z, x + 1.0, y + 1.0, z + 1.0).expand(1);
        Predicate<Entity> filterPredicate = entity -> {
            boolean isRelevantType = entity instanceof AlloyLiquidEntity || entity instanceof DisplayEntity.ItemDisplayEntity;
            boolean isHere = entity.getBlockPos().equals(this.pos) || entity.getPos().isInRange(this.pos.toCenterPos(), 1.5);
            boolean isNotPlayer = !(entity instanceof PlayerEntity); // Important safety check, never get rid of the player

            return isRelevantType && isHere && isNotPlayer;
        };


        if (this.getWorld() instanceof ServerWorld server) {
            List<Entity> foundEntities = server.getEntitiesByType(TypeFilter.instanceOf(Entity.class), searchBox, filterPredicate);

            for (Entity entity : foundEntities) {
                entity.discard();
            }

            this.itemDisplay = null;
            this.itemDisplay2 = null;
            this.liquidDisplay = null;
        }


        this.markDirty();
    }

    private boolean checkForItems(Item item1, Item item2) {
        if (this.getStack(0).isOf(item1) && this.getStack(1).isOf(item2)) {
            return true;
        }
        if (this.getStack(1).isOf(item1) && this.getStack(0).isOf(item2)) {
            return true;
        }
        return false;
    }

    private float getFluidLevel(int alloyAmount) {

        float slope = (fluidFullLevel - fluidEmptyLevel) / maxStackPerItem;

        return slope * alloyAmount + fluidEmptyLevel;

    }



    private void trySpecificAlloy(String name, int heat, Item ingredient1, Item ingredient2, int ticks) {
        if (checkForHeatFromBlocks(this.getPos()) >= heat && checkForItems(ingredient1, ingredient2) && !this.alloying) {
            if (this.currentAlloy.equals("empty") || this.currentAlloy.equals(name) && this.alloyAmount < maxStackPerItem) {
                setAlloying(true);



                scheduleTask(() -> {
                    this.getStack(0).decrement(1);
                    this.getStack(1).decrement(1);

                    this.currentAlloy = name;

                    this.alloyAmount++;

                    setAlloying(false);

                    if (this.getWorld() instanceof ServerWorld server) {
                        this.updateLiquidDisplay(this.currentAlloy, server, getFluidLevel(this.alloyAmount));
                        this.updateSlot0Display(this.getStack(0), server);
                        this.updateSlot1Display(this.getStack(1), server);

                        server.playSound(null, this.getPos(), SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
                    }

                    tryAlloy();

                    this.markDirty();

                }, ticks); // change this to adjust how long a specific alloy takes to produce
            }
        }
    }



    //ticks the block on the server, which allows for animations and other stuff
    public void serverTick() {


        if (this.needsDisplayRefresh) {
            if (this.getWorld() instanceof ServerWorld server) {
                this.needsDisplayRefresh = false;
                clearItemDisplays();

                updateSlot0Display(this.getStack(0), server);
                updateSlot1Display(this.getStack(1), server);
                updateLiquidDisplay(this.currentAlloy, server, getFluidLevel(this.alloyAmount));

                tryAlloy();
            }
        }

        assert this.getWorld() != null;

        if (this.alloying && this.getWorld().random.nextDouble() < .1) {
            if (this.getWorld() instanceof ServerWorld server) {
                server.playSound(null, this.getPos(), SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, .7f, 1);
            }
        }

        if (this.getWorld().random.nextDouble() < .005 && this.alloyAmount != 0) {
            if (this.getWorld() instanceof ServerWorld server) {
                server.playSound(null, this.getPos(), SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, .5f, .7f);
            }
        }

        // Create a list to hold tasks to be executed in this tick
        List<UUID> tasksToExecute = new ArrayList<>();

        // Iterate over the map to find tasks ready for execution
        // DO NOT modify scheduledTasks within this loop directly!
        for (Map.Entry<UUID, DelayedTask> entry : scheduledTasks.entrySet()) {
            DelayedTask task = entry.getValue();

            task.ticksRemaining--; // Decrement the remaining ticks

            if (task.ticksRemaining <= 0) {
                tasksToExecute.add(entry.getKey()); // Mark this task for execution and removal
            }
        }

        // Now, execute tasks and remove them outside the iteration
        for (UUID taskId : tasksToExecute) {
            DelayedTask task = scheduledTasks.remove(taskId); // Remove the task from the map
            if (task != null) {
                try {
                    task.runnable.run();
                } catch (Exception e) {
                    Bunnycraft.LOGGER.error("Error executing delayed task {}: {}", taskId, e.getMessage());
                }
            }
        }


    }

    public boolean use ( @Nullable ItemStack stack, PlayerEntity player, World world) {



        if (stack != null && ModItems.ingotList.containsValue(stack.getItem()) && !world.isClient()) { // if the item is a valid alloyable ingot


            if(insertStack(stack, player, world)) { // inserts the stack into the inventory
                tryAlloy(); // and check if an alloy recipe has been successfully created
            }

            this.markDirty(); // tells the game to save the data

            return false;

        } else if (stack != null && stack.isOf(Items.BUCKET) && !world.isClient() && this.alloyAmount >= getAlloyConversionRate.get(this.currentAlloy)) { // if the item is an empty bucket

            if (returnLiquids(player, stack)) {

                if(this.alloyAmount == 0) {
                    this.currentAlloy = "empty";

                    if (this.getWorld() instanceof ServerWorld server) {
                        updateLiquidDisplay(this.currentAlloy, server, 0);
                    }
                }
            }

            this.markDirty(); // tells the game to save the data

            return false;

        } else if (!world.isClient()) { // the item was not in the ingot list or it was null/empty
            return returnItems(player);
        } else {
            return false;
        }

    }

    private boolean returnLiquids (PlayerEntity player, ItemStack stack) {

        boolean successfullyInserted;

        if (player.getInventory().getStack(player.getInventory().selectedSlot).getCount() == 1) { // if the selected slot is empty insert the new bucket there

            //replaces the bucket if there was only 1 bucket there
            player.getInventory().setStack(player.getInventory().selectedSlot, getAlloyBucket.get(this.currentAlloy).getDefaultStack().copyWithCount(1));

            successfullyInserted = true;
        } else {


            //inserts the item
            if(player.getInventory().getEmptySlot() != -1) { // if its -1 then theres no space for a new item

                //insert the alloy filled bucket
                player.getInventory().insertStack(getAlloyBucket.get(this.currentAlloy).getDefaultStack().copyWithCount(1));
                stack.decrement(1); // get rid of the empty bucket

                successfullyInserted = true;

            } else {

                successfullyInserted = false;

            }
        }


        if (successfullyInserted) {
            this.alloyAmount -= getAlloyConversionRate.get(this.currentAlloy);


            if (this.getWorld() instanceof ServerWorld server) {
                updateLiquidDisplay(this.currentAlloy, server, getFluidLevel(this.alloyAmount));
            }

            this.markDirty();

            tryAlloy();

            return true;
        }
        return false;
    }


    public boolean returnItems(PlayerEntity player) {

        if (this.alloying) {

            //gives the player all but 1 of each item
            player.getInventory().insertStack(this.getStack(0).copyWithCount(this.getStack(0).getCount() - 1));
            player.getInventory().insertStack(this.getStack(1).copyWithCount(this.getStack(1).getCount() - 1));

            //gets rid of all but 1 of the items
            this.getStack(0).decrement(this.getStack(0).getCount() - 1);
            this.getStack(1).decrement(this.getStack(1).getCount() - 1);

            //dont update the displays cause there still should be al least 1 of each item in there

            return false; // dont pass to default cauldron interaction

        } else if (this.getStack(0) != ItemStack.EMPTY || this.getStack(1) != ItemStack.EMPTY){

            player.getInventory().insertStack(this.getStack(0));
            player.getInventory().insertStack(this.getStack(1));

            this.setStack(0, ItemStack.EMPTY);
            this.setStack(1, ItemStack.EMPTY);


            this.updateSlot0Display(ItemStack.EMPTY, (ServerWorld) this.getWorld());
            this.updateSlot1Display(ItemStack.EMPTY, (ServerWorld) this.getWorld());

            return false;
        } else {
            return true;
        }
    }

    private boolean insertStack (ItemStack stack, PlayerEntity player, World world) {



        //if the inventory already has the stack, merge the stacks
        if (ItemStack.areItemsEqual(this.getStack(0), stack)) {

            if (this.getStack(0).getCount() >= maxStackPerItem) { // if theres no room return
                return false;
            } else if (maxStackPerItem - this.getStack(0).getCount() <= stack.getCount()){ // if player has more than enough or equal to enough insert the max amount possible
                this.getStack(0).increment(maxStackPerItem - this.getStack(0).getCount());
                stack.decrement(maxStackPerItem - this.getStack(0).getCount());
                return true;
            } else {
                this.getStack(0).increment(stack.getCount());
                stack.copyAndEmpty();
                return true;
            }

        } else if (ItemStack.areItemsEqual(this.getStack(1), stack)) {

            if (this.getStack(1).getCount() >= maxStackPerItem) { // if theres no room return
                return false;
            } else if (maxStackPerItem - this.getStack(1).getCount() <= stack.getCount()){ // if player has more than enough or equal to enough insert the max amount possible
                this.getStack(1).increment(maxStackPerItem - this.getStack(1).getCount());
                stack.decrement(maxStackPerItem - this.getStack(1).getCount());
                return true;
            } else {
                this.getStack(1).increment(stack.getCount());
                stack.copyAndEmpty();
                return true;
            }

        } else if (this.getStack(0).isEmpty()){

            if (maxStackPerItem <= stack.getCount()) {
                this.setStack(0, stack.copyWithCount(maxStackPerItem));
                stack.decrement(maxStackPerItem);
            } else {
                this.setStack(0, stack.copyAndEmpty());
            }


            this.updateSlot0Display(this.getStack(0), (ServerWorld) world);

            return true;

        } else if (this.getStack(1).isEmpty()) {

            if (maxStackPerItem <= stack.getCount()) {
                this.setStack(1, stack.copyWithCount(maxStackPerItem));
                stack.decrement(maxStackPerItem);
            } else {
                this.setStack(1, stack.copyAndEmpty());
            }

            this.updateSlot1Display(this.getStack(1), (ServerWorld) world);

            return true;
        } else {

            returnItems(player);

            return false;
        }


    }

    private int checkForHeat(BlockPos pos, FlowableFluid thisFluid, int heatForBlock) {

        int heat = 0;

        heat += isHotOrCold(new BlockPos(this.getPos().getX(), this.getPos().getY() - 1, this.getPos().getZ()), thisFluid, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX() - 1 , this.getPos().getY(), this.getPos().getZ()), thisFluid, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX() + 1, this.getPos().getY(), this.getPos().getZ()), thisFluid, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ() - 1), thisFluid, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ() + 1), thisFluid, heatForBlock);

        return heat;

    }

    private int checkForHeat(BlockPos pos, Block block, int heatForBlock) {

        int heat = 0;

        heat += isHotOrCold(new BlockPos(this.getPos().getX(), this.getPos().getY() - 1, this.getPos().getZ()), block, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX() - 1, this.getPos().getY(), this.getPos().getZ()), block, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX() + 1, this.getPos().getY(), this.getPos().getZ()), block, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ() - 1), block, heatForBlock);
        heat += isHotOrCold(new BlockPos(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ() + 1), block, heatForBlock);

        return heat;

    }

    private int isHotOrCold(BlockPos pos, FlowableFluid thisFluid, int heatForBlock) {

        assert this.getWorld() != null;
        BlockState block = this.getWorld().getBlockState(pos);
        FluidState fluid = block.getFluidState();

        if (fluid.isOf(thisFluid) && fluid.isStill()) {
            return heatForBlock;
        } else {
            return 0;
        }
    }

    private int isHotOrCold(BlockPos pos, Block block, int heatForBlock) {

        assert this.getWorld() != null;
        if (this.getWorld().getBlockState(pos).isOf(block)) {
            return heatForBlock;
        } else {
            return 0;
        }
    }

    private void updateLiquidDisplay(String liquid, ServerWorld world, float height) {

        BlockPos currentPos = this.getPos();

        if (liquid.equals("empty")) {
            if (this.liquidDisplay != null) {
                this.liquidDisplay.discard();
                this.liquidDisplay = null;
            }
        } else {
            if (this.liquidDisplay == null) {
                this.liquidDisplay = new AlloyLiquidEntity(world, currentPos.getX() + 0.5, currentPos.getY() + height, currentPos.getZ() + 0.5, getAlloyColor.get(liquid));

                world.spawnEntity(this.liquidDisplay);
            }
            this.liquidDisplay.requestTeleport(currentPos.getX() + 0.5, currentPos.getY() + height, currentPos.getZ() + 0.5);
            this.liquidDisplay.setHueColor(getAlloyColor.get(liquid));
        }
    }

    private void updateSlot0Display(ItemStack itemStack, ServerWorld world) {
        BlockPos currentPos = this.getPos();

        // Handle itemDisplay (for slot 0)
        if (itemStack.isEmpty()) {
            if (this.itemDisplay != null) {
                this.itemDisplay.discard();
                this.itemDisplay = null;
            }
        } else {
            if (this.itemDisplay == null) {
                this.itemDisplay = new DisplayEntity.ItemDisplayEntity(EntityType.ITEM_DISPLAY, world);

                this.itemDisplay.refreshPositionAndAngles(currentPos.getX() + 0.5, currentPos.getY() + 0.25, currentPos.getZ() + 0.5, 30, 90);
                world.spawnEntity(this.itemDisplay);
            }
            this.itemDisplay.setItemStack(itemStack);

            this.itemDisplay.setTransformationMode(ModelTransformationMode.GROUND);
        }
    }

    private void updateSlot1Display(ItemStack itemStack, ServerWorld world) {
        BlockPos currentPos = this.getPos();

        // Handle itemDisplay2 (for slot 1)
        if (itemStack.isEmpty()) {
            if (this.itemDisplay2 != null) {
                this.itemDisplay2.discard();
                this.itemDisplay2 = null;
            }
        } else {
            if (this.itemDisplay2 == null) {
                this.itemDisplay2 = new DisplayEntity.ItemDisplayEntity(EntityType.ITEM_DISPLAY, world);

                this.itemDisplay2.refreshPositionAndAngles(currentPos.getX() + 0.5, currentPos.getY() + 0.3, currentPos.getZ() + 0.3, -20, 80);
                world.spawnEntity(this.itemDisplay2);
            }
            this.itemDisplay2.setItemStack(itemStack);

            this.itemDisplay2.setTransformationMode(ModelTransformationMode.GROUND);

        }
    }



    // --- Setter method that triggers the sync ---
    public void setAlloying(boolean newValue) {
        if (this.alloying != newValue) {
            this.alloying = newValue;
            this.markDirty(); // Mark the block entity as needing to be saved
            if (this.world != null && !this.world.isClient()) {
                // If on the server, send an update to clients.
                // This will internally call toUpdatePacket() and send the NBT from toInitialChunkDataNbt().
                assert this.getWorld() != null;
                this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
            }
        }
    }

    //these two methods prevent hopppers from inserting and removing items

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {

        return false;
    }
}
