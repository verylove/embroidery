package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserZxPic;
import cn.wind.db.ar.entity.ArUserZxTattoo;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.db.ar.service.IArUserZxPicService;
import cn.wind.db.ar.service.IArUserZxTattooService;
import cn.wind.xboot.dto.app.ar.arUserZxTattooDto;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/12 19:33
 * @Description:
 */
@Service
public class CXArZxTattooManage {

    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserZxTattooService zxTattooService;
    @Autowired
    private IArUserZxPicService zxPicService;

    /**
     * 发布纹身咨询（爱好者）
     * @param dto
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void publish(arUserZxTattooDto dto, Long userId)throws Exception {
        //1.插入纹身咨询数据
        ArUserZxTattoo zxTattoo = new ArUserZxTattoo();
        BeanUtils.copyProperties(dto,zxTattoo);
        zxTattoo.setUserId(userId);
        if(dto.getIsPhone()==1){
            ArUser user = userService.selectById(userId);
            zxTattoo.setPhone(user.getPhone());
        }
        zxTattooService.insert(zxTattoo);

        //2.插入纹身咨询图片数据
        List<ArUserZxPic> pics = Lists.newArrayList();
        for(String pic:dto.getPics()){
            ArUserZxPic pic1 = new ArUserZxPic();
            pic1.setImg(pic);
            pic1.setZxTattooId(zxTattoo.getId());
            pics.add(pic1);
        }
        zxPicService.insertBatch(pics);

    }
}
