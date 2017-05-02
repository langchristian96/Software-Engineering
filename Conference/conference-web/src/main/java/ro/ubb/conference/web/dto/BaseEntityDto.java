package ro.ubb.conference.web.dto;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BaseEntityDto implements Serializable {
    private Long id;
}