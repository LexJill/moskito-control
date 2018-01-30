package org.moskito.control.plugins.slack;

import org.configureme.ConfigurationManager;
import org.junit.Test;
import org.moskito.control.core.Application;
import org.moskito.control.core.Component;
import org.moskito.control.core.HealthColor;
import org.moskito.control.core.status.Status;
import org.moskito.control.core.status.StatusChangeEvent;

import java.util.List;

import static org.junit.Assert.*;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 03.05.17 13:59
 */
public class StatusChangeSlackNotifierTest {


	/**
	 * This test ensures that if a new color is added to the HealthColors, the developer will add a mapping for the slack integration as well.
	 */
	@Test
	public void testAllColoursAreHandled(){
		for (HealthColor c : HealthColor.values()){
			String response = SlackMessageBuilder.color2color(c);
			assertNotNull(response);
			assertTrue(response.length()>0);
		}
	}

	private StatusChangeEvent createStatusChangeEvent(String appName){
		Application app = new Application(appName);
		StatusChangeEvent event = new StatusChangeEvent(
				app,
				new Component(app, "TestComp"),
				new Status(HealthColor.RED, "RED"),
				new Status(HealthColor.GREEN, "GREEN"),
				System.currentTimeMillis()

		);

		return event;
	}

	/**
	 * Checks is list of channel profiles
	 * contain channel with given name.
	 *
	 * @param channelConfigs list of channel profiles
	 * @param channelName name if channel
	 *
	 * @return true  - channel with given name was found in given list
	 * 		   false - channel with given name was not found in list
	 */
	private boolean isProfilesHasChannel(List<SlackChannelConfig> channelConfigs, String channelName) {

		for (SlackChannelConfig channelConfig : channelConfigs) {
			if(channelConfig.getName().equals(channelName))
				return true;
		}

		return false;

	}

	@Test public void testRoutingIntoTestChannelOnly(){
		StatusChangeEvent event = createStatusChangeEvent("TEST");
		SlackConfig config = new SlackConfig();
		ConfigurationManager.INSTANCE.configureAs(config, "slack");

		assertEquals("test-monitoring", config.getProfileForEvent(event).get(0).getName());
	}

	@Test public void testRoutingIntoFooChannelOnlyWithStatus(){
		StatusChangeEvent event = createStatusChangeEvent("FOO");
		event.setStatus(new Status(HealthColor.RED, ""));
		event.setOldStatus(new Status(HealthColor.GREEN, ""));
		SlackConfig config = new SlackConfig();
		ConfigurationManager.INSTANCE.configureAs(config, "slack");

		//not in channel
		assertEquals(0, config.getProfileForEvent(event).size());

		StatusChangeEvent event2 = createStatusChangeEvent("FOO");
		event2.setStatus(new Status(HealthColor.PURPLE, ""));


		//not in channel
		assertEquals("foo-monitoring", config.getProfileForEvent(event2).get(0).getName());

	}
	@Test public void testRoutingIntoMultipleChannels(){
		StatusChangeEvent event = createStatusChangeEvent("PROD");
		event.setStatus(new Status(HealthColor.PURPLE, ""));
		event.setOldStatus(new Status(HealthColor.GREEN, ""));
		SlackConfig config = new SlackConfig();
		ConfigurationManager.INSTANCE.configureAs(config, "slack");

		assertEquals(2, config.getProfileForEvent(event).size());
		//ensure not in default channel
		assertFalse(
				isProfilesHasChannel(config.getProfileForEvent(event), "general")
        );

		//ensure not in foo channel
		assertFalse(
				isProfilesHasChannel(config.getProfileForEvent(event), "foo-monitoring")
        );

		//ensure in prod and test channels.
		assertTrue(
				isProfilesHasChannel(config.getProfileForEvent(event), "test-monitoring")
        );

		assertTrue(
				isProfilesHasChannel(config.getProfileForEvent(event), "prod-monitoring")
        );


		StatusChangeEvent event2 = createStatusChangeEvent("FOO");
		event2.setStatus(new Status(HealthColor.PURPLE, ""));

		//not in channel
		assertEquals("foo-monitoring", config.getProfileForEvent(event2).get(0).getName());

	}

	@Test public void testConditionsWithOldAndNewStatus(){

		StatusChangeEvent event = createStatusChangeEvent("YELLOWTEST");
		event.setStatus(new Status(HealthColor.PURPLE, ""));
		event.setOldStatus(new Status(HealthColor.GREEN, ""));

		SlackConfig config = new SlackConfig();
		ConfigurationManager.INSTANCE.configureAs(config, "slack");

		//ensure not in foo channel
		assertFalse(
				isProfilesHasChannel(config.getProfileForEvent(event), "only-yellow-monitoring")
        );

		//and now with new yellow, but old orange
		event.setOldStatus(new Status(HealthColor.ORANGE, ""));
		event.setStatus(new Status(HealthColor.YELLOW, ""));

		//ensure not in foo channel
		assertFalse(
				isProfilesHasChannel(config.getProfileForEvent(event), "only-yellow-monitoring")
        );

		//and now with new yellow, and old green
		event.setOldStatus(new Status(HealthColor.GREEN, ""));
		event.setStatus(new Status(HealthColor.YELLOW, ""));

		//ensure not in foo channel
		assertTrue(
				isProfilesHasChannel(config.getProfileForEvent(event), "only-yellow-monitoring")
        );

	}


}
