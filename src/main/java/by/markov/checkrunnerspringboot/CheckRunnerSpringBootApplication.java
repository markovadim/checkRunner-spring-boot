package by.markov.checkrunnerspringboot;

import by.markov.checkrunnerspringboot.services.commandline.CommandLineArgumentsParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CheckRunnerSpringBootApplication implements CommandLineRunner {
    private final CommandLineArgumentsParser commandLineArgumentsParser;

    public static void main(String[] args) {
        SpringApplication.run(CheckRunnerSpringBootApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        commandLineArgumentsParser.start(args);
    }
}
