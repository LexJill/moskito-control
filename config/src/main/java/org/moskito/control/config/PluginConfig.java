package org.moskito.control.config;

import com.google.gson.annotations.SerializedName;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Config element that describes a single plugin.
 *
 * @author lrosenberg
 * @since 19.03.13 15:48
 */
@ConfigureMe
public class PluginConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -5682152228778301153L;

	/**
	 * Name of the plugin. This allows to have multiple copies of same plugin in the runtime instance.
	 */
	@Configure
	@SerializedName("name")
	private String name;
	/**
	 * Name of the plugin class. It must implement net.anotheria.moskito.core.plugins.MoskitoPlugin.
	 */
	@Configure
	@SerializedName("className")
	private String className;
	/**
	 * Name of the configuration for the plugin. It will be passed to the plugin after creation.
	 */
	@Configure
	@SerializedName("configurationName")
	private String configurationName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	@Override
	public String toString(){
		return "PluginConfig ("+getName()+", "+getClassName()+", "+getConfigurationName()+ ')';
	}
}
