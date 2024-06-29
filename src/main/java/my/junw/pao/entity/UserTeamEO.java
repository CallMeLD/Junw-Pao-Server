package my.junw.pao.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户队伍关系
 * </p>
 *
 * @author ld 
 * @since 2024-06-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_team")
public class UserTeamEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 队伍id
     */
    @TableField("teamId")
    private Long teamId;

    /**
     * 加入时间
     */
    @TableField("joinTime")
    private Date joinTime;

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

    public static final String USERID = "userId";

    public static final String TEAMID = "teamId";

    public static final String JOINTIME = "joinTime";

    public static final String CREATETIME = "createTime";

    public static final String UPDATETIME = "updateTime";

    public static final String ISDELETE = "isDelete";
}
