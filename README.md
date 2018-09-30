# TheMovieDb
This sample app uses [The Movie Database API](https://www.themoviedb.org/documentation/api) (TMDB) API to demostrate many topics related to Android Development.

It is written in **Kotlin** following the principles of **Clean Architecture**. 

The Presentation layer implemented using Model-View-ViewModel (**MVVM**) and **LiveData** and **Room** is used for persistence.

**Dagger 2** with Android support is used for dependency injection.

The data and domain classes and ViewModel are tested using JUnit and dependencies are mocked using **Mockito**.

Other tools/concepts:
- [ktlin](https://github.com/shyiko/ktlint) and [detekt](https://github.com/arturbosch/detekt) for static code analysis
- RxJava2


## NOTE

Before running the app, you will need to replace the `THE_MOVIE_DB_API_KEY` in the `gradle.properties` file with your own TMDB API key, otherwise API calls will fail.
