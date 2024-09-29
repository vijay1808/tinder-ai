package io.learning.tinder_ai_backend.matches;

import io.learning.tinder_ai_backend.conversations.ChatMessage;
import io.learning.tinder_ai_backend.conversations.Conversation;
import io.learning.tinder_ai_backend.conversations.ConversationController;
import io.learning.tinder_ai_backend.conversations.ConversationRepository;
import io.learning.tinder_ai_backend.profiles.Profile;
import io.learning.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MatchController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    private final MatchRepository matchRepository;

    public MatchController(ConversationRepository conversationRepository, ProfileRepository profileRepository, MatchRepository matchRepository) {

        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.matchRepository=matchRepository;
    }

    public record CreateMatchRequest(String profileId){}

    @PostMapping("/matches")
    public Match createNewMatch(@RequestBody CreateMatchRequest createMatchRequest) {

        Profile profile=profileRepository.findById(createMatchRequest.profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unable to find a Profile with ID" + createMatchRequest.profileId));

        //TODO: Make sure there are no existing conversations with this profile already
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                profile.id(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        Match match= new Match(
                UUID.randomUUID().toString(),
                profile,
                conversation.id());


        matchRepository.save(match);
        return match;

    }

    @GetMapping("/matches")
    public List<Match> getMatches(){

        return matchRepository.findAll();
    }



}
