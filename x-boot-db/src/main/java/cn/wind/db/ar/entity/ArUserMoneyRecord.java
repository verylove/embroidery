package cn.wind.db.ar.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户金额变动记录数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_money_record")
public class ArUserMoneyRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user`ID
     */
    private Long userId;
    /**
     * 变化的金额
     */
    private BigDecimal amount;
    /**
     * 类型 1-充值 2-消费 3-提现 4-奖励
     */
    private Integer type;
    /**
     * 消费模块 1-钱包；2-特价纹身 3-特价纹身评论 4-图库找图 5-图库找图评论 6-贴吧话题 7-贴吧话题评论
     * 8-纹纹达人 9-纹纹达人评论 10-动态 11-动态评论 12-作品 13-作品评论
     */
    private Integer module;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 1-待完成 2-已完成 3-操作失败
     */
    private Integer status;
    /**
     * 1-支付宝 2-微信 3-银行卡 4-刺币
     */
    private Integer way;
    /**
     * 银行卡ID
     */
    private Long bankId;
    /**
     * 邮费 商城消费用
     */
    private BigDecimal postage=new BigDecimal("0.00");
    /**
     * 收件人名 商城消费用
     */
    private String receiptName;
    /**
     * 收件人电话 商城消费用
     */
    private String receiptPhone;
    /**
     * 收件人地址 商城消费用
     */
    private String receiptAddress;


}
