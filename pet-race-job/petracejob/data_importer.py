from __future__ import absolute_import

import csv
import getopt
import glob
import logging
import os
import sys

from datetime import datetime

from cassandra.cqlengine.connection import get_session
from cassandra.cqlengine.connection import set_session
from cassandra.cqlengine.connection import setup as setup_cass
from cassandra.cqlengine.management import sync_table, drop_keyspace, create_keyspace_simple
from cassandra.util import uuid_from_time

from model import *


class DataImporter(object):
    session = None
    keyspace = None
    seeds = None
    cass = None
    logger = None

    """ arguments: seed, keyspace """

    def __init__(self, **kwargs):

        # if kwargs is None:
        self.seeds = kwargs.get('seeds')
        self.keyspace = kwargs.get('keyspace')
        setup_cass(self.seeds, 'system')
        self.session = get_session()
        self.logger = logging.getLogger('pet_race_job.logger')
        super()

    def connect_cass(self):
        setup_cass(self.seeds, self.keyspace)
        self.session = get_session()
        set_session(self.session)

    def create_keyspace(self):
        set_session(self.session)
        drop_keyspace(self.keyspace)
        create_keyspace_simple(name=self.keyspace, replication_factor=3)
        self.logger.debug("ks created")

    def create_tables(self):
        self.connect_cass()
        sync_table(PetCategories)
        sync_table(Pets)
        sync_table(RaceData)
        sync_table(RaceParticipants)
        sync_table(Race)
        self.logger.debug("tables created")

    def save_pets(self, pets_create, category_name):

        self.connect_cass()

        q = PetCategories.objects.filter(name=category_name)
        if len(q) is not 1:
            raise ValueError('category not found: ', category_name)
        pet_cat = q.first()

        for _p in pets_create:
            Pets.create(
                petId=uuid_from_time(datetime.utcnow()),
                name=_p['name'],
                description=_p['description'],
                petCategoryName=pet_cat['name'],
                petCategoryId=pet_cat['petCategoryId'],
                petSpeed=pet_cat['speed']
            )
            self.logger.debug("pet created: %s", _p['name'])

    def save_pet_categories(self, categories):

        self.connect_cass()

        for cat in categories:
            speed = float(cat['speed'])

            PetCategories.create(
                petCategoryId=uuid_from_time(datetime.utcnow()),
                name=cat['name'],
                speed=speed
            )
            self.logger.debug("pet cat created: %s", cat['name'])

    def parse_pet_files(self, d):
        files = glob.glob(d)
        _pets = []
        for pet_f in files:
            self.logger.debug(pet_f)
            data = self.parse_pet(pet_f)
            fn, fx = os.path.splitext(pet_f)
            fn = fn.split('/')[-1]
            _pets.append({'pet': data, 'cat': fn})
        return _pets

    @staticmethod
    def parse_pet_categories(file):
        data = []
        with open(file, newline='') as csv_file:
            reader = csv.DictReader(csv_file)
            for row in reader:
                data.append(row)
        return data

    def parse_pet(self, file):
        self.logger.debug(file)
        data = []
        with open(file, newline='') as csv_file:
            reader = csv.DictReader(csv_file, fieldnames=['name', 'description'])
            for row in reader:
                data.append(row)
        return data


if __name__ == '__main__':

    options, remainder = getopt.getopt(sys.argv[1:], 'd:h', ['directory=', 'help'])

    # if options.d is None: # where foo is obviously your required option
    #    parser.print_help()
    #    sys.exit(1)

    for opt, arg in options:
        if opt in ('-d', '--directory'):
            data_dir = arg
        if opt in ('-h', '--help'):
            print("usage: --directory=data_directory")
            exit(0)

    if data_dir is None:
        exit("no parameter found")

    os.environ["CQLENG_ALLOW_SCHEMA_MANAGEMENT"] = "1"
    # TODO move this to config files
    loader = DataImporter(seeds=['cassandra-0.cassandra.default.svc.cluster.local',
                                 'cassandra-1.cassandra.default.svc.cluster.local'], keyspace='gpmr')
    loader.create_keyspace()
    loader.create_tables()

    # todo move path to config files
    pet_cats = loader.parse_pet_categories(data_dir + '/pet_categories.csv')
    loader.save_pet_categories(pet_cats)

    pets_data = loader.parse_pet_files(data_dir + '/pets/*.csv')

    for p in pets_data:
        loader.save_pets(p['pet'], p['cat'])
