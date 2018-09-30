package no.fint.provider.springer.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class Springer {

    private String type;
    private Object value;
}
