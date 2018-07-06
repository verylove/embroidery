package cn.wind.db.pf.service;

import cn.wind.db.pf.entity.PfNotice;
import cn.wind.db.sys.entity.SysUser;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 平台通知 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-07-02
 */
public interface IPfNoticeService extends IService<PfNotice> {
    boolean updateReadedById(Long id);
    boolean updateReadedByIds(List ids);
    boolean updateDelFlagByIds(List ids);
    Page<PfNotice> findByDelFlag(Page page,Integer delFlag);
    Integer countByDelFlag(Integer delFlag);
}
