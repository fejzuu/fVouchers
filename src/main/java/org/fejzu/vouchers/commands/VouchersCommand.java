package org.fejzu.vouchers.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.fejzu.vouchers.Plugin;
import org.fejzu.vouchers.data.Voucher;
import org.fejzu.vouchers.helpers.ChatHelper;
import org.fejzu.vouchers.helpers.ItemsHelper;
import java.util.Optional;

public class VouchersCommand  implements CommandExecutor
{
    public VouchersCommand(Plugin plugin) {
        plugin.getCommand("vouchers").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Plugin.getInstance().getConfig().getString("permission"))) {
            ChatHelper.message(sender, Plugin.getInstance().getConfig().getString("messages.no_permissions.type"), Plugin.getInstance().getConfig().getString("messages.no_permissions.message"));
            return true;
        }
        if (args.length == 0) {
            ChatHelper.message(sender, Plugin.getInstance().getConfig().getString("messages.correct_use.type"), Plugin.getInstance().getConfig().getString("messages.correct_use.message"));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(ChatHelper.fix("&bVouchers &8(&f" + Plugin.getInstance().getVoucherManager().voucherList.size() + "&8):"));
                Plugin.getInstance().getVoucherManager().voucherList.forEach(voucher -> sender.sendMessage(ChatHelper.fix("&f " + voucher.getName())));
                return false;
            } else if (args[0].equalsIgnoreCase("reload")) {
                Plugin.getInstance().reloadPlugin();
                return false;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                if (!(sender instanceof Player)) {
                    Bukkit.getLogger().info("Player only use this command");
                    return true;
                }
                Optional<Voucher> optionalVoucher = Plugin.getInstance().getVoucherManager().getVoucher(args[1]);
                Voucher voucher = optionalVoucher.get();
                Player player = (Player) sender;
                if (optionalVoucher.isPresent()) {
                    ItemStack voucherItem = ItemsHelper.getVoucherItemStack(voucher);
                    player.getInventory().addItem(voucherItem);
                    String message = Plugin.getInstance().getConfig().getString("messages.added_voucher.message");
                    message = StringUtils.replace(message, "{VOUCHER}", args[1]);
                    ChatHelper.message(player, Plugin.getInstance().getConfig().getString("messages.added_voucher.type"), message);
                }
                else {
                    String message = Plugin.getInstance().getConfig().getString("messages.doesnt_exists.message");
                    message = StringUtils.replace(message, "{VOUCHER}", args[1]);
                    ChatHelper.message(player, Plugin.getInstance().getConfig().getString("messages.doesnt_exists.type"), message);
                    return false;
                }
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                Optional<Voucher> optionalVoucher = Plugin.getInstance().getVoucherManager().getVoucher(args[1]);
                Voucher voucher = optionalVoucher.get();
                if (optionalVoucher.isPresent()) {
                    Player players = Bukkit.getPlayer(args[2]);
                    if (players == null) {
                        String message = Plugin.getInstance().getConfig().getString("messages.offline_player.message");
                        message = StringUtils.replace(message, "{PLAYER}", players.getName());
                        ChatHelper.message(sender, Plugin.getInstance().getConfig().getString("messages.offline_player.type"), message);
                        return false;
                    }
                    ItemStack voucherItem = ItemsHelper.getVoucherItemStack(voucher);
                    players.getInventory().addItem(voucherItem);
                    String message = Plugin.getInstance().getConfig().getString("messages.added_voucher_other_player.message");
                    message = StringUtils.replace(message, "{PLAYER}", players.getName());
                    message = StringUtils.replace(message, "{VOUCHER}", args[1]);
                    ChatHelper.message(sender, Plugin.getInstance().getConfig().getString("messages.added_voucher_other_player.type"), message);
                    return false;
                }
                else {
                    String message = Plugin.getInstance().getConfig().getString("messages.doesnt_exists.message");
                    message = StringUtils.replace(message, "{VOUCHER}", args[1]);
                    ChatHelper.message(sender, Plugin.getInstance().getConfig().getString("messages.doesnt_exists.type"), message);
                    return false;
                }
            }
        }
        return false;
    }
}