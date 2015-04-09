import sys
sys.path.append('../../main/python/lib/aws')
import aws.S3

########################################################
## START OF A TEST #####################################
########################################################

S3.list_s3_locations()

s3 = S3("ofenton", "us-west-2")

try:
    s3.create_bucket("test")
except:
    print "bucket creation error"

s3.list_buckets()
s3.delete_bucket("test")
