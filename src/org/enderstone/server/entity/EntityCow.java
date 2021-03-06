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
package org.enderstone.server.entity;

import org.enderstone.server.Main;
import org.enderstone.server.api.Block;
import org.enderstone.server.api.Location;
import org.enderstone.server.entity.goals.GoalWalkAround;
import org.enderstone.server.entity.player.EnderPlayer;
import org.enderstone.server.entity.targets.TargetEntityInRange;
import org.enderstone.server.regions.EnderWorld;

public class EntityCow extends EntityAnimal {

	private static final byte APPEARANCE_ID = 92;
	private final GoalWalkAround walkAround;

	public EntityCow(EnderWorld world, Location location) {
		super(APPEARANCE_ID, world, location);
		this.getNavigator().addGoal(this.walkAround = new GoalWalkAround(this, null));
		this.getNavigator().addTarget(new TargetEntityInRange(this, EnderPlayer.class));
	}

	@Override
	protected String getDamageSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected String getDeadSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected String getRandomSound() {
		if (Main.random.nextInt(2) == 0) {
			return "mob.cow.step";
		} else {
			return "mob.cow.say";
		}
	}

	private int sleepTime = 100;
	private int tick = 0;

	@Override
	public void serverTick() {
		super.serverTick();
		if (tick++ % sleepTime == 0) {
			this.sleepTime = Main.random.nextInt(50) + 50;
			Block block = this.getLocation().getWorld().getHighestBlockAt(random(this.getLocation().getBlockX()), random(this.getLocation().getBlockZ()));
			this.walkAround.setToLocation(block.getLocation());
		}
	}

	private int random(int i) {
		boolean minus = Main.random.nextBoolean();
		int amount = Main.random.nextInt(3) + 2;
		if (minus) {
			return i - amount;
		} else {
			return i + amount;
		}
	}
	
	@Override
	public float getMovementSpeed() {
		return 2.5F;
	}
}
