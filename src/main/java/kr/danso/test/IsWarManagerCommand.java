package kr.danso.test;

import kr.danso.test.impl.SettingGui;
import kr.danso.test.impl.data.WarData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IsWarManagerCommand implements CommandExecutor {

    private void send(CommandSender sender, String s) {
        sender.sendMessage("§a§l§oIswar §f" + s);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            send(sender,"§c권한이 없습니다.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            send(sender,"/isw create [이름]");
            send(sender,"/isw setting [이름]");
        } else if (args[0].equals("create")) {
            if (args.length == 1) {
                send(sender,"이름을 적어주세요");
                return true;
            }
            new WarData(args[1],player);
            send(sender,args[1] + "생성");
        } else if (args[0].equals("setting")) {
            if (args.length == 1) {
                send(sender,"이름을 적어주세요");
                return true;
            }
            if (WarData.getWars().get(args[1]) == null) {
                send(sender,"없음");
                return true;
            }
            SettingGui.open(player,args[1]);
        }
        return false;
    }
}
