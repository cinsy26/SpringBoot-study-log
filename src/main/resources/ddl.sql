DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS part;

CREATE TABLE project(
    project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(225) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE
);

CREATE TABLE part(
    part_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(225) NOT NULL,
    description VARCHAR(1000),
    project_id BIGINT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);