package com.hans.likefunction.service;

import com.hans.likefunction.config.RedisDao;
import com.hans.likefunction.entity.UserLikeRecord;
import com.hans.likefunction.util.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private RedisKeyUtils redisKeyUtils;

    /**
     * 判断用户是 点赞 or 取消点赞
     * @param userId
     * @param entiyType
     * @param entiyId
     * @return 返回1--取消点赞  返回0--点赞
     */
    public int getLikeStatus(final int userId,final int entiyType,final int entiyId)
    {
        final String likekey = redisKeyUtils.getLikekey(entiyType, entiyId);
        if(redisDao.hasKey(likekey))
        {
           return redisDao.sismember(likekey, String.valueOf(userId)) ? 1:0;
        }
        return 0;
    }


    /**
     * 判断用户是 不喜欢 or 取消不喜欢
     * @param userId
     * @param entiyType
     * @param entiyId
     * @return 返回1--不喜欢  返回0--取消不喜欢
     */
    public int getDisLikeStatus(final int userId,final int entiyType,final int entiyId)
    {
        final String disLikekey = redisKeyUtils.getDisLikekey(entiyType, entiyId);
        if(redisDao.hasKey(disLikekey))
        {
            return redisDao.sismember(disLikekey, String.valueOf(userId)) ? 1:0;
        }
        return 0;
    }

    /**
     * 点赞
     * @param userId
     * @param entiyType
     * @param entiyId
     */
    public void like(final int userId,final int entiyType,final int entiyId)
    {
        final String likekey = redisKeyUtils.getLikekey(entiyType, entiyId);
        final String disLikekey = redisKeyUtils.getDisLikekey(entiyType, entiyId);
        //加入点赞集合，从不喜欢集合中移除
        redisDao.sadd(likekey,String.valueOf(userId));
        redisDao.srem(disLikekey,String.valueOf(userId));
    }

    /**
     * 取消点赞
     * @param userId
     * @param entiyType
     * @param entiyId
     */
    public void canclelike(final int userId,final int entiyType,final int entiyId)
    {
        final String likekey = redisKeyUtils.getLikekey(entiyType, entiyId);
        //从点赞集合中移除
        redisDao.srem(likekey,String.valueOf(userId));
    }

    /**
     * 不喜欢
     * @param userId
     * @param entiyType
     * @param entiyId
     */
    public void dislike(final int userId,final int entiyType,final int entiyId)
    {
        final String likekey = redisKeyUtils.getLikekey(entiyType, entiyId);
        final String disLikekey = redisKeyUtils.getDisLikekey(entiyType, entiyId);
        redisDao.sadd(disLikekey,String.valueOf(userId));
        redisDao.srem(likekey,String.valueOf(userId));
    }

    /**
     * 取消不喜欢
     * @param userId
     * @param entiyType
     * @param entiyId
     */
    public void cancledislike(final int userId,final int entiyType,final int entiyId)
    {
        final String disLikekey = redisKeyUtils.getDisLikekey(entiyType, entiyId);
        redisDao.srem(disLikekey,String.valueOf(userId));
    }

    /**
     * 记录每次点赞的情况（对应数据库的一条记录）
     * @param key
     * @param hashkey
     * @param value
     */
    public  void likeRecord(final String key,final String hashkey,final UserLikeRecord value)
    {
        //用hash存的原因是可以存对象类型数据u，而且方便修改
        redisDao.hset(key,hashkey,value);
    }


    /**
     * 获得点赞数量
     * @param key
     * @return
     */
    public int getlikeCount(final String key)
    {
        return  redisDao.smembers(key).size();
    }


}
