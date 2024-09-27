create table PhoneList (
    id varchar(255) not null,
    customerSegmentGroup varchar(255),
    phone varchar(255),
    productId varchar(255),
    serviceId varchar(255),
    status bit,
    updateTime datetime(6),
    registrationTime datetime(6),
    primary key (id)
) engine=InnoDB;