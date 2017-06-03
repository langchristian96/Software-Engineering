package ro.ubb.conference.core.domain;

import lombok.*;

import java.io.Serializable;

/**
 * Created by CristianCosmin on 19.05.2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SessionListenerPK implements Serializable {
    private Listener listener;
    private Session listenerSession;
}
