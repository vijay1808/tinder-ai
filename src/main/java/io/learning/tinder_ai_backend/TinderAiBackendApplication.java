package io.learning.tinder_ai_backend;

import io.learning.tinder_ai_backend.profiles.Gender;
import io.learning.tinder_ai_backend.profiles.Profile;
import io.learning.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {
    @Autowired
	ProfileRepository profileRepository;
	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Testing DB Connection");
		Profile profile = new Profile("1","Vijay","Parthasarathy",
				33,"Indian", Gender.MALE,
				"Software Consultant",
				"foo.jpg","Nill"
		);
	    profileRepository.save(profile);
		System.out.println("Data Saved");
		profileRepository.findAll().forEach(System.out::println);

	}
}
