package cn.wind.db.bc.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主播直播申请数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_bc_record")
public class BcRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主播ID
     */
    private Long userId;
    /**
     * 房间ID
     */
    private Long roomId;
    /**
     * 城市
     */
    private Long cityId;
    /**
     * 1-娱乐 2-大咖秀
     */
    private Integer category=1;
    /**
     * 房间标题
     */
    private String roomInfo;
    /**
     * 房间封面
     */
    private String roomPic;
    /**
     * 1-正常直播下播 2-被封禁
     */
    private Integer status=1;


}
