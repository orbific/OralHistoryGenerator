package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SingleAccount(
        @JsonProperty(required = true, value = "name") String name,
        @JsonProperty(required = true, value = "role") String role,
        @JsonProperty(required = true, value = "accountText") String accountText
) {}
