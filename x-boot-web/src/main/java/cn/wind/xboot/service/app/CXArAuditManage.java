package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.ArSignAudit;
import cn.wind.db.ar.entity.ArStoreAduit;
import cn.wind.db.ar.entity.ArStorePic;
import cn.wind.db.ar.entity.ArTattooAduit;
import cn.wind.db.ar.service.IArSignAuditService;
import cn.wind.db.ar.service.IArStoreAduitService;
import cn.wind.db.ar.service.IArStorePicService;
import cn.wind.db.ar.service.IArTattooAduitService;
import cn.wind.xboot.dto.app.ar.arNameAduitDto;
import cn.wind.xboot.dto.app.ar.arStoreAduitDto;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 16:12
 * @Description: 认证管理
 */
@Service
public class CXArAuditManage {

    @Autowired
    private IArSignAuditService signAuditService;
    @Autowired
    private IArStoreAduitService storeAduitService;
    @Autowired
    private IArStorePicService storePicService;
    @Autowired
    private IArTattooAduitService tattooAduitService;

    @Transactional
    public void newSignAudit(Long userId)throws Exception{
        ArSignAudit signAudits = new ArSignAudit();
        signAudits.setContent("签约保障");
        signAudits.setStatus(1);
        signAudits.setUserId(userId);
        signAuditService.insert(signAudits);
    }

    @Transactional
    public void newStoreAudit(arStoreAduitDto dto, Long userId)throws Exception {
        ArStoreAduit storeAduit = new ArStoreAduit();
        BeanUtils.copyProperties(dto,storeAduit);
        storeAduit.setUserId(userId);
        storeAduit.setContent("店铺认证");
        storeAduit.setStatus(1);
        storeAduitService.insert(storeAduit);

        if(dto.getPics() != null){
            List<ArStorePic> pics = Lists.newArrayList();
            for(String p:dto.getPics()){
                ArStorePic pic = new ArStorePic();
                pic.setStoreId(storeAduit.getId());
                pic.setImg(p);
                storePicService.insert(pic);
            }
        }
    }

    @Transactional
    public void newNameAudit(arNameAduitDto dto, Long userId)throws Exception {
        ArTattooAduit arTattooAduit = new ArTattooAduit();
        BeanUtils.copyProperties(dto,arTattooAduit);
        arTattooAduit.setUserId(userId);
        arTattooAduit.setStatus(1);
        tattooAduitService.insert(arTattooAduit);
    }
}
