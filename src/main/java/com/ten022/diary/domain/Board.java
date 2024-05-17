package com.ten022.diary.domain;

import com.ten022.diary.domain.enumtype.Categories;
import com.ten022.diary.domain.enumtype.Emotions;
import com.ten022.diary.domain.enumtype.Factors;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Enumerated(value = EnumType.STRING)
    private Categories categories;

    @Enumerated(value = EnumType.STRING)
    private Emotions emotions;

    @Enumerated(value = EnumType.STRING)
    private Factors factors;

    private Long satisfaction;

    private LocalDate date;

    public void setMember(Member member) {
        this.member = member;
        member.getBoard().add(this);
    }

    public Long updateBoard(Storage storage, String categories, String emotions, String factors, Long satisfaction, String contents, LocalDate date) {
        this.storage = storage;
        this.categories = Categories.valueOf(categories);
        this.emotions = Emotions.valueOf(emotions);
        this.factors = Factors.valueOf(factors);
        this.satisfaction = satisfaction;
        this.contents = contents;
        this.date = date;

        return this.id;
    }

}
