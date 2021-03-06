package com.sk89q.craftbook.mechanics.dispenser;

import com.mcsunnyside.craftbookoptimize.Limiter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemShooter extends Recipe {

    private final Material itemId;

    public ItemShooter(Material id, Material[] materials) {

        super(materials);
        itemId = id;
    }

    @Override
    public boolean doAction(Block block, ItemStack item, Vector velocity, BlockDispenseEvent event) {
        if(!Limiter.ping(event.getBlock().getLocation(),this.getClass())){
            return true;
        }
        event.setItem(new ItemStack(itemId, 1));
        return false;
    }
}