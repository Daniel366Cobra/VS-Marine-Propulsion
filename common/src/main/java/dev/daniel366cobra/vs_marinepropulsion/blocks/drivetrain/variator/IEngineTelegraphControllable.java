package dev.daniel366cobra.vs_marinepropulsion.blocks.drivetrain.variator;

import net.minecraft.core.BlockPos;

public interface IEngineTelegraphControllable {

    void setMasterTelegraph(BlockPos blockPos);
    void removeMasterTelegraph();
    void setThrottleOrder(int throttleOrder);


}
