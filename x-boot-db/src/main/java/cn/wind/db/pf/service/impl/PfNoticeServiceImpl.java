package cn.wind.db.pf.service.impl;

import cn.wind.db.pf.dao.PfNoticeMapper;
import cn.wind.db.pf.entity.PfNotice;
import cn.wind.db.pf.service.IPfNoticeService;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 平台通知 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-07-02
 */
@Service
public class PfNoticeServiceImpl extends ServiceImpl<PfNoticeMapper, PfNotice> implements IPfNoticeService {
    @Override
    public boolean updateReadedById(Long id) {
        return SqlHelper.delBool(this.baseMapper.updateReadedById(id));
    }

    @Override
    public boolean updateReadedByIds(List ids) {
        return SqlHelper.delBool(this.baseMapper.updateReadedByIds(ids));
    }

    @Override
    public Page<PfNotice> findByDelFlag(Page page, Integer delFlag) {
        return page.setRecords(this.baseMapper.findByDelFlag(delFlag));
    }

    @Override
    public boolean updateDelFlagByIds(List ids) {
        return SqlHelper.delBool(this.baseMapper.updateDelFlagByIds(ids));
    }

    @Override
    public Integer countByDelFlag(Integer delFlag) {
        return this.baseMapper.countByDelFlag(delFlag);
    }
}
