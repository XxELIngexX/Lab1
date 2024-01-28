package edu.escuelaing.arem.ASE.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;


public class MovieServiceTest extends TestCase {

    private HttpConnectionLab1 ApiConnection = new HttpConnectionLab1();

    public MovieServiceTest(String httpConnectionLab1) {
        super(httpConnectionLab1);
    }

    public static Test suite() {
        return new TestSuite(MovieServiceTest.class);
    }

    public void testNonExistMovie() {
        StringBuffer wrongRequest;
        try {
            wrongRequest = ApiConnection.getMovie("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("{\"Response\":\"False\",\"Error\":\"Incorrect IMDb ID.\"}", wrongRequest.toString());
    }

    public void testExistentMovie() {
        StringBuffer correctRequest;
        try {
            correctRequest = ApiConnection.getMovie("Avengers");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("{\"Title\":\"The Avengers\",\"Year\":\"2012\",\"Rated\":\"PG-13\",\"Released\":\"04 May 2012\",\"Runtime\":\"143 min\",\"Genre\":\"Action, Sci-Fi\",\"Director\":\"Joss Whedon\",\"Writer\":\"Joss Whedon, Zak Penn\",\"Actors\":\"Robert Downey Jr., Chris Evans, Scarlett Johansson\",\"Plot\":\"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.\",\"Language\":\"English, Russian\",\"Country\":\"United States\",\"Awards\":\"Nominated for 1 Oscar. 38 wins & 81 nominations total\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.0/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"91%\"},{\"Source\":\"Metacritic\",\"Value\":\"69/100\"}],\"Metascore\":\"69\",\"imdbRating\":\"8.0\",\"imdbVotes\":\"1,445,496\",\"imdbID\":\"tt0848228\",\"Type\":\"movie\",\"DVD\":\"22 Jun 2014\",\"BoxOffice\":\"$623,357,910\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}", correctRequest.toString());
    }
}
