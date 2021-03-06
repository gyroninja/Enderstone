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
package org.enderstone.server.entity.pathfinding;

import java.util.ArrayList;
import java.util.List;
import org.enderstone.server.api.Location;
import org.enderstone.server.api.Vector;
import org.enderstone.server.entity.EnderEntity;
import org.enderstone.server.entity.EntityMob;
import org.enderstone.server.entity.goals.Goal;
import org.enderstone.server.entity.targets.Target;

/**
 *
 * @author gyroninja
 */
public class PathNavigator {

	private final EntityMob mob;

	private EnderEntity target;

	private final List<Goal> goals = new ArrayList<>();
	private final List<Target> targets = new ArrayList<>();

	private Goal currentGoal;
	private Target currentTarget;

	private PathFinder pathfinder;

	private List<PathTile> path;
	private int currentTile = 0;

	private int counter = 0;

	public PathNavigator(EntityMob mob) {
		this.mob = mob;
	}

	public EnderEntity getTarget() {
		return target;
	}

	public void setTarget(EnderEntity target) {
		this.target = target;
	}

	public List<PathTile> getPath() {
		return path;
	}

	public void setPath(PathFinder pathfinder, List<PathTile> path) {
		this.pathfinder = pathfinder;
		this.path = path;
		currentTile = 0;
	}

	public PathFinder getPathfinder() {
		return pathfinder;
	}

	public PathTile getCurrentTile() {
		if (path != null) {
			return path.get(currentTile);
		}
		return null;
	}

	// Goals should be added in order of priority
	public void addGoal(Goal goal) {
		goals.add(goal);
	}

	public List<Goal> getGoals() {
		return this.goals;
	}

	// Targets should be added in order of priority
	public void addTarget(Target target) {
		targets.add(target);
	}

	public void run() {
		if (counter++ % 20 == 0) {
			updateCurrentGoal();
			updateCurrentTarget();
		}
		if (currentGoal != null) {
			currentGoal.run();
		}
		if (currentTarget != null) {
			currentTarget.run();
		}
		if (path != null) {
			if (mob.getLocation().isInRange(1, path.get(currentTile).getLocation(pathfinder.getStartLocation()), false)) {
				if (path.size() == currentTile + 1) {
					return;
				}
				currentTile++;
			}
			Location nextDestination = path.get(currentTile).getLocation(pathfinder.getStartLocation()).add(0, 1, 0);
			Vector difference = Vector.substractAndNormalize(mob.getLocation(), nextDestination);
			float speed = mob.getMovementSpeed() / 20;// 20 ticks per second
			difference.setX(difference.getX() > 0 ? Math.min(difference.getX(), speed) : Math.max(difference.getX(), -speed));
			difference.setY(difference.getY() > 0 ? Math.min(difference.getY(), speed) : Math.max(difference.getY(), -speed));
			difference.setZ(difference.getZ() > 0 ? Math.min(difference.getZ(), speed) : Math.max(difference.getZ(), -speed));
			// Change new move method
			// mob.teleport(mob.getLocation().clone().add(difference.getX(), difference.getY(), difference.getZ()));
			mob.moveInstantly(mob.getLocation().add(difference.getX(), difference.getY(), difference.getZ()));
			// There is probably a better way to do this
			if (difference.getX() == 0) {
				if (difference.getZ() > 0) {
					mob.broadcastRotation(0, 0);
					return;
				} else {
					mob.broadcastRotation(0, 180);
					return;
				}
			} else if (difference.getZ() == 0) {
				if (difference.getX() > 0) {
					mob.broadcastRotation(0, 270);
					return;
				} else {
					mob.broadcastRotation(0, 90);
					return;
				}
			} else if (difference.getX() > 0 && difference.getZ() > 0) {
				mob.broadcastRotation(0, 315);
			} else if (difference.getX() < 0 && difference.getZ() < 0) {
				mob.broadcastRotation(0, 135);
			} else if (difference.getX() < 0 && difference.getZ() > 0) {
				mob.broadcastRotation(0, 45);
			} else if (difference.getX() > 0 && difference.getZ() < 0) {
				mob.broadcastRotation(0, 225);
			}
		}
	}

	private void updateCurrentGoal() {
		if (currentGoal == null) {
			for (Goal goal : goals) {

				if (goal.start()) {

					currentGoal = goal;

					break;
				}
			}
		} else {
			if (!currentGoal.shouldContinue()) {
				currentGoal.reset();
				currentGoal = null;
			}
		}
	}

	private void updateCurrentTarget() {
		if (currentTarget == null) {
			for (Target newTarget : targets) {
				if (newTarget.start()) {
					currentTarget = newTarget;
					break;
				}
			}
		} else {
			if (!currentTarget.shouldContinue()) {
				currentTarget.reset();
				currentTarget = null;
			}
		}
	}
}
