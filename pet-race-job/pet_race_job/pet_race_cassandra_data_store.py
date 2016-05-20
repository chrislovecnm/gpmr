import logging
from datetime import datetime

from cassandra.cqlengine.connection import get_session
from cassandra.cqlengine.connection import set_session
from cassandra.cqlengine.connection import setup as setup_cass
from cassandra.util import uuid_from_time

from pet_race_job.model import *


class PetRaceCassandraDataStore(object):
    seeds = []
    keyspace = 'gpmr'
    session = None
    logger = None

    def __init__(self, seeds, keyspace):
        super()
        # if kwargs is None:
        self.seeds = seeds
        self.keyspace = keyspace
        setup_cass(self.seeds, self.keyspace)
        self.session = get_session()
        set_session(self.session)
        self.logger = logging.getLogger('pet_race_job')

    def get_pets_by_name(self, pet_name):
        q = Pets.objects.filter(name=pet_name)
        # may be q.first()
        pet_objs = q.get()
        if len(pet_objs) > 1:
            raise ValueError('pets not found, name: ', pet_name)
        self.logger.debug("loaded pet", pet_objs)
        return pet_objs

    def get_pets_by_category_name(self, pet_cat_name):
        q = Pets.objects(petCategoryName=pet_cat_name)
        self.logger.debug("loaded pets")
        return q

    def get_pet_category_by_name(self, category_name):
        q = PetCategories.objects.filter(name=category_name)
        pet_cat = q.get()
        if pet_cat is None:
            raise ValueError('category not found: ', category_name)
        self.logger.debug("loaded cat")
        return pet_cat

    def create_race(self, length, description, pet_category_name):
        dt = datetime.utcnow()
        uuid = uuid_from_time(datetime.utcnow())

        # TODO this is loading all pets ... random number??
        race_pets = self.get_pets_by_category_name(pet_category_name)
        pet_category = self.get_pet_category_by_name(pet_category_name)


        pet_ids = []
        for _pet in race_pets:
            pet_ids.append(_pet["petId"])

        saved_race = {
            'raceId': uuid,
            'numOfPets': len(race_pets),
            'length': length,
            'description': description,
            'petCategoryId': pet_category['petCategoryId'],
            'petCategoryName': pet_category['name'],
            'startTime': dt,
            'racersIds': pet_ids,
            'baseSpeed': pet_category['speed']
        }

        Race.create(
            raceId=uuid,
            numOfPets=len(race_pets),
            length=length,
            description=description,
            petCategoryId=pet_category['petCategoryId'],
            petCategoryName=pet_category['name'],
            startTime=dt,
            racersIds=pet_ids,
            baseSpeed=pet_category['speed']
        )

        participants = {}

        for pet in race_pets:
            p_id = uuid_from_time(datetime.utcnow())

            participant = {
                'raceParticipantsId': p_id,
                'petId': pet["petId"],
                'raceId': uuid,
                'petName': pet["name"],
                # petColor = columns.UUID(primary_key=True, default=uuid.uuid4)
                'petCategoryName': pet_category['name'],
                'petCategoryId': pet_category['petCategoryId'],
                'startTime': dt,
                'endTime': None,
                'finished': False,
                'finished_position': None,
                'racers_positions_by_time': [],
                'current_distance': 0
            }

            participants[p_id] = participant

            RaceParticipants.create(
                raceParticipantsId=p_id,
                petId=_pet["petId"],
                raceId=uuid,
                petName=_pet["name"],
                # petColor = columns.UUID(primary_key=True, default=uuid.uuid4)
                petCategoryName=pet_category['name'],
                petCategoryId=pet_category['name'],
                startTime=dt
            )

        self.logger.debug("race created")

        return saved_race, participants

    def save_normal(self, normals, loc, scale, size, race_obj):
        """
        Saves a race_normal to C*

        raceNormalId = columns.UUID(primary_key=True, default=uuid.uuid4)
        raceId = columns.UUID(primary_key=True, default=uuid.uuid4)
        petCategoryName = columns.Text(required=False)
        petCategoryId = columns.UUID(primary_key=True, default=uuid.uuid4)
        currentTime = columns.DateTime()
        normals = columns.List()
        normalLoc = columns.Float()
        normalScale = columns.Float()
        normalSize = columns.Tuple()
        """
        dt = datetime.utcnow()
        uuid = uuid_from_time(datetime.utcnow())

        RaceNormal.create(
            raceNormalId=uuid,
            raceId=race_obj['raceId'],
            petCategoryName=race_obj['petCategoryName'],
            petCategoryId=race_obj['petCategoryId'],
            currentTime=dt,
            normals=normals,
            normalLoc=loc,
            normalScale=scale,
            normalSize=size
        )
        self.logger.debug("normal saved")

    # TODO
    def save_racer_current(self, racer, finished):
        self.logger.debug("save racer current")
        return

    # TODO
    def save_racer_finish(self, racer):
        self.logger.debug("save racer finish")
        return

    # TODO
    def save_racer_current_point(self, racer):
        self.logger.debug("save current point")
        return

    def save_race(self, race, racers):
        self.logger.debug("save race")
        return
