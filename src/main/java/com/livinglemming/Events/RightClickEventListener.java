package com.livinglemming.Events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class RightClickEventListener {
    public static void registerRightClickEvent() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.isSneaking() && entity instanceof PiglinEntity piglin) {
                NbtCompound nbt = new NbtCompound();
                piglin.writeCustomDataToNbt(nbt);

                if (!PiglinBrain.wearsGoldArmor(player)) {
                    player.sendMessage(Text.literal("You need to equip some gold armor to pickup a piglin"));
                    return ActionResult.FAIL;
                }

                Item spawnEgg = SpawnEggItem.forEntity(piglin.getType());
                if (spawnEgg != null) {
                    ItemStack spawnEggStack = new ItemStack(spawnEgg);

                    NbtCompound nbtCompound = new NbtCompound();

                    nbtCompound.put("EntityTag", nbt);

                    spawnEggStack.setNbt(nbtCompound);
                    if (player.getInventory().getEmptySlot() != -1) {
                        player.giveItemStack(spawnEggStack);
                    } else {
                        player.dropItem(spawnEggStack, true);
                    }
                }

                piglin.remove(Entity.RemovalReason.DISCARDED);
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!player.isCreative() && world.getBlockState(hitResult.getBlockPos()).getBlock() == Blocks.SPAWNER && player.getStackInHand(hand).getItem() == Items.VILLAGER_SPAWN_EGG) {
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });
    }
}