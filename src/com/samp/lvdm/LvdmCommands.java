package com.samp.lvdm;

import net.gtaun.shoebill.common.command.Command;
import net.gtaun.shoebill.common.command.CommandHelp;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;

public class LvdmCommands
{
	public LvdmCommands()
	{

	}

	@Command
	@CommandHelp("Kill yourself")
	public boolean kill(Player p)
	{
		p.setHealth(0.0f);
		return true;
	}

	@Command
	@CommandHelp("Give someone some money")
	public boolean givecash(Player p, Player target, int amount)
	{
		if (target == null || target == p)
		{
			p.sendMessage(Color.RED, "Invalid player id.");
			return true;
		}

		if (amount <= 0 || amount > p.getMoney())
		{
			p.sendMessage(Color.WHITE, "Invalid transaction amount.");
			return true;
		}

		p.giveMoney(-amount);
		target.giveMoney(amount);

		p.sendMessage(Color.YELLOW, "You have sent " + target.getName() + "(" + target.getId() + "), $" + amount);
		target.sendMessage(Color.YELLOW, "You have recieved $" + amount + " from " + p.getName() + "(" + p.getId() + ").");

		LvdmGamemode.logger().info("{}({}) has transfered {} to {}({})\n", p.getName(), p.getId(), amount, target.getName(), target.getId());
		return true;
	}

	@Command
	@CommandHelp("Show objective message")
	public boolean objective(Player p)
	{
		p.sendMessage(Color.YELLOW, "This gamemode is faily open, there's no specific win / endgame conditions to meet.");
		p.sendMessage(Color.YELLOW, "In LVDM:Money Grub, when you kill a player, you will receive whatever money they have.");
		p.sendMessage(Color.YELLOW, "Consequently, if you have lots of money, and you die, your killer gets your cash.");
		p.sendMessage(Color.YELLOW, "However, you're not forced to kill players for money, you can always gamble in the");
		p.sendMessage(Color.YELLOW, "Casino's.");

		return true;
	}

	@Command
	@CommandHelp("Show tips")
	public boolean tips(Player p)
	{
		p.sendMessage(Color.YELLOW, "Spawning with just a desert eagle might sound lame, however the idea of this");
		p.sendMessage(Color.YELLOW, "gamemode is to get some cash, get better guns, then go after whoever has the");
		p.sendMessage(Color.YELLOW, "most cash. Once you've got the most cash, the idea is to stay alive(with the");
		p.sendMessage(Color.YELLOW, "cash intact)until the game ends, simple right?");

		return true;
	}

	@Command
	@CommandHelp("Show help message")
	public boolean help(Player p)
	{
		p.sendMessage(Color.YELLOW, "Las Venturas Deathmatch: Money Grub Coded By Jax and the SA-MP Team.");
		p.sendMessage(Color.YELLOW, "Type: /objective : to find out what to do in this gamemode.");
		p.sendMessage(Color.YELLOW, "Type: /givecash [playerid] [money-amount] to send money to other players.");
		p.sendMessage(Color.YELLOW, "Type: /tips : to see some tips from the creator of the gamemode.");

		return true;
	}
}
