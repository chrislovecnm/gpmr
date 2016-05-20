import logging

# from .cassandra_driver import CassandraDriver
# from .core import hmm
# from .pet_race import PetRace
# from .data_importer import DataImporter
# from .pet_race_cassandra_data_store import PetRaceCassandraDataStore


ch = logging.StreamHandler()
ch.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
ch.setFormatter(formatter)

module_logger = logging.getLogger('pet_race_job')
module_logger.setLevel(logging.DEBUG)
module_logger.addHandler(ch)

__version_info__ = (1, 0, 0, 'post0')
__version__ = '.'.join(map(str, __version_info__))

__all__ = ['model', 'mock_obj']
