package fc.potluck;

import fc.potluck.model.FoodItem;
import fc.potluck.model.Participant;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class PotluckPlanner {
    /**
     * Allocates expenses among the potluck participants that are over 18. If byob is true,
     * then alcohol related expenses are not split.
     */
    public void splitCosts(Potluck potluck, boolean byob, List<String> _attendanceRoster) {
        final Set<String> attendanceRoster = new HashSet<>(_attendanceRoster);

        final List<Participant> attendedParticipants = potluck.getParticipants()
                .stream()
                .filter(it -> attendanceRoster.contains(it.getName()))
                .filter(it -> !isUnderAge(it))
                .collect(toList());

        final long splittingParticipantsCount = attendedParticipants.size();

        final Map<Participant, Double> contributionsByParticipant = attendedParticipants
                .stream()
                .collect(toMap(identity(), participant -> participant.getContribution(byob)));

        final double evenSplit = contributionsByParticipant.values()
                .stream()
                .mapToDouble(it -> it / splittingParticipantsCount).sum();

        for (Participant participant : attendedParticipants) {
            final double giveOrTake = contributionsByParticipant.get(participant) - evenSplit;
            System.out.printf("%s: %s\n", participant.getName(), giveOrTake);
            System.out.flush();
        }
    }

    private boolean isUnderAge(final Participant participant) {
        return participant.getAge() < 18;
    }
}
