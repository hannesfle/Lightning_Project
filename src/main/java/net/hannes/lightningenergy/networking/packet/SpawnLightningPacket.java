package net.hannes.lightningenergy.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnLightningPacket {

    public SpawnLightningPacket() {

    }

    public SpawnLightningPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }



    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()-> {
            //HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            // Here you can change stuff like spawn a lightning bolt
            EntityType.LIGHTNING_BOLT.spawn(level, null, null, player.blockPosition(), MobSpawnType.COMMAND, true, false);


        });
        return true;
    }
}
