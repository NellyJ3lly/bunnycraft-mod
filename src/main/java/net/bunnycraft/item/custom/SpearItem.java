package net.bunnycraft.item.custom;


import net.bunnycraft.entity.custom.SpearEntity;
import net.bunnycraft.item.ModItems;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;


public class SpearItem extends ToolItem implements ProjectileItem {

    //initializes the tracked variables so they can be accessed from anywhere and be updated


    private final float divergence; //divergence is how accurate the throw is, bigger number is worse accuracy
    private final float speed; // how hard its thrown

    public SpearItem(ToolMaterial material, Item.Settings settings, float deviation, float throwSpeed) {

        super(material, settings);
        //divergence is the variable in this class, accuracy is declared in the registering of the spear, same with the throw speed
        divergence = deviation;
        speed = throwSpeed;
    }

    //helper method for getting the tool material
    public ToolMaterial getToolMaterial() {
        return super.getMaterial();
    }

    //attributes, passed in when declaring a new SpearItem
    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }


    //checks if an entity was hit using the item, idk why its needed
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    //damages the item
    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

    //sets animations when the spear is used with right click
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    //defines the max use time. its such a high value (1hour) so as to not terminate the action early like eating a consumable would normally
    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }


    //called when the item is not being used anymore, requires stack.consume to be called
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

        //checks if the user is a player and creates a variable for the player
        if (user instanceof PlayerEntity player) {

            //gets the time the spear was used for
            int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
            if (useTime > 10) {

                //checks if the spear has loyalty
                int slot = -2;
                if (world instanceof ServerWorld serverWorld && EnchantmentHelper.getTridentReturnAcceleration(serverWorld, stack, user) > 0) {
                    slot = getUseSlot(stack, player);
                }

                //breaks instead of throws if about to break
                if (isAboutToBreak(stack)) {

                    //ensures the stacks damage is about to break for redundancy
                    stack.setDamage(stack.getMaxDamage());
                    //breaks the item stack on use
                    stack.damage(1, player, EquipmentSlot.MAINHAND);

                } else { // if it wasnt about to break do normal stuff

                    //play the sound pitched a bit lower so it sounds different
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 0.8F, 0.8F);


                    //damages then removes the item if the player is not in creative
                    if (!player.isInCreativeMode()) {
                        stack.damage(1, player, EquipmentSlot.MAINHAND);
                        player.getInventory().removeOne(stack);

                        //inserts the slot reserver at the slot that the spear was used from
                            if (slot == -1) {//-1 means it wasnt found in main inventory which means it was likely in offhand
                                player.getInventory().offHand.set(0, ModItems.EMPTY_SPEAR_SLOT.getDefaultStack());
                            } else if (slot >= 0 && slot <= 36 ){
                                player.getInventory().setStack(slot, ModItems.EMPTY_SPEAR_SLOT.getDefaultStack());
                            } // -2 means should skip because it doesnt have loyalty or it was client world
                    }

                    //create the entity idk why i need to check if the world is not client
                    if (!world.isClient) {

                        //passes in all variables needed
                        SpearEntity thrownSpear = new SpearEntity(world, player, stack,this);
                        //sets the velocity of the new spear
                        thrownSpear.setVelocity(player, player.getPitch(), player.getYaw(), 0f, speed, divergence);

                        //actually spawns in the entity
                        world.spawnEntity(thrownSpear);

                    }
                }
            }
        }
    }



    // gets the keypress and calls consume
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        //if (isAboutToBreak(itemStack)) {
        //    return TypedActionResult.fail(itemStack);
        //} else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        //}
    }

    //helper method for checking if its about to break
    private static boolean isAboutToBreak(ItemStack stack) {
        return stack.getDamage() >= stack.getMaxDamage() - 1;
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        SpearEntity thrown = new SpearEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1));
        thrown.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return thrown;
    }


    public int getUseSlot(ItemStack stack, PlayerEntity player) {
        //finds the slot in the inventory that the item is in, does not check outside of the 36 slot array
        if(player.getInventory().getStack(player.getInventory().selectedSlot) != stack) {
            return -1; // returns -1 if item was not found
        } else {
            return player.getInventory().selectedSlot;
        }
    }
}
