from pymongo import MongoClient
import pprint

client = MongoClient('localhost', 27017)

db = client['security']

#pprint.pprint(db)
collection = db['pin']
cursor = collection.find({})
for pin in cursor:
    print(int(pin['pin']))
    #pprint.pprint(collection.find_all())
