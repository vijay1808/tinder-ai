package io.learning.tinder_ai_backend.conversations;

import io.learning.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository) {

        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/conversations")
    public Conversation createNewConversation(@RequestBody CreateConversationRequest createConversationRequest) {

        profileRepository.findById(createConversationRequest.profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unable to find a Profile with ID" + createConversationRequest.profileId));

        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                createConversationRequest.profileId,
                new ArrayList<>()
        );

        conversationRepository.save(conversation);
        return conversation;

    }

    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId,
                                                @RequestBody ChatMessage chatMessage) {
        System.out.println("here");
        Conversation conversation=conversationRepository.findById(conversationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find conversation with ID " + conversationId));
        profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unable to find a Profile with ID" + chatMessage.authorId()));

        // Need to validate that the author of the message happens to be only the profile associated with the message or user.
        ChatMessage messageWithTime= new ChatMessage(
                chatMessage.message(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );

        conversation.messages().add(messageWithTime);
        conversationRepository.save(conversation);
        return conversation;
    }
    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@PathVariable String conversationId)
    {
        System.out.println("hello");
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with ID " + conversationId));
    }

    public record CreateConversationRequest(String profileId) {
    }
}
