package slvtwn.khu.toyouserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolling_paper_id")
    private RollingPaper rollingPaper;

    private String imageUrl;

    private Integer x;
    private Integer y;

    private Double rotate;
    private Double scale;

    @Enumerated(value = EnumType.STRING)
    private StickerSide side;

    public Sticker(RollingPaper rollingPaper, String imageUrl, Integer x, Integer y,
                   Double rotate, Double scale, StickerSide side) {
        this.rollingPaper = rollingPaper;
        this.imageUrl = imageUrl;
        this.x = x;
        this.y = y;
        this.rotate = rotate;
        this.scale = scale;
        this.side = side;
    }
}
