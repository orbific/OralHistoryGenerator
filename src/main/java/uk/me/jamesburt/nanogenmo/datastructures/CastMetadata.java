package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CastMetadata(
        @JsonProperty(required = true, value = "cast") CharacterMetadata[] characterMetadata
) {
    public static String convertCastToString(CastMetadata cast) {
        StringBuilder sb = new StringBuilder();
        for(CharacterMetadata character: cast.characterMetadata()) {
            sb.append(character.name());
            sb.append(" ("+character.profession()+"\n");
            sb.append("Description: "+character.description());
            sb.append("History: "+character.history());
            sb.append("\n");
        }
        return sb.toString();
    }

}




