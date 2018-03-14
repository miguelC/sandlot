#! /bin/bash

# backup-postgresql.sh
# by Craig Sanders
# this script is public domain.  feel free to use or modify as you like.

PSQL="/usr/bin/psql"
SCRIPT_ROLES="0_interop_ROLES_CREATE.sql"
SCRIPT_DBS="1_interop_DB_CREATE.sql"

YMDT=$(date "+%Y-%m-%d %T")
# Run psql to delete interop audits older than 5 days
echo $YMDT Running $SCRIPT_ROLES
$PSQL -f $SCRIPT_ROLES

echo $YMDT Running $SCRIPT_DBS
$PSQL -f $SCRIPT_DBS

YMDT=$(date "+%Y-%m-%d %T")
echo $YMDT ------ DONE -------