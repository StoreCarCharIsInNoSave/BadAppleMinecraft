package touhou.badapple;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public final class BadApple extends JavaPlugin implements Listener {
     private Block startBlock = null;



    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Loaded!");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("badapple")) {
            if (sender instanceof Player) {
                Player ply = (Player) sender;
                if (ply.getWorld().getName().equals("world")) {
                    if (startBlock != null) {
                        ply.sendMessage("Loading images");
                        BufferedImage[] matrix = new BufferedImage[4382];
                        for (int i = 0; i < matrix.length; i++) {
                            try {
                                matrix[i] = ImageIO.read(new File("plugins/BadAppleFrames/" + i + ".jpg"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        ply.sendMessage("Images was loaded");
                        for (int x = 0; x < 80; x++) {
                            for (int y = 0; y < 60; y++) {
                                ply.getWorld().getBlockAt(startBlock.getX() + x, startBlock.getY()+y, startBlock.getZ()).setType(Material.AIR);
                            }
                        }
                        final int[] counter = {0};
                        ply.sendMessage("Starting...");
                        ply.playSound(ply.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,10,1);
                        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                            public void run() {
                                for (int x = 0; x < matrix[counter[0]].getWidth(); x++) {
                                    for (int y = 0; y < matrix[counter[0]].getHeight(); y++) {
                                        if ((new Color(matrix[counter[0]].getRGB(x, y)).getRed() < 127) && (new Color(matrix[counter[0]].getRGB(x, y)).getGreen() < 127) && (new Color(matrix[counter[0]].getRGB(x, y)).getBlue() < 127)) {
                                            ply.getWorld().getBlockAt(startBlock.getX() + x, startBlock.getY()+(60-y), startBlock.getZ()).setType(Material.COAL_BLOCK);
                                        } else {
                                            ply.getWorld().getBlockAt(startBlock.getX() + x, startBlock.getY()+(60-y), startBlock.getZ()).setType(Material.QUARTZ_BLOCK);
                                        }
                                    }
                                }
                                counter[0]++;
                                if (counter[0] > 4381) {
                                    ply.sendMessage("Finished");
                                    return;
                                }

                            }

                        }, 0L, 1L);


                        return true;
                    } else {
                        ply.sendMessage("No start block (/setba)");
                        return false;
                    }
                } else {
                    ply.sendMessage("You can run this command only from the main world");
                    return false;
                }
            } else {
                sender.sendMessage("Just for a players");
                return false;
            }
        }
        if (command.getName().equalsIgnoreCase("setba")) {
            if (sender instanceof Player) {
                Player ply = (Player) sender;
                if (ply.getWorld().getName().equals("world")) {
                    startBlock = ply.getWorld().getBlockAt(ply.getLocation().getBlockX(), ply.getLocation().getBlockY() - 1, ply.getLocation().getBlockZ());
                    ply.sendMessage("Block was saved");
                    return true;
                } else {
                    ply.sendMessage("You can run this command only from the main world");
                    return false;
                }
            } else {
                sender.sendMessage("Just for a player");
                return false;
            }
        }
        return false;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
