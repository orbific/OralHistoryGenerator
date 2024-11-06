package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CastMetadata(
        @JsonProperty(required = true, value = "cast") CharacterMetadata[] characterMetadata
) {}




