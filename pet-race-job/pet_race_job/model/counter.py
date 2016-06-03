import uuid

from cassandra.cqlengine import columns
from cassandra.cqlengine.models import Model


# first, define a model
class Counters(Model):
class Counters(Model):
    counterId = columns.UUID(primary_key=True, default=uuid.uuid4)
    type = columns.Text(required=True, index=True)
    count = columns.Counter()

    __keyspace__ = 'gpmr'