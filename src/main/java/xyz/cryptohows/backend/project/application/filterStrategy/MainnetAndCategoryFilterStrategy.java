package xyz.cryptohows.backend.project.application.filterStrategy;

import org.springframework.data.domain.Pageable;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

import java.util.List;

public class MainnetAndCategoryFilterStrategy extends FilterStrategy {

    public MainnetAndCategoryFilterStrategy(ProjectRepository projectRepository, RoundRepository roundRepository) {
        super(projectRepository, roundRepository);
    }

    @Override
    public List<Round> findRounds(String order, Integer page, Integer roundsPerPage, List<Mainnet> mainnets, List<Category> categories) {
        Pageable pageable = generateRoundPageable(order, page, roundsPerPage);
        return roundRepository.findRoundsFilterMainnetAndCategory(pageable, mainnets, categories);
    }

    @Override
    public Long countAllRounds(List<Mainnet> mainnets, List<Category> categories) {
        return roundRepository.countRoundsFilterMainnetAndCategory(mainnets, categories);
    }

    @Override
    public List<Project> findProjects(Integer page, Integer projectsPerPage, List<Mainnet> mainnets, List<Category> categories) {
        Pageable pageable = generateProjectPageable(page, projectsPerPage);
        return projectRepository.findProjectsFilterMainnetAndCategory(pageable, mainnets, categories);
    }

    @Override
    public Long countAllProjects(List<Mainnet> mainnets, List<Category> categories) {
        return projectRepository.countProjectsFilterMainnetAndCategory(mainnets, categories);
    }
}
