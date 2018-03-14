CREATE OR REPLACE FUNCTION create_interop_roles_function()
RETURNS void
LANGUAGE plpgsql
AS
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'interopadmin') THEN

      CREATE ROLE interopadmin LOGIN
          ENCRYPTED PASSWORD 'md53ede299b3595d6a27c45e3ff6d9da895'
          SUPERUSER INHERIT CREATEDB CREATEROLE;
   END IF;
   
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'interopsvc') THEN
      
      CREATE ROLE interopsvc LOGIN
          ENCRYPTED PASSWORD 'md50393fd926c9d5cff172d1b5c27bfbd6e'
          NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
   END IF;
    
END
$body$;

SELECT create_interop_roles_function();

DROP FUNCTION create_interop_roles_function();