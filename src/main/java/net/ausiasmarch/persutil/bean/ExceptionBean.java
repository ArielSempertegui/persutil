package net.ausiasmarch.persutil.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionBean {
    private Integer status;
    private String message;
    private Long timestamp;
}
