'''
entity RaceData {
  raceDataId UUID,
  petId UUID,
  petName String,
  petCategory String,
  petCategoryId UUID,
  runnerPosition Integer,
  runnerSashColor String,
  interval Integer,
  RunnerDistance BigDecimal,
  startTime Date,
}
'''

import uuid

from cassandra.cqlengine import columns
from cassandra.cqlengine.models import Model


# first, define a model
class RaceData(Model):
    raceDataId = columns.UUID(primary_key=True, default=uuid.uuid4)
    petId = columns.UUID(primary_key=True, default=uuid.uuid4)
    raceId = columns.UUID(index=True, default=uuid.uuid4)
    petName = columns.Text(index=True)
    petColor = columns.UUID(index=True, default=uuid.uuid4)
    petCategoryName = columns.Text(required=False)
    petCategoryId = columns.UUID(index=True, default=uuid.uuid4)
    interval = columns.Integer()
    runnerPosition = columns.Integer()
    runnerDistance = columns.Decimal()
    startTime = columns.DateTime()
    finished = columns.Boolean()
    runnerPreviousDistance = columns.Decimal()

    __keyspace__ = 'gpmr'
    __options__ = {'compaction': {'class': 'SizeTieredCompactionStrategy',
                                  'bucket_low': '.3',
                                  'bucket_high': '2',
                                  'min_threshold': '2',
                                  'max_threshold': '64',
                                  'tombstone_compaction_interval': '86400'},
                   'gc_grace_seconds': '0'}
