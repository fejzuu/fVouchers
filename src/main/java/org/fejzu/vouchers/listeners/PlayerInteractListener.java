package org.fejzu.vouchers.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.fejzu.vouchers.Plugin;
import org.fejzu.vouchers.data.Voucher;
import org.fejzu.vouchers.helpers.ChatHelper;

import java.util.Optional;

public class PlayerInteractListener implements Listener
{
    public PlayerInteractListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!Plugin.getInstance().getConfig().getBoolean("redeem_voucher.action")) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().hasItemMeta() && !player.getInventory().getItemInMainHand().getItemMeta().hasLocalizedName()) {
            return;
        }
        Optional<Voucher> optionalVoucher = Plugin.getInstance().getVoucherManager().getVoucher(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName());
        if (!optionalVoucher.isPresent()) {
            return;
        }
        Voucher voucher = optionalVoucher.get();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), voucher.getCommand().replace("{PLAYER}", player.getName()));
        if (player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
        String message = Plugin.getInstance().getConfig().getString("messages.use_voucher.message");
        message = StringUtils.replace(message, "{VOUCHER}", voucher.getName());
        ChatHelper.message(player, Plugin.getInstance().getConfig().getString("messages.use_voucher.type"), message);
    }
}
