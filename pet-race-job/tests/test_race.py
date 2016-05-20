# -*- coding: utf-8 -*-

import unittest

from pet_race_job.pet_race import PetRace
from pet_race_job.pet_race_cassandra_data_store import PetRaceCassandraDataStore

class AdvancedTestSuite(unittest.TestCase):
    """Advanced test cases."""

    def test_race(self):
        seeds = ['cassandra-0.cassandra.default.svc.cluster.local',
                 'cassandra-1.cassandra.default.svc.cluster.local']
        keyspace = 'gpmr'
        ds = PetRaceCassandraDataStore(seeds, keyspace)

        race_created, racers = ds.create_race(length=10, description="description", pet_category_name="Lions")

        pet_race = PetRace(race=race_created, normal_scale=1, racers=racers, data_source=ds)
        pet_race.run_race()


if __name__ == '__main__':
    unittest.main()
