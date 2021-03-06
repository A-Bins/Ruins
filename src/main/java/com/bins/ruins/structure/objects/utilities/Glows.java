package com.bins.ruins.structure.objects.utilities;

import com.bins.ruins.Ruins;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Glows {


    public static void main() {
        FileConfiguration playerdata = Ruins.Companion.getInstance().getConfig();
        Human DDang_ = new Human("DDang_", 20);
        DDang_.getAge(); // 20
        DDang_.getName(); // DDang_
        DDang_.setAge(4);
        DDang_.setName("A_bins");
        Human A_bins = new Human("A_bins", 4);
        A_bins.getAge(); // 4
        A_bins.getName(); // A_bins
        A_bins.setAge(20);
        A_bins.setName("DDang_");
    }

    public static class Human {
        private String name;
        private Integer age;
        public void setAge(Integer age) {
            this.age = age;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public String getName() {
            return name;
        }
        public Human(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }

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
