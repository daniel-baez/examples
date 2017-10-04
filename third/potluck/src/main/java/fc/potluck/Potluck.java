package fc.potluck;

import fc.potluck.model.Participant;

import java.util.*;
import java.util.stream.Collectors;

public final class Potluck {

    private LinkedHashSet<Participant> participants;

    public Potluck(List<Participant> participants) {
        this.participants = participants == null ? new LinkedHashSet<>() : participants.stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LinkedHashSet<Participant> getParticipants() {
        return new LinkedHashSet<>(participants);
    }

}
