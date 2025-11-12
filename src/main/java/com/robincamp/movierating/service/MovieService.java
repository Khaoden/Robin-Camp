package com.robincamp.movierating.service;

import com.robincamp.movierating.client.BoxOfficeClient;
import com.robincamp.movierating.dto.*;
import com.robincamp.movierating.entity.*;
import com.robincamp.movierating.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
    private final BoxOfficeClient boxOfficeClient;

    public MovieService(MovieRepository movieRepository, BoxOfficeClient boxOfficeClient) {
        this.movieRepository = movieRepository;
        this.boxOfficeClient = boxOfficeClient;
    }

    @Transactional
    public MovieResponse createMovie(MovieCreateRequest request) {
        if (movieRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Movie with title already exists");
        }

        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setGenre(request.getGenre());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setDistributor(request.getDistributor());
        movie.setBudget(request.getBudget());
        movie.setMpaRating(request.getMpaRating());

        movie = movieRepository.save(movie);

        BoxOfficeRecord boxOfficeRecord = boxOfficeClient.fetchBoxOfficeData(request.getTitle());
        if (boxOfficeRecord != null) {
            BoxOfficeData boxOfficeData = new BoxOfficeData();

            if (boxOfficeRecord.getRevenue() != null) {
                Revenue revenue = new Revenue();
                revenue.setWorldwide(boxOfficeRecord.getRevenue().getWorldwide());
                revenue.setOpeningWeekendUSA(boxOfficeRecord.getRevenue().getOpeningWeekendUSA());
                boxOfficeData.setRevenue(revenue);
            }

            boxOfficeData.setCurrency("USD");
            boxOfficeData.setSource("ExampleBoxOfficeAPI");
            boxOfficeData.setLastUpdated(Instant.now());

            if (movie.getDistributor() == null && boxOfficeRecord.getDistributor() != null) {
                movie.setDistributor(boxOfficeRecord.getDistributor());
            }
            if (movie.getBudget() == null && boxOfficeRecord.getBudget() != null) {
                movie.setBudget(boxOfficeRecord.getBudget());
            }
            if (movie.getMpaRating() == null && boxOfficeRecord.getMpaRating() != null) {
                movie.setMpaRating(boxOfficeRecord.getMpaRating());
            }

            movie.setBoxOffice(boxOfficeData);
            movie = movieRepository.save(movie);
        }

        return toMovieResponse(movie);
    }

    @Transactional(readOnly = true)
    public MoviePageResponse searchMovies(String q, Integer year, String genre,
            String distributor, Long budget,
            String mpaRating, Integer limit, String cursor) {
        int pageSize = limit != null && limit > 0 ? limit : 20;
        int pageNumber = 0;

        if (cursor != null && !cursor.isEmpty()) {
            try {
                String decoded = new String(Base64.getDecoder().decode(cursor), StandardCharsets.UTF_8);
                String offsetStr = decoded.replaceAll(".*offset.*?([0-9]+).*", "$1");
                pageNumber = Integer.parseInt(offsetStr) / pageSize;
            } catch (Exception e) {
                logger.warn("Invalid cursor: {}", cursor);
            }
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movie> page = movieRepository.searchMovies(q, year, genre, distributor, budget, mpaRating, pageable);

        List<MovieResponse> items = page.getContent().stream()
                .map(this::toMovieResponse)
                .collect(Collectors.toList());

        MoviePageResponse response = new MoviePageResponse();
        response.setItems(items);

        if (page.hasNext()) {
            int nextOffset = (pageNumber + 1) * pageSize;
            String nextCursor = Base64.getEncoder().encodeToString(
                    ("{\"offset\":" + nextOffset + "}").getBytes(StandardCharsets.UTF_8));
            response.setNextCursor(nextCursor);
        }

        return response;
    }

    @Transactional(readOnly = true)
    public MovieResponse getMovieByTitle(String title) {
        return movieRepository.findByTitle(title)
                .map(this::toMovieResponse)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    private MovieResponse toMovieResponse(Movie movie) {
        MovieResponse response = new MovieResponse();
        response.setId("m_" + movie.getId());
        response.setTitle(movie.getTitle());
        response.setReleaseDate(movie.getReleaseDate());
        response.setGenre(movie.getGenre());
        response.setDistributor(movie.getDistributor());
        response.setBudget(movie.getBudget());
        response.setMpaRating(movie.getMpaRating());

        if (movie.getBoxOffice() != null) {
            BoxOfficeResponse boxOfficeResponse = new BoxOfficeResponse();
            BoxOfficeData boxOffice = movie.getBoxOffice();

            if (boxOffice.getRevenue() != null) {
                RevenueResponse revenueResponse = new RevenueResponse();
                revenueResponse.setWorldwide(boxOffice.getRevenue().getWorldwide());
                revenueResponse.setOpeningWeekendUSA(boxOffice.getRevenue().getOpeningWeekendUSA());
                boxOfficeResponse.setRevenue(revenueResponse);
            }

            boxOfficeResponse.setCurrency(boxOffice.getCurrency());
            boxOfficeResponse.setSource(boxOffice.getSource());
            boxOfficeResponse.setLastUpdated(boxOffice.getLastUpdated());
            response.setBoxOffice(boxOfficeResponse);
        }

        return response;
    }
}
