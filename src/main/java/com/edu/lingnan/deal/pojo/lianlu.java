package com.edu.lingnan.deal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author shaosen
 * @Description //TODO
 * @Date 16:25 2020/10/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class lianlu {
    String name;
    String mark;
}
