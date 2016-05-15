package chrislovecnm.k8s.gpmr.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A RaceData.
 */

@Table(name = "raceData")
public class RaceData implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private UUID petId;

    private String petName;

    private String petCategory;

    private UUID petCategoryId;

    private Integer runnerPostion;

    private String runnerSashColor;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public UUID getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(UUID petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public Integer getRunnerPostion() {
        return runnerPostion;
    }

    public void setRunnerPostion(Integer runnerPostion) {
        this.runnerPostion = runnerPostion;
    }

    public String getRunnerSashColor() {
        return runnerSashColor;
    }

    public void setRunnerSashColor(String runnerSashColor) {
        this.runnerSashColor = runnerSashColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RaceData raceData = (RaceData) o;
        if(raceData.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, raceData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RaceData{" +
            "id=" + id +
            ", petId='" + petId + "'" +
            ", petName='" + petName + "'" +
            ", petCategory='" + petCategory + "'" +
            ", petCategoryId='" + petCategoryId + "'" +
            ", runnerPostion='" + runnerPostion + "'" +
            ", runnerSashColor='" + runnerSashColor + "'" +
            '}';
    }
}