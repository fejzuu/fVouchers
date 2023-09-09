package org.fejzu.vouchers.helpers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.fejzu.vouchers.data.Voucher;

public class ItemsHelper
{
    public static ItemStack getVoucherItemStack(Voucher voucher) {
        ItemStack item = new ItemStack(voucher.getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatHelper.fix(voucher.getDisplayname()));
        meta.setLore(ChatHelper.fix(voucher.getLores()));
        meta.setLocalizedName(voucher.getName());
        item.setItemMeta(meta);
        return item;
    }
}