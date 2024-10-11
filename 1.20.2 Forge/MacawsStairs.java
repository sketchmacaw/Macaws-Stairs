package com.mcwstairs.kikoz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mcwstairs.kikoz.init.BlockInit;
import com.mcwstairs.kikoz.init.ItemInit;
import com.mcwstairs.kikoz.init.TabInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("mcwstairs")
@Mod.EventBusSubscriber(modid = MacawsStairs.MOD_ID, bus = Bus.MOD)
public class MacawsStairs {
	public static final String MOD_ID = "mcwstairs";
	public static MacawsStairs instance;
	public static final Logger LOGGER = (Logger) LogManager.getLogger(MOD_ID);

	public MacawsStairs() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);

		BlockInit.BLOCKS.register(modEventBus);
		ItemInit.ITEMS.register(modEventBus);
		TabInit.CREATIVE_TABS.register(modEventBus);

		instance = this;
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {

	}

	private void doClientStuff(final FMLClientSetupEvent event) {

	}

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {

	}


	
	
	
}
