package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SingleAccount(
        @JsonProperty(required = true, value = "text") String text
) {}