package my.junw.pao.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author ld 
 * @since 2024-06-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tag")
public class TagEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    @TableField("tagName")
    private String tagName;

    /**
     * 用户 id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 父标签 id
     */
    @TableField("parentId")
    private Long parentId;

    /**
     * 0 - 不是, 1 - 父标签
     */
    @TableField("isParent")
    private Byte isParent;

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

    public static final String TAGNAME = "tagName";

    public static final String USERID = "userId";

    public static final String PARENTID = "parentId";

    public static final String ISPARENT = "isParent";

    public static final String CREATETIME = "createTime";

    public static final String UPDATETIME = "updateTime";

    public static final String ISDELETE = "isDelete";
}
