#! /bin/bash

# backup-postgresql.sh
# by Craig Sanders
# this script is public domain.  feel free to use or modify as you like.

DUMPALL="/usr/bin/pg_dumpall"
PGDUMP="/usr/bin/pg_dump"
PSQL="/usr/bin/psql"
VACUUMDB="/usr/bin/vacuumdb"

# directory to save backups in, must be rwx by postgres user
BASE_DIR="/backup/interop"
YMD=$(date "+%Y-%m-%d")
YMDT=$(date "+%Y-%m-%d %T")
DIR="$BASE_DIR/$YMD"
mkdir -p $DIR
cd $DIR

# get list of databases in system , exclude the tempate dbs
DBS=$($PSQL -l -t | egrep -v 'template[01]' | awk '{print $1}' | egrep -v '^\|' | egrep -v '^$')
DBS="interopaudits"

# first dump entire postgres database, including pg_shadow etc.
$DUMPALL -D | gzip -9 > "$DIR/db.out.gz"

# next dump globals (roles and tablespaces) only
$DUMPALL -g | gzip -9 > "$DIR/globals.gz"

# now loop through each individual database and backup the schema and data separately
for database in $DBS; do
    SCHEMA=$DIR/$database.schema.gz
    DATA=$DIR/$database.data.gz

    echo $YMDT Backing up database $DBS to $DIR
    # export data from postgres databases to plain text
    $PGDUMP -s $database | gzip -9 > $SCHEMA

    # dump data
    $PGDUMP -a $database | gzip -9 > $DATA
done


YMDT=$(date "+%Y-%m-%d %T")
# Run psql to delete interop audits older than 5 days
echo $YMDT Running cleanup_audits_function on $DBS
$PSQL -d interopaudits -c 'SELECT cleanup_audits_function()'

YMDT=$(date "+%Y-%m-%d %T")
# Run vacuumdb to free space on disk after the delete function
echo $YMDT Running vacuumdb on $DBS
$VACUUMDB -d interopaudits -f


# delete backup files older than 30 days
#OLD=$(find $BASE_DIR -type d -mtime +120)
#if [ -n "$OLD" ] ; then
        #        echo deleting old backup files: $OLD
        #echo $OLD | xargs rm -rfv
#fi

YMDT=$(date "+%Y-%m-%d %T")
echo $YMDT ------ DONE -------