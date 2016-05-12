PRAGMA foreign_keys = ON;

DROP TABLE dependency;
DROP TABLE build;
DROP TABLE stand;
DROP TABLE component;

CREATE TABLE component (
	name TEXT not null,
	source_url TEXT,
	source_type TEXT,
	PRIMARY KEY (name)
);

CREATE TABLE stand (
	reference TEXT not null,
	version TEXT,
	component_name TEXT not null,
	PRIMARY KEY (reference),
	FOREIGN KEY (component_name) REFERENCES component(name)
);

CREATE TABLE build (
	id TEXT not null,
	time_stamp long not null,
	build_number INTEGER not null, 
	job_name TEXT,
	PRIMARY KEY (id)
);

CREATE TABLE dependency (
	reference TEXT not null,
	build_id TEXT not null,
	type TEXT not null,
	PRIMARY KEY (reference, build_id),
	FOREIGN KEY (reference) REFERENCES stand(reference),
	FOREIGN KEY (build_id) REFERENCES build(id)	
);	

INSERT INTO component VALUES ("Kunde", "https://github.com/Sandritter/k1.git", "git");
INSERT INTO component VALUES ("Produkt-Katalog", "https://github.com/Sandritter/k2.git", "git");
INSERT INTO component VALUES ("Warenkorb", "https://github.com/Sandritter/k3.git", "git");
INSERT INTO component VALUES ("Bestellprozess", "https://github.com/Sandritter/k4.git", "git");
INSERT INTO component VALUES ("Checkout", "https://github.com/Sandritter/k5.git", "git");
INSERT INTO component VALUES ("Billing", "https://github.com/Sandritter/k6.git", "svn");

INSERT INTO stand VALUES ("ref-k1-01", "1.0", "Kunde");
INSERT INTO stand VALUES ("ref-k1-02", "1.1", "Kunde");
INSERT INTO stand VALUES ("ref-k1-03", "1.2", "Kunde");
INSERT INTO stand VALUES ("ref-k1-04", "1.4", "Kunde");

INSERT INTO stand VALUES ("ref-k2-01", "2.0", "Produkt-Katalog");
INSERT INTO stand VALUES ("ref-k2-02", "2.1", "Produkt-Katalog");
INSERT INTO stand VALUES ("ref-k2-03", "2.2", "Produkt-Katalog");
INSERT INTO stand VALUES ("ref-k2-04", "2.4", "Produkt-Katalog");

INSERT INTO stand VALUES ("ref-k3-01", "3.0", "Warenkorb");
INSERT INTO stand VALUES ("ref-k3-02", "3.1", "Warenkorb");
INSERT INTO stand VALUES ("ref-k3-03", "3.2", "Warenkorb");

INSERT INTO stand VALUES ("ref-k4-01", "4.0", "Bestellprozess");
INSERT INTO stand VALUES ("ref-k4-02", "4.1", "Bestellprozess");

INSERT INTO stand VALUES ("ref-k5-01", "5.0", "Checkout");
INSERT INTO stand VALUES ("ref-k5-02", "5.1", "Checkout");

INSERT INTO stand VALUES ("ref-k6-01", "6.0", "Billing");
INSERT INTO stand VALUES ("ref-k6-02", "6.1", "Billing");

INSERT INTO build VALUES ("b-k1-01", 1449362787916, 1, "Job1");
INSERT INTO build VALUES ("b-k1-02", 1449362783916, 2, "Job1");
INSERT INTO build VALUES ("b-k1-03", 1449362784916, 3, "Job1");
INSERT INTO build VALUES ("b-k1-04", 1449362785916, 4, "Job1");

INSERT INTO build VALUES ("b-k2-01", 1449362782916, 1, "Job2");
INSERT INTO build VALUES ("b-k2-02", 1449362737916, 2, "Job2");
INSERT INTO build VALUES ("b-k2-03", 1449362747916, 3, "Job2");
INSERT INTO build VALUES ("b-k2-04", 1449362757916, 4, "Job2");

INSERT INTO build VALUES ("b-k3-01", 144936, 1, "Job3");
INSERT INTO build VALUES ("b-k3-02", 1449362487916, 2, "Job3");
INSERT INTO build VALUES ("b-k3-03", 1449362687916, 2, "Job3");

INSERT INTO build VALUES ("b-k4-01", 1449362187916, 1, "Job4");
INSERT INTO build VALUES ("b-k4-02", 1449362487916, 2, "Job4");

INSERT INTO build VALUES ("b-k5-01", 1449362887916, 1, "Job5");
INSERT INTO build VALUES ("b-k5-02", 1449362987916, 2, "Job5");

INSERT INTO build VALUES ("b-k6-01", 1449365787916, 1, "Job6");
INSERT INTO build VALUES ("b-k6-02", 1449366787916, 2, "Job6");

--
-- Komponente 1
INSERT INTO dependency VALUES ("ref-k1-01", "b-k1-01", "main");
INSERT INTO dependency VALUES ("ref-k1-02", "b-k1-02", "main");
INSERT INTO dependency VALUES ("ref-k1-03", "b-k1-03", "main");
INSERT INTO dependency VALUES ("ref-k1-04", "b-k1-04", "main");

--
-- Komponente 2
INSERT INTO dependency VALUES ("ref-k2-01", "b-k2-01", "main");
INSERT INTO dependency VALUES ("ref-k2-02", "b-k2-02", "main");
INSERT INTO dependency VALUES ("ref-k2-03", "b-k2-03", "main");
INSERT INTO dependency VALUES ("ref-k2-04", "b-k2-04", "main");

--
-- Komponente 3
INSERT INTO dependency VALUES ("ref-k3-01", "b-k3-01", "main");
INSERT INTO dependency VALUES ("ref-k1-01", "b-k3-01", "direct");
INSERT INTO dependency VALUES ("ref-k2-01", "b-k3-01", "direct");

INSERT INTO dependency VALUES ("ref-k3-02", "b-k3-02", "main");
INSERT INTO dependency VALUES ("ref-k1-01", "b-k3-02", "direct");
INSERT INTO dependency VALUES ("ref-k2-01", "b-k3-02", "direct");

INSERT INTO dependency VALUES ("ref-k3-03", "b-k3-03", "main");
INSERT INTO dependency VALUES ("ref-k1-02", "b-k3-03", "direct");
INSERT INTO dependency VALUES ("ref-k2-02", "b-k3-03", "direct");

--
-- Komponente 6
INSERT INTO dependency VALUES ("ref-k6-01", "b-k6-01", "main");
INSERT INTO dependency VALUES ("ref-k1-01", "b-k6-01", "direct");
INSERT INTO dependency VALUES ("ref-k3-01", "b-k6-01", "direct");
INSERT INTO dependency VALUES ("ref-k2-01", "b-k6-01", "direct");

-- Warnung bei Komponente 3 (direkt) mit Komponente 1 v1.1, getestet v1.0 
INSERT INTO dependency VALUES ("ref-k6-02", "b-k6-02", "main");
INSERT INTO dependency VALUES ("ref-k1-02", "b-k6-02", "direct");
INSERT INTO dependency VALUES ("ref-k3-02", "b-k6-02", "direct");
INSERT INTO dependency VALUES ("ref-k2-02", "b-k6-02", "direct");

--
-- Komponente 5
INSERT INTO dependency VALUES ("ref-k5-01", "b-k5-01", "main");
INSERT INTO dependency VALUES ("ref-k6-01", "b-k5-01", "direct");
INSERT INTO dependency VALUES ("ref-k1-01", "b-k5-01", "direct");
INSERT INTO dependency VALUES ("ref-k2-01", "b-k5-01", "direct");
INSERT INTO dependency VALUES ("ref-k3-01", "b-k5-01", "direct");

-- Warning bei Komponente 3 (indirekt) mit Komponente 1 v1.1, getestet v1.0 
INSERT INTO dependency VALUES ("ref-k5-02", "b-k5-02", "main");
INSERT INTO dependency VALUES ("ref-k6-02", "b-k5-02", "direct");
INSERT INTO dependency VALUES ("ref-k1-02", "b-k5-02", "direct");
INSERT INTO dependency VALUES ("ref-k2-02", "b-k5-02", "direct");
INSERT INTO dependency VALUES ("ref-k3-02", "b-k5-02", "direct");

--
-- Komponente 4
INSERT INTO dependency VALUES ("ref-k4-01", "b-k4-01", "main");
INSERT INTO dependency VALUES ("ref-k3-01", "b-k4-01", "direct");
INSERT INTO dependency VALUES ("ref-k1-01", "b-k4-01", "direct");
INSERT INTO dependency VALUES ("ref-k5-01", "b-k4-01", "direct");
INSERT INTO dependency VALUES ("ref-k2-01", "b-k4-01", "direct");
INSERT INTO dependency VALUES ("ref-k6-01", "b-k4-01", "direct");

-- Warning bei Komponente 1 und 6 wegen Komponente 1 in zwei getesteten Unterversionen
INSERT INTO dependency VALUES ("ref-k4-02", "b-k4-02", "main");
INSERT INTO dependency VALUES ("ref-k3-02", "b-k4-02", "direct");
INSERT INTO dependency VALUES ("ref-k1-02", "b-k4-02", "direct");
INSERT INTO dependency VALUES ("ref-k5-01", "b-k4-02", "direct");
INSERT INTO dependency VALUES ("ref-k2-03", "b-k4-02", "direct");
INSERT INTO dependency VALUES ("ref-k6-01", "b-k4-02", "direct");