package com.cms.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * AuditLogPlugin - Sample plugin that logs all CMS events
 * Demonstrates the Microkernel plugin system
 */
@Slf4j
@Component
public class AuditLogPlugin implements CmsPlugin {

    private final PluginRegistry pluginRegistry;

    public AuditLogPlugin(PluginRegistry pluginRegistry) {
        this.pluginRegistry = pluginRegistry;
    }

    @PostConstruct
    public void register() {
        pluginRegistry.register(this);
    }

    @Override
    public String getName() { return "audit-log-plugin"; }

    @Override
    public String getVersion() { return "1.0.0"; }

    @Override
    public void initialize() {
        log.info("[AuditLogPlugin] Initialized - will track all post events");
    }

    @Override
    public void onPostCreate(Object post) {
        log.info("[AuditLogPlugin] POST CREATED: {}", post);
    }

    @Override
    public void onPostUpdate(Object post) {
        log.info("[AuditLogPlugin] POST UPDATED: {}", post);
    }

    @Override
    public void onPostDelete(Long postId) {
        log.info("[AuditLogPlugin] POST DELETED: id={}", postId);
    }
}
