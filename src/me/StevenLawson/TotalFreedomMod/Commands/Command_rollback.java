package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.List;
import me.StevenLawson.TotalFreedomMod.Bridge.TFM_CoreprotectBridge;
import me.StevenLawson.TotalFreedomMod.TFM_RollbackManager;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.coreprotect.CoreProtectAPI;
import net.coreprotect.CoreProtectAPI.ParseResult;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(description = "Issues a rollback on a player", usage = "/<command> <[partialname] | undo [partialname]>", aliases = "rb")
public class Command_rollback extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0 || args.length > 2)
        {
            return false;
        }

        if (args.length == 1)
        {
                final Player player = getPlayer(args[0]);
                TFM_Util.adminAction(sender.getName(), "Rolling back player: " + player.getName(), false);
                CoreProtectAPI CoreProtect = TFM_CoreprotectBridge.getCoreProtect();
                if (CoreProtect!=null){ //Ensure we have access to the API
                  List<String[]> lookup = CoreProtect.performRollback(player.getName(), 86400, 0, null, null, null);
                  if (lookup!=null){
                    for (String[] value : lookup){
                      ParseResult result = CoreProtect.parseResult(value);
                      int x = result.getX();
                      int y = result.getY();
                      int z = result.getZ();
                      //...
                    }
                  }
                }
                playerMsg("Rolled back " + player.getName() + ".");
                playerMsg("If this rollback was a mistake, use /rollback undo " + player.getName() + " within 40 seconds to reverse the rollback.");
            return true;
        }

        if (args.length == 2)
        {
            if ("undo".equalsIgnoreCase(args[0]))
            {
                final Player player = getPlayer(args[0]);
                CoreProtectAPI CoreProtect = TFM_CoreprotectBridge.getCoreProtect();
                if (CoreProtect!=null){ //Ensure we have access to the API
                  List<String[]> lookup = CoreProtect.performRestore(player.getName(), 86400, 0, null, null, null);
                  if (lookup!=null){
                    for (String[] value : lookup){
                      ParseResult result = CoreProtect.parseResult(value);
                      int x = result.getX();
                      int y = result.getY();
                      int z = result.getZ();
                      //...
                    }
                  }
                }
                TFM_Util.adminAction(sender.getName(), "Reverting rollback for player: " + player.getName(), false);
                playerMsg("Reverted rollback for " + player.getName() + ".");
                return true;
            }
        }

        return false;
    }
}
