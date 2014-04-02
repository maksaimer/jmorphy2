package net.uaprom.jmorphy2;

import java.io.InputStream;
import java.io.IOException;

import net.uaprom.dawg.IntegerDAWG;


public class ProbabilityEstimator {
    public static final String PROBABILITY_FILENAME = "p_t_given_w.intdawg";
    public static final String KEY_FORMAT = "%s:%s";
    public static final float MULTIPLIER = 1000000f;

    private IntegerDAWG dict;

    public ProbabilityEstimator(MorphAnalyzer.Loader loader) throws IOException {
        this(loader.getStream(PROBABILITY_FILENAME));
    }
    
    public ProbabilityEstimator(InputStream stream) throws IOException {
        dict = new IntegerDAWG(stream);
    }

    public float getProbability(String word, Tag tag) throws IOException {
        String key = String.format(KEY_FORMAT, word, tag);
        return dict.get(key, 0) / MULTIPLIER;
    }
}
