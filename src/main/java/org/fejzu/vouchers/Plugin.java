package org.fejzu.vouchers;

import org.bukkit.plugin.java.JavaPlugin;
import org.fejzu.vouchers.commands.UseCommand;
import org.fejzu.vouchers.commands.VouchersCommand;
import org.fejzu.vouchers.files.VoucherFile;
import org.fejzu.vouchers.listeners.PlayerInteractListener;
import org.fejzu.vouchers.managers.VoucherManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plugin extends JavaPlugin
{
    private static Plugin instance;
    private final Logger logger = getLogger();
    private VoucherFile voucherFile;
    private VoucherManager voucherManager;

    @Override
    public void onEnable() {
        initialize();
    }

    private void initialize() {
        long start = System.currentTimeMillis();
        logger.log(Level.INFO, "");
        logger.log(Level.INFO, " .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. .----------------. ");
        logger.log(Level.INFO, "| .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. | .--------------. |");
        logger.log(Level.INFO, "| |  _________   | | | ____   ____  | | |     ____     | | | _____  _____ | | |     ______   | | |  ____  ____  | | |  _________   | | |  _______     | | |    _______   | |");
        logger.log(Level.INFO, "| | |_   ___  |  | | ||_  _| |_  _| | | |   .'    `.   | | ||_   _||_   _|| | |   .' ___  |  | | | |_   ||   _| | | | |_   ___  |  | | | |_   __ \\    | | |   /  ___  |  | |");
        logger.log(Level.INFO, "| |   | |_  \\_|  | | |  \\ \\   / /   | | |  /  .--.  \\  | | |  | |    | |  | | |  / .'   \\_|  | | |   | |__| |   | | |   | |_  \\_|  | | |   | |__) |   | | |  |  (__\\_|  | |");
        logger.log(Level.INFO, "| |   |  _|      | | |   \\ \\ / /    | | |  | |    | |  | | |  | '    ' |  | | |  | |         | | |   |  __  |   | | |   |  _|  _   | | |   |  __ /    | | |   '.___`-.   | |");
        logger.log(Level.INFO, "| |  _| |_       | | |    \\ ' /     | | |  \\  `--'  /  | | |   \\ `--' /   | | |  \\ `.___.'\\  | | |  _| |  | |_  | | |  _| |___/ |  | | |  _| |  \\ \\_  | | |  |`\\____) |  | |");
        logger.log(Level.INFO, "| | |_____|      | | |     \\_/      | | |   `.____.'   | | |    `.__.'    | | |   `._____.'  | | | |____||____| | | | |_________|  | | | |____| |___| | | |  |_______.'  | |");
        logger.log(Level.INFO, "| |              | | |              | | |              | | |              | | |              | | |              | | |              | | |              | | |              | |");
        logger.log(Level.INFO, "| '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' | '--------------' |");
        logger.log(Level.INFO, " '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' '----------------' ");
        logger.log(Level.INFO, "");
        logger.log(Level.INFO, "Version: " + getDescription().getVersion());
        logger.log(Level.INFO, "Author: fejzu");
        logger.log(Level.INFO, "");
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException ex) {
            logger.severe("fVouchers requires Spigot to run, you can download");
            logger.severe("Spigot here: https://www.spigotmc.org/wiki/spigot-installation/.");
            getPluginLoader().disablePlugin(this);
            return;
        }
        voucherFile = new VoucherFile();
        voucherManager = new VoucherManager();
        instance = this;
        saveDefaultConfig();
        voucherFile.loadFile();
        voucherManager.loadVouchers();
        new VouchersCommand(this);
        new PlayerInteractListener(this);
        new UseCommand(this);
        logger.log(Level.INFO, "Loaded " + voucherManager.voucherList.size() + " vouchers");
        logger.log(Level.INFO, "Successfully loaded in " + (System.currentTimeMillis() - start) + "ms");
    }

    public void reloadPlugin() {
        long start = System.currentTimeMillis();
        reloadConfig();
        voucherFile.loadFile();
        voucherManager.reloadVouchers();
        logger.info("file vouchers.yml has been reload and loaded: " + Plugin.getInstance().getVoucherManager().voucherList.size() + " vouchers");
        logger.info("file config.yml has been reloaded");
        logger.log(Level.INFO, "Successfully reloaded plugin in " + (System.currentTimeMillis() - start) + "ms");
    }

    public static Plugin getInstance() {
        return instance;
    }

    public VoucherFile getVoucher() {
        return this.voucherFile;
    }

    public VoucherManager getVoucherManager() {
        return this.voucherManager;
    }
}
