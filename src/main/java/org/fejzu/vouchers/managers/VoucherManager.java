package org.fejzu.vouchers.managers;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.fejzu.vouchers.Plugin;
import org.fejzu.vouchers.data.*;
import java.util.*;

public class VoucherManager
{
    public final List<Voucher> voucherList = new ArrayList<>();

    public void loadVouchers() {
        ConfigurationSection vouchers = Plugin.getInstance().getVoucher().getConfig().getConfigurationSection("vouchers");
        for (String voucherName : vouchers.getKeys(false)) {
            ConfigurationSection section = vouchers.getConfigurationSection(voucherName);
            Voucher voucher = new Voucher(
                    voucherName,
                    section.getString("command"),
                    section.getString("item.displayname"),
                    section.getStringList("item.lores"),
                    Material.matchMaterial(section.getString("item.material")));
            voucherList.add(voucher);
        }
    }

    public Optional<Voucher> getVoucher(String name) {
        return voucherList.stream().filter(voucher -> voucher.getName().equalsIgnoreCase(name)).findAny();
    }

    public void reloadVouchers() {
        voucherList.clear();
        loadVouchers();
    }
}
