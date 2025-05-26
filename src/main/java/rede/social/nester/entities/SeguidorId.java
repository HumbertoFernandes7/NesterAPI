package rede.social.nester.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SeguidorId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long seguidorId;
    private Long seguidoId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeguidorId)) return false;
        SeguidorId that = (SeguidorId) o;
        return Objects.equals(seguidorId, that.seguidorId) &&
               Objects.equals(seguidoId, that.seguidoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seguidorId, seguidoId);
    }
}
