CREATE ROLE "auth-svc-user";
ALTER ROLE "auth-svc-user" WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:Qm5oBTqW81NGJud86DM6SQ==$CukmY0k5I1jxBK+rLNHWLdnVJCLmoA6kw0EBbFaL7Rk=:y7YFCbaVBlZzy7mSS2l6gEJG85aoHu7vv+EZX9rqukc=';
CREATE ROLE "product-svc-user";
ALTER ROLE "product-svc-user" WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:c6kH5wh5lI/5klbwSwF3/g==$0KfhLI1YVQvAZy0QK8tQPCjsuLKoghjw2+72ntl/QbA=:C44amaQJEQWe1ipxUsykegC4DMtUfSbexs6+f1itIjA=';
CREATE ROLE share;
ALTER ROLE share WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:wkX0zHH2Y4UZ1E0F+W5lqw==$amF1tvp2NWV5m85QmsGjPS386pAe2vi4dE0kLIyKp7A=:XiXdrNqK2gB6i5t5wfzk6NpI4zNGKt5JDgix/M02f0Q=';
CREATE ROLE test;
ALTER ROLE test WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:J7ej22ORgnSQAKeEKfp8uA==$LirXaSsY+wFBYO+bzjBroOS8N4C3X4b9dL0Y2/mb+bQ=:Y9S44k7lsOmfDYjPGrOQsjokGv5y2zXX46qkhKCRpDk=';
CREATE ROLE "user-svc-user";
ALTER ROLE "user-svc-user" WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:LfLOcV7VvlKevx+chrsnPw==$t5rQLup4773uyMHt9FFwbfS+b4xXuMEKNEiawgTi+qA=:z7/7kF2PET2hmULiF1KC4ANc7+GSuwOW/crHrI6Ri2k=';



CREATE DATABASE "auth-svc-db" WITH OWNER = "auth-svc-user" ENCODING = 'UTF8' CONNECTION LIMIT = -1;
CREATE DATABASE "product-svc-db" WITH OWNER = "product-svc-user" ENCODING = 'UTF8' CONNECTION LIMIT = -1;
CREATE DATABASE "user-svc-db" WITH OWNER = "user-svc-user" ENCODING = 'UTF8' CONNECTION LIMIT = -1;