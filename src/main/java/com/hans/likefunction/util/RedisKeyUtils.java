package com.hans.likefunction.util;

import org.springframework.stereotype.Component;

@Component
public class RedisKeyUtils {

    private static final String SPLIT=":";
    private static final String KEY_LIKE="LIKE";
    private static final String KEY_DISLIKE="DISLIKE";

    /**
     * 获取点赞集合的key
     * @param entityType
     * @param entityId
     * @return
     */
    public String getLikekey(int entityType,int entityId)
    {
        return KEY_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    /**
     * 获取点赞集合的key
     * @param entityType
     * @param entityId
     * @return
     */
    public String getDisLikekey(int entityType,int entityId)
    {
        return KEY_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
}
