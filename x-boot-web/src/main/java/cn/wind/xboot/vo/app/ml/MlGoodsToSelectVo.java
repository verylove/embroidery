package cn.wind.xboot.vo.app.ml;

import cn.wind.db.ml.entity.MlGoodsModels;
import cn.wind.db.ml.entity.MlGoodsSpecifications;
import lombok.Data;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 16:35
 * @Description:
 */
@Data
public class MlGoodsToSelectVo {

    private Long id;

    /**
     * 封面
     */
    private String coverImg;
    /**
     * 商品价格 刺币(单价)
     */
    private String price;
    /**
     * 库存
     */
    private Long remainNum;

    /**
     * 产品规格
     */
    private List<MlGoodsSpecifications> specifications;
    /**
     * 产品型号
     */
    private List<MlGoodsModels> models;
}
