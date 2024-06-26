package my.junw.pao.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 队伍
 * </p>
 *
 * @author ld 
 * @since 2024-06-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("team")
public class TeamEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 队伍名称
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 最大人数
     */
    @TableField("maxNum")
    private Integer maxNum;

    /**
     * 过期时间
     */
    @TableField("expireTime")
    private Date expireTime;

    /**
     * 用户id（队长 id）
     */
    @TableField("userId")
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    @TableField("status")
    private Integer status;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    @TableField("updateTime")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField("isDelete")
    private Byte isDelete;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String MAXNUM = "maxNum";

    public static final String EXPIRETIME = "expireTime";

    public static final String USERID = "userId";

    public static final String STATUS = "status";

    public static final String PASSWORD = "password";

    public static final String CREATETIME = "createTime";

    public static final String UPDATETIME = "updateTime";

    public static final String ISDELETE = "isDelete";
}
