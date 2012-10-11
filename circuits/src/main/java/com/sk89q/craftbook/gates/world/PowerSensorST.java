package com.sk89q.craftbook.gates.world;

import org.bukkit.Server;
import org.bukkit.block.Sign;

import com.sk89q.craftbook.ic.AbstractICFactory;
import com.sk89q.craftbook.ic.ChipState;
import com.sk89q.craftbook.ic.IC;
import com.sk89q.craftbook.ic.ICFactory;
import com.sk89q.craftbook.ic.ICUtil;
import com.sk89q.craftbook.ic.ICVerificationException;
import com.sk89q.craftbook.ic.RestrictedIC;
import com.sk89q.craftbook.ic.SelfTriggeredIC;

/**
 * @author Silthus
 */
public class PowerSensorST extends PowerSensor implements SelfTriggeredIC {

    public PowerSensorST(Server server, Sign block, ICFactory factory) {

        super(server, block, factory);
    }

    @Override
    public String getTitle() {

        return "Self Triggered Power Sensor";
    }

    @Override
    public String getSignTitle() {

        return "ST POWER SENSOR";
    }

    @Override
    public void think(ChipState state) {

        state.setOutput(0, isPowered());
    }

    @Override
    public boolean isActive() {

        return true;
    }

    public static class Factory extends AbstractICFactory implements RestrictedIC {

        public Factory(Server server) {

            super(server);
        }

        @Override
        public IC create(Sign sign) {

            try {
                if (sign.getLine(1).equalsIgnoreCase("[MC0270]")) {
                    sign.setLine(1, "[MC0266]");
                    sign.update();
                }
            }
            catch(Exception e){}
            return new PowerSensorST(getServer(), sign, this);
        }

        @Override
        public void verify(Sign sign) throws ICVerificationException {

            ICUtil.verifySignSyntax(sign);
        }
    }
}
