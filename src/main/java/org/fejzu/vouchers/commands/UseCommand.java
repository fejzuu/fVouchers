package org.fejzu.vouchers.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fejzu.vouchers.Plugin;
import org.fejzu.vouchers.data.Voucher;
import org.fejzu.vouchers.helpers.ChatHelper;
import org.fejzu.vouchers.helpers.ItemsHelper;

import java.util.Optional;

public class UseCommand implements CommandExecutor {
    public UseCommand(Plugin plugin) {
        plugin.getCommand("use").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!Plugin.getInstance().getConfig().getBoolean("redeem_voucher.command")) {
            return false;
        }
        if (player.getItemInHand().getType() == Material.AIR) {
            return false;
        }
        Optional<Voucher> optionalVoucher = Plugin.getInstance().getVoucherManager().getVoucher(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName());
        if (!optionalVoucher.isPresent()) {
            return false;
        }
        Voucher voucher = optionalVoucher.get();
        if (player.getItemInHand().isSimilar(ItemsHelper.getVoucherItemStack(voucher))) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), voucher.getCommand().replace("{PLAYER}", player.getName()));
            if (player.getItemInHand().getAmount() > 1) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }
            String message = Plugin.getInstance().getConfig().getString("messages.use_voucher.message");
            message = StringUtils.replace(message, "{VOUCHER}", voucher.getName());
            ChatHelper.message(player, Plugin.getInstance().getConfig().getString("messages.use_voucher.type"), message);
            return false;
        }
        return false;
    }
}