import boto

from boto.s3.connection import Location
from boto.exception import S3ResponseError

class S3:

    def __init__(self, project, region):
        self.project = project
        self.region = region

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
        self.s3 = boto.connect_s3()

    def create_bucket(self, name):
        bucket_name = self.project + "_" + self.region + "_" + name
        print "creating bucket " + bucket_name
        bucket = self.s3.create_bucket(bucket_name)

    def delete_bucket(self, name):
        bucket_name = self.project + "_" + self.region + "_" + name
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
    def list_s3_locations():
        print '\n'.join(i for i in dir(Location) if i[0].isupper())



########################################################
## START ###############################################
########################################################

S3.list_s3_locations()

s3 = S3("ofenton", "us-west-2")
s3.connect()
s3.create_bucket("a_test_bucket")
s3.list_buckets()
s3.delete_bucket("a_test_bucket")
