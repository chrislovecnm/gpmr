import logging

import numpy
import math
from operator import itemgetter

from pet_race_job.pet_race_cassandra_data_store import PetRaceCassandraDataStore


class PetRace(object):
    data_source = ()

    # guid list of racers still running
    racers_still_running = []

    # order list of finishers {guid: guid, total_distance: total_distance}
    racers_finished = []

    # racers with guid, race_guid, current_distance
    # {guid:guid, {current_distance: 42,
    #              finished: False, finished_position: 1, name: name, monster_type: guid
    #              racers_positions_by_time : [{postion: 1, velocity: 42, total_traveled: 84 }]
    #             }}
    racers = {}

    # includes race_guid, distance
    # {guid: 42, length: 4, location: guid}
    race = {}

    # list of lists
    # point in time with each poll spot
    racers_positions_by_time = [[]]

    logger = None

    normal_scale = None
    base_racer_speed = None

    def __init__(self, seeds, keyspace):
        self.logger = logging.getLogger('pet_race_job')
        self.data_source = PetRaceCassandraDataStore(seeds, keyspace)
        self.logger.debug("race __init__")
        super()

    def create_race(self, length, description, pet_category_name, normal_scale):
        self.normal_scale = normal_scale
        race_created, racers = self.data_source.create_race(
            length=length, description=description, pet_category_name=pet_category_name)
        self.racers = racers
        self.racers_still_running = list(racers.keys())
        self.race = race_created
        self.base_racer_speed = self.race['baseSpeed']
        self.logger.debug("created race")

    # http://docs.scipy.org/doc/numpy-1.10.1/reference/generated/numpy.random.normal.html
    # Still need to figure out scale
    def numpy_normal(self, normal_size, loc=None, scale=None):

        if loc is None:
            loc = self.base_racer_speed
        if scale is None:
            scale = self.normal_scale

        if normal_size <= 0:
            raise "normal_size cannot be <= 0"

        self.logger.debug("loc: %s scale %s size %s", loc, scale, normal_size)

        random_normal = numpy.random.normal(loc=loc, scale=scale, size=normal_size)

        self.save_normal(random_normal, loc, scale, normal_size)

        n = random_normal.tolist()

        if len(n) <= 0:
            raise "normal cannot be zero"

        # self.logger.debug("normals created: %s", n)

        return n

    # TODO
    def save_normal(self, normals, loc, scale, size):
        self.data_source.save_normal(normals, loc, scale, size, self.race)

    # TODO
    def save_racer_current(self, racer_guid, finished):
        racer = self.racers[racer_guid]
        # self.logger.debug("racer: %s", racer)
        # self.logger.debug("race: %s", self.race)
        # self.logger.debug("race: %s", finished)
        self.data_source.save_racer_current(racer, self.race, finished)

    # TODO
    def save_racer_finish(self, racer_guid):
        racer = self.racers[racer_guid]
        self.data_source.save_racer_finish(racer, self.race)

    # TODO
    def save_racer_current_point(self, racer_guid, race_sample):
        racer = self.racers[racer_guid]
        self.data_source.save_racer_current_point(racer, self.race, race_sample)

    def save_race(self):
        self.data_source.save_race(self.race, self.racers, self.racers_positions_by_time)

    @staticmethod
    def either_race_distance_current(race_distance, current_distance):
        if current_distance >= race_distance:
            return race_distance, True
        else:
            return current_distance, False

    # total number of seconds running + how long it took to finish
    # last sample of race
    # FIXME how do we calculate this?
    def calc_finish_time(self, racer):
        self.logger.debug("TODO calc")
        return 42

    def run_race(self):
        logging.debug("Starting a race")
        # current_positions = []
        n = 0
        race_distance = self.race['length']
        while True:

            racers_finished_this_iteration = []

            self.logger.debug("racers running: %s", self.racers_still_running)
            # calculate normals and save them
            random_normal = self.numpy_normal(len(self.racers_still_running))

            sample_distances = {}
            for racer in self.racers_still_running:

                self.logger.debug("racer: %s", racer)
                self.logger.debug("racer obj: %s", self.racers[racer])
                racer_obj = self.racers[racer]
                previous_distance = racer_obj['current_distance']
                self.logger.debug("previous distance: %s", previous_distance)
                distance_this_sample = random_normal.pop()
                current_racer_distance = previous_distance + distance_this_sample
                current_racer_distance_adj, finished = self.either_race_distance_current(race_distance,
                                                                                         current_racer_distance)
                self.racers[racer]['current_distance'] = current_racer_distance_adj

                self.racers[racer]['finished'] = finished

                self.logger.debug("current distance: %s", current_racer_distance_adj)
                self.logger.debug("finished: %s", finished)

                race_sample = {
                    'racerId': racer,
                    'finished': finished,
                    'current_distance': current_racer_distance_adj,
                    'current_distance_all': current_racer_distance,
                    'previous_distance': previous_distance,
                    'distance_this_sample': distance_this_sample
                }
                self.racers[racer]['racers_positions_by_time'].append(race_sample)

                sample_distances[racer] = current_racer_distance

                # TODO
                # store race interval in racer
                # no position here
                self.save_racer_current(racer, finished)

                if finished:
                    racers_finished_this_iteration.append(racer)
                    self.racers[racer]['total_time'] = self.calc_finish_time(racer_obj)
                    self.racers[racer]['total_distance'] = current_racer_distance
                    self.racers[racer]['finished'] = finished
                    # TODO
                    self.save_racer_finish(racer)
                # TODO

                self.save_racer_current_point(racer, race_sample)

            self.racers_still_running = [x for x in self.racers_still_running
                                         if x not in racers_finished_this_iteration]

            # how the heck do we do this??
            # self.logger.debug("sample_distances %s", sample_distances.items())

            # current_positions = sorted(sample_distances, key=itemgetter('current_distance_all'), reverse=True)

            # this is working ... not sure how :)
            self.logger.debug("current positions full %s", sample_distances.items())
            current_positions = sorted(sample_distances.items(), key=itemgetter(2), reverse=True) # , reverse=True)
            for idx, val in enumerate(current_positions):
                self.logger.debug("current position %s", idx+1)
                self.logger.debug("current position guid %s, distance", val[0], val[1])

            # add each iteration add the racers that have finished this iteration into the finished lists
            # TODO save finished data

            n += 1
            self.logger.debug("racers still runnning: %i", len(self.racers_still_running))
            self.logger.debug("racers still runnning: %s", self.racers_still_running)
            if len(self.racers_still_running) == 0:
                print("HERE")
                self.logger.debug("RACE FINISHED loops: %i", n)
                break
        # end while
        self.logger.debug("saving race")
        self.save_race()


            # TODO save race data
