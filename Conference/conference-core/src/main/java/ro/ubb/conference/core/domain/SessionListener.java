package ro.ubb.conference.core.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CristianCosmin on 19.05.2017.
 */
@Entity
@Table(name="SessionListener")
@IdClass(SessionListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SessionListener implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="ListenerId")
    private Listener listener;

    @Id
    @ManyToOne(optional=false,fetch=FetchType.EAGER)
    @JoinColumn(name="listenerSession")
    private Session listenerSession;
}
