package net.hannes.lightningenergy.networking;

import net.hannes.lightningenergy.LightningEnergy;
import net.hannes.lightningenergy.networking.packet.SpawnLightningPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }
    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(LightningEnergy.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;

        //register the Packets here
        net.messageBuilder(SpawnLightningPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SpawnLightningPacket::new)
                .encoder(SpawnLightningPacket::toBytes)
                .consumerMainThread(SpawnLightningPacket::handle)
                .add();

    }




    //methods to send msg to server and player
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
    }
}
