package com.bins.ruins.structure.objects.utilities;

import com.bins.ruins.Ruins;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Glows {


    public static void setGlow(Player p, Entity e, boolean glow) {
        final ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        final WrappedDataWatcher watcher = new WrappedDataWatcher();
        final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);

        watcher.setEntity(e);
        if (glow) {
            if (watcher.getObject(0) == null)
                watcher.setObject(0, serializer, (byte) 0x40);
            else
                watcher.setObject(0, serializer, (byte) watcher.getObject(0) | 1 << 6);
        } else {
            if (watcher.getObject(0) == null)
                watcher.setObject(0, serializer, (byte) 0);
            else
                watcher.setObject(0, serializer, (byte) watcher.getObject(0) & ~1 << 6);
        }

        final PacketContainer packet = pm.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, e.getEntityId());
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        try {
            pm.sendServerPacket(p, packet);
        } catch (InvocationTargetException i) {
            i.printStackTrace();
        }
    }
}
