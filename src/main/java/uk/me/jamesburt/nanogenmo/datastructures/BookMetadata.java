package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookMetadata(
        @JsonProperty(required = true, value = "chapters") ChapterMetadata[] chapterMetadata
) {}




