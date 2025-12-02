ALTER TABLE events
ADD CONSTRAINT fk_venue
FOREIGN KEY (venue_id) REFERENCES venues(id);
