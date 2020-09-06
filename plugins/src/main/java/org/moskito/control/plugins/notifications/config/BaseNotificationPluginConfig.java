package org.moskito.control.plugins.notifications.config;

import org.moskito.control.core.status.StatusChangeEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic class for notification plugins configurations.
 * Contains method to find profiles for specific applications and from-to statuses configurations.
 * @param <T> class of profile configuration corresponds to plugin
 */
public abstract class BaseNotificationPluginConfig <T extends BaseNotificationProfileConfig> {

    /**
     * Must return array of all profiles
     * that this config contain.
     *
     * @return array of plugin config profiles
     */
    protected abstract T[] getProfileConfigs();

    /**
     * Returns profiles for specified event
     * @param event event to return it corresponding profile
     * @return list of plugin profiles apply to event
     */
    public List<T> getProfileForEvent(StatusChangeEvent event){

        return Arrays.stream(getProfileConfigs())
                .filter(profile -> profile.isAppliableToEvent(event))
                .collect(Collectors.toList());

    }

}
