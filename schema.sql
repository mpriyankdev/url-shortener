create table alias_info (alias varchar(30) not null, created_at datetime, ttl integer, ttl_unit varchar(15), primary key (alias)) engine=MyISAM;
create table short_url_info (short_code varchar(30) not null, creation_time datetime, is_deleted bit, long_url longtext, ttl integer, ttl_unit varchar(15), primary key (short_code)) engine=MyISAM;