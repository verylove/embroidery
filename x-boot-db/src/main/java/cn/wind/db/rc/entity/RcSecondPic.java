package cn.wind.db.rc.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 二手市场图片数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_rc_second_pic")
public class RcSecondPic extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 二手市场ID
     */
    private Long secondTransactId;
    /**
     * 图片
     */
    private String img;


}
