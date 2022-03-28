package io.github.dinty1.discordchatwhitelist;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePreBroadcastEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class DiscordChatWhitelist extends JavaPlugin {

    @Override
    public void onEnable() {
        DiscordSRV.api.subscribe(this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Subscribe
    public void onDiscordMessagePreBroadcast(DiscordGuildMessagePreBroadcastEvent event) {
        final List<String> allowedWorlds = this.getConfig().getStringList("allowed-worlds");
        final List<Player> recipientsToRemove = new ArrayList<>();

        for (final CommandSender commandSender : event.getRecipients()) {
            final Player player = this.getServer().getPlayer(commandSender.getName());
            if (player == null) return;
            if (!allowedWorlds.contains(player.getWorld().getName())) recipientsToRemove.add(player);
        }

        event.getRecipients().removeAll(recipientsToRemove);
    }
}
