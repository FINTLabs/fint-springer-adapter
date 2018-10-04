package no.fint.provider.adapter;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FintAdapterProps {

    @Value("${fint.adapter.organizations}")
    private String[] organizations;

}
