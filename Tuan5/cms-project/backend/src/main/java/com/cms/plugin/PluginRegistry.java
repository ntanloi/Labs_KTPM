package com.cms.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PluginRegistry - Core of Microkernel architecture
 * Responsible for registering, loading, and dispatching events to plugins
 */
@Slf4j
@Component
public class PluginRegistry {

    private final Map<String, CmsPlugin> plugins = new ConcurrentHashMap<>();

    public void register(CmsPlugin plugin) {
        plugins.put(plugin.getName(), plugin);
        plugin.initialize();
        log.info("Plugin registered: {} v{}", plugin.getName(), plugin.getVersion());
    }

    public void unregister(String pluginName) {
        plugins.remove(pluginName);
        log.info("Plugin unregistered: {}", pluginName);
    }

    public List<CmsPlugin> getAllPlugins() {
        return new ArrayList<>(plugins.values());
    }

    public CmsPlugin getPlugin(String name) {
        return plugins.get(name);
    }

    public void dispatchPostCreate(Object post) {
        plugins.values().forEach(p -> {
            try { p.onPostCreate(post); }
            catch (Exception e) { log.error("Plugin {} error on postCreate: {}", p.getName(), e.getMessage()); }
        });
    }

    public void dispatchPostUpdate(Object post) {
        plugins.values().forEach(p -> {
            try { p.onPostUpdate(post); }
            catch (Exception e) { log.error("Plugin {} error on postUpdate: {}", p.getName(), e.getMessage()); }
        });
    }

    public void dispatchPostDelete(Long postId) {
        plugins.values().forEach(p -> {
            try { p.onPostDelete(postId); }
            catch (Exception e) { log.error("Plugin {} error on postDelete: {}", p.getName(), e.getMessage()); }
        });
    }
}
