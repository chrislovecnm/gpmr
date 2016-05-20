# -*- coding: utf-8 -*-

import unittest

from petracejob.pet_race import PetRace as pet_race
from petracejob.mock_obj.mock_data_source import MockDataSource


class AdvancedTestSuite(unittest.TestCase):
    """Advanced test cases."""

    def test_race(self):
        race = {"guid": 42, "length": 4, "location": 4242}
        racers = {}
        data_source = MockDataSource()
        race = pet_race(base_racer_speed=42, racers=racers, race=race, data_source=data_source)
        race.run_race()


if __name__ == '__main__':
    unittest.main()
