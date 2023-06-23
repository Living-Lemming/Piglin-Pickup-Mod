package com.livinglemming.Events;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ActionResult;

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

                villager.teleport(villager.getX(), villager.getY() + 100, villager.getZ());
                villager.kill();
            }

            return ActionResult.PASS;
        });
    }
}