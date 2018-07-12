package cn.wind.db.ar.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户特价纹身点赞记录表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sp_great_record")
public class ArUserSpGreatRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_sp_tattoo`ID
     */
    private Long specialTattooId;
    /**
     * `cx_ar_user`ID 点赞人
     */
    private Long userId;
    /**
     * `cx_ar_user`ID 作者
     */
    private Long authorId;


}
