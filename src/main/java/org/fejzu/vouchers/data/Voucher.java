package org.fejzu.vouchers.data;

import org.bukkit.Material;

import java.util.List;

public class Voucher
{
    private final String name;
    private final String command;
    private final String displayname;
    private final Material material;
    private final List<String> lores;

    public Voucher(String name, String command, String displayname, List<String> lores, Material material) {
        this.name = name;
        this.command = command;
        this.displayname = displayname;
        this.material = material;
        this.lores = lores;
    }

    public String getName() {
        return this.name;
    }

    public String getCommand() {
        return this.command;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public Material getMaterial() {
        return this.material;
    }

    public List<String> getLores() {
        return this.lores;
    }
}
