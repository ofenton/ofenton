import getopt
import os.path
import sys

from S3 import S3

class Aws:

    def __init__(self, properties_file):
        props = self.read_properties_file(properties_file)
        self.validate_properties(props)
        self.s3 = S3(props.get('project_name'), props.get('location'))

    def getS3(self):
        return self.s3

    def validate_properties(self, props):
        if not 'location' in props:
            print "'location' property not defined"
            sys.exit(1)

        if not 'project_name' in props:
            print "'project_name' property not defined"
            sys.exit(1)
        
        print "initializing AWS with the following properties:"
        print props

    def read_properties_file(self, properties_file):
        if not os.path.isfile(properties_file):
            print "properties file " + properties_file + " does not exist. Please provide a valid properties file"
            sys.exit(1)

        # Read key-value pairs in a map, verify all required settings have been set
        props = {}
        with open(properties_file, 'r') as f:
            for line in f:
                line = line.rstrip() #removes trailing whitespace and '\n' chars

                if "=" not in line: continue #skips blanks and comments w/o =
                if line.startswith("#"): continue #skips comments which contain =

                k, v = line.split("=", 1)
                props[k] = v

        return props


########################################################
## START ###############################################
########################################################
def main(argv):
    try:
        opts, args = getopt.getopt(argv, "hp:", ["help", "propertiesfile="])
    except getopt.GetoptError:
        usage()
        sys.exit(1)
    for opt, arg in opts:
        if opt in ("-h", "--help"):
            usage()
            sys.exit()
        elif opt in ("-p", "--propertiesfile"):
            properties_file = arg

    try:
        properties_file
    except NameError:
        print "propertiesfile is a required argument"
        usage()
        sys.exit(1)

    aws = Aws(properties_file)

    ### TEST TEST TEST ###
    aws.getS3().list_s3_locations()
    try:
        aws.getS3().create_bucket("test")
    except:
        print "bucket creation error"
    aws.getS3().list_buckets()
    aws.getS3().delete_bucket("test")


def usage():
    print "python Aws.py [-h] [-p]"
    print ""
    print "arguments:"
    print "  -h, --help  show this help message and exit"
    print "  -p, --propertiesfile [FILE]  configuration options for AWS services (required)"

if __name__ == "__main__":
    main(sys.argv[1:])
