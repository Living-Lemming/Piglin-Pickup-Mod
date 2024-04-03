package com.livinglemming;

import com.livinglemming.Events.RightClickEventListener;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagerPickup implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("villager-pickup");

	@Override
	public void onInitialize() {
		RightClickEventListener.registerRightClickEvent();
	}
}