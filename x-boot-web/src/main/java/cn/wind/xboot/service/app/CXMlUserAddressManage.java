package cn.wind.xboot.service.app;

import cn.wind.db.ml.entity.MlUserAddress;
import cn.wind.db.ml.service.IMlUserAddressService;
import cn.wind.xboot.dto.app.ml.mlUserAddressDto;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/31 15:08
 * @Description:
 */
@Service
public class CXMlUserAddressManage {

    @Autowired
    private IMlUserAddressService addressService;

    @Transactional
    public void addUserAddress(mlUserAddressDto dto, Long userId)throws Exception {
        //1.添加地址
        MlUserAddress address = new MlUserAddress();
        BeanUtils.copyProperties(dto,address);
        address.setUserId(userId);
        if(dto.getType()==1){
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",userId);
            map.put("type",1);
            map.put("status",1);
            MlUserAddress defaultAddress = addressService.findOneByConditions(map);
            if(defaultAddress != null){
                defaultAddress.setType(0);
                addressService.updateById(defaultAddress);
            }
        }
        addressService.insert(address);
    }

    @Transactional
    public void modifyUserAddress(mlUserAddressDto dto, Long userAddressId, Long userId)throws Exception {
        //检索数据
        Map<String,Object> map = Maps.newHashMap();
        if(dto.getType()==1){
            map.put("userId",userId);
            map.put("type",1);
            map.put("status",1);
            MlUserAddress defaultAddress = addressService.findOneByConditions(map);
            if(defaultAddress != null){
                defaultAddress.setType(0);
                addressService.updateById(defaultAddress);
            }
        }
        map = Maps.newHashMap();
        map.put("userAddressId",userAddressId);
        map.put("name",dto.getName());
        map.put("province",dto.getProvince());
        map.put("city",dto.getCity());
        map.put("county",dto.getCounty());
        map.put("adressDetail",dto.getAdressDetail());
        map.put("phone",dto.getPhone());
        map.put("type",dto.getType());
        addressService.updateByConditions(map);
    }

    @Transactional
    public void deleteUserAddress(Long userAddressId, Long userId)throws Exception {
        MlUserAddress address = addressService.selectById(userAddressId);
        if(address.getType()==1){
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",userId);
            map.put("type",0);
            map.put("status",1);
            map.put("orderBy","create_time desc");
            List<MlUserAddress> addresses = addressService.findAllByConditions(map);
            if(addresses != null && addresses.size()>0){
                MlUserAddress newDefaultAddress = addresses.get(0);
                newDefaultAddress.setType(1);
                addressService.updateById(newDefaultAddress);
            }
        }
        address.setStatus(0);
        addressService.updateById(address);
    }
}
