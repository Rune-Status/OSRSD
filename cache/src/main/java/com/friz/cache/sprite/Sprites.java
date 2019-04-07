/*
 * Copyright (C) 2016 Kyle Fricilone
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.friz.cache.sprite;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.friz.cache.Cache;
import com.friz.cache.Container;
import com.friz.cache.ReferenceTable;
import com.friz.cache.ReferenceTable.Entry;
import com.friz.cache.utility.crypto.Djb2;

/**
 * Created by Kyle Fricilone on Sep 15, 2016.
 */
public class Sprites {

	private static Logger logger = Logger.getLogger(Sprites.class.getName());

	private static Sprite[] sprites;
	private static final Map<Integer, Integer> hashes = new HashMap<>();

	public static void initialize(Cache cache) {
		int count = 0;
		try {
			Container container = Container.decode(cache.getStore().read(255, 8));
			ReferenceTable table = ReferenceTable.decode(container.getData());

			sprites = new Sprite[table.capacity()];

			for (int i = 0; i < table.capacity(); i++) {
				Entry e = table.getEntry(i);
				if (e == null)
					continue;

				Container c = cache.read(8, i);
				Sprite sprite = Sprite.decode(c.getData());
				sprites[i] = sprite;
				hashes.put(e.getIdentifier(), i);

				count++;
			}

		}

		catch (Exception e) {
			logger.log(Level.SEVERE, "Error Loading Sprite(s)!", e);
		}
		logger.info("Loaded " + count + " Sprite(s)!");
	}

	public static final Sprite getSprite(int id) {
		return sprites[id];
	}

	public static final Sprite getSprite(String name) {
		return sprites[hashes.get(Djb2.hash(name))];
	}

}
