package com.github.atelieramber.mona.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;

public class LivingEvents {
    public static void onUseItemBlock(RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        player.sendMessage(new StringTextComponent("Clicked block"), player.getUniqueID());
    }
    public static void onUseItem(RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        player.sendMessage(new StringTextComponent("Clicked item"), player.getUniqueID());
    }
}
