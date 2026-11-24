package com.minecartorbital;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ExplosiveMinecart;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class MinecartOrbital extends JavaPlugin {

    private static final int DEFAULT_COUNT = 20;
    private static final int DEFAULT_RADIUS = 10;
    private static final int MAX_COUNT = 200;
    private static final int MAX_RADIUS = 50;
    private static final int SPAWN_HEIGHT = 40;

    @Override
    public void onEnable() {
        getLogger().info("Minecart Orbital 1.0.0 enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Minecart Orbital 1.0.0 disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("minecartorbital")) {
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!player.hasPermission("minecartorbital.use")) {
            player.sendMessage("§cYou don't have permission to do that.");
            return true;
        }

        int count = DEFAULT_COUNT;
        int radius = DEFAULT_RADIUS;

        try {
            if (args.length >= 1) count = Integer.parseInt(args[0]);
            if (args.length >= 2) radius = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cUsage: /minecartorbital [count] [radius]");
            return true;
        }

        count = Math.max(1, Math.min(count, MAX_COUNT));
        radius = Math.max(1, Math.min(radius, MAX_RADIUS));

        final int total = count;
        final int r = radius;
        final World world = player.getWorld();
        final Location center = player.getLocation();
        final int maxY = world.getMaxHeight() - 2;

        player.sendMessage("§6Minecart Orbital strike incoming: §e" + total + " §6TNT minecarts!");

        new BukkitRunnable() {
            int spawned = 0;

            @Override
            public void run() {
                // Spawn a few per tick so the server doesn't choke
                for (int i = 0; i < 3 && spawned < total; i++, spawned++) {
                    ThreadLocalRandom rng = ThreadLocalRandom.current();
                    double x = center.getX() + rng.nextDouble(-r, r);
                    double z = center.getZ() + rng.nextDouble(-r, r);
                    double y = Math.min(center.getY() + SPAWN_HEIGHT, maxY);

                    Location spawnLoc = new Location(world, x, y, z);
                    ExplosiveMinecart cart = world.spawn(spawnLoc, ExplosiveMinecart.class);
                    cart.setVelocity(new Vector(0, -1.5, 0));
                }
                if (spawned >= total) {
                    cancel();
                }
            }
        }.runTaskTimer(this, 0L, 1L);

        return true;
    }
}
