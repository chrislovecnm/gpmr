package chrislovecnm.k8s.gpmr.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A RaceData.
 */

@Table(name = "race_data")
public class RaceData implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID raceDataId;

    @PartitionKey
    private UUID petId;

    private UUID raceId;

    private String petName;

    private String petCategoryName;

    private UUID petCategoryId;

    private Integer interval;

    private Integer runnerPosition;

    private Double runnerDistance;

    private Date startTime;

    private Boolean finished;

    private Double runnerPreviousDistance;

    public UUID getRaceDataId() {
        return raceDataId;
    }

    public void setRaceDataId(UUID raceDataId) {
        this.raceDataId = raceDataId;
    }

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public UUID getRaceId() {
        return raceId;
    }

    public void setRaceId(UUID raceId) {
        this.raceId = raceId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetCategoryName() {
        return petCategoryName;
    }

    public void setPetCategoryName(String petCategoryName) {
        this.petCategoryName = petCategoryName;
    }

    public UUID getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(UUID petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getRunnerPosition() {
        return runnerPosition;
    }

    public void setRunnerPosition(Integer runnerPosition) {
        this.runnerPosition = runnerPosition;
    }

    public Double getRunnerDistance() {
        return runnerDistance;
    }

    public void setRunnerDistance(Double runnerDistance) {
        this.runnerDistance = runnerDistance;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Boolean isFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Double getRunnerPreviousDistance() {
        return runnerPreviousDistance;
    }

    public void setRunnerPreviousDistance(Double runnerPreviousDistance) {
        this.runnerPreviousDistance = runnerPreviousDistance;
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
        if(raceData.raceDataId == null || raceDataId == null) {
            return false;
        }
        return Objects.equals(raceDataId, raceData.raceDataId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(raceDataId);
    }

    @Override
    public String toString() {
        return "RaceData{" +
            "raceDataId='" + raceDataId + "'" +
            ", petId='" + petId + "'" +
            ", raceId='" + raceId + "'" +
            ", petName='" + petName + "'" +
            ", petCategoryName='" + petCategoryName + "'" +
            ", petCategoryId='" + petCategoryId + "'" +
            ", interval='" + interval + "'" +
            ", runnerPosition='" + runnerPosition + "'" +
            ", runnerDistance='" + runnerDistance + "'" +
            ", startTime='" + startTime + "'" +
            ", finished='" + finished + "'" +
            ", runnerPreviousDistance='" + runnerPreviousDistance + "'" +
            '}';
    }
}
