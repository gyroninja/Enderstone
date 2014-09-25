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
package org.enderstone.server.api.event.player;

import org.enderstone.server.api.Location;
import org.enderstone.server.api.entity.Player;
import org.enderstone.server.api.event.Cancellable;
import org.enderstone.server.api.event.Event;

public class PlayerMoveEvent extends Event implements Cancellable {

	private boolean cancelled = false;
	private final Player player;
	private final Location fromLocation;
	private final Location toLocation;

	/**
	 * PlayerMoveEvent is called when a player
	 * moves from one location to another.
	 * 
	 * @param player the player that moved
	 * @param fromLocation the location the player moved from
	 * @param toLocation the location the player moved to
	 */
	public PlayerMoveEvent(Player player, Location fromLocation, Location toLocation) {
		this.player = player;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * Get the player that moved.
	 * 
	 * @return The player that moed
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the location the player moved from.
	 * 
	 * @return The location the player moved form
	 */
	public Location getFromLocation() {
		return fromLocation;
	}

	/**
	 * Get the location the player moved to.
	 * 
	 * @return The location the player moved to
	 */
	public Location getToLocation() {
		return toLocation;
	}
}
