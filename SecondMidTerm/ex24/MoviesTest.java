package mk.kolokvium2.ex24;

import java.util.*;
import java.util.stream.Collectors;

class Movie {
    public String title;
    public List<Integer> ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = new ArrayList<>();
        for (int rating : ratings) {
            this.ratings.add(rating);
        }
    }

    public String getTitle() {
        return title;
    }

    public double averageRatings() {
        return (double) ratings.stream().mapToInt(x -> x).sum() / ratings.size();
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", title, averageRatings(), ratings.size());
    }

    public double coefOfMovie() {
        return averageRatings() * ratings.size();
    }
}

class MoviesList {
    public List<Movie> movieList;

    public MoviesList() {
        this.movieList = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings) {
        movieList.add(new Movie(title, ratings));
    }

    public List<Movie> top10ByAvgRating() {
        return movieList.stream().sorted(Comparator.comparing(Movie::averageRatings).reversed()).limit(10).collect(Collectors.toList());
    }


    public List<Movie> top10ByRatingCoef() {
        movieList.sort(Comparator.comparing(movie -> movie.coefOfMovie() / totalRatingsOfAllFilms()));
        Collections.reverse(movieList);
        return movieList.stream().limit(10).collect(Collectors.toList());
    }

    public double totalRatingsOfAllFilms() {
        return movieList.stream().mapToInt(x -> x.ratings.size()).max().orElse(1);
    }

}


public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde
