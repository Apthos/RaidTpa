package net.apthos.raidtp;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public final class RaidTpa extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    // LID : TASKID, TIME
    private HashMap<Integer,Pair<Integer, Integer>> IDs = new HashMap<>();

    @EventHandler
    public void onPlayerCommand( PlayerCommandPreprocessEvent e ){
        String Command = e.getMessage();
        if( !Command.split(" ")[0].equalsIgnoreCase("/tpa") ) return;
        if( !Bukkit.getOfflinePlayer( Command.split(" ")[1] ).isOnline() ) return;
         int LID = (int) (Math.random() * 10000 ) + 1;
        IDs.put( LID, new Pair(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                if( ((int)((Pair)IDs.get( LID )).getValue()) >= 5 ){
                    int TASK_ID = ((int)((Pair)IDs.get( LID )).getKey());
                    IDs.remove( LID );
                    Bukkit.getScheduler().cancelTask( TASK_ID );
                } else {
                    e.getPlayer().getLocation().getWorld().playSound( e.getPlayer().getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1 );
                    IDs.put(LID, new Pair( IDs.get( LID ).getKey(), (int) IDs.get( LID ).getValue() +1 ) );
                }

            }
        }, 10, 10), 0) );
    }


}
