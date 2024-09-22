# Toyou-mermaid ERD

```mermaid
erDiagram
    User {
        Long id
        String name
        LocalDate birthday
        String introduction
        String profilePicture
        SocialAuthProvider provider
    }

    Sticker {
        Long id
        String imageUrl
        Integer x
        Integer y
        Double rotate
        Double scale
        StickerSide side
    }

    RollingPaper {
        Long id
        String title
        String content
        String coverImageUrl
        Long themeId
    }

    Member {
        Long id
    }

    Group {
        Long id
        String name
    }

    Event {
        Long id
        String name
        LocalDate date
        EventType eventType
        String description
    }

    User ||--o{ Member : has
    Member ||--o{ Group : belongs_to
    RollingPaper ||--o{ Sticker : has
    RollingPaper ||--o| Member : belongs_to
    Member ||--o| Group : belongs_to
    Sticker ||--o| RollingPaper : belongs_to
    Event ||--o| User : associated_with

```

### Details

- `USER`: 사용자 정보
- `GROUP`: 그룹 (사용자들의 모임)
- `MEMBER`: 그룹에 속한 사용자
- `ROLLING_PAPER`: 그룹 내에서 공유되는 메시지
- `COMMENT`: 롤링페이퍼에 작성된 댓글
