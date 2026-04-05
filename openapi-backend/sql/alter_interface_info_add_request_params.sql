use open_api;

set @column_exists := (
    select count(*)
    from information_schema.columns
    where table_schema = database()
      and table_name = 'interface_info'
      and column_name = 'requestParams'
);

set @alter_sql := if(
    @column_exists = 0,
    'alter table interface_info add column requestParams text not null comment ''request params'' after method',
    'select ''requestParams already exists'''
);

prepare stmt from @alter_sql;
execute stmt;
deallocate prepare stmt;

update interface_info
set requestParams = concat(
    '{',
    '\"userId\":', coalesce(userId, 0), ',',
    '\"pageNum\":', floor(1 + rand() * 5), ',',
    '\"pageSize\":', elt(1 + floor(rand() * 3), 10, 20, 50), ',',
    '\"keyword\":\"demo-', lower(substring(replace(uuid(), '-', ''), 1, 8)), '\",',
    '\"includeDetails\":', elt(1 + floor(rand() * 2), 'true', 'false'),
    '}'
)
where requestParams is null
   or trim(requestParams) = '';
