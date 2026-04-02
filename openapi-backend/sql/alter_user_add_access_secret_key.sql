alter table user
    add column accessKey varchar(256) null comment 'access key';

alter table user
    add column secretKey varchar(512) null comment 'secret key';
