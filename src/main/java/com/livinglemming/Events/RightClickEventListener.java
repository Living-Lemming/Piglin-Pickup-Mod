package com.livinglemming.Events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class RightClickEventListener {
    public static void registerRightClickEvent() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.isSneaking() && entity instanceof VillagerEntity) {
                VillagerEntity villager = (VillagerEntity) entity;
                NbtCompound nbt = new NbtCompound();
                villager.writeCustomDataToNbt(nbt);

                Item spawnEgg = SpawnEggItem.forEntity(villager.getType());
                if (spawnEgg != null) {
                    ItemStack spawnEggStack = new ItemStack(spawnEgg);
                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.put("EntityTag", nbt);
                    spawnEggStack.setNbt(nbtCompound);
                    player.giveItemStack(spawnEggStack);
                }

                villager.remove(Entity.RemovalReason.DISCARDED);
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}