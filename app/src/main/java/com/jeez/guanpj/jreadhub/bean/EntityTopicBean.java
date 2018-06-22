package com.jeez.guanpj.jreadhub.bean;

public class EntityTopicBean {

    /**
     * nerName : 刘江峰
     * entityId : 2086
     * entityName : 刘江峰
     * entityType : person
     * eventType : 0
     * eventTypeLabel :
     */

    private String nerName;
    private int entityId;
    private String entityName;
    private String entityType;
    private int eventType;
    private String eventTypeLabel;

    public String getNerName() {
        return nerName;
    }

    public void setNerName(String nerName) {
        this.nerName = nerName;
    }

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
