package chrislovecnm.k8s.gpmr.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A Race.
 */

@Table(name = "race")
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private UUID raceId;

    private UUID petCategoryId;

    private String petCategoryName;

    private Integer numOfPets;

    private Integer length;

    private String description;

    private UUID winnerId;

    private Date startTime;

    private Date endTime;

    private Float baseSpeed;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Integer getNumOfPets() {
        return numOfPets;
    }

    public void setNumOfPets(Integer numOfPets) {
        this.numOfPets = numOfPets;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(UUID winnerId) {
        this.winnerId = winnerId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Float getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(Float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Race race = (Race) o;
        if(race.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, race.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Race{" +
            "id=" + id +
            ", raceId='" + raceId + "'" +
            ", petCategoryId='" + petCategoryId + "'" +
            ", petCategoryName='" + petCategoryName + "'" +
            ", numOfPets='" + numOfPets + "'" +
            ", length='" + length + "'" +
            ", description='" + description + "'" +
            ", winnerId='" + winnerId + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", baseSpeed='" + baseSpeed + "'" +
            '}';
    }
}
