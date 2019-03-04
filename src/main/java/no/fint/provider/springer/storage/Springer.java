package no.fint.provider.springer.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class Springer {

    @Id
    private String id;
    private String type;
    private Object value;
}
