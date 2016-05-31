package chrislovecnm.k8s.gpmr.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A RaceResult.
 */

@Table(name = "raceResult")
public class RaceResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private UUID raceResultId;

    private UUID raceId;

    private UUID petCategoryId;

    private UUID raceParticipantId;

    private String petName;

    private String petType;

    private UUID petColor;

    private String petCategoryName;

    private Integer finishPosition;

    private BigDecimal finishTime;

    private Date startTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRaceResultId() {
        return raceResultId;
    }

    public void setRaceResultId(UUID raceResultId) {
        this.raceResultId = raceResultId;
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

    public UUID getRaceParticipantId() {
        return raceParticipantId;
    }

    public void setRaceParticipantId(UUID raceParticipantId) {
        this.raceParticipantId = raceParticipantId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public UUID getPetColor() {
        return petColor;
    }

    public void setPetColor(UUID petColor) {
        this.petColor = petColor;
    }

    public String getPetCategoryName() {
        return petCategoryName;
    }

    public void setPetCategoryName(String petCategoryName) {
        this.petCategoryName = petCategoryName;
    }

    public Integer getFinishPosition() {
        return finishPosition;
    }

    public void setFinishPosition(Integer finishPosition) {
        this.finishPosition = finishPosition;
    }

    public BigDecimal getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(BigDecimal finishTime) {
        this.finishTime = finishTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RaceResult raceResult = (RaceResult) o;
        if(raceResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, raceResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RaceResult{" +
            "id=" + id +
            ", raceResultId='" + raceResultId + "'" +
            ", raceId='" + raceId + "'" +
            ", petCategoryId='" + petCategoryId + "'" +
            ", raceParticipantId='" + raceParticipantId + "'" +
            ", petName='" + petName + "'" +
            ", petType='" + petType + "'" +
            ", petColor='" + petColor + "'" +
            ", petCategoryName='" + petCategoryName + "'" +
            ", finishPosition='" + finishPosition + "'" +
            ", finishTime='" + finishTime + "'" +
            ", startTime='" + startTime + "'" +
            '}';
    }
}
