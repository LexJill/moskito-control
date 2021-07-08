package org.moskito.control.plugins.mail.core.message;

import net.anotheria.util.NumberUtils;
import org.moskito.control.core.status.StatusChangeEvent;
import org.moskito.control.plugins.mail.MailServiceConfig;
import org.moskito.control.plugins.mail.core.message.util.MessageCreationUtil;

/**
 * Message builder for status changed mail.
 *
 * @author Khilkevich Oleksii
 */
public class MailMessageBuilder {

	public static MailMessage buildStatusChangedMessage(StatusChangeEvent event, MailServiceConfig config, String[] recipients){

		String content = "Timestamp: <b>" + NumberUtils.makeISO8601TimestampString((event.getTimestamp())) + "</b><br/>"
				+ "Component: <b>" + event.getComponent() + "</b><br/>"
				+ "Old status: <b>" + event.getOldStatus() + "</b><br/>"
				+ "New status: <b>" + event.getStatus() + "</b><br/>";

		return  MessageCreationUtil.createHtmlMailMessage(
				recipients,
				config.getDefaultMessageSender(),
				content,
				config.getDefaultMessageSubject()
		);

	}
}
