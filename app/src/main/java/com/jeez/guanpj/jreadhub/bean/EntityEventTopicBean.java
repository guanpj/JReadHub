package com.jeez.guanpj.jreadhub.bean;

public class EntityEventTopicBean {

    /**
     * entityId : 8915
     * entityName : 北极光
     * entityType : company
     * eventType : 1
     * eventTypeLabel : 投融资
     */

    private int entityId;
    private String entityName;
    private String entityType;
    private int eventType;
    private String eventTypeLabel;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeLabel() {
        return eventTypeLabel;
    }

    public void setEventTypeLabel(String eventTypeLabel) {
        this.eventTypeLabel = eventTypeLabel;
    }
}
