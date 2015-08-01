package net.uaprom.jmorphy2;

import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class ParsedWord implements Comparable {
    public static final float EPS = 1e-6f;

    public final String word;
    public final Tag tag;
    public final String normalForm;
    public final String foundWord;
    public final float score;

    public ParsedWord(String word, Tag tag, String normalForm, String foundWord, float score) {
        this.word = word;
        this.tag = tag;
        this.normalForm = normalForm;
        this.foundWord = foundWord;
        this.score = score;
    }

    public abstract ParsedWord rescore(float newScore);

    public abstract List<ParsedWord> getLexeme();

    public List<ParsedWord> inflect(Collection<Grammeme> requiredGrammemes) {
        return inflect(requiredGrammemes, null);
    }

    public List<ParsedWord> inflect(Collection<Grammeme> requiredGrammemes, Collection<Grammeme> excludeGrammemes) {
        List<ParsedWord> paradigm = new ArrayList<ParsedWord>();
        for (ParsedWord p : getLexeme()) {
            if (p.tag.containsAll(requiredGrammemes) && !p.tag.containsAny(excludeGrammemes)) {
                paradigm.add(p);
            }
        }
        return paradigm;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }

        ParsedWord other = (ParsedWord) obj;
        return word.equals(other.word)
            && tag.equals(other.tag)
            && normalForm.equals(other.normalForm)
            && Math.abs(score - other.score) < EPS;
    }

    @Override
    public String toString() {
        return String.format("<ParsedWord: \"%s\", \"%s\", \"%s\", \"%s\", %.6f>", word, tag, normalForm, foundWord, score);
    }

    @Override
    public int compareTo(Object obj) {
        ParsedWord other = (ParsedWord) obj;
        if (score > other.score) return 1;
        if (score < other.score) return -1;
        return 0;
    }
}
