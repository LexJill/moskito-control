package org.moskito.control.plugins.mail;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.moskito.control.core.status.StatusChangeEvent;

import java.util.Arrays;

/**
 * Config for the mail service.
 *
 * @author Khilkevich Oleksii
 */
@ConfigureMe
public final class MailServiceConfig {

	/**
	 * Notifications.
	 */
	@Configure
	private MailNotificationConfig[] notifications;

	/**
	 * Host.
	 */
	@Configure
	private String host;

    /**
     * SMTP port.
     */
    @Configure
    private int port;

	/**
	 * User.
	 */
	@Configure
	private String user;

	/**
	 * Password.
	 */
	@Configure
	private String password;

    /**
     * Message sender field in change status message.
     */
    @Configure
    private String defaultMessageSender;

	/**
	 * Default message subject.
	 */
	@Configure
	private String defaultMessageSubject;

	/**
	 *  Is debug enabled.
	 */
	@Configure
	private boolean debug;

	public String toString(){
		return getUser()+"!"+getPassword()+":"+getHost()+" - "+isDebug();
	}

    public MailNotificationConfig[] getNotifications() {
        return notifications;
    }

    public void setNotifications(MailNotificationConfig[] notifications) {
        this.notifications = notifications;
    }

    public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

    public String getDefaultMessageSender() {
        return defaultMessageSender;
    }

    public void setDefaultMessageSender(String defaultMessageSender) {
        this.defaultMessageSender = defaultMessageSender;
    }

	public String getDefaultMessageSubject() {
		return defaultMessageSubject;
	}

	public void setDefaultMessageSubject(String defaultMessageSubject) {
		this.defaultMessageSubject = defaultMessageSubject;
	}


	/**
	 * Returns channel name for specified event or default channel (if it was configured)
	 * @param event event to return it corresponding channel
	 * @return slack channel name
	 */
	public String[] getRecipientsForEvent(StatusChangeEvent event){

		return Arrays.stream(notifications)
				.filter(channel -> channel.isAppliableToEvent(event))
				.findFirst()
				.map(MailNotificationConfig::getRecipients)
				.orElse(new String[]{});

	}

}
