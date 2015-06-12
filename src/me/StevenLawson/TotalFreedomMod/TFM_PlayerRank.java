package me.StevenLawson.TotalFreedomMod;

import static me.StevenLawson.TotalFreedomMod.TFM_Util.DEVELOPERS;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.FOP_DEVELOPERS;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.FR_DEVELOPERS;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum TFM_PlayerRank
{
    DEVELOPER("a " + ChatColor.DARK_PURPLE + "TotalFreedomMod Developer", ChatColor.DARK_PURPLE + "[TFM Dev]"),
    FOP_DEVELOPER("a " + ChatColor.DARK_PURPLE + "FreedomOP Developer", ChatColor.DARK_PURPLE + "[FOP-Dev]"),
    FRM_DEVELOPER("a " + ChatColor.DARK_PURPLE + "Developer", ChatColor.DARK_PURPLE + "[Dev]"),
    SPEC_EXEC("a " + ChatColor.YELLOW + "Special Executive", ChatColor.YELLOW + "[Spec-Exec]"),
    SYS_ADMIN("a " + ChatColor.DARK_RED + "System-Admin", ChatColor.DARK_RED + "[Sys-Admin]"),
    IMPOSTOR("an " + ChatColor.GRAY + ChatColor.UNDERLINE + "Impostor", ChatColor.GRAY.toString() + ChatColor.UNDERLINE + "[IMP]"),
    NON_OP("a " + ChatColor.GREEN + "Non-OP", ChatColor.GREEN.toString()),
    OP("an " + ChatColor.RED + "OP", ChatColor.RED + "[OP]"),
    TYLER("the " + ChatColor.BLUE + "FreedomRoamingMod Creator", ChatColor.BLUE + "[FRM-Creator]"),
    SUPER("a " + ChatColor.GOLD + "Super Admin", ChatColor.GOLD + "[SA]"),
    TELNET("a " + ChatColor.DARK_GREEN + "Super Telnet Admin", ChatColor.DARK_GREEN + "[STA]"),
    SENIOR("a " + ChatColor.LIGHT_PURPLE + "Senior Admin", ChatColor.LIGHT_PURPLE + "[SrA]"),
    OWNER("the " + ChatColor.BLUE + "Owner of " + ChatColor.LIGHT_PURPLE + "FreedomRoaming", ChatColor.BLUE + "[Owner]"),
    CO_OWNER("the " + ChatColor.BLUE + "Co Owner of " + ChatColor.LIGHT_PURPLE + "FreedomRoaming", ChatColor.BLUE + "[Co Owner]"),
    CONSOLE("the " + ChatColor.DARK_PURPLE + "Console", ChatColor.DARK_PURPLE + "[Console]");
    private final String loginMessage;
    private final String prefix;

    private TFM_PlayerRank(String loginMessage, String prefix)
    {
        this.loginMessage = loginMessage;
        this.prefix = prefix;
    }

    public static String getLoginMessage(CommandSender sender)
    {
        // Handle console
        if (!(sender instanceof Player))
        {
            return fromSender(sender).getLoginMessage();
        }

        // Handle admins
        final TFM_Admin entry = TFM_AdminList.getEntry((Player) sender);
        if (entry == null)
        {
            // Player is not an admin
            return fromSender(sender).getLoginMessage();
        }

        // Custom login message
        final String loginMessage = entry.getCustomLoginMessage();

        if (loginMessage == null || loginMessage.isEmpty())
        {
            return fromSender(sender).getLoginMessage();
        }

        return ChatColor.translateAlternateColorCodes('&', loginMessage);
    }

    public static TFM_PlayerRank fromSender(CommandSender sender)
    {
        if (!(sender instanceof Player))
        {
            return CONSOLE;
        }
        

        if (TFM_AdminList.isAdminImpostor((Player) sender))
        {
            return IMPOSTOR;
        }
        
        else if (sender.getName().equals("ChrisTheDragon"))
        {
            return OWNER;
        }

        else if (sender.getName().equals("tylerhyperHD"))
        {
            return TYLER;
        }

        if (DEVELOPERS.contains(sender.getName()))
        {
            return DEVELOPER;
        }
        
        if (FOP_DEVELOPERS.contains(sender.getName()))
        {
            return FOP_DEVELOPER;
        }

        if (FR_DEVELOPERS.contains(sender.getName()))
        {
            return FRM_DEVELOPER;
        }

        final TFM_Admin entry = TFM_AdminList.getEntryByIp(TFM_Util.getIp((Player) sender));

        final TFM_PlayerRank rank;

        if (entry != null && entry.isActivated())
        {
            if (entry.isSeniorAdmin())
            {
                rank = SENIOR;
            }
            else if (entry.isSystemAdmin())
            {
                rank = SYS_ADMIN;
            }
            else if (entry.isSpecialExecutive())
            {
                rank = SPEC_EXEC;
            }
            else if (entry.isTelnetAdmin())
            {
                rank = TELNET;
            }
            else
            {
                rank = SUPER;
            }
        }
        else
        {
            if (sender.isOp())
            {
                rank = OP;
            }
            else
            {
                rank = NON_OP;
            }

        }
        return rank;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getLoginMessage()
    {
        return loginMessage;
    }
}
