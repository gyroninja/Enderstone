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

import org.enderstone.server.api.entity.Player;
import org.enderstone.server.api.event.Cancellable;
import org.enderstone.server.api.event.Event;
import org.enderstone.server.inventory.InventoryHandler;

public class PlayerInventoryClickEvent extends Event implements Cancellable {

	private boolean cancelled = false;
	private final Player player;
	private final InventoryHandler inventory;

	//TODO implement this
	/**
	 * PlayerInventoryClickEvent is called when a player
	 * clicks inside of an inventory.
	 * 
	 * @param player the player that clicked inside an inventory
	 * @param inventory the inventory that was clicked inside
	 */
	public PlayerInventoryClickEvent(Player player, InventoryHandler inventory) {
		this.player = player;
		this.inventory = inventory;
	}

	/**
	 * Get the player that clicked inside an inventory.
	 * 
	 * @return The player that clicked inside an inventory
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the inventory that was clicked inside.
	 * 
	 * @return The inventory that was clicked inside
	 */
	public InventoryHandler getInventory() {
		return inventory;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
