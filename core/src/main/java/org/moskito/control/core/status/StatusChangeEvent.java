package org.moskito.control.core.status;

import org.moskito.control.common.Status;
import org.moskito.control.core.Component;

/**
 * Component status change event.
 */
public class StatusChangeEvent {

    /**
     * Component that changed the status.
     */
    private Component component;

    /**
     * Old status of the component.
     */
    private Status oldStatus;

    /**
     * New status of the component.
     */
    private Status status;

    /**
     * Timestamp at which the status change took place.
     */
    private long timestamp;

    /**
     * @param component component that changed the status.
     * @param oldStatus old status of the component.
     * @param status new status of the component.
     * @param timestamp timestamp at which the status change took place.
     */
    public StatusChangeEvent(Component component, Status oldStatus, Status status, long timestamp) {
        this.component = component;
        this.oldStatus = oldStatus;
        this.status = status;
        this.timestamp = timestamp;
    }

	/**
	 * Testing constructor.
	 */
	public StatusChangeEvent(){
		
	}

    public Component getComponent() {
        return component;
    }

    public Status getOldStatus() {
        return oldStatus;
    }

    public Status getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatusChangeEvent{");
        sb.append(", component=").append(component);
        sb.append(", oldStatus=").append(oldStatus);
        sb.append(", status=").append(status);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }

	public void setComponent(Component component) {
		this.component = component;
	}

	public void setOldStatus(Status oldStatus) {
		this.oldStatus = oldStatus;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
