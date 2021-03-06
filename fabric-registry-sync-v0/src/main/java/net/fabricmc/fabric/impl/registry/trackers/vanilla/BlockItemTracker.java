/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.impl.registry.trackers.vanilla;

import net.fabricmc.fabric.impl.registry.ListenableRegistry;
import net.fabricmc.fabric.impl.registry.callbacks.RegistryPostRegisterCallback;
import net.fabricmc.fabric.impl.registry.callbacks.RegistryPreClearCallback;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

public final class BlockItemTracker implements RegistryPreClearCallback<Item>, RegistryPostRegisterCallback<Item> {
	private BlockItemTracker() {

	}

	public static void register(SimpleRegistry<Item> registry) {
		BlockItemTracker tracker = new BlockItemTracker();
		((ListenableRegistry<Item>) registry).getPreClearEvent().register(tracker);
		((ListenableRegistry<Item>) registry).getPostRegisterEvent().register(tracker);
	}

	@Override
	public void onPostRegister(int rawId, Identifier id, Item object) {
		if (object instanceof BlockItem) {
			((BlockItem) object).registerBlockItemMap(Item.BLOCK_ITEM_MAP, object);
		}
	}

	@Override
	public void onPreClear() {
		Item.BLOCK_ITEM_MAP.clear();
	}
}
