package com.livinglemming.Events;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.ActionResult;

public class RightClickEventListener {
    public static void registerRightClickEvent() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.isSneaking() && entity instanceof VillagerEntity villager) {
                NbtCompound nbt = new NbtCompound();
                villager.writeCustomDataToNbt(nbt);

                Item spawnEgg = SpawnEggItem.forEntity(villager.getType());
                if (spawnEgg != null) {
                    ItemStack spawnEggStack = new ItemStack(spawnEgg);

                    NbtCompound nbtCompound = new NbtCompound();
                    NbtCompound textCompound = new NbtCompound();
                    NbtList tooltipList = new NbtList();

                    nbtCompound.put("EntityTag", nbt);
                    tooltipList.add(NbtString.of("{\"text\":\"Profession: [" + villager.getVillagerData().getProfession().toString() +  "]\",\"color\":\"gray\",\"italic\":false}"));
                    textCompound.put("Lore", tooltipList);
                    nbtCompound.put("display", textCompound);

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