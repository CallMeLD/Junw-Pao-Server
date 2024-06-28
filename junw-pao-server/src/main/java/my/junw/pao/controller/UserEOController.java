package my.junw.pao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import my.junw.pao.exception.BusinessException;
import my.junw.pao.common.BaseResponse;
import my.junw.pao.common.ErrorCode;
import my.junw.pao.common.ResultUtils;
import my.junw.pao.entity.UserEO;
import my.junw.pao.service.IUserEOService;
import my.junw.pao.util.UserToUser;
import my.junw.pao.vo.UserLoginRequst;
import my.junw.pao.vo.UserRegisterRequst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static my.junw.pao.contant.UserConstant.ADMIN_ROLE;
import static my.junw.pao.contant.UserConstant.USER_LOGIN_STATE;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author ld
 * @since 2024-06-21
 */
@RestController   // 返回值默认为 json 类型
@RequestMapping("/user")
@Tag(name = "|UserEO| 用户-前端控制器 ")
public class UserEOController {

    @Resource
    private IUserEOService service;

    /**
     * 注册
     * @param urr
     * @return
     */
    @Operation(summary = "注册", description = "用户初始注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequst urr){
        // 入参校验，无关业务逻辑
        if(urr == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = urr.getUserAccount();
        String userPassword = urr.getUserPassword();
        String checkPassword = urr.getCheckPassword();
        if(StringUtils.isBlank(userAccount) || StringUtils.isBlank(userPassword) || StringUtils.isBlank(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(service.userRegister(userAccount, userPassword, checkPassword));
    }

    /**
     * 登录
     * @param ulr
     * @param hsr
     * @return
     */
    @Operation(summary = "登录", description = "用户登录系统")
    @PostMapping("/login")
    public BaseResponse<UserEO> userLogin(@RequestBody UserLoginRequst ulr, HttpServletRequest hsr){
        // 入参校验，无关业务逻辑
        if(ulr == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = ulr.getUserAccount();
        String userPassword = ulr.getUserPassword();
        if(StringUtils.isBlank(userAccount) || StringUtils.isBlank(userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(service.doLogin(userAccount, userPassword,hsr));
    }

    /**
     * 用户注销
     * @param hsr
     * @return
     */
    @Operation(summary = "注销", description = "用户退出登录")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest hsr){
        if(hsr == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = service.userLogout(hsr);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     * @param hsr
     * @return
     */
    @Operation(summary = "获取当前登录用户", description = "获取当前登录用户的详细信息，前台用于显示信息、判定权限等")
    @GetMapping("/current")
    public BaseResponse<UserEO> getCurrentUser(HttpServletRequest hsr){
        UserEO cuser = (UserEO) hsr.getSession().getAttribute(USER_LOGIN_STATE);
        if(cuser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = cuser.getId();
        // 数据库 验证用户是否合法
        UserEO dbuser = service.getById(userId);
        UserEO safeUser = UserToUser.INSTANCE.toSafeUser(dbuser);
        return  ResultUtils.success(safeUser);
    }


    /**
     * 根据用户名查询用户
     * @param userName  可为空
     * @return
     */
    @Operation(summary = "用户管理-查询用户", description = "获取用户信息列表；仅限管理员可查，查询条件：用户名")
    @GetMapping("/search")
    public BaseResponse<List<UserEO>> userSearch(String userName,HttpServletRequest hsr){
        // 仅管理员可查询
        if(!isAdmin(hsr)){
            throw new BusinessException(ErrorCode.NO_AUTH,"仅限管理员可操作");
        }
        // 查询
        QueryWrapper<UserEO> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(userName)){
            queryWrapper.like(UserEO.USERNAME,userName);
        }
        List<UserEO> rlist = service.list(queryWrapper);
        List<UserEO> safelist = rlist.stream().map(user -> UserToUser.INSTANCE.toSafeUser(user)).collect(Collectors.toList());
        // 脱敏
        return ResultUtils.success(safelist);
    }

    /**
     * 根据id删除用户
     * @param id   逻辑删除
     * @return
     */
    @Operation(summary = "根据id删除用户", description = "根据id删除用户；仅限管理员可查，逻辑删除")
    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteUser(long id, HttpServletRequest hsr){
        // 仅管理员可查询
        if(!isAdmin(hsr)){
            throw new BusinessException(ErrorCode.NO_AUTH,"仅限管理员可操作");
        }
        if(id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不在数据范围内");
        }
        return ResultUtils.success(service.removeById(id));
    }

    // 判定当前登录用户是否为管理员
    private boolean isAdmin(HttpServletRequest hsr){
        // 鉴权
        UserEO loginUser = (UserEO)hsr.getSession().getAttribute(USER_LOGIN_STATE);
        // 仅管理员可查询
        if(loginUser != null && loginUser.getUserRole() != null &&  ADMIN_ROLE == loginUser.getUserRole()){
            return true;
        }
        return false;
    }

    /**
     * 根据标签名集合查找用户
     * @param tagNameList
     * @return
     */
    @Operation(summary = "根据标签名集合查找用户", description = "根据标签名集合查找用户；参数不可为空")
    @GetMapping("/search/tags")
    public BaseResponse<List<UserEO>> searchUsersByTags(@RequestParam(required = false) List<String> tagNameList) {
        // 解除注解必填校验；自定义返回错误信息
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<UserEO> userList = service.searchUsersByTags(tagNameList);
        return ResultUtils.success(userList);
    }

















}
