package com.lfj.plugin.patb;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;

public class BootstrapClass implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext bootstrapContext) {

    }

    @Override
    public JavaPlugin createPlugin(PluginProviderContext context) {
        return new Main();
    }
}
