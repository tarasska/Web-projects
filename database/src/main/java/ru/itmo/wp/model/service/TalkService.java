package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public Talk find(long id) {
        return talkRepository.find(id);
    }

    public List<Talk> findByUserOrderedByCreationTime(User user) {
        return talkRepository.findByUserOrderedByCreationTime(user);
    }

    public void save(long sourceUserId, long targetUserId, String text) {
        Talk talk = new Talk();
        talk.setSourceUserId(sourceUserId);
        talk.setTargetUserId(targetUserId);
        talk.setText(text);
        talkRepository.save(talk);
    }
}
