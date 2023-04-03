package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Borrow implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer uid;

    private Integer bid;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime startTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime endTime;

    private Integer status = 0;


}
