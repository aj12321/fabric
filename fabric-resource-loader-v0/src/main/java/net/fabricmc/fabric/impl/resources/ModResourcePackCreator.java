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

package net.fabricmc.fabric.impl.resources;

import net.fabricmc.fabric.api.resource.ModResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackContainer;
import net.minecraft.resource.ResourcePackCreator;
import net.minecraft.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModResourcePackCreator implements ResourcePackCreator {
	private final ResourceType type;

	public ModResourcePackCreator(ResourceType type) {
		this.type = type;
	}

	@Override
	public <T extends ResourcePackContainer> void registerContainer(Map<String, T> map, ResourcePackContainer.Factory<T> factory) {
		// TODO: "vanilla" does not emit a message; neither should a modded datapack
		List<ResourcePack> packs = new ArrayList<>();
		ModResourcePackUtil.appendModResourcePacks(packs, type);
		for (ResourcePack pack : packs) {
			if (!(pack instanceof ModResourcePack)) {
				throw new RuntimeException("Not a ModResourcePack!");
			}

			T var3 = ResourcePackContainer.of("fabric/" + ((ModResourcePack) pack).getFabricModMetadata().getId(),
				false, () -> pack, factory, ResourcePackContainer.InsertionPosition.BOTTOM);

			if (var3 != null) {
				map.put(var3.getName(), var3);
			}
		}
	}
}
