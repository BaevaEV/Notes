delete from nfaut.users_roles where user_id in(select id from nfaut.users where login = ? )