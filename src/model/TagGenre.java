package model;

public enum TagGenre
{
    ANIMATION("Animation"),
    BABY("Baby"),
    BLUES_JAZZ("Blues Jazz"),
    CLASSICAL("Classical"),
    DISCO_FUNK("Disco Funk"),
    ELECTRO_LOUNGE("Electro Lounge"),
    FRENCH("French"),
    HARD_ROCK("Hard Rock"),
    INSTRUMENTAL("Instrumental"),
    LATINA("Latina"),
    MISC("Misc"),
    OLDIES_RETRO("Oldies Retro"),
    POP_DANCE("Pop Dance"),
    SOFT_ROCK("Soft Rock"),
    SOUL("Soul"),
    SOUNDTRACK("Soundtrack"),
    WORLD_MUSIC("World Music");

    protected String label;

    TagGenre(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }

    public static boolean exist(String genre) {
        for (TagGenre tagGenre : TagGenre.values()) {
            if (tagGenre.getLabel().equals(genre)) {
                return true;
            }
        }

        return false;
    }
}
