package com.hans.likefunction.entity;

import java.sql.Timestamp;

public class UserLikeRecord {
    private int id;
    private int userId;
    private int entityId;
    private Timestamp liketime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Timestamp getLiketime() {
        return liketime;
    }

    public void setLiketime(Timestamp liketime) {
        this.liketime = liketime;
    }
}
