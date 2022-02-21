package xyz.cryptohows.backend.round.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    private String announcedDate;
    private String moneyRaised;

    @Enumerated(EnumType.STRING)
    private FundingStage fundingStage;

    @OneToMany(mappedBy = "round")
    private List<RoundParticipation> participants = new ArrayList<>();

    @Builder
    public Round(Project project, String announcedDate, String moneyRaised, FundingStage fundingStage) {
        this.project = project;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.fundingStage = fundingStage;
    }

    public void makeParticipation(VentureCapital ventureCapital) {
        RoundParticipation roundParticipation = new RoundParticipation(ventureCapital, this);
        participants.add(roundParticipation);
    }

    public void makeParticipations(List<VentureCapital> ventureCapitals) {
        for (VentureCapital ventureCapital : ventureCapitals) {
            makeParticipation(ventureCapital);
        }
    }

    public List<VentureCapital> getParticipatedVC() {
        return participants.stream()
                .map(RoundParticipation::getVentureCapital)
                .collect(Collectors.toList());
    }

    public boolean isSameProject(Project project) {
        return this.project.equals(project);
    }
}