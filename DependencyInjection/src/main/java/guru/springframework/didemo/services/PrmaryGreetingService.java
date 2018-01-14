package guru.springframework.didemo.services;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("en")
@Primary
public class PrmaryGreetingService implements GreetingService {

    @Override
    public String sayGreetings() {
        return "Hello - Primary GreetingService ";
    }
}
