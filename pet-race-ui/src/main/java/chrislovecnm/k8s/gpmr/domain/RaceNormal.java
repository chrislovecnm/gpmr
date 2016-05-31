package chrislovecnm.k8s.gpmr.domain;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A RaceNormal.
 */

@Table(name = "race_normal")
public class RaceNormal implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private UUID raceNormalId;

    private UUID raceId;

    private UUID petCategoryId;

    private String petCategoryName;

    private Date currentTime;

    private Float normalLoc;

    private Float normalScale;

    private Integer normalSize;

    private List<BigDecimal> normals;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRaceNormalId() {
        return raceNormalId;
    }

    public void setRaceNormalId(UUID raceNormalId) {
        this.raceNormalId = raceNormalId;
    }

    public UUID getRaceId() {
        return raceId;
    }

    public void setRaceId(UUID raceId) {
        this.raceId = raceId;
    }

    public UUID getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(UUID petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public String getPetCategoryName() {
        return petCategoryName;
    }

    public void setPetCategoryName(String petCategoryName) {
        this.petCategoryName = petCategoryName;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Float getNormalLoc() {
        return normalLoc;
    }

    public void setNormalLoc(Float normalLoc) {
        this.normalLoc = normalLoc;
    }

    public Float getNormalScale() {
        return normalScale;
    }

    public void setNormalScale(Float normalScale) {
        this.normalScale = normalScale;
    }

    public Integer getNormalSize() {
        return normalSize;
    }

    public void setNormalSize(Integer normalSize) {
        this.normalSize = normalSize;
    }

    public List<BigDecimal> getNormals() {
        return this.normals;
    }

    public void setNormals(List<BigDecimal> normals) {
        this.normals = normals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RaceNormal raceNormal = (RaceNormal) o;
        if (raceNormal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, raceNormal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RaceNormal{" +
            "id=" + id +
            ", raceNormalId='" + raceNormalId + "'" +
            ", raceId='" + raceId + "'" +
            ", petCategoryId='" + petCategoryId + "'" +
            ", petCategoryName='" + petCategoryName + "'" +
            ", currentTime='" + currentTime + "'" +
            ", normalLoc='" + normalLoc + "'" +
            ", normalScale='" + normalScale + "'" +
            ", normalSize='" + normalSize + "'" +
            '}';
    }
}
