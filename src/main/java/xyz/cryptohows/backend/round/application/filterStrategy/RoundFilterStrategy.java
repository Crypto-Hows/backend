package xyz.cryptohows.backend.round.application.filterStrategy;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

import java.util.List;

public abstract class RoundFilterStrategy {

    protected final RoundRepository roundRepository;

    protected RoundFilterStrategy(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    protected Pageable generatePageable(String order, Integer page, Integer roundsPerPage) {
        if ("old".equals(order)) {
            return PageRequest.of(page, roundsPerPage, Sort.by("announcedDate").ascending());
        }
        return PageRequest.of(page, roundsPerPage, Sort.by("announcedDate").descending());
    }

    public abstract List<Round> findRounds(String order, Integer page, Integer roundsPerPage, Mainnet mainnet, Category category);

    public abstract Long count(Mainnet mainnet, Category category);
}