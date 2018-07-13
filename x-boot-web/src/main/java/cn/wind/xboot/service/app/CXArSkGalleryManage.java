package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.ArUserSkGallery;
import cn.wind.db.ar.entity.ArUserSkPic;
import cn.wind.db.ar.service.IArUserSkGalleryService;
import cn.wind.db.ar.service.IArUserSkPicService;
import cn.wind.xboot.dto.app.ar.arUserSkGalleryDto;
import cn.wind.xboot.enums.contants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/13 15:13
 * @Description:
 */
@Service
public class CXArSkGalleryManage {

    @Autowired
    private IArUserSkGalleryService skGalleryService;
    @Autowired
    private IArUserSkPicService skPicService;

    /**
     * 图库找图发布
     * @param dto
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void publishSkGallery(arUserSkGalleryDto dto, Long userId)throws Exception {
        //1.插入图库找图数据
        ArUserSkGallery skGallery = new ArUserSkGallery();
        BeanUtils.copyProperties(dto,skGallery);
        skGallery.setUserId(userId);
        if(dto.getPics()!=null && dto.getPics().size()>0){
            skGallery.setCoverImg(dto.getPics().get(0));
        }else {
            skGallery.setCoverImg(contants.defaultCoverImg);
        }
        skGalleryService.insert(skGallery);

        //2.插入图库找图图片数据
        List<ArUserSkPic> pics = new ArrayList<>();
        for(String pic:dto.getPics()){
            ArUserSkPic pic1 = new ArUserSkPic();
            pic1.setImg(pic);
            pic1.setSeekGalleryId(skGallery.getId());
            pics.add(pic1);
        }
        skPicService.insertBatch(pics);

    }
}
