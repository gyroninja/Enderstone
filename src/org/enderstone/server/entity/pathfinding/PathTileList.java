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

import java.util.HashMap;

/**
 *
 * @author gyroninja
 */
public class PathTileList extends HashMap<Integer, PathTile> {

	private static final long serialVersionUID = 4368318447403103929L;

	public void add(PathTile tile) {
		this.put(tile.hashCode(), tile);
	}

	public void add(PathTile tile, boolean overwrite) {
		if (containsKey(tile.hashCode())) {
			if (overwrite) {
				add(tile);
			}
		} else {
			add(tile);
		}
	}
}
