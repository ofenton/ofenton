import boto

from boto.exception import S3ResponseError

from boto.s3.connection import Location
from boto.s3.connection import S3Connection

class S3:

    def __init__(self, project, location):
        self.project = project
        self.location = S3.get_location(location)
        if self.location == None:
            raise ValueError('not a valid S3 location: %s' % location)
        self.connect()

    def connect(self):

        # Instantiate a new client for Amazon Simple Storage Service (S3). With no
        # parameters or configuration, the AWS SDK for Python (Boto) will look for
        # access keys in these environment variables:
        #
        #    AWS_ACCESS_KEY_ID='...'
        #    AWS_SECRET_ACCESS_KEY='...'
        #
        # For more information about this interface to Amazon S3, see:
        # http://boto.readthedocs.org/en/latest/s3_tut.html
        #self.s3 = boto.connect_s3()
        #self.s3 = S3Connection()
        self.s3 = boto.s3.connect_to_region(self.location["value"])

    def get_bucket_name(self, name):
        return self.project + "." + self.location["value"] + "." + name
        #return self.project + "_" + self.location["value"] + "_" + name

    def create_bucket(self, name):
        bucket_name = self.get_bucket_name(name)
        location = "location=" + self.location["value"]
        print "creating bucket " + bucket_name + " in " + self.location["value"]
        bucket = self.s3.create_bucket(bucket_name, location=self.location["value"])

    def delete_bucket(self, name):
        bucket_name = self.get_bucket_name(name)
        print "deleting bucket " + bucket_name
        self.s3.delete_bucket(bucket_name)

    def list_buckets(self):
        try:
            buckets = []
            for bucket in self.s3.get_all_buckets():
                buckets.append({ 
                    "name":bucket.name, "created_date":bucket.creation_date
                })
            print buckets
        except S3ResponseError:
            print "unable to list buckets"


########################################################
## STATICS #############################################
########################################################

    @staticmethod
    def get_location(locationStr):
        locations = S3.get_locations()
        for location in locations:
            if location["value"] == locationStr:
                return location
        return None
    

    @staticmethod
    def list_s3_locations():
        locations = S3.get_locations()
        for location in locations:
            print location


    @staticmethod
    def get_locations():
        locations = []
        for location in dir(Location):
            if location[0].isupper():
                locations.append({
                    "key":location,
                    "value":getattr(Location, location)
                })
        return locations

