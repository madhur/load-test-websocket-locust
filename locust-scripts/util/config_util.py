import configparser
import os
import definitions

"""
Purpose: Util to manage ini config

Example:
from utils import config_util
project_config = config_util.get_value_from_config('sample_config.ini')
db_host = project_config.get('MYSQL_DATABASE', 'HOST')

Note: get_value_from_config method will always return a string, if you want to change the data type use ast.
Example:
pool_size = ast.literal_eval(project_config.get('MYSQL_DATABASE', 'POOL_SIZE'))

"""


def get_value_from_config(file):
    config = configparser.ConfigParser()
    config.read(file)
    return config

def update_config(file,config):
    with open(file, 'w') as configfile:
        config.write(configfile)


def read_global_config():
    config = configparser.ConfigParser()
    config.read(definitions.get_project_path() + '/config.ini')
    return config

def get_current_working_dir():
    return os.getcwd()


def get_parent_dir():
    return os.path.abspath(os.path.join(get_current_working_dir(), os.pardir))

