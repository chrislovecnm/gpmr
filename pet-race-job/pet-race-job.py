#! /usr/bin/env python

import getopt
import sys

from pet_race_job.pet_race import PetRace


def main(arg):

    options, remainder = getopt.getopt(arg[1:], 'h',
                                       ['description=',
                                        'length=',
                                        'pet=',
                                        'scale=',
                                        'help'])

    # logger = logging.getLogger('pet_race_job')

    seeds = ['cassandra-0.cassandra.default.svc.cluster.local',
             'cassandra-1.cassandra.default.svc.cluster.local']

    keyspace = 'gpmr'

    description = None
    length = None
    pet = None
    scale = None

    for opt, arg in options:
        if opt in '--description':
            description = str(arg)
        if opt in '--length':
            length = int(arg)
        if opt in '--pet':
            pet = str(arg)
        if opt in '--scale':
            scale = int(arg)
        if opt in ('-h', '--help'):
            print("usage: read the code")
            exit(0)

    if not isinstance(description, str):
        exit("--description is missing")

    if not isinstance(length, int):
        exit("--length is missing")

    if not isinstance(scale, int):
        exit("--scale is missing")

    if not isinstance(pet, str):
        exit("--pet is missing")

    pet_race = PetRace(seeds=seeds, keyspace=keyspace)
    pet_race.create_race(length, description, pet, scale)
    # logger.debug("running race")
    pet_race.run_race()


if __name__ == '__main__':
    main(sys.argv)
