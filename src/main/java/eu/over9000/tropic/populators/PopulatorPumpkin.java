/*
 * Copyright 2012 s1mpl3x
 * 
 * This file is part of Tropic.
 * 
 * Tropic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Tropic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Tropic If not, see <http://www.gnu.org/licenses/>.
 */
package eu.over9000.tropic.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class PopulatorPumpkin extends BlockPopulator {

	int chancePer100, numMelons, depositRadius;

	public PopulatorPumpkin() {
		this.chancePer100 = 3;
		this.numMelons = 5;
		this.depositRadius = 20;
	}

	@Override
	public void populate(final World world, final Random random, final Chunk source) {
		// Check if we should place a melon patch on this chunk
		if (random.nextInt(100) < chancePer100) {
			final int x = (source.getX() << 4) + random.nextInt(16);
			final int z = (source.getZ() << 4) + random.nextInt(16);

			for (int i = 0; i < random.nextInt(numMelons); i++) {
				// Pick a random spot within the radius
				final int cx = x + random.nextInt(depositRadius * 2) - depositRadius;
				final int cz = z + random.nextInt(depositRadius * 2) - depositRadius;

				final Block base = getHighestBlock(world, cx, cz);
				if (base != null) {
					final Block pumpkin = base.getRelative(0, 1, 0);
					pumpkin.setType(Material.PUMPKIN);
					pumpkin.setData((byte) random.nextInt(4));
				}
			}
		}
	}

	/**
	 * Iteratively determines the highest grass block
	 *
	 * @param world
	 * @param x
	 * @param z
	 * @return Block highest non-air
	 */
	private Block getHighestBlock(final World world, final int x, final int z) {
		Block block = null;
		// Return the highest block
		for (int i = world.getMaxHeight(); i >= 0; i--)
			if ((block = world.getBlockAt(x, i, z)).getTypeId() == 2)
				return block;
		// And as a matter of completeness, return the lowest point
		return block;
	}
}