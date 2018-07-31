package cn.wind.db.rc.service.impl;

import cn.wind.db.rc.entity.RcSecondPic;
import cn.wind.db.rc.dao.RcSecondPicMapper;
import cn.wind.db.rc.service.IRcSecondPicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 二手市场图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class RcSecondPicServiceImpl extends ServiceImpl<RcSecondPicMapper, RcSecondPic> implements IRcSecondPicService {

    @Override
    public List<RcSecondPic> findAllBySecondTransactId(Long SecondTransactId) {
        return this.baseMapper.findAllBySecondTransactId(SecondTransactId);
    }
}
