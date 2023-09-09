package org.fejzu.vouchers.helpers;

import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ChatHelper {
    public static String fix(String text) {
        if (text == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', text).replace(">>", "»").replace("<<", "«").replace("<3", "");
    }

    public static List<String> fix(List<String> raw) {
        List<String> stringList = new ArrayList<>();
        for (String string : raw) {
            stringList.add(fix(string));
        }
        return stringList;
    }

    public static void message(CommandSender sender, String type, String message) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            switch (type.toUpperCase()) {
                case "TITLE": {
                    p.sendTitle(fix(message), "");
                    break;
                }
                case "TITLE_SUBTITLE": {
                    p.sendTitle(fix(message), fix(message));
                    break;
                }
                case "SUBTITLE": {
                    p.sendTitle("", fix(message));
                    break;
                }
                case "ACTIONBAR": {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(fix(message)));
                    break;
                }
                case "CHAT": {
                    p.sendMessage(fix(message));
                    break;
                }
            }
        } else {
            sender.sendMessage(fix(message));
        }
    }
}