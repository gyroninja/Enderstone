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
import org.enderstone.server.api.messages.Message;

public class PlayerKickEvent extends Event implements Cancellable {

	private boolean cancelled;
	private final Player player;
	private Message reason = null;

	/**
	 * PlayerKickEvent is called when a player
	 * gets kicked from the server.
	 * 
	 * @param player the player that was kicked
	 * @param reason the reason the player was kicked
	 */
	public PlayerKickEvent(Player player, Message reason) {
		this.player = player;
		this.reason = reason;
	}

	/**
	 * Get the player that was kicked.
	 * 
	 * @return The player that was kicked
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the reason the player was kicked.
	 * 
	 * @return The reason the player was kicked
	 */
	public Message getReason() {
		return reason;
	}

	/**
	 * Set the reason the player was kicked.
	 * 
	 * @param reason the reason the player was kicked
	 */
	public void setReason(Message reason) {
		this.reason = reason;
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
