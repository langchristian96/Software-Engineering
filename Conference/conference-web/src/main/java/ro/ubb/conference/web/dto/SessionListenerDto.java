package ro.ubb.conference.web.dto;

import lombok.*;

/**
 * Created by CristianCosmin on 19.05.2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SessionListenerDto {
    private Long sessionId;
    private Long listenerId;
}