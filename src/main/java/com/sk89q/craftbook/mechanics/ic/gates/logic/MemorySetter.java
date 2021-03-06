package com.sk89q.craftbook.mechanics.ic.gates.logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.sk89q.craftbook.bukkit.util.CraftBookBukkitUtil;
import org.bukkit.Server;

import com.sk89q.craftbook.ChangedSign;
import com.sk89q.craftbook.mechanics.ic.AbstractIC;
import com.sk89q.craftbook.mechanics.ic.AbstractICFactory;
import com.sk89q.craftbook.mechanics.ic.ChipState;
import com.sk89q.craftbook.mechanics.ic.IC;
import com.sk89q.craftbook.mechanics.ic.ICFactory;
import com.sk89q.craftbook.mechanics.ic.ICManager;
import com.sk89q.craftbook.mechanics.ic.RestrictedIC;

public class MemorySetter extends AbstractIC {

    public MemorySetter(Server server, ChangedSign block, ICFactory factory) {

        super(server, block, factory);
    }

    @Override
    public String getTitle() {

        return "Memory Setter";
    }

    @Override
    public String getSignTitle() {

        return "MEMORY SET";
    }

    @Override
    public void trigger(ChipState chip) {

        setMemory(chip);
    }

    File f;

    @Override
    public void load() {

        f = new File(ICManager.inst().getRomFolder(), getSign().getLine(2) + ".dat");
        if (!f.exists())  {
            try {
                f.createNewFile();
            } catch (IOException e) {
                CraftBookBukkitUtil.printStacktrace(e);
            }
        }
    }

    public boolean setMemory(ChipState chip) {

        try {
            PrintWriter pw = new PrintWriter(f, "UTF-8");
            for (int i = 0; i < chip.getInputCount(); i++)
                pw.print(chip.getInput(i) ? "1" : "0");
            pw.close();
            return true;
        } catch (Exception e) {
            CraftBookBukkitUtil.printStacktrace(e);
        }
        return false;
    }

    public static class Factory extends AbstractICFactory implements RestrictedIC {

        public Factory(Server server) {

            super(server);
        }

        @Override
        public String[] getLongDescription() {

            return new String[] {
                    "The '''MC3300''' sets memory that can be read by the ([[../MC3301/]]) set to access the same file.",
                    "",
                    "This IC writes to a file in the filesystem stored in /plugins/CraftBook/rom/fileName.dat.",
                    "This file can be accessed by other services to allow for external programs to interact with redstone."
            };
        }

        @Override
        public String[] getPinDescription(ChipState state) {

            return new String[] {
                    "Bit to set 1",//Inputs
                    "Bit to set 2",
                    "Bit to set 3",
                    "Nothing"//Outputs
            };
        }

        @Override
        public String getShortDescription() {

            return "Sets the memory state for a file for usage in the MemorySetter/Access IC group.";
        }

        @Override
        public IC create(ChangedSign sign) {

            return new MemorySetter(getServer(), sign, this);
        }
    }
}