package cn.wind.xboot.service.app;

import cn.wind.db.rc.entity.RcSecondPic;
import cn.wind.db.rc.entity.RcSecondTransactions;
import cn.wind.db.rc.service.IRcSecondPicService;
import cn.wind.db.rc.service.IRcSecondTransactionsService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.dto.app.rc.rcSecondTransactDto;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 16:00
 * @Description:二手市场
 */
@Service
public class CXRcSecondTransactManage {

    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IRcSecondTransactionsService secondTransactionsService;
    @Autowired
    private IRcSecondPicService secondPicService;

    /**
     * 二手市场发布
     * @param dto
     * @param userId
     */
    @Transactional
    public void publishInSecondTransact(rcSecondTransactDto dto, Long userId) {
        //1.插入二手市场数据
        RcSecondTransactions secondTransactions = new RcSecondTransactions();
        BeanUtils.copyProperties(dto,secondTransactions);
        secondTransactions.setUserId(userId);

        SrArea area = areaService.selectById(dto.getCity());
        String center = area.getCenter();
        if(Strings.isNullOrEmpty(center)){
            throw new RuntimeException();
        }
        String[] coordinates = center.split(",");
        secondTransactions.setLongitude(coordinates[0]);
        secondTransactions.setLatitude(coordinates[1]);
        secondTransactionsService.insert(secondTransactions);

        //2.插入二手市场图片数据
        List<RcSecondPic> pics = new ArrayList<>();
        for(String pic:dto.getPics()){
            RcSecondPic pic1 = new RcSecondPic();
            pic1.setImg(pic);
            pic1.setSecondTransactId(secondTransactions.getId());
            pics.add(pic1);
        }
        secondPicService.insertBatch(pics);

    }
}
