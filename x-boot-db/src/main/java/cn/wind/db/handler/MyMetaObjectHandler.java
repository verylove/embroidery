package cn.wind.db.handler;

import cn.wind.common.utils.UserUtil;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * <p>Title: MyMetaObjectHandler</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/21
 */
public class MyMetaObjectHandler extends MetaObjectHandler {

    /**
     * 测试 user 表 name 字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //获取当前登录用户
        String userId = String.valueOf(0);
        Date date = new Date();
        if (hasFiled(metaObject, "modifyBy")) {
            Object modifyBy = getFieldValByName("modifyBy", metaObject);
            if (null == modifyBy) {
               setFieldValByName("modifyBy", userId,metaObject);
            }
        }
        if (hasFiled(metaObject, "modifyTime")) {
            Object modifyTime = getFieldValByName("modifyTime", metaObject);
            if (null == modifyTime) {
                setFieldValByName("modifyTime", date,metaObject);
            }
        }
        if (hasFiled(metaObject, "createBy")) {
            Object createBy = getFieldValByName("createBy", metaObject);
            if (null == createBy) {
                setFieldValByName("createBy", userId,metaObject);
            }
        }
        if (hasFiled(metaObject, "createTime")) {
            Object createTime = getFieldValByName("createTime", metaObject);
            if (null == createTime) {
                setFieldValByName("createTime", date,metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //获取当前登录用户
        String userId = String.valueOf(UserUtil.getCurrentUserId());
        Date date = new Date();
        if (hasFiled(metaObject, "modifyBy")) {
           setFieldValByName("modifyBy", userId,metaObject);
        }
        if (hasFiled(metaObject, "modifyTime")) {
            setFieldValByName("modifyTime", date,metaObject);
        }
    }

    public boolean hasFiled(MetaObject metaObject, String fieldName) {
        return metaObject.hasGetter(fieldName) || metaObject.hasGetter("et." + fieldName);

    }
}
