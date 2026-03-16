package com.cms.plugin;

/**
 * Plugin interface - Microkernel pattern
 * Any plugin must implement this interface to be loaded into CMS
 */
public interface CmsPlugin {
    String getName();
    String getVersion();
    void initialize();
    void onPostCreate(Object post);
    void onPostUpdate(Object post);
    void onPostDelete(Long postId);
}
