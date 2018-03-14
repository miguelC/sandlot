CREATE DATABASE interopaudits
  WITH OWNER = interopadmin
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE interopaudits TO postgres;
GRANT CONNECT ON DATABASE interopaudits TO interopsvc;


CREATE DATABASE interopihe
  WITH OWNER = interopadmin
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE interopaudits TO postgres;
GRANT CONNECT ON DATABASE interopihe TO interopsvc;