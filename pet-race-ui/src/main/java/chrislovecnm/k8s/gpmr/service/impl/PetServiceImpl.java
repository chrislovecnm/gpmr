package chrislovecnm.k8s.gpmr.service.impl;

import chrislovecnm.k8s.gpmr.domain.Pet;
import chrislovecnm.k8s.gpmr.repository.PetRepository;
import chrislovecnm.k8s.gpmr.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Service Implementation for managing Pet.
 */
@Service
public class PetServiceImpl implements PetService {

    private final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);

    @Inject
    private PetRepository petRepository;

    /**
     * Save a pet.
     *
     * @param pet the entity to save
     * @return the persisted entity
     */
    public Pet save(Pet pet) {
        log.debug("Request to save Pet : {}", pet);
        return petRepository.save(pet);
    }

    /**
     * Get all the pets.
     *
     * @return the list of entities
     */
    public List<Pet> findAll() {
        log.debug("Request to get all Pets");
        return petRepository.findAll();
    }

    /**
     * Get one pet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Pet findOne(String id) {
        log.debug("Request to get Pet : {}", id);
        return petRepository.findOne(UUID.fromString(id));
    }

    /**
     * Delete the  pet by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Pet : {}", id);
        petRepository.delete(UUID.fromString(id));
    }
}
