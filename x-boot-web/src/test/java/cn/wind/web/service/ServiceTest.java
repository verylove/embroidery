//package cn.wind.web.service;
//
//import cn.wind.db.pf.entity.PfNotice;
//import cn.wind.db.pf.service.IPfNoticeService;
//import cn.wind.db.sys.entity.*;
//import cn.wind.db.sys.service.*;
//import cn.wind.xboot.XbootApplication;
//import cn.wind.xboot.vo.sys.SysPermissionVo;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import org.assertj.core.util.Lists;
//import org.dozer.DozerBeanMapper;
//import org.dozer.loader.api.BeanMappingBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import static org.dozer.loader.api.TypeMappingOptions.mapEmptyString;
//import static org.dozer.loader.api.TypeMappingOptions.mapNull;
//
///**
// * <p>Title: ServiceTest</p>
// * <p>Description: TODO</p>
// *
// * @author xukk
// * @version 1.0
// * @date 2018/6/21
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = XbootApplication.class)
//public class ServiceTest {
//    @Autowired
//    private ISysUserService sysUserService;
//    @Autowired
//    private ISysRoleService sysRoleService;
//    @Autowired
//    private ISysPrivilegeService sysPrivilegeService;
//    @Autowired
//    private ISysUserInfoService sysUserInfoService;
//    @Autowired
//    private ISysPermissionService permissionService;
//    @Autowired
//    protected DozerBeanMapper beanMapper;
//    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//
//    void copyProperties(final Object sources, final Object destination) {
//        beanMapper.addMapping(new BeanMappingBuilder() {
//            @Override
//            protected void configure() {
//                mapping(sources.getClass(), destination.getClass(), mapNull(false), mapEmptyString(false));
//            }
//        });
//        beanMapper.map(sources, destination);
//    }
//    @Test
//    public void update(){
//        SysPermission permission=permissionService.selectById(1009726998067458050L);
//        SysPermissionVo sysPermissionVo=new SysPermissionVo();
//        sysPermissionVo.setTitle("系统设置1111");
//        copyProperties(sysPermissionVo,permission);
//        System.out.println(permission);
//
//    }
//    @Test
//    public void queryAll(){
//        EntityWrapper<SysPermission> ew = new EntityWrapper<SysPermission>();
//        List<SysPermission> permissions=permissionService.selectList(ew);
//        System.out.println(permissions);
//    }
//    @Test
//    public void insertRoleAdmin(){
//        SysRole sysRole=new SysRole();
//        sysRole.setName("ADMIN");
//        sysRole.setDescription("管理员");
//        sysRole.setEnabled(true);
//        sysRole.setTitle("管理员");
//        sysRoleService.insert(sysRole);
//    }
//    @Test
//    public void insertRoleUser(){
//        SysRole sysRole=new SysRole();
//        sysRole.setName("TEST");
//        sysRole.setDescription("测试人员");
//        sysRole.setEnabled(true);
//        sysRole.setTitle("测试人员");
//        sysRoleService.insert(sysRole);
//    }
//
//    @Test
//    public void insertUser(){
//        SysUser sysUser=new SysUser();
//        sysUser.setUsername("xukk");
//        sysUser.setPassword(passwordEncoder.encode("123456"));
//        sysUser.setUuid(UUID.randomUUID().toString());
//        sysUser.setStatus(1);
//        List<SysRole> sysRoles=Lists.newArrayList();
//        SysRole sysRole=sysRoleService.selectById(1009712269710225409L);
//        sysRoles.add(sysRole);
//        sysUser.setRoles(sysRoles);
//        sysUserService.save(sysUser);
//    }
//
//    @Test
//    public void testPermission(){
//        SysPermission sysPermission=new SysPermission();
//        sysPermission.setName("sys");
//        sysPermission.setTitle("系统管理");
//        sysPermission.setIcon("ios-gear");
//        sysPermission.setLevel(1);
//        sysPermission.setPId(0L);
//        sysPermission.setSort(new BigDecimal("0"));
//        sysPermission.setType("MENU");
//        permissionService.insert(sysPermission);
//    }
//
//    @Test
//    public void testPermission1(){
//        SysPermission sysPermission=new SysPermission();
//        sysPermission.setName("menu-manage");
//        sysPermission.setTitle("权限管理");
//        sysPermission.setIcon("navicon-round");
//        sysPermission.setLevel(2);
//        sysPermission.setPId(1009726998067458050L);
//        sysPermission.setSort(new BigDecimal("1.3"));
//        sysPermission.setType("MENU");
//        permissionService.insert(sysPermission);
//        SysPermission sysPermission1=new SysPermission();
//        sysPermission1.setName("log-manage");
//        sysPermission1.setTitle("日志管理");
//        sysPermission1.setIcon("android-list");
//        sysPermission1.setLevel(2);
//        sysPermission1.setPId(1009726998067458050L);
//        sysPermission1.setSort(new BigDecimal("1.4"));
//        sysPermission1.setType("MENU");
//        permissionService.insert(sysPermission1);
//    }
//
//
//    @Test
//    public void testPermissionButton(){
//        SysPermission sysPermission=new SysPermission();
//        sysPermission.setTitle("分配权限");
//        sysPermission.setName("role-editPerm");
//        sysPermission.setLevel(2);
//        sysPermission.setPId(1009730748542828546L);
//        sysPermission.setButtonType("editPerm");
//        sysPermission.setSort(new BigDecimal("1.24"));
//        sysPermission.setType("BUTTON");
//        permissionService.insert(sysPermission);
//    }
//
//    @Test
//    public void editPerm(){
//        List<SysPrivilege> sysPrivileges=Lists.newArrayList();
//        List<SysPermission> sysPermissions=permissionService.findAll();
//        sysPermissions.forEach(v->{
//            SysPrivilege sysPrivilege=new SysPrivilege();
//            sysPrivilege.setRoleId(1009712269710225409L);
//            sysPrivilege.setPermissionId(v.getId());
//            sysPrivileges.add(sysPrivilege);
//        });
//        sysPrivilegeService.insertBatch(sysPrivileges);
//    }
//
//    @Test
//    public void insertUserInfo(){
//        SysUserInfo sysUserInfo=new SysUserInfo();
//        sysUserInfo.setBirthday(new Date());
//        sysUserInfo.setMobile("18268213183");
//        sysUserInfo.setFixedPhone("0576-85621474");
//        sysUserInfo.setEmail("410551654@qq.com");
//        sysUserInfo.setVersion(1);
//        sysUserInfo.setUserNo(System.currentTimeMillis()+"");
//        sysUserInfo.setUserId(1009722428004200450L);
//        sysUserInfoService.insert(sysUserInfo);
//    }
//    @Autowired
//    private IPfNoticeService pfNoticeService;
//
//    @Test
//    public void testInsertNotice(){
//        PfNotice pfNotice=new PfNotice();
//        pfNotice.setDelFlag(0);
//        pfNotice.setContent("这是您点击的《欢迎登录x-boot后台管理系统，来了解他的用途吧》的相关内容。");
//        pfNotice.setTitle("欢迎登录x-boot后台管理系统，来了解他的用途吧");
//        pfNotice.setReaded(0);
//        pfNotice.setSortOrder(0);
//        pfNotice.setType(0);
//        pfNotice.setCreateBy("0");
//        pfNotice.setModifyBy("0");
//        pfNotice.setCreateTime(new Date());
//        pfNotice.setModifyTime(pfNotice.getCreateTime());
//        pfNoticeService.insert(pfNotice);
//    }
//    @Test
//    public void testInsertNotice1(){
//        PfNotice pfNotice=new PfNotice();
//        pfNotice.setDelFlag(0);
//        pfNotice.setContent("这是您点击的《使用iView-admin和iView-ui组件库快速搭建你的后台系统吧》的相关内容。");
//        pfNotice.setTitle("使用iView-admin和iView-ui组件库快速搭建你的后台系统吧");
//        pfNotice.setReaded(0);
//        pfNotice.setSortOrder(0);
//        pfNotice.setType(0);
//        pfNotice.setCreateBy("0");
//        pfNotice.setModifyBy("0");
//        pfNotice.setCreateTime(new Date());
//        pfNotice.setModifyTime(pfNotice.getCreateTime());
//        pfNoticeService.insert(pfNotice);
//    }
//    @Test
//    public void testInsertNotice2(){
//        PfNotice pfNotice=new PfNotice();
//        pfNotice.setDelFlag(0);
//        pfNotice.setContent("这是您点击的《喜欢x-boot的话，欢迎到github主页给个star吧》的相关内容。");
//        pfNotice.setTitle("喜欢x-boot的话，欢迎到github主页给个star吧");
//        pfNotice.setReaded(0);
//        pfNotice.setSortOrder(0);
//        pfNotice.setType(0);
//        pfNotice.setCreateBy("0");
//        pfNotice.setModifyBy("0");
//        pfNotice.setCreateTime(new Date());
//        pfNotice.setModifyTime(pfNotice.getCreateTime());
//        pfNoticeService.insert(pfNotice);
//    }
//    @Test
//    public void testInsertNotice3(){
//        PfNotice pfNotice=new PfNotice();
//        pfNotice.setDelFlag(0);
//        pfNotice.setContent("这是您点击的《这是一条您已经读过的消息》的相关内容。");
//        pfNotice.setTitle("这是一条您已经读过的消息");
//        pfNotice.setReaded(1);
//        pfNotice.setSortOrder(0);
//        pfNotice.setType(0);
//        pfNotice.setCreateBy("0");
//        pfNotice.setModifyBy("0");
//        pfNotice.setCreateTime(new Date());
//        pfNotice.setModifyTime(pfNotice.getCreateTime());
//        pfNoticeService.insert(pfNotice);
//    }
//    @Test
//    public void testInsertNotice4(){
//        PfNotice pfNotice=new PfNotice();
//        pfNotice.setDelFlag(1);
//        pfNotice.setContent("这是您点击的《这是一条被删除的消息》的相关内容。");
//        pfNotice.setTitle("这是一条被删除的消息");
//        pfNotice.setReaded(1);
//        pfNotice.setSortOrder(0);
//        pfNotice.setType(0);
//        pfNotice.setCreateBy("0");
//        pfNotice.setModifyBy("0");
//        pfNotice.setCreateTime(new Date());
//        pfNotice.setModifyTime(pfNotice.getCreateTime());
//        pfNoticeService.insert(pfNotice);
//    }
//}
