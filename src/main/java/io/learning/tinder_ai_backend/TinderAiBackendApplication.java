package io.learning.tinder_ai_backend;

import io.learning.tinder_ai_backend.conversations.ChatMessage;
import io.learning.tinder_ai_backend.conversations.Conversation;
import io.learning.tinder_ai_backend.conversations.ConversationRepository;
import io.learning.tinder_ai_backend.profiles.Gender;
import io.learning.tinder_ai_backend.profiles.Profile;
import io.learning.tinder_ai_backend.profiles.ProfileCreationService;
import io.learning.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {


	@Autowired
	ProfileCreationService profileCreationService;

    @Autowired
	ProfileRepository profileRepository;
	@Autowired
	ConversationRepository conversationRepository;

	@Autowired
	private OpenAiChatModel chatModel;
	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Testing DB Connection");
		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		profileCreationService.saveProfilesToDB();
		/*profileRepository.deleteAll();
		conversationRepository.deleteAll();
		Prompt prompt= new Prompt("Hello, How are you ?");
		ChatResponse response=chatModel.call(prompt);
		System.out.println(response.getResult().getOutput().getContent());
		System.out.println(response);
		Profile profile = new Profile("1","Vijay","Parthasarathy",
				33,"Indian", Gender.MALE,
				"Software Consultant",
				"foo.jpg","Nill"
		);
	    profileRepository.save(profile);
		Profile profile2 = new Profile("2","foo","bar",
				33,"Indian", Gender.MALE,
				"Software Consultant",
				"foo.jpg","Nill"
		);
		profileRepository.save(profile2);
		System.out.println("Data Saved");
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation= new Conversation(
				"1", profile.id(), List.of(new ChatMessage("Hello", profile.id(), LocalDateTime.now()))
		);

        conversationRepository.save(conversation);
		System.out.println("Data Saved");
		conversationRepository.findAll().forEach(System.out::println);*/

	}
}
