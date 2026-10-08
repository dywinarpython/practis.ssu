CREATE TABLE warehouses (
    warehouse_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500) NOT NULL
);

CREATE TABLE responsible_persons (
    responsible_person_id SERIAL PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    position VARCHAR(150) NOT NULL,
    phone VARCHAR(20)
);

CREATE TABLE material_values (
    material_value_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    cost NUMERIC(19,2) NOT NULL CHECK (cost >= 0),
    condition VARCHAR(255) NOT NULL,
    responsible_person_id INTEGER REFERENCES responsible_persons(responsible_person_id)
);

CREATE TABLE movements (
    movement_id SERIAL PRIMARY KEY,
    warehouse_id INTEGER NOT NULL REFERENCES warehouses(warehouse_id),
    movement_type VARCHAR(20) NOT NULL CHECK (movement_type IN ('INCOMING', 'OUTGOING')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('COMPLETED', 'IN_PROGRESS')),
    date TIMESTAMP NOT NULL,
    material_value_id INTEGER NOT NULL REFERENCES material_values(material_value_id)
);

CREATE TABLE value_transfers (
    transfer_id SERIAL PRIMARY KEY,
    from_responsible_person_id INTEGER REFERENCES responsible_persons(responsible_person_id),
    to_responsible_person_id INTEGER REFERENCES responsible_persons(responsible_person_id),
    material_value_id INTEGER NOT NULL REFERENCES material_values(material_value_id),
    date TIMESTAMP NOT NULL
);
