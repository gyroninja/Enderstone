/* 
 * Enderstone
 * Copyright (C) 2014 Sander Gielisse and Fernando van Loenhout
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.enderstone.server.commands.enderstone;

import java.util.List;

import org.enderstone.server.api.Location;
import org.enderstone.server.api.messages.SimpleMessage;
import org.enderstone.server.commands.Command;
import org.enderstone.server.commands.CommandMap;
import org.enderstone.server.commands.CommandSender;
import org.enderstone.server.commands.SimpleCommand;
import org.enderstone.server.entity.pathfinding.PathFinder;
import org.enderstone.server.entity.pathfinding.PathTile;
import org.enderstone.server.entity.player.EnderPlayer;

/**
 *
 * @author gyroninja
 */
public class AiCommand extends SimpleCommand {

	private int avg;

	public AiCommand() {

		super("command.enderstone.ai", "ai", CommandMap.DEFAULT_ENDERSTONE_COMMAND_PRIORITY);
	}

	@Override
	public int executeCommand(Command cmd, String alias, CommandSender sender, String[] args) {

		if (sender instanceof EnderPlayer) {

			final EnderPlayer player = (EnderPlayer) sender;

			PathFinder pathfinder = new PathFinder(null, getBlockUnderLocation(new Location(player.getWorld(), 0.5, 64, 0.5, 0, 0)), getBlockUnderLocation(player.getLocation()), 32);

			long time = System.nanoTime();

			final List<PathTile> path = pathfinder.calculatePath();

			long took = System.nanoTime() - time;

			if (pathfinder.hasPath()) {

				avg += took;
				avg /= 2;

				sender.sendMessage(new SimpleMessage("AI TOOK:" + (((double) took) / 1000000) + "MS AVG: " + (((double) avg) / 1000000) + "MS"));
			}
		}

		return COMMAND_SUCCESS;
	}

	private Location getBlockUnderLocation(Location loc) {

		Location check = loc.clone();

		for (int i = 0;; i++) {

			if (!loc.getWorld().getBlock(check).getBlock().doesInstantBreak()) {

				return check;
			}

			check.add(0, -1, 0);
		}
	}
}
